import javax.swing.JTextArea;


public class game {
	static boolean gameReady = false;
	String hostPlayer;
	String guestPlayer;
	
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
	public void playGame(JTextArea textBox){
		textBox.setText(textBox.getText()+"Let's play some battleship\n");
		System.out.println(myShipGrid.subPlaced);
	}
}
