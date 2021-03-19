package Line98;

import java.io.*;

import javax.swing.*;

public class Player {
	
	public String name;
	public int scores;
	
	public Player() {
		this.name = "";
		this.scores = 0;
	}
	
	public Player(String name, int scores) {
		this.name = name;
		this.scores = scores;
	}
	
	public void setName() {
		String nameOfPlayer;
		nameOfPlayer = JOptionPane.showInputDialog("Nhập tên của bạn: ");
		this.name = nameOfPlayer;
	}
	
	public void writeFixedString(String s, int size, DataOutput out) throws IOException{
		int i;
		for (i = 0; i < size; i++){
			char ch = 0;
		    if (i < s.length()) {
		    	ch = s.charAt(i);
		    }
		    out.writeChar(ch);
		}
	}
	
	public String readFixedString(int size, DataInput in) throws IOException {
		StringBuilder str = new StringBuilder(size);
		int i = 0;
		boolean more = true;
		while (more && i < size) {
			char ch = in.readChar();
			i++;
			if (ch == 0) {
				more = false;
			} else {
				str.append(ch);
			}
		}
		in.skipBytes(2 * (size - i));
		return str.toString();
	}
	
	public void writeData(DataOutput out) throws IOException {
		writeFixedString(this.name, 20 , out);
		out.writeInt(this.scores);
	}
	
	public void readData(DataInput in) throws IOException {
		this.name = readFixedString(20, in);
		this.scores = in.readInt();
	}
	
}













