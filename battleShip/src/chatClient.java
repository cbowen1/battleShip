import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class chatClient extends JFrame implements Runnable,ActionListener{
	private JFrame jfrm;
	private Socket Socket;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private JTextArea jta;
	private JScrollPane jscrlp;
	private JTextField jtfInput;
	private JButton jbtnSend;
	
	private JTextField IPaddress;
	String IP;
	
	public chatClient(){
		 
        setTitle("Chat Client");
 
        JPanel panel = new JPanel(new GridBagLayout());
        this.getContentPane().add(panel);
 
        JTable t = new JTable(null);
 
        JLabel label = new JLabel("Battleship V1.0");
 
        JPanel tableButtonPanel = new JPanel();
        tableButtonPanel.add(new JButton("Btn1"));
        tableButtonPanel.add(new JButton("Btn2"));
        tableButtonPanel.add(new JButton("Btn3"));
 
        JPanel detailsPanel = createChatPanel();
        detailsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
 
        GridBagConstraints gbc = new GridBagConstraints();
 
        gbc.gridx = 0;
        gbc.gridy = 0;
 
        panel.add(label, gbc);
 
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JScrollPane(t), gbc);
 
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(tableButtonPanel, gbc);
 
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 2;
        panel.add(detailsPanel, gbc);
 
        this.pack();
 
        this.setVisible(true);
		/*
		jfrm = new JFrame("Chat Client");
		jfrm.setMinimumSize(new Dimension(640,480));
		jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		GridBagLayout layout = new GridBagLayout();
		jfrm.setLayout(layout);
		GridBagConstraints c = new GridBagConstraints();
		
		//jfrm.setLayout(new SpringLayout());
		jfrm.setSize(1280, 1024);
		//jfrm.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		Thread myThread = new Thread(this);
		myThread.start();
		//jta = new JTextArea();
		jta = new JTextArea(15,15);
		jta.setEditable(false);
		jta.setLineWrap(true);
		jscrlp = new JScrollPane(jta);
		jtfInput = new JTextField(15);
		jtfInput.addActionListener(this);
		jbtnSend = new JButton("Send");
		jbtnSend.addActionListener(this);
		
		IPaddress = new JTextField();
		IPaddress.setBounds(150, 50, 80, 20);
		jfrm.add(IPaddress);
		IPaddress.setColumns(10);
		
		JButton btnNewButton = new JButton("Connect");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				IP = IPaddress.getText();
				System.out.println(IP);
				jbtnSend.setEnabled(true);
			}
		});
		btnNewButton.setBounds(0, 0, 80, 30);
		jfrm.add(btnNewButton);
		//jfrm.getContentPane().add(mainBoard);
		jfrm.getContentPane().add(jscrlp);
		jfrm.getContentPane().add(jtfInput);
		jfrm.getContentPane().add(jbtnSend);
		
		
		
		jfrm.setVisible(true);
		if(IP==null){
			jbtnSend.setEnabled(false);

		}
		pack();
		*/
	}
	
	private JPanel createChatPanel(){
		
		JPanel panel = new JPanel();
		 
        JLabel ipLabel = new JLabel("IP Address:");
        JLabel chat =  new JLabel("Chat:");
 
        JTextField ipAddress = new JTextField(15);
 
        panel.setLayout(new GridBagLayout());
 
        GridBagConstraints gbc = new GridBagConstraints();
 
        int i=0;
 
        gbc.gridx = 0;
        gbc.gridy = i;
        panel.add(ipLabel,  gbc);
 
        gbc.gridx = 1;
        gbc.gridy = i;
        gbc.gridwidth = 2;
        panel.add(ipAddress,  gbc);        
 
        i++;
 
        gbc.gridx = 0;
        gbc.gridy = i;
        panel.add(chat,gbc);
        
        i++;
        
        return panel;
		
	}
	
	public void run(){
		try{
			Socket = new Socket(IP , 4444);
			oos = new ObjectOutputStream(Socket.getOutputStream());
			ois = new ObjectInputStream(Socket.getInputStream());
			while(true){
				Object input = ois.readObject();
				jta.setText(jta.getText()+"Server says: "+(String)input+"\n");
			}
		}catch (IOException e){
			e.printStackTrace();
		}catch (ClassNotFoundException e){
			e.printStackTrace();
		}			

	}
	
	public void actionPerformed(ActionEvent ae){
		if (ae.getActionCommand().equals("Send")){
			try{
				oos.writeObject(jtfInput.getText());
				jta.setText(jta.getText()+"You Say: "+ jtfInput.getText()+"\n");
			} catch(IOException e){
				e.printStackTrace();
			}
		}		
	}

}
