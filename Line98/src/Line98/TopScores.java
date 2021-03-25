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
	public JScrollPane scrollPane;
	public JPanel panel;

	
	public TopScores() {
		setTitle("TopScores");
		setSize(200,320);
		panel = new JPanel(new GridLayout(0, 1));
		scrollPane = new JScrollPane(panel);
		add(scrollPane);
		setResizable(false);
	} 

	
	public void showTopScores() {
		int i = 0;
		list = player.getListPlayer();
		for (Player p : list) {
			number[i] = new JButton((i + 1) + ". " + p.getName() + " : " + p.getScores());
			panel.add(number[i]);
			i++; 
		}
	}
	
	
	
	public void resetTopScores() {
		new TopScores();
	}	

}

















