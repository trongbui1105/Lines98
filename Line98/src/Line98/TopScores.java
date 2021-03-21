package Line98;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.*;

public class TopScores extends JFrame {
	
	public Player player = new Player();
	public JButton number[] = new JButton[player.countPlayer()];
	ArrayList<Player> list = new ArrayList<>();
	public int countPlayer = player.countPlayer();
	
	public TopScores() {
//		showTopScores();
		setTitle("TopScores");
		setSize(200,320);
		setLayout(new GridLayout(countPlayer,1));
		setResizable(false);	
	}
	
	
	public void showTopScores() {
		int i = 0;
		list = player.getListPlayer();
		for (Player p : list) {
			number[i] = new JButton((i + 1) + ". " + p.getName() + " : " + p.getScores());
			add(number[i]);
			i++;
		}
	}
	
	
	public void resetTopScores() {
		new TopScores();
	}	

}

















