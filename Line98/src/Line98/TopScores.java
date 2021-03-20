package Line98;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.*;

public class TopScores extends JFrame {
	
	public Player player = new Player();
	public JButton number[] = new JButton[player.countPlayer()];
	
	public TopScores() throws SQLException {
		showTopScores();
		
		setTitle("TopScores");
		setSize(200,320);
		setLayout(new GridLayout(10,1));
		setResizable(false);	
	}
	
	

	
	public void showTopScores() throws SQLException {
		int i = 0;
		for (Player p : player.getListPlayer()) {
			number[i] = new JButton((i + 1) + ". " + p.getName() + " : " + p.getScores());
			i++;
		}
	}
	
	
	public void resetTopScores() throws SQLException{
		new TopScores();
	}	

}

















