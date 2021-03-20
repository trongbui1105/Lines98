package Line98;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.sql.SQLException;

public class Line98 extends JFrame {
	
	public LineBall lineBall = new LineBall();
	public JFrame gameOver = new JFrame(" GameOver !");
	public Player player = new Player();
	public int numOfPlayer = player.countPlayer();
	public TopScores[] topScores = new TopScores[numOfPlayer];
	public Icon icon[] = new Icon[22];
	public JButton button[][] = new JButton[9][9];
	public JMenuItem nextBall[] = new JMenuItem[3];
	public JMenuItem score = new JMenuItem("0 "); 
	public int x = -1, y = -1;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					Line98 frame = new Line98();
//					frame.startGame();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public Line98() {
		icon[1] = new ImageIcon("src/Images/big1.png");   
    	icon[2] = new ImageIcon("src/Images/big2.png");
    	icon[3] = new ImageIcon("src/Images/big3.png");
    	icon[4] = new ImageIcon("src/Images/big4.png");
    	icon[5] = new ImageIcon("src/Images/big5.png");
    	
    	icon[6] = new ImageIcon("src/Images/small1.png");
    	icon[7] = new ImageIcon("src/Images/small2.png");
    	icon[8] = new ImageIcon("src/Images/small3.png");
    	icon[9] = new ImageIcon("src/Images/small4.png");
    	icon[10] = new ImageIcon("src/Images/small5.png");
    	
    	icon[11] = new ImageIcon("src/Images/d1.gif");
		icon[12] = new ImageIcon("src/Images/d2.gif");
		icon[13] = new ImageIcon("src/Images/d3.gif");
		icon[14] = new ImageIcon("src/Images/d4.gif");
		icon[15] = new ImageIcon("src/Images/d5.gif");
		
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				button[i][j]=new JButton(icon[0]);
			}
		}
		
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				add(button[i][j]);
			}
		}
		
		x = -1;
		y = -1;
		
		setMenu();
		setButton();
		setTitle("Lines 98");
		setLayout(new GridLayout(9,9));
		setSize(600,650);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	public void setMenu() {
		JMenuBar menuBar = new JMenuBar();
		menuBar.setLayout(new GridLayout(1,7));
		setJMenuBar(menuBar);
		
		JMenu gameMenu = new JMenu("Game");
		menuBar.add(gameMenu);
		
		JMenuItem newGameItem = new JMenuItem("New Game"); //them vao muc New
		newGameItem.setToolTipText("New Game selected");
		newGameItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				startGame();
			}
		});
		gameMenu.add(newGameItem);
		
		JMenuItem saveGameItem = new JMenuItem("Save Game");
		saveGameItem.setToolTipText("Save Game selected");
		saveGameItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					saveGame();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		gameMenu.add(saveGameItem);
		
		JMenuItem exitItem=new JMenuItem("Exit");//them vao muc exit
		exitItem.setToolTipText("Exit selected");
		exitItem.addActionListener(new ActionListener(){		
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		});
		gameMenu.add(exitItem);
		
		JMenu gameUtilities = new JMenu("Utilities");
		menuBar.add(gameUtilities);
		
		JMenuItem topScoresItem=new JMenuItem("TopScores");//them vao muc TopScores
		topScoresItem.setToolTipText("TopScores selected");
		topScoresItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					topScores.showTopScores();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		gameUtilities.add(topScoresItem);
		
		JMenuItem undoItem=new JMenuItem("Step Back"); //them vao muc StepBack
		undoItem.setToolTipText("Step Back selected ");
		undoItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				stepBack();
			}
		});
		gameUtilities.add(undoItem);
		
		for(int i = 0; i < 3; i++){
	    	nextBall[i]= new JMenuItem();
	    	menuBar.add(nextBall[i]);
	    }
	    			
	    Icon scoreIcon = new ImageIcon("src/Images/score.png");
	    score.setIcon(scoreIcon);
		menuBar.add(score);
	}
	
	public void stepBack() {
		lineBall.undo();
		displayNextBall();
		score.setText((int) lineBall.totalResult + " ");
		drawBall();
	}
	
	public void drawBall() {
		for (int i = 0; i < 9; i++) {	
			for (int j = 0; j < 9; j++) {
				button[i][j].setIcon(icon[lineBall.ball[i][j]]);
			}
		}
	}
	
	public void displayNextBall(){
		for(int i = 0; i < 3; i++){
	    	nextBall[i].setIcon(icon[lineBall.nextColor[i]]);
	    }		
	}
	
	public void bounceBall() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (lineBall.ball[i][j] > 10) {
					lineBall.ball[i][j] -= 10;
				}
			}
		}
		
		if (x >= 0 && y >= 0) {
			lineBall.ball[x][y] += 10;
		}
		drawBall();
	}
	
	
	public void setButton() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				button[i][j].addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent ae) {
						// TODO Auto-generated method stub
						for (int i = 0; i < 9; i++) {
							for (int j = 0; j < 9; j++) {
								if (ae.getSource() == button[i][j]) {
									Icon n = button[i][j].getIcon();
									if (x != i && y != j && (n == icon[1] || n == icon[2] ||
										n == icon[3] || n == icon[4] ||n == icon[5])) {
										x = i;
										y = j;
									} else if (x == i && y == j) {
										x = y = -1;
									} else if (x > -1 && y > -1 && (n == icon[0] || n == icon[6] ||
											n == icon[7] || n == icon[8] ||n == icon[9] ||
											n == icon[10])) {
										if (lineBall.BFS(x, y, i, j)) {
											lineBall.saveUndo(); // lưu lại trạng thái trước khi di chuyển
											try {
												moveBall(x, y, i, j);
											} catch (Exception e) {
												// TODO: handle exception
											}
											drawBall();
											if (!lineBall.cutBall()) {
												lineBall.new3Balls();
											}
											lineBall.cutBall();
											displayNextBall();
											drawBall();
											x = y = -1;
										}
									}
									bounceBall();
									player.scores=(int) lineBall.totalResult;
									score.setText((int) lineBall.totalResult + "  ");
									try {
										stopGame();
									} catch (IOException e) {
										// TODO: handle exception
									}
								}
							}
						}
					}
				});
			}
		}
	}
	
	public void moveBall(int i, int j, int i1, int j1) throws Exception {
		lineBall.ball[i1][j1] = lineBall.ball[i][j] - 10;
		lineBall.ball[i][j] = 0;
		for (int k = 0; k < 16; k++) {
			if (button[i][j].getIcon() == icon[k]) {
				button[i1][j1].setIcon(icon[k - 10]);
			}
			button[i][j].setIcon(icon[0]);
		}
	}
	
	
	public void startGame() {
		lineBall.startGame();
		score.setText("0");		
		displayNextBall();
		drawBall();
		x = y = -1;
	
	}
	
	public void stopGame() throws IOException {
		if (lineBall.gameOver) {
//			topScores.readFile();
			boolean checkPoint = false;
			for (int i = 0; i < 10; i++) {
				if (topScores.player[i].scores < player.scores) {
					checkPoint = true;
					break;
				}
			}
			
			if (checkPoint) {
//				player.setName();
//				topScores.add(player);
				topScores.showTopScores();
				startGame();
			} else {
				gameOver = new JFrame("Game Over !!!");
				JButton msg1 = new JButton("Trò Chơi Kết Thúc !");
				JButton msg2 = new JButton("Bạn ghi được " + player.scores + " điểm");
				gameOver.add(msg1);
				gameOver.add(msg2);
				gameOver.setLayout(new GridLayout(2, 1));
				gameOver.setSize(290, 150);
				gameOver.setResizable(false);
				gameOver.show();
				gameOver.addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent e) {
						startGame();
					}
				});
			}
		}
	}
	
	public void saveGame() throws IOException {
//		player.setName();
//		topScores.add(player);
		topScores.showTopScores();
		startGame();
	}
}















