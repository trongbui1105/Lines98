package Line98;
import java.awt.*;
import java.awt.event.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

import com.sun.tools.javac.Main;

import Line98.LineBall.Point;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class Line98 extends JFrame {
	
	public LineBall lineBall = new LineBall();
	public JFrame gameOver = new JFrame(" GameOver !");
	public Player player = new Player();
	public int numOfPlayer = player.countPlayer();
	public TopScores topScores = new TopScores();
	public Icon icon[] = new Icon[22];
	public JButton button[][] = new JButton[9][9];
	public JMenuItem nextBall[] = new JMenuItem[3];
	public JMenuItem score = new JMenuItem("0"); 
	public int x = -1, y = -1, startPoint;
	public final int maxCell = 9; 
	public Thread moveThread, cutBallThread, newBallThread;
	public boolean checkMove = false;
	public int numOfPoint;

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
		GridLayout gridLayout = new GridLayout(9, 9);
	    gridLayout.setHgap(-4);
	    gridLayout.setVgap(-4);
		setLayout(gridLayout);
		
		setSize(600,650);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		playSound("src/sound/BACKGROUND.wav");
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
				saveGame();
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
				topScores = new TopScores();
				topScores.showTopScores();
				topScores.setVisible(true);
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
						playSound("src/sound/CLICK.wav");
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
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
											
											drawBall();
											if (!lineBall.cutBall() && checkMove) {
												lineBall.new3Balls();
											} else if (lineBall.cutBall()) {
												lineBall.cutBall();
												cutBall();
											}
											displayNextBall();
											drawBall();
											x = y = -1;
										} else {
											playSound("src/sound/CANTMOVE.WAV");
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

	
	public void moveBall(int si, int sj, int fi, int fj) {
		if (moveThread != null && moveThread.isAlive()) {
			return;
		}
		startPoint = lineBall.ball[si][sj] - 10;
		lineBall.ball[si][sj] = 0;
		button[si][sj].setIcon(icon[lineBall.ball[si][sj]]);
		ArrayList<Point> listSmallBall = new ArrayList<>();
		moveThread = new Thread(new Runnable() {
			public void run() {
				ArrayList<Point> path = new ArrayList<>();
				path = lineBall.getPathBall();
				numOfPoint = path.size();
				for (int k = 2; k < path.size() - 1; k++) {
					Point pos = path.get(k);
					if (lineBall.ball[pos.x][pos.y] == 0) {
						lineBall.ball[pos.x][pos.y] = startPoint;
						button[pos.x][pos.y].setIcon(icon[startPoint]);
						repaint();
					} else {
						listSmallBall.add(pos);
					}

					try {
						Thread.sleep(70);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				Boolean[] saveSmallBall = new Boolean[path.size()];
				for (int i = 0; i < saveSmallBall.length; i++) {
					saveSmallBall[i] = false;
				}
				
				for (int k = 2; k < path.size() - 1; k++) {
					Point pos = path.get(k);
					if (listSmallBall.size() == 0) {
						lineBall.ball[pos.x][pos.y] = 0;
						button[pos.x][pos.y].setIcon(icon[0]);
					} else {
						for (int n = 0; n < listSmallBall.size(); n++) {
							Point pos1 = listSmallBall.get(n);
							if (pos.x == pos1.x && pos.y == pos1.y) {
								saveSmallBall[k] = true;
							} else {
								if (lineBall.ball[pos.x][pos.y] == startPoint && !saveSmallBall[k]) {
									lineBall.ball[pos.x][pos.y] = 0;
									button[pos.x][pos.y].setIcon(icon[0]);
								}
							}
						}
					}
				}
			}
		});
		moveThread.start();
		
		lineBall.ball[fi][fj] = startPoint;
		button[fi][fj].setIcon(icon[lineBall.ball[fi][fj]]);
		
		checkMove = true;
		playSound("src/sound/MOVE.WAV");
	}
	
	public void cutBall() {
		ArrayList<Point> listCutBall = lineBall.getListCutBall();
		if (checkMove) {
			cutBallThread = new Thread(() -> {
				try {
					Thread.sleep(lineBall.getPathBall().size() * 70 + 100);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				for (int i = 0; i < listCutBall.size(); i++) {
					Point pos = listCutBall.get(i);
					lineBall.ball[pos.x][pos.y] = 0;
					button[pos.x][pos.y].setIcon(icon[0]);
					repaint();
					
					try {
						Thread.sleep(60);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				playSound("src/sound/DESTROY.wav");
			});
			cutBallThread.start();
		}
		
	}

	
	
	public void startGame() {
		lineBall.startGame();
		score.setText("0");		
		displayNextBall();
		drawBall();
		x = y = -1;
	
	}
	
	@SuppressWarnings("deprecation")
	public void stopGame() throws IOException {
		if (lineBall.gameOver) {
			gameOver = new JFrame("Game Over !!!");
			JButton msg1 = new JButton("Trò Chơi Kết Thúc !");
			JButton msg2 = new JButton("Bạn ghi được " + player.scores + " điểm");

			gameOver.add(msg1);
			gameOver.add(msg2);
			gameOver.setLayout(new GridLayout(2, 1));
			gameOver.setSize(290, 150);
			gameOver.setResizable(false);
			gameOver.show();
			
			saveGame();
			gameOver.hide();
			gameOver.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					startGame();
				}
			}); 
		}
	}
	
	public void saveGame() {
		player.saveGame();
		topScores = new TopScores();
		topScores.showTopScores();
		topScores.setVisible(true);
		startGame();
	}

	public JButton[][] getButton() {
		return button;
	}

	public void setButton(JButton[][] button) {
		this.button = button;
	}

	void playSound(String soundFile) {
	    File f = new File("./" + soundFile);
	    AudioInputStream audioIn = null;
		try {
			audioIn = AudioSystem.getAudioInputStream(f.toURI().toURL());
		} catch (UnsupportedAudioFileException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	    Clip clip = null;
		try {
			clip = AudioSystem.getClip();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    try {
			clip.open(audioIn);
		} catch (LineUnavailableException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    clip.start();
	}
	
}















