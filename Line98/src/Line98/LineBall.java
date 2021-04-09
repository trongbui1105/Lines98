package Line98;

import java.awt.*;
import java.awt.List;

import javax.swing.*;

import Line98.LineBall.Point;

import java.awt.event.*;
import java.util.*;

public class LineBall {
	public Line98 line;
	
	
	public class Point {
		public int x, y;
		public Point(int x1, int y1) {
			x = x1;
			y = y1;
		}
	}
	
	public final int maxCell = 9; 
    public final int maxColor = 5;
    public int ball[][]= new int[maxCell][maxCell];
    public int ballTemp[][] = new int[maxCell][maxCell];
    public ArrayList<Point> pathBall = new ArrayList<>();
	public ArrayList<Point> listPathBall;
	public ArrayList<Point> listCutBall;
	public int nextColor[] = new int[3];
	public int nextColorTemp[] = new int[3];
	public int numOfCountPath;
	public double totalResult, totalResultTemp;
	boolean gameOver;
	public Icon icon = new ImageIcon();
	
	
	public LineBall() {
		
	}
	
	public void startGame() {
		for (int i = 0; i < maxCell; i++) {
			for (int j = 0; j < maxCell; j++) {
				ball[i][j] = 0;
			}
		}
		
		totalResult = 0;
		gameOver = false;
		
		int i, j;
		Point freeCell[] = new Point[3];
		Random random = new Random();
		
		// đặt vào 3 quả bóng lớn
		if (randomBall(3, freeCell)) {
			for (int k = 0; k < 3; k++) {
				i = freeCell[k].x;
				j = freeCell[k].y;
				ball[i][j] = random.nextInt(maxColor) + 1;
			}
		} else {
			System.out.println("Game Over !!!");
		}
		
		// đặt vào 3 quả bóng nhỏ
		if (randomBall(3, freeCell)) {
			for (int k = 0; k < 3; k++) {
				i = freeCell[k].x;
				j = freeCell[k].y;
				ball[i][j] = random.nextInt(maxColor) + maxColor + 1;
			}
		} else {
			System.out.println("Game Over !!!");
		}
		
		for (i = 0; i < maxCell; i++) {
			for (j = 0; j < maxCell; j++) {
				ballTemp[i][j] = ball[i][j];
			}
		}
		new3Colors();
		for (int k = 0; k < 3; k++) {
			nextColorTemp[k] = nextColor[k];
		}
	}
	
	public boolean randomBall(int numOfBall, Point[] resultBall) {
		int countFreeBall = 0; // số ô còn trống
		Point checkCell[] = new Point[maxCell * maxCell]; // lưu các ô còn trống
		boolean boolCheckCell[] = new boolean[maxCell * maxCell]; // đánh dấu các ô đã có bóng
		
		for (int i = 0; i < maxCell; i++) {
			for (int j = 0; j < maxCell; j++) {
				if (ball[i][j] == 0) { // nếu ô chưa có bóng
					checkCell[countFreeBall] = new Point(i, j);
					boolCheckCell[countFreeBall] = true;
					countFreeBall++;
				} else {
					boolCheckCell[countFreeBall] = false;
				}
			}
		}
		
		if (countFreeBall < numOfBall) {
			return false;
		}
		
		Random random = new Random();
		int x;
		int count = 0;
		while (count < numOfBall) {
			x = random.nextInt(countFreeBall); // chọn ngẫu nhiên 1 ô trống
			if (boolCheckCell[x]) {
				boolCheckCell[x] = false;
				resultBall[count++] = checkCell[x];
			}
		}	
		return true;
	}
	
	// next 3 colors
	public void new3Colors() {
		Random random = new Random();
		for (int i = 0; i < 3; i++) {
			nextColor[i] = random.nextInt(maxColor) + 1;
		}
	}
	
	
	// next 3 balls
	public void new3Balls() {
		for (int i = 0; i < maxCell; i++) {
			for (int j = 0; j < maxCell; j++) {
				if (ball[i][j] > maxColor) {
					ball[i][j] -= maxColor;   
				}
			}
		}
		
		int i, j;
		Point freeCell[] = new Point[3];
		Random random = new Random();
		if (randomBall(3, freeCell)) {
			for (int k = 0; k < 3; k++) {
				i = freeCell[k].x;
				j = freeCell[k].y;
				
				ball[i][j] = nextColor[k] + maxColor;
			}
		} else {
			gameOver = true;
		}
		
		new3Colors();
	}
	
	
	//Step back
	public void undo() {
		for (int i = 0; i < maxCell; i++) {
			for (int j = 0; j < maxCell; j++) {
				ball[i][j] = ballTemp[i][j];
			}
		}
		
		for (int k = 0; k < 3; k++) {
			nextColor[k] = nextColorTemp[k];
		}
		
		totalResult = totalResultTemp;
	}
		
	public void saveUndo() {
		for (int i = 0; i < maxCell; i++) {
			for (int j = 0; j < maxCell; j++) {
				if (ball[i][j] > 2 * maxColor) {
					ballTemp[i][j] = ball[i][j] - 2 * maxColor;
				} else {
					ballTemp[i][j] = ball[i][j];
				}
			}
		}
		
		for (int k = 0; k < 3; k++) {
			nextColorTemp[k] = nextColor[k];
		}
		
		totalResultTemp = totalResult;
	}
	
	public boolean cutBall() {
		int numOfCutBall = 0;
		int numOfBall;
		boolean checkBall[][] = new boolean[maxCell][maxCell];
		Point[] tempBall = new Point[maxCell];
		Point[] cellBall = new Point[maxCell * maxCell];
		int i, j, numOfRow, numOfCol, numOfCount;
		
		for (i = 0; i < maxCell; i++) {
			for (j = 0; j < maxCell; j++) {
				checkBall[i][j] = false;
			}
		}
		
		for (numOfRow = 0; numOfRow < maxCell; numOfRow++) {
			for (numOfCol = 0; numOfCol < maxCell; numOfCol++) {
				if (ball[numOfRow][numOfCol] > 0 && !checkBall[numOfRow][numOfCol]) {
					numOfBall = ball[numOfRow][numOfCol];
					
					// check vertical
					i = numOfRow;
					j = numOfCol;
					while(i > 0 && ball[i-1][j] == numOfBall) {
						i--;
					}
					numOfCount = 0;
					while(i < maxCell && ball[i][j] == numOfBall) {
						checkBall[i][j] = true;
						tempBall[numOfCount++] = new Point(i, j);
						i++;
					}
					
					if (numOfCount >= 5) {
						for (i = 0; i < numOfCount; i++) {
							cellBall[numOfCutBall++] = tempBall[i];
						}
						totalResult += (numOfCount - 4) * numOfCount;
					}
					
					// check horizontal
					i = numOfRow;
					j = numOfCol;
					while (j > 0 && ball[i][j-1] == numOfBall) {
						j--;
					}
					numOfCount = 0;
					while (j < maxCell && ball[i][j] == numOfBall) {
						checkBall[i][j] = true;
						tempBall[numOfCount++] = new Point(i, j);
						j++;
					}
					if (numOfCount >= 5) {
						for (i = 0; i < numOfCount; i++) {
							cellBall[numOfCutBall++] = tempBall[i];
						}
						totalResult += (numOfCount - 4) * numOfCount;
					}
					
					// check left diagonal
					i = numOfRow;
					j = numOfCol;
					while (i > 0 && j > 0 && ball[i-1][j-1] == numOfBall) {
						i--;
						j--;
					}
					numOfCount = 0;
					while (i < maxCell && j < maxCell && ball[i][j] == numOfBall) {
						checkBall[i][j] = true;
						tempBall[numOfCount++] = new Point(i, j);
						i++;
						j++;
					}
					if (numOfCount >= 5) {
						for (i = 0; i < numOfCount; i++) {
							cellBall[numOfCutBall++] = tempBall[i];
						}
						totalResult += (numOfCount - 4) * numOfCount;
					}
					
					// check right diagonal
					i = numOfRow;
					j = numOfCol;
					while (i > 0 && j + 1 < maxCell && ball[i-1][j+1] == numOfBall) {
						i--;
						j++;
					}
					numOfCount = 0;
					while (i < maxCell && j >= 0 && ball[i][j] == numOfBall) {
						checkBall[i][j] = true;
						tempBall[numOfCount++] = new Point(i, j);
						i++;
						j--;
					}
					if (numOfCount >= 5) {
						for (i = 0; i < numOfCount; i++) {
							cellBall[numOfCutBall++] = tempBall[i];
						}
						totalResult += (numOfCount - 4) * numOfCount;
					}
				}
			}
		}
		
		listCutBall = new ArrayList<>();
		for (i = 0; i < numOfCutBall; i++) {
//			ball[cellBall[i].x][cellBall[i].y] = 0;
			listCutBall.add(cellBall[i]);
		}

		if (numOfCutBall > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	// save path
	public void findPath(Point p, Point [][] pathBallTemp) {
		pathBall = new ArrayList<Point>();
		if (p.x != -1 && p.y != -1) {
			if (pathBallTemp[p.x][p.y] != new Point(-1, -1)) {
				findPath(pathBallTemp[p.x][p.y], pathBallTemp);
			}
		}
		pathBall.add(p);
	}
	
	// tìm đường đi ngắn nhất từ (si, sj) -> (fi, fj)
	public boolean BFS(int si, int sj, int fi, int fj) {
		int []di = {-1, 1, 0, 0};
		int []dj = {0, 0, -1, 1};
		int i, j, k, numOfCount;
		Point pStart, pFinish, pCurrent;
		Point [][] query = new Point[2][maxCell * maxCell];
		Point [][] pathBallTemp = new Point[maxCell][maxCell];

		boolean [][] ballCheck = new boolean[maxCell][maxCell];
		
		pStart = new Point(si, sj);
		pFinish = new Point(fi, fj);
		// cho pstart vào hàng đợi
		int numOfQuery = 1;
		query[0][0] = pStart;
		
		// đánh dấu các ô đã có bóng
		for (i = 0; i < maxCell; i++) {
			for (j = 0; j < maxCell; j++) {
				if (ball[i][j] > 0 & ball[i][j] < 6) {
					ballCheck[i][j] = true;
				} else {
					ballCheck[i][j] = false;
				}
			}
		}
		ballCheck[pStart.x][pStart.y] = true;
		if (ballCheck[fi][fj]) {
			return false;
		}
		// BFS tìm đường đi
		pathBallTemp[si][sj] = new Point(-1, -1);
		while (numOfQuery > 0) {
			numOfCount = 0;
			for (int nLast = 0; nLast < numOfQuery; nLast++) {
				pCurrent = query[0][nLast];
				
				// tìm xung quanh 4 hướng của ô (i, j) xem có hướng nào đi được hay không ?
				for (k = 0; k < 4; k++) {
					i = pCurrent.x + di[k];
					j = pCurrent.y + dj[k];
					if (i >= 0 && i < maxCell && j >= 0 && j < maxCell && !ballCheck[i][j]) {
						query[1][numOfCount++] = new Point(i, j);
						ballCheck[i][j] = true;
						pathBallTemp[i][j] = new Point(pCurrent.x, pCurrent.y); 
						// tìm thấy ô đích thì ngưng tìm kiếm
						if (ballCheck[fi][fj]) {
							findPath(new Point(fi, fj), pathBallTemp);
							return true;
						}
					}
				}
			}
			// bỏ query[1] vào query[0] để tiếp tục tìm kiếm BFS
			for (k = 0; k < numOfCount; k++) {
				query[0][k] = query[1][k];
			}
			numOfQuery = numOfCount;
			
		}
		return false;
	}
	
	
	public void showPath() {
		for(int i = 0; i < pathBall.size(); i++) {
			System.out.println(pathBall.get(i).x + " " + pathBall.get(i).y);
		}
	}

	public ArrayList<Point> getPathBall() {
		return pathBall;
	}

	public void setPathBall(ArrayList<Point> pathBall) {
		this.pathBall = pathBall;
	}
	
	public ArrayList<Point> getListCutBall() {
		return listCutBall;
	}

	public void setListCutBall(ArrayList<Point> listCutBall) {
		this.listCutBall = listCutBall;
	}

}














