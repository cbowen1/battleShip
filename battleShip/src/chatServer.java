import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;


public class chatServer implements Runnable,ActionListener {
	private JFrame jfrm;
	private ServerSocket serverSocket;
	private Socket clientSocket;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private JTextArea jta;
	private JScrollPane jscrlp;
	private JTextField jtfInput;
	private JButton jbtnSend;
	
	public chatServer(){
		jfrm = new JFrame("Chat Server");
		jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jfrm.setLayout(new FlowLayout());
		//jfrm.setSize(300, 320);
		jfrm.setExtendedState(JFrame.MAXIMIZED_BOTH); 
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
		
		jfrm.getContentPane().add(jscrlp);
		jfrm.getContentPane().add(jtfInput);
		jfrm.getContentPane().add(jbtnSend);
		jfrm.setVisible(true);
	}
	
	public void run(){
		try{
			serverSocket = new ServerSocket(4444);
			clientSocket = serverSocket.accept();
			oos = new ObjectOutputStream(clientSocket.getOutputStream());
			ois = new ObjectInputStream(clientSocket.getInputStream());
			while(true){
				Object input = ois.readObject();
				jta.setText(jta.getText()+"Client says: "+(String)input+"\n");
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
