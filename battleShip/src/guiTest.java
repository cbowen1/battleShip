import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class guiTest implements Runnable,ActionListener {
	private JFrame jfrm;
	private Socket Socket;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private JTextArea jta;
	private JScrollPane jscrlp;
	private JTextField jtfInput;
	private JButton jbtnSend;
	
	public guiTest() {
		jfrm = new JFrame("GUI TEST");
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
		
		//jfrm.getContentPane().add(jscrlp);
		jfrm.getContentPane().add(jtfInput);
		//jfrm.getContentPane().add(jbtnSend);
		jfrm.setVisible(true);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new guiTest();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
