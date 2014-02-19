import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragSource;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.SoftBevelBorder;

public class serverGUI extends JFrame implements Runnable, ActionListener,KeyListener {

    private JPanel header;
    private JTextField userEnteredIP;
    private JButton sendBtn;
    private JButton connectBtn;
    private JPanel jPanel2;
    private JScrollPane jScrollPane2;
    private static JTextArea textBox;
    private JTextField chatInput;
    private JPanel mainBoard;
    private JPanel secondaryDisp;
    
	private ServerSocket serverSocket;
	private Socket clientSocket;
	private ObjectInputStream ois;
	private static ObjectOutputStream oos;
	
    private JPanel shipMenu;
    private JPanel smallGrid;	
    
    private static JLabel carrierBox;
    private static JLabel battleshipBox;
    private static JLabel cruiserBox;
    private static JLabel subBox;
    private static JLabel destroyerBox;
    
    private JButton beginGame;
    
	Ship carrier;
	Ship battleship;
	Ship cruiser;
	Ship submarine;
	Ship destroyer;
	
	public static Grid mainGrid;
	
	game Game;
	
    public serverGUI() {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(serverGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(serverGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(serverGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(serverGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
		Thread myThread = new Thread(this);
		myThread.start(); 
        initComponents();
    }
                       
    private void initComponents() {
    	Game = new game();
    	Game.setRunner("server");
    	
    	Game.setGuestPlayer("Cale");
    	Game.setHostPlayer("Ryan");
    	
    	beginGame = new JButton();
    	beginGame.setText("Play");
    	shipMenu = new JPanel();
        smallGrid = new JPanel(){
        	Image image = Toolkit.getDefaultToolkit().getImage(Constants.GRID);
            public void paintComponent( Graphics g )
            {
                 super.paintComponent(g);
                 g.drawImage( image, 0, 0, this );
            }	
        };
        setFocusable(true);
        addKeyListener(this);
        
        mainBoard = new JPanel();
        secondaryDisp = new JPanel();
        header = new JPanel(){
        	Image image = Toolkit.getDefaultToolkit().getImage(Constants.HEADER);
            public void paintComponent( Graphics g )
            {
                 super.paintComponent(g);
                 g.drawImage( image, 0, 0, this );
            }	
        };
        
        carrier = new Ship("Carrier",Constants.CARRIER);
        battleship = new Ship("Battleship",Constants.BATTLESHIP);
        cruiser = new Ship("Cruiser",Constants.CRUISER);
        submarine = new Ship("Submarine",Constants.SUBMARINE);
        destroyer = new Ship("Destroyer",Constants.DESTROYER);
        
        jPanel2 = new JPanel();
        jScrollPane2 = new JScrollPane();
        textBox = new JTextArea();
        sendBtn = new JButton();
        connectBtn = new JButton();
        userEnteredIP = new JTextField();
        chatInput = new JTextField();
        connectBtn.setVisible(false);
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1080, 800));
        setTitle("Battleship v 1.01");
        setResizable(false);
        textBox.setEditable(false);

        mainBoard.setBorder(BorderFactory.createTitledBorder("Where would you like to shoot?"));
        mainBoard.setPreferredSize(new Dimension(500, 500));
        mainGrid = new Grid(mainBoard,textBox,44,0);
        new MyDropTargetListener(mainBoard);
        
        secondaryDisp.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
        secondaryDisp.setPreferredSize(new Dimension(200, 200));
        smallGrid.setBorder(new LineBorder(Color.BLACK));
        
        javax.swing.GroupLayout smallGridLayout = new javax.swing.GroupLayout(smallGrid);
        smallGrid.setLayout(smallGridLayout);
        smallGridLayout.setHorizontalGroup(
            smallGridLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 321, Short.MAX_VALUE)
        );
        smallGridLayout.setVerticalGroup(
            smallGridLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        //This section shows the ships that can be dragged into the users grid
        shipMenu.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
        shipMenu.setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        carrierBox = new JLabel(carrier.getImg(),JLabel.CENTER);
        battleshipBox = new JLabel(battleship.getImg(),JLabel.CENTER);
        cruiserBox = new JLabel(cruiser.getImg(),JLabel.CENTER);
        subBox = new JLabel(submarine.getImg(),JLabel.CENTER);
        destroyerBox = new JLabel(destroyer.getImg(),JLabel.CENTER);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        shipMenu.add(carrierBox,gbc);
    
        gbc.gridy = 1;
        shipMenu.add(battleshipBox,gbc);
        
        gbc.gridy = 3;
        shipMenu.add(cruiserBox,gbc);
        
        gbc.gridy = 5;
        shipMenu.add(subBox,gbc);
        
        gbc.gridy = 7;
        shipMenu.add(destroyerBox,gbc);
        
        beginGame.addActionListener(this);
        gbc.gridy = 9;
        shipMenu.add(beginGame,gbc);
        
        javax.swing.GroupLayout scoreLayout = new javax.swing.GroupLayout(secondaryDisp);
        secondaryDisp.setLayout(scoreLayout);
        scoreLayout.setHorizontalGroup(
            scoreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(scoreLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(smallGrid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(shipMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        scoreLayout.setVerticalGroup(
            scoreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, scoreLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(scoreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(shipMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(smallGrid, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        new MyDropTargetListener(smallGrid);
        smallGrid.setPreferredSize(new Dimension(325,325));
        smallGrid.setMinimumSize(new Dimension(325,325));
        smallGrid.setMaximumSize(new Dimension(325,325));
        smallGrid.setLayout(null);
        
        //begin code to make drag and drop work
        MyDragGestureListener dlistener = new MyDragGestureListener();
        DragSource ds1 = new DragSource();
        DragSource ds2 = new DragSource();
        DragSource ds3 = new DragSource();
        DragSource ds4 = new DragSource();
        DragSource ds5 = new DragSource();        
        	
        ds1.createDefaultDragGestureRecognizer(carrierBox, DnDConstants.ACTION_COPY, dlistener);
        ds2.createDefaultDragGestureRecognizer(battleshipBox, DnDConstants.ACTION_COPY, dlistener);
        ds3.createDefaultDragGestureRecognizer(cruiserBox, DnDConstants.ACTION_COPY, dlistener);
        ds4.createDefaultDragGestureRecognizer(subBox, DnDConstants.ACTION_COPY, dlistener);
        ds5.createDefaultDragGestureRecognizer(destroyerBox, DnDConstants.ACTION_COPY, dlistener);        
        //end drag and drop code
        
        header.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
        GroupLayout headerLayout = new GroupLayout(header);
        header.setLayout(headerLayout);
        headerLayout.setHorizontalGroup(
            headerLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        headerLayout.setVerticalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 261, Short.MAX_VALUE)
        );

        jPanel2.setBorder(new SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel2.setBorder(new SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        textBox.setColumns(20);
        textBox.setRows(5);
        jScrollPane2.setViewportView(textBox);

        sendBtn.setText("Send");
        sendBtn.addActionListener(this);

        userEnteredIP.setVisible(false);

        chatInput.setText("");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(sendBtn))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(userEnteredIP, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(connectBtn))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(chatInput)
                        .addContainerGap())))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(connectBtn)
                            .addComponent(userEnteredIP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(chatInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sendBtn))
                    .addComponent(jScrollPane2))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(header, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(secondaryDisp, javax.swing.GroupLayout.DEFAULT_SIZE, 550, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(mainBoard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(header, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(mainBoard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(secondaryDisp, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
       this.setVisible(true);
    }                                                              
	
	public void run() {
		try{
			serverSocket = new ServerSocket(4444);
			clientSocket = serverSocket.accept();
			oos = new ObjectOutputStream(clientSocket.getOutputStream());
			ois = new ObjectInputStream(clientSocket.getInputStream());
			while(true){
				Object input = ois.readObject();
				String text = input.toString();
				/*
				 * Anything that starts with #! will be considered a system message and as such
				 * it will need to be intercepted prior to being shown to the user. It will come here
				 * and the game logic will then be done.
				 */
				if(text.startsWith("#!")){
					text = text.substring(2);
					if(text.equals("READY")){
						game.guestReady = true;
						textBox.setText(textBox.getText()+Game.getGuestPlayer()+" is ready.\n");
					}else{
						textBox.setText(textBox.getText()+"SYS MSG:: "+text+"\n");	
					}
				}else{
					textBox.setText(textBox.getText()+Game.getGuestPlayer()+": "+(String)input+"\n");	
				}
			}
		}catch (IOException e){
			e.printStackTrace();
		}catch (ClassNotFoundException e){
			e.printStackTrace();
		}
		
	}

    static public void disableCarrierBox(){
    	carrierBox.setEnabled(false);
    }
    static public void disableBattleshipBox(){
    	battleshipBox.setEnabled(false);
    }
    static public void disableCruiserBox(){
    	cruiserBox.setEnabled(false);
    }
    static public void disableSubBox(){
    	subBox.setEnabled(false);
    }
    static public void disableDestroyerBox(){
    	destroyerBox.setEnabled(false);
    }
    static public void displayText(String text){
    	textBox.setText(textBox.getText()+text+"\n");
    }
    
    static public void sendMessage(String text){
    	try {
			oos.writeObject(text);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }	

	@Override
	public void actionPerformed(ActionEvent ae) {
    	if (ae.getActionCommand().equals("Send")){
			try{
				oos.writeObject(chatInput.getText());
				textBox.setText(textBox.getText()+Game.getHostPlayer()+": "+ chatInput.getText()+"\n");
				chatInput.setText("");
			} catch(IOException e){
				e.printStackTrace();
			}
		}else if(ae.getActionCommand().equals("Play")){
			if(!myShipGrid.carrierPlaced || !myShipGrid.battleshipPlaced || !myShipGrid.cruiserPlaced
					|| !myShipGrid.subPlaced || !myShipGrid.destroyerPlaced){
				textBox.setText(textBox.getText()+"Please place your ships first\n");
			}else{
				beginGame.setEnabled(false);
				Game.playGame(textBox,shipMenu);
			}
		}
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()){
		case KeyEvent.VK_UP:
			Ship.setOrientation('v');
			textBox.setText(textBox.getText()+"Place your ship VERTICALLY"+"\n");
			break;
		case KeyEvent.VK_DOWN:
			Ship.setOrientation('h');
			textBox.setText(textBox.getText()+"Place your ship HORIZONTALLY"+"\n");
			break;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) {}
}
