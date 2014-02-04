import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class StartingClass extends JFrame {

	public static void main(String args[]) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				StartingClass ex = new StartingClass();
				ex.setVisible(true);
			}
		});
		// startClient();
		//startServer();
		
		//If we run startClient or startServer from here it works. If we run it from  initUI it fails, hangs
	}

	private StartingClass() {
		initUI();	
	}

	private static void startClient() {
		Client application = new Client("127.0.0.1"); // connect to localhost
		// Client application = new Client("131.118.193.218"); // connect to
		// localhost
		application.setDefaultCloseOperation(EXIT_ON_CLOSE);
		application.runClient(); // run client application
	}

	private static void startServer() {
		Server application = new Server(); // create server
		application.setDefaultCloseOperation(EXIT_ON_CLOSE);
		application.runServer();// run server application
	}

	private void initUI() {
		JPanel panel = new JPanel();
		getContentPane().add(panel);
		panel.setLayout(null);

		setTitle("Battleship Test");
		setSize(1080, 800);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// Add background
		setLayout(new BorderLayout());
		JLabel background = new JLabel(new ImageIcon("src/img/splash.png"));
		add(background);
		background.setLayout(new FlowLayout());

		JButton serverButton = new JButton("Server");
		JButton clientButton = new JButton("Client");

		background.add(clientButton);
		background.add(serverButton);

		clientButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				startClient();
			}
		});

		serverButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				startServer();
			}
		});
	}

}