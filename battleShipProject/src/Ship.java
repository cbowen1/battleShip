import java.awt.Image;
import java.awt.Point;

import javax.swing.ImageIcon;


public class Ship {
	private String shipName;
	private ImageIcon icon;
	public static char orientation = 'h';
	private int size;
	
	public Ship(String name,String src){
		shipName = name;
		icon = new ImageIcon(src);  
	}
	
	public void setImg(String src){
		icon = new ImageIcon(src);
	}
	public ImageIcon getImg(){
		return icon;
	}
	public String getName(){
		return shipName;
	}
	static public void setOrientation(char hOrV){
		if(hOrV == 'h'){
			orientation = 'h';
		}else if(hOrV == 'v'){
			orientation = 'v';
		}
	}
}
