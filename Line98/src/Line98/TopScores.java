package Line98;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

public class TopScores extends JFrame {
	
	public Player player[] = new Player[10];
	public JButton number[] = new JButton[10];
	
	public TopScores() {
		for (int i = 0; i < 10; i++) {
			this.player[i] = new Player();
			this.player[i].name =  "Player " + (i + 1);
		}
		
		for (int i = 0; i < 10; i++) {
			number[i] = new JButton((i + 1) + ". " + this.player[i].name + " : " + this.player[i].scores);
			add(number[i]);
		}
		
		setTitle("TopScores");
		setSize(200,320);
		setLayout(new GridLayout(10,1));
		setResizable(false);	
	}
	
	
	public void add(Player newPlayer) throws IOException {
		readFile();
		if (this.player[9].scores < newPlayer.scores) {
			this.player[9] = newPlayer;
			sortPlayer();
			for (int i = 0; i < 10; i++) {
				this.number[i].setText((i + 1) + ". " + player[i].name + " : " + player[i].scores);
			}
			writeFile();
		}
	}
	
	public void sortPlayer() {
		for(int i = 0; i < 10; i++) {
			for(int j = i; j > 0; j--) {
				if (player[j].scores > player[j - 1].scores) {
					Player temp = this.player[j];
					this.player[j] = this.player[j - 1];
					this.player[j - 1] = temp;
				}
			}
		}
	}
	
	public void readFile() throws IOException {	
		RandomAccessFile in = new RandomAccessFile("TopScores.txt","r");
		for(int i = 0; i < 10; i++) {
			this.player[i].readData(in);
		}
		sortPlayer();
		for(int i = 0; i < 10; i++) {
			this.number[i].setText((i+1)+". "+player[i].name+"  :   "+player[i].scores);
		}
	}
	
	public void writeFile() throws IOException {
		DataOutputStream out = new DataOutputStream(new FileOutputStream("TopScores.txt"));
		for (int i = 0; i < 10; i++) {
			this.player[i].writeData(out);
		}
		out.close();
	}
	
	public void showTopScores()throws IOException{
		readFile();
		show();
	}
	
	//reset la TopScores
	public void resetTopScores()throws IOException{
		new TopScores();
		writeFile();
	}	
}

















