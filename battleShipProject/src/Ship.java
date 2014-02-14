import java.awt.Image;
import java.awt.Point;

import javax.swing.ImageIcon;


public class Ship {
	private String shipName;
	private ImageIcon icon;
	private char orientation;
	private int size;
	
	public Ship(String name,String src,int wid,int hgt){
		shipName = name;
		icon = new ImageIcon(src);  
	}
	
	public ImageIcon getImg(){
		return icon;
	}
	public String getName(){
		return shipName;
	}
	public void setOrientation(char hOrV){
		if(hOrV == 'h'){
			orientation = 'h';
		}else if(hOrV == 'v'){
			orientation = 'v';
		}
	}
	public char getOrientation(){
		return orientation;
	}
	public void setSize(int squares){
		size = squares;
	}
	
}
