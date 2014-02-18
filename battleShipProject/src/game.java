import javax.swing.JPanel;
import javax.swing.JTextArea;


public class game {
	static boolean hostReady = false;
	static boolean guestReady = false;
	String hostPlayer;
	String guestPlayer;
	static boolean serverTurn;
	static boolean guestTurn;
	static boolean gameOver = false;
	
	/*
	 * For shootInfo the first two slots will be the !# to tell the system this is system information
	 * 	the third slot will be the x-coordinate we are attempting to shoot
	 * 	the fourth slot will be the y-coordinate we are attempting to shoot
	 */
	static String shootInfo;
	/*
	 * For returnInfo the first two slots will be the !# to tell the system this is system information
	 * 	the third slot will be either a H or a M to tell the user if it was a hit or miss respectively
	 * 	the fourth slot will be either an E for a miss or the Char corresponding to the hit ship
	 * 	the fifth slot will be the remaining number of hit points for that ship or an X if a miss
	 */
	static String returnInfo;
	
	static String runner;
	
	void setRunner(String txt){
		runner = txt;
	}
	
	static public String getRunner(){
		return runner;
	}
	
	void setHostPlayer(String name){
		hostPlayer = name;
	}
	void setGuestPlayer(String name){
		guestPlayer = name;
	}
	String getHostPlayer(){
		return hostPlayer;
	}
	String getGuestPlayer(){
		return guestPlayer;
	}
	public void playGame(JTextArea textBox,JPanel shipMenu){
		if(getRunner()=="client"){
			guestReady = true;
			clientGUI.sendMessage("#!READY");
		}else{
			serverGUI.sendMessage("#!READY");
			hostReady = true;
		}
		shipMenu.removeAll();
		shipMenu.repaint();
		//textBox.setText(textBox.getText()+"Let's play some battleship\n");
		while(!hostReady || !guestReady){
			try {
				Thread.sleep(240);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		textBox.setText(textBox.getText()+"NOW PLAYING!"+"\n");
		while(!gameOver){
			if(getRunner()=="server"){
				if(serverTurn){	//Our turn
					serverGUI.sendMessage(shootInfo);
					serverTurn = false;
					guestTurn = true;
				}else{	//The opponents turn
					
				}
			}else{
				if(serverTurn){	//The opponents turn
					
				}else{ //Our turn
					clientGUI.sendMessage(shootInfo);
					guestTurn = false;
					serverTurn = true;
				}
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}	//End of the while loop to run run the game
	}
}
