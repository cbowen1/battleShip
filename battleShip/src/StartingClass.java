import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class StartingClass extends JFrame {

	public StartingClass() {
		initUI();
	}

	private void initUI() {
			JPanel panel = new JPanel();
			getContentPane().add(panel);
			
			panel.setLayout(null);
			
			JButton serverButton = new JButton("Server");
			serverButton.setBounds(0, 20, 80, 30);
			
			JButton quitButton = new JButton("Quit");
			quitButton.setBounds(50, 60, 80, 30);
			
			quitButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent event){
					System.exit(0);
				}
			});
			
			serverButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent event){
					startServer();
				}
			});
			panel.add(serverButton);
			panel.add(quitButton);
			setTitle("Quit Button");
			setSize(300,200);
			setLocationRelativeTo(null);
			setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		
		/*
		JPanel panel = new JPanel();
		getContentPane().add(panel);
		panel.setLayout(null);
		setSize(1080, 800);
		//setTitle("Battleship Test");
		//setLocationRelativeTo(null);
		//setDefaultCloseOperation(EXIT_ON_CLOSE);
		// Add background
		//setLayout(new BorderLayout());
		//JLabel background = new JLabel(new ImageIcon("src/img/test.png"));
		//add(background);
		//background.setLayout(new FlowLayout());
		
		JButton serverButton = new JButton("Server");
		//JButton clientButton = new JButton("Client");
		
		//background.add(clientButton);
		background.add(serverButton);

		/*clientButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				runClient();
			}
		});

		serverButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				runServer();
			}
		});*/
	}

	private static void runClient() {

		//Client application = new Client("127.0.0.1"); // connect to localhost
		Client application = new Client("131.118.193.218"); // connect to localhost
		// else
		// application = new Client(args[0]); // use args to connect

		application.setDefaultCloseOperation(EXIT_ON_CLOSE);
		application.runClient(); // run client application
	}

	private static void startServer() {
		Server application = new Server(); // create server
		application.setDefaultCloseOperation(EXIT_ON_CLOSE);
		application.runServer();// run server application
	}

	public static void main(String args[]) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				StartingClass ex = new StartingClass();
				ex.setVisible(true);
			}
		});
		
		//runClient();
		//runServer();

	}

}