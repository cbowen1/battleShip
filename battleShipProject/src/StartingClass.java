import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class StartingClass implements ActionListener {
	JFrame mainFrame = null;
	JButton myButton = null;
	JButton clientButton = null;
	JLabel label = null;
	JLabel background = null;
	JTextField enterField = null;

	public static void main(String args[]) {
		Constants.myGrid = new char[10][10];
		for(int i=0;i<10;i++){
			for(int j=0;j<10;j++){
				Constants.myGrid[i][j]='E';
			}
		}
		
		for(int i=0;i<10;i++){
			for(int j=0;j<10;j++){
				//System.out.print(Constants.myGrid[i][j]);
			}
			System.out.println();
		}
		
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(StartingClass.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(StartingClass.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(StartingClass.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(StartingClass.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
		StartingClass ex = new StartingClass();
	}

	private StartingClass() {
		mainFrame = new JFrame("Battleship Welcome");
		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		background =  new JLabel(new ImageIcon(Constants.SPLASH)); 
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
			clientButton.setEnabled(false);
			new serverGUI();
			mainFrame.dispose();
		}else if(clientButton == e.getSource()){		
			myButton.setEnabled(false);
			new clientGUI();
			mainFrame.dispose();
		}
	}
}