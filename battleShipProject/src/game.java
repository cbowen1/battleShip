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
		
		
		shipMenu.removeAll();
		shipMenu.repaint();
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
}



