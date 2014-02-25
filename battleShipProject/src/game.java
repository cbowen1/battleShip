import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;


public class game implements Runnable {
	static boolean hostReady = false;
	static boolean guestReady = false;
	String hostPlayer;
	String guestPlayer;
	static boolean serverTurn;
	static boolean guestTurn;
	static boolean gameOver = false;
	
	static int carrierHitPoints = 5;
	static int battleshipHitPoints = 4;
	static int cruiserHitPoints = 3;
	static int subHitPoints = 3;
	static int destroyerHitPoints = 2;
	
	static int totalHitPoints = 17;
	static int totalEnemyPoints = 17;
	
	/*
	 * For shootInfo the first two slots will be the #! to tell the system this is system information
	 * 	the third slot will be the x-coordinate we are attempting to shoot
	 * 	the fourth slot will be the y-coordinate we are attempting to shoot
	 */
	static String shootInfo;
	/*
	 * For returnInfo the first two slots will be the #! to tell the system this is system information
	 * 	the third slot will be either a H or a M to tell the user if it was a hit or miss respectively
	 * 	the fourth slot will be either an E for a miss or the Char corresponding to the hit ship
	 * 	the fifth slot will be either an X for a sunken ship or O for a not sunken ship
	 */
	static String returnInfo;

	static String runner;
	private JTextArea text;
	
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
	public String getHostPlayer(){
		return hostPlayer;
	}
	public String getGuestPlayer(){
		return guestPlayer;
	}
	public void playGame(JTextArea textBox,JPanel shipMenu){
		Thread myThread = new Thread(this);
		myThread.start();
		text = textBox;
		
		if(getRunner()=="client"){
			clientGUI.createScorePanel();
		}else{
			serverGUI.createScorePanel();
		}
		myShipGrid.displayGrid();
	}

	@Override
	public void run() {
		if(getRunner()=="client"){
			guestReady = true;
			clientGUI.sendMessage("#!READY");
		}else{
			serverGUI.sendMessage("#!READY");
			hostReady = true;
		}
		
		while(!hostReady || !guestReady){
			try {
				Thread.sleep(240);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		serverTurn = true;
		guestTurn = false;
		
		if(getRunner() == "server") {
			while (!gameOver){
				while(!serverTurn) {
					if (gameOver) {
						break;
					}
					
					text.setText("Please wait for your opponent");
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} // end of serverTurn while loop
				text.setText("Choose where to attack");
			} // end of gameOver while loop
		} else {
			while (!gameOver){
				while(!guestTurn) {
					if (gameOver) {
						break;
					}
					text.setText("Please wait for your opponent");
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} // end of guestTurn while loop
				text.setText("Choose where to attack");
			} // end of gameOver while loop
		}

		text.setText("Game Over\nThanks for playing");
		
	}
	
	public static boolean checkForSunk(char value){
		boolean sunk = false;
		switch(value){
		case 'C':
			if(carrierHitPoints == 0){
				sunk = true;
			}
			break;
		case 'B':
			if(battleshipHitPoints == 0){
				sunk = true;
			}
			break;
		case 'R':
			if(cruiserHitPoints == 0){
				sunk = true;
			}
			break;
		case 'S':
			if(subHitPoints == 0){
				sunk = true;
			}
			break;
		case 'D':
			if(destroyerHitPoints == 0){
				sunk = true;
			}
			break;
		}
		return sunk;
	}
}



