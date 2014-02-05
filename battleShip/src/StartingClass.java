import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class StartingClass implements ActionListener {
	JFrame mainFrame = null;
	JButton myButton = null;
	JButton clientButton = null;
	JLabel label = null;
	JLabel background = null;
	JTextField enterField = null;

	public static void main(String args[]) {
		StartingClass ex = new StartingClass();
	}

	private StartingClass() {
		mainFrame = new JFrame("Battleship Network Tester");
		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		background =  new JLabel(new ImageIcon("src/img/splash.png")); 
		myButton = new JButton("Server");
		clientButton = new JButton("Client");
		clientButton.addActionListener(this);
		myButton.addActionListener(this);
		myButton.setBounds(10, 10, 80, 30);
		clientButton.setBounds(10,55,80,30);
		//mainFrame.setLocationRelativeTo(null);
		mainFrame.add(myButton);
		mainFrame.add(clientButton);
		mainFrame.add(background);
		mainFrame.setSize(1080, 800);
		mainFrame.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if (myButton == e.getSource()) {
			//For testing we are allowing the user to run both the client and server
			//To disable uncomment the following line:
			//clientButton.setEnabled(false);
			new chatServer();
			//mainFrame.dispose();
		}else if(clientButton == e.getSource()){
			//For testing we are allowing the user to run both the client and server
			//To disable uncomment the following line:			
			//myButton.setEnabled(false);
			new chatClient();
			mainFrame.dispose();
		}
	}
}