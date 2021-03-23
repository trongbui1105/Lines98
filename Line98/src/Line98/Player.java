package Line98;

import java.io.*;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.*;

public class Player {
	
	public ArrayList<Player> listOfPlayers;
	public String name;
	public int scores, countPlayer;
	public Player newPlayer;
	public LineBall lineBall;
	
	public Player() {
		this.name = "";
		this.scores = 0;
	}
	
	public Player(String name, int scores) {
		this.name = name;
		this.scores = scores;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getScores() {
		return scores;
	}

	public void setScores(int scores) {
		this.scores = scores;
	}

	public Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			String connStr = "jdbc:mysql://127.0.0.1/line98?user=root&password=bqt110500";
			conn = DriverManager.getConnection(connStr);
//			System.out.println("Kết nối thành công");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("Kết nối không thành công");
		}
		return conn;
	}
	
	public ArrayList<Player> getListPlayer() {
		listOfPlayers = new ArrayList<>();
		String sql = "call sortPlayerScores()";
		try {
			CallableStatement cStmt = getConnection().prepareCall(sql);
			ResultSet rs = cStmt.executeQuery();
			while (rs.next()) {
				Player p = new Player();
				p.setName(rs.getString("nameOfPlayer"));
				p.setScores(rs.getInt("scores"));
				listOfPlayers.add(p);
			}
			getConnection().close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return listOfPlayers;
	}
	
	
	public int countPlayer() {
		String sql = "call countPlayer()";
		try {
			CallableStatement cStmt = getConnection().prepareCall(sql);
			ResultSet rs = cStmt.executeQuery();
			while (rs.next()) {
				countPlayer = rs.getInt("countPlayer");
			}
			getConnection().close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return countPlayer;
	}
	

	
	public void saveGame() {
		String nameOfNewPlayer;
		nameOfNewPlayer = JOptionPane.showInputDialog("Nhập tên của bạn: ");
		int scoresOfNewPlayer = this.scores;
		String sql = "call insertPlayer(?, ?)";
		try {
			CallableStatement cStmt = getConnection().prepareCall(sql);
			cStmt.setString(1, nameOfNewPlayer);
			cStmt.setInt(2, scoresOfNewPlayer);
			ResultSet rs = cStmt.executeQuery();
			JOptionPane.showMessageDialog(null, "Save Succesfully!");
			getConnection().close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "Save Failed!");
			e1.printStackTrace();
		}
	}
}













