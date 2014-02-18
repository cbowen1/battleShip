import javax.swing.JPanel;
import javax.swing.JTextArea;


public class game {
	static boolean gameReady = false;
	String hostPlayer;
	String guestPlayer;
	static boolean myTurn;
	static boolean enemyTurn;
	
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
	public void playGame(JPanel shipMenu){
		shipMenu.setVisible(false);
		clientGUI.displayText("Let's play some battleship");
		//textBox.setText(textBox.getText()+"Let's play some battleship\n");
		//myShipGrid.displayGrid();
		clientGUI.sendMessage("!#READY");
	}
}
