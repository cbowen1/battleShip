/**************************************
 * game.java
 * Cale Bowen and Ryan Mulligan
 **************************************/

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class game implements Runnable {
	static boolean hostReady = false;
	static boolean guestReady = false;
	String hostPlayer = "Host";
	String guestPlayer = "Guest";
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

	static private Clip clip;

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
		// swap ship panel for score panel when that player is ready
		if(getRunner()=="client"){				
			clientGUI.createScorePanel();
		}else{
			serverGUI.createScorePanel();
		}
	}

	@Override
	public void run() {
		if(getRunner()=="client"){			// client set ships and pressed play
			guestReady = true;
			clientGUI.sendMessage("#!READY");
		}else{								// server set ships and pressed play
			serverGUI.sendMessage("#!READY");
			hostReady = true;
		}

		while(!hostReady || !guestReady){
			// wait 240 ms and check again if they're both ready
			try {
				Thread.sleep(240);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		// server gets first turn
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
						Thread.sleep(500);				// check every 500 ms for a move from client
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
						Thread.sleep(500);				// check every 500 ms for a move from server
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
		System.out.println(value);
		boolean sunk = false;
		switch(value){				// if any ship has 0 hit points left, it is now considered sunk
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

	static public void playSound(int sound){
		AudioInputStream audioInputStream = null;
		try {
			switch (sound){
			case 0:
				audioInputStream = AudioSystem.getAudioInputStream(new File(Constants.SONAR).getAbsoluteFile());
				break;
			case 1:
				audioInputStream = AudioSystem.getAudioInputStream(new File(Constants.TAPS).getAbsoluteFile());
				break;
			case 2:
				audioInputStream = AudioSystem.getAudioInputStream(new File(Constants.BLAST).getAbsoluteFile());
				break;
			case 3:
				audioInputStream = AudioSystem.getAudioInputStream(new File(Constants.MISS).getAbsoluteFile());
				break;
			case 4:
				audioInputStream = AudioSystem.getAudioInputStream(new File(Constants.ABANDON).getAbsoluteFile());
				break;
			case 5:
				audioInputStream = AudioSystem.getAudioInputStream(new File(Constants.WINNER).getAbsoluteFile());
				break;
			case 99:
				clip.stop();
				break;
			}
			if(sound != 99){
				// start playing sound
				clip=AudioSystem.getClip();
				clip.open(audioInputStream);
				clip.start();	
			}
			// repeat sonar sound on main screen until screen is closed
			if (sound == 0){
				clip.loop(Clip.LOOP_CONTINUOUSLY);
			}
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e){
			e.printStackTrace();
		}
	}
}



