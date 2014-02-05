import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;


public class chatClient implements Runnable,ActionListener{
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
		jfrm = new JFrame("Chat Client");
		jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jfrm.setLayout(new FlowLayout());
		jfrm.setSize(300, 320);
		//jfrm.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		Thread myThread = new Thread(this);
		myThread.start();
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
			public void actionPerformed(ActionEvent arg0) {
				IP = IPaddress.getText();
				System.out.println(IP);
				jbtnSend.setEnabled(true);
			}
		});
		btnNewButton.setBounds(0, 0, 80, 30);
		jfrm.add(btnNewButton);
		
		jfrm.getContentPane().add(jscrlp);
		jfrm.getContentPane().add(jtfInput);
		jfrm.getContentPane().add(jbtnSend);
		jfrm.setVisible(true);
		if(IP==null){
			jbtnSend.setEnabled(false);

		}
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
		if (ae.getActionCommand().equals("Send")||ae.getSource() instanceof JTextField){
			try{
				oos.writeObject(jtfInput.getText());
				jta.setText(jta.getText()+"You Say: "+ jtfInput.getText()+"\n");
			} catch(IOException e){
				e.printStackTrace();
			}
		}		
	}

}
