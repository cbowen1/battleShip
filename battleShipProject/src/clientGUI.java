import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.SoftBevelBorder;

public class clientGUI extends JFrame implements Runnable, ActionListener,KeyListener {

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

	private String IP;

	private static JPanel shipMenu;
	private JPanel smallGrid;

	private static JLabel cliCarrierBox;
	private static JLabel cliBattleshipBox;
	private static JLabel cliCruiserBox;
	private static JLabel cliSubBox;
	private static JLabel cliDestroyerBox;

	private JButton beginGame;
	private JButton randomShips;
	
	private int lastX;
	private int lastY;

	Ship carrier;
	Ship battleship;
	Ship cruiser;
	Ship submarine;
	Ship destroyer;

	game Game;
	
	public static ImageIcon scoreBox = null;
	static JLabel scoreLabel;
	public static JLabel enemyHitPoints;
	public static JLabel yourHitPoints;
	
	private static JLabel youSunk;
	
	private Socket Socket;
	private ObjectInputStream ois;
	public static ObjectOutputStream oos;

	public clientGUI() {
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(clientGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(clientGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(clientGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(clientGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		Thread myThread = new Thread(this);
		myThread.start(); 
		initComponents();
	}

	private void initComponents() {
		Game = new game();
		Game.setRunner("client");
		
		beginGame = new JButton();
		randomShips = new JButton();
		beginGame.setText("Play");
		randomShips.setText("Random");
		mainBoard = new JPanel(){
			Image image = Toolkit.getDefaultToolkit().getImage(Constants.BIGGRID);
			public void paintComponent( Graphics g )
			{
				super.paintComponent(g);
				g.drawImage( image, 20, 24, this );
			}	
		};
		mainBoard.setLayout(null);
		secondaryDisp = new JPanel();
		//secondaryDisp.setBackground(Color.black);
		smallGrid = new JPanel(){
			Image image = Toolkit.getDefaultToolkit().getImage(Constants.GRID);
			public void paintComponent( Graphics g )
			{
				super.paintComponent(g);
				g.drawImage( image, 0, 0, this );
			}	
		};
		shipMenu = new JPanel();
		
		setFocusable(true);
		addKeyListener(this);

		carrier = new Ship("Carrier",Constants.CARRIER);
		battleship = new Ship("Battleship",Constants.BATTLESHIP);
		cruiser = new Ship("Cruiser",Constants.CRUISER);
		submarine = new Ship("Submarine",Constants.SUBMARINE);
		destroyer = new Ship("Destroyer",Constants.DESTROYER);

		header = new JPanel(){
			Image image = Toolkit.getDefaultToolkit().getImage(Constants.HEADER);
			public void paintComponent( Graphics g )
			{
				super.paintComponent(g);
				g.drawImage( image, 0, 0, this );
			}	
		};
		jPanel2 = new JPanel();
		jScrollPane2 = new JScrollPane();
		textBox = new JTextArea();
		sendBtn = new JButton();
		connectBtn = new JButton();
		userEnteredIP = new JTextField();
		chatInput = new JTextField();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(1080, 800));
		setTitle("Battleship v 1.01");
		setResizable(false);
		textBox.setEditable(false);

		mainBoard.setBorder(BorderFactory.createTitledBorder("Where would you like to shoot?"));
		mainBoard.setPreferredSize(new Dimension(500, 500));
		mainBoard.setMinimumSize(new Dimension(500, 500));
		mainBoard.setMaximumSize(new Dimension(500, 500));
		mainBoard.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e){
				int x = e.getX();
				int y = e.getY();
				x = myShipGrid.mainXpoint(x);
				y = myShipGrid.mainXpoint(y-4);
				
				/*
				 * Leave this code to allow for testing if we change the hit/miss icon
				 */
				/*
				x = myShipGrid.getBIGxPx(x);
				y = myShipGrid.getBIGyPx(y);
				Icon ico = new ImageIcon(Constants.REDPEG);
				JLabel testPanel = new JLabel(ico);
				testPanel.setBounds(x, y, ico.getIconWidth(), ico.getIconHeight());
				mainBoard.add(testPanel);
				mainBoard.revalidate();
				mainBoard.repaint();
				//end of pegLoc test
				*/
				
					if(game.guestTurn){
						lastX = x;
						lastY = y;
						game.shootInfo="#!"+x;
						game.shootInfo+=y;
						clientGUI.sendMessage(game.shootInfo);
						game.shootInfo = "";
					}
					
			}
		});

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
		//This section shows the ships that can be dragged onto the users grid
		shipMenu.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
		shipMenu.setLayout(new GridBagLayout());

		GridBagConstraints gbc = new GridBagConstraints();        
		cliCarrierBox = new JLabel(carrier.getImg(),JLabel.CENTER);
		cliBattleshipBox = new JLabel(battleship.getImg(),JLabel.CENTER);
		cliCruiserBox = new JLabel(cruiser.getImg(),JLabel.CENTER);
		cliSubBox = new JLabel(submarine.getImg(),JLabel.CENTER);
		cliDestroyerBox = new JLabel(destroyer.getImg(),JLabel.CENTER);

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.CENTER;
		shipMenu.add(cliCarrierBox,gbc);

		gbc.gridy = 2;
		shipMenu.add(cliBattleshipBox,gbc);

		gbc.gridy = 3;
		shipMenu.add(cliCruiserBox,gbc);

		gbc.gridy = 5;
		shipMenu.add(cliSubBox,gbc);

		gbc.gridy = 7;
		shipMenu.add(cliDestroyerBox,gbc);
		
		randomShips.addActionListener(this);
		gbc.gridy = 0;
		shipMenu.add(randomShips,gbc);

		beginGame.addActionListener(this);
		gbc.gridy = 9;
		shipMenu.add(beginGame,gbc);
		
		
		beginGame.setEnabled(false);

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

		ds1.createDefaultDragGestureRecognizer(cliCarrierBox, DnDConstants.ACTION_COPY, dlistener);
		ds2.createDefaultDragGestureRecognizer(cliBattleshipBox, DnDConstants.ACTION_COPY, dlistener);
		ds3.createDefaultDragGestureRecognizer(cliCruiserBox, DnDConstants.ACTION_COPY, dlistener);
		ds4.createDefaultDragGestureRecognizer(cliSubBox, DnDConstants.ACTION_COPY, dlistener);
		ds5.createDefaultDragGestureRecognizer(cliDestroyerBox, DnDConstants.ACTION_COPY, dlistener);        
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

		textBox.setColumns(20);
		textBox.setRows(5);
		jScrollPane2.setViewportView(textBox);
		

		sendBtn.setText("Send");
		sendBtn.setEnabled(false);
		sendBtn.addActionListener(this);

		connectBtn.setText("Connect");
		connectBtn.addActionListener(this);
		userEnteredIP.setText("Host IP");
		userEnteredIP.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e){
				userEnteredIP.setText("");
			}
		});

		chatInput.setText("");

		javax.swing.GroupLayout jPanel2Layout = new GroupLayout(jPanel2);
		jPanel2.setLayout(jPanel2Layout);
		jPanel2Layout.setHorizontalGroup(
				jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(jPanel2Layout.createSequentialGroup()
						.addContainerGap()
						.addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 350, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addGroup(GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
										.addGap(0, 0, Short.MAX_VALUE)
										.addComponent(sendBtn))
										.addGroup(GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
												.addComponent(userEnteredIP, GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
												.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(connectBtn))
												.addGroup(jPanel2Layout.createSequentialGroup()
														.addComponent(chatInput)
														.addContainerGap())))
				);
		jPanel2Layout.setVerticalGroup(
				jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
								.addGroup(jPanel2Layout.createSequentialGroup()
										.addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
												.addComponent(connectBtn)
												.addComponent(userEnteredIP, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
												.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(chatInput, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(sendBtn))
												.addComponent(jScrollPane2))
												.addContainerGap())
				);

		javax.swing.GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addComponent(header, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addGroup(layout.createSequentialGroup()
										.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
												.addComponent(secondaryDisp, GroupLayout.DEFAULT_SIZE, 550, Short.MAX_VALUE)
												.addComponent(jPanel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
												.addComponent(mainBoard, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
												.addContainerGap())
				);
		layout.setVerticalGroup(
				layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
						.addContainerGap()
						.addComponent(header, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
								.addComponent(mainBoard, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addGroup(layout.createSequentialGroup()
										.addComponent(secondaryDisp,GroupLayout.PREFERRED_SIZE, 355, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(jPanel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
										.addContainerGap())
				);

		pack();
		this.setVisible(true);
	}                                                              

	public void run() {
		try{
			while(IP == null){
				continue;
			}
			sendBtn.setEnabled(true);
			Socket = new Socket(IP , 4444);
			oos = new ObjectOutputStream(Socket.getOutputStream());
			ois = new ObjectInputStream(Socket.getInputStream());
			connectBtn.setEnabled(false);
			userEnteredIP.setEditable(false);
			while(true){
				beginGame.setEnabled(true);
				Object input = ois.readObject();
				String text = input.toString();
				/*
				 * Anything that starts with #! will be considered a system message and as such
				 * it will need to be intercepted prior to being shown to the user. It will come here
				 * and the game logic will then be done.
				 */
				if(text.startsWith("#!")){
					text = text.substring(2);
					System.out.println("Client recieves from server :: "+text);
					if(text.equals("READY")){
						game.hostReady = true;
						textBox.setText(textBox.getText()+Game.getHostPlayer()+" is ready.\n");
					}else if(text.equals("GAMEOVER")){
						whatDidYouSink("You Win!");
						game.guestTurn = false;
						game.gameOver = true;
					}else if(text.equals("GRID")){
						myShipGrid.displayGrid();
					}else{
						if(text.charAt(0)=='H'||text.charAt(0)=='M'){
							paintHit(text);
							game.guestTurn = false;
							game.serverTurn = true;
						}else{
							int y = Character.getNumericValue(text.charAt(0));
							int x = Character.getNumericValue(text.charAt(1));
							if(myShipGrid.checkForHit(y, x)){
								game.playSound(2);
								placePeg('h',y,x);
								game.totalHitPoints--;
								setScore();
								if(game.totalHitPoints == 0){
									game.playSound(1);
									whatDidYouSink("LOSER");
									textBox.setText("SORRY!! You Lost!");
									game.gameOver = true;
									sendMessage("#!GAMEOVER");
								}
								char value = Constants.myGrid[x][y];
								switch (value){
								case 'C':
									game.carrierHitPoints--;
									break;
								case 'B':
									game.battleshipHitPoints--;
									break;
								case 'R':
									game.cruiserHitPoints--;
									break;
								case 'S':
									game.subHitPoints--;
									break;
								case 'D':
									game.destroyerHitPoints--;
									break;
								}
								game.returnInfo = "#!H"+value;
								Constants.myGrid[x][y] = 'X';
								if(game.checkForSunk(value)){
									game.returnInfo = game.returnInfo+"X";
									game.playSound(4);
								}else{
									game.returnInfo = game.returnInfo+"O";
								}
							}else{
								game.playSound(3);
								placePeg('m',y,x);
								game.returnInfo = "#!M";
							}
							sendMessage(game.returnInfo);
							game.returnInfo="";
							game.serverTurn = false;
							game.guestTurn = true;	
						}
						
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
	private void placePeg(char s, int x, int y) {
		Icon ico;
		if(s == 'h'){
			ico = (Icon) new ImageIcon(Constants.REDPEG);
		}else{
			ico = (Icon) new ImageIcon(Constants.WHITEPEG);
		}
		int z = myShipGrid.getXPx(x);
		int w = myShipGrid.getYPx(y);
		JLabel peg = new JLabel(ico);
		peg.setBounds(z, w, ico.getIconWidth(), ico.getIconHeight());
		smallGrid.add(peg, 0);
		smallGrid.revalidate();
		smallGrid.repaint();
		
	}

	static public void disableClientCarrierBox(){
		cliCarrierBox.setEnabled(false);
	}
	static public void disableClientBattleshipBox(){
		cliBattleshipBox.setEnabled(false);
	}
	static public void disableClientCruiserBox(){
		cliCruiserBox.setEnabled(false);
	}
	static public void disableClientSubBox(){
		cliSubBox.setEnabled(false);
	}
	static public void disableClientDestroyerBox(){
		cliDestroyerBox.setEnabled(false);
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
		if (ae.getActionCommand().equals("Connect")){
			IP = userEnteredIP.getText();		
			beginGame.setEnabled(true);
		}else if (ae.getActionCommand().equals("Send")){
			try{
				oos.writeObject(chatInput.getText());
				textBox.setText(textBox.getText()+Game.getGuestPlayer() +": "+ chatInput.getText()+"\n");
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
		}else if(ae.getActionCommand().equals("Random")){
			placeRandomShips();
			randomShips.setEnabled(false);
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
	public void keyReleased(KeyEvent e) {		
	}

	@Override
	public void keyTyped(KeyEvent e) {	
	}
	
    private void paintHit(String input) {
    	Icon ico;
    	char hitOrMiss = input.charAt(0);
    	int x,y;
    	
    	if(hitOrMiss == 'H'){
    		ico = new ImageIcon(Constants.REDPEG);
        	char hitShip = input.charAt(1);
        	char sunk = input.charAt(2);
        	game.totalEnemyPoints--;
        	setScore();
        	if(game.totalEnemyPoints == 0){
        		game.gameOver = true;
        	}
        	if(sunk == 'X'){
        		switch (hitShip){
        		case 'C':
        			whatDidYouSink("Carrier Sunk");
        			break;
        		case 'B':
        			whatDidYouSink("Battleship Sunk");
        			break;
        		case 'R':
        			whatDidYouSink("Cruiser Sunk");
        			break;
        		case 'S':
        			whatDidYouSink("Submarine Sunk");
        			//sub sunk
        			break;
        		case 'D':
        			whatDidYouSink("Destroyer Sunk");
        			break;
        		}
        	}
    	}else{
    		ico = new ImageIcon(Constants.WHITEPEG);
    	}
		x = myShipGrid.getBIGxPx(lastX);
		y = myShipGrid.getBIGyPx(lastY);
    	
		JLabel hit = new JLabel(ico);
		hit.setBounds(x,y,ico.getIconWidth(),ico.getIconHeight());
		mainBoard.add(hit);
		mainBoard.revalidate();
		mainBoard.repaint();
	}
    
    static public void createScorePanel(){
    	shipMenu.removeAll();
    	scoreBox = new ImageIcon(Constants.SCOREBOX);
    	scoreLabel = new JLabel(scoreBox);
    	scoreLabel.setBounds(0, 0, scoreBox.getIconWidth(), scoreBox.getIconHeight());
    	shipMenu.add(scoreLabel);
    	shipMenu.revalidate();
    	shipMenu.repaint();
    	enemyHitPoints = new JLabel();
    	yourHitPoints = new JLabel();
    	youSunk = new JLabel();
    	
    	enemyHitPoints.setBounds(140, 115, 200, 30);
    	yourHitPoints.setBounds(140, 150, 200, 30);
    	youSunk.setBounds(15, 230, 200, 30);
    	
    	shipMenu.setLayout(null);
    	shipMenu.add(enemyHitPoints,0);
    	shipMenu.add(yourHitPoints,1);
    	shipMenu.add(youSunk,0);
    	
    	//This allows us to use our own font to keep everything similar looking
    	Font myFont = null;
    	File fontFile = new File(Constants.FONT);
    	try {
			myFont = Font.createFont(Font.TRUETYPE_FONT, fontFile).
					deriveFont(Font.PLAIN,22f);
		} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    	ge.registerFont(myFont);
    	enemyHitPoints.setFont(myFont);
    	yourHitPoints.setFont(myFont);
    	youSunk.setFont(myFont);
    	setScore();
    }
    static private void setScore(){
    	enemyHitPoints.setText(Integer.toString(game.totalEnemyPoints));
    	yourHitPoints.setText(Integer.toString(game.totalHitPoints));
    }
    static private void whatDidYouSink(String msg){
    	youSunk.setText(msg);
    }
    private void placeRandomShips(){
    	JLabel carrier,battleship,cruiser,sub,destroyer;
    	Random rand = new Random();
    	int xRand,yRand,vertNum;
    	ImageIcon ico;
    	int xPx = 0,yPx = 0;
    	
    	while(myShipGrid.carrierPlaced == false){
    		vertNum = rand.nextInt(2);
        	if(vertNum == 0){
        		xRand = rand.nextInt(6);
        		yRand = rand.nextInt(10);
        		ico = new ImageIcon(Constants.CARRIER);
        		
            	switch (xRand){
            	case 0:
            		xPx = 7;
            		break;
            	case 1:
            		xPx = 36;
            		break;
            	case 2:
            		xPx = 70;
            		break;
            	case 3:
            		xPx = 105;
            		break;
            	case 4:
            		xPx = 135;
            		break;
            	case 5:
            		xPx = 167;
            		break;
            	}
            	switch (yRand){
            	case 0:
            		yPx = -3;
            		break;
            	case 1:
            		yPx = 30;
            		break;
            	case 2:
            		yPx = 62;
            		break;
            	case 3:
            		yPx = 95;
            		break;
            	case 4:
            		yPx = 126;
            		break;
            	case 5:
            		yPx = 160;
            		break;
            	case 6:
            		yPx = 192;
            		break;
            	case 7:
            		yPx = 225;
            		break;
            	case 8:
            		yPx = 257;
            		break;
            	case 9:
            		yPx = 290;
            		break;
            	}
            	myShipGrid.carrierLocation[0]=xRand;
    			myShipGrid.carrierLocation[1]=yRand;
            	if(myShipGrid.check('h', 5, myShipGrid.carrierLocation)){
            		myShipGrid.carrierPlaced = true;
    				int temp = xRand;
    				for(int i=xRand;i<xRand+5;i++){
    					Constants.myGrid[yRand][temp] = 'C';
    					temp++;
    				}
    				clientGUI.disableClientCarrierBox();
    				carrier = new JLabel(ico);
    				carrier.setBounds(xPx,yPx,ico.getIconWidth(),ico.getIconHeight());
    				smallGrid.add(carrier);
    				smallGrid.repaint();
    				
            	}
        	}else{
        		ico = new ImageIcon(Constants.CARRIERVERT);
        		xRand = rand.nextInt(10);
        		yRand = rand.nextInt(6);
        		
            	switch (xRand){
            	case 0:
            		xPx = -6;
            		break;
            	case 1:
            		xPx = 26;
            		break;
            	case 2:
            		xPx = 59;
            		break;
            	case 3:
            		xPx = 92;
            		break;
            	case 4:
            		xPx = 125;
            		break;
            	case 5:
            		xPx = 157;
            		break;
            	case 6:
            		xPx = 190;
            		break;
            	case 7:
            		xPx = 221;
            		break;
            	case 8:
            		xPx = 254;
            		break;
            	case 9:
            		xPx = 286;
            		break;
            	}
            	
            	switch (yRand){
            	case 0:
            		yPx = 2;
            		break;
            	case 1:
            		yPx = 34;
            		break;
            	case 2:
            		yPx = 66;
            		break;
            	case 3:
            		yPx = 98;
            		break;
            	case 4:
            		yPx = 131;
            		break;
            	case 5:
            		yPx = 164;
            		break;
            	}
            	myShipGrid.carrierLocation[0]=xRand;
    			myShipGrid.carrierLocation[1]=yRand;
            	if(myShipGrid.check('v', 5, myShipGrid.carrierLocation)){
            		myShipGrid.carrierPlaced = true;
    				int temp = yRand;
    				for(int i=yRand;i<yRand+5;i++){
    					Constants.myGrid[temp][xRand] = 'C';
    					temp++;
    				}
    				clientGUI.disableClientCarrierBox();
    				carrier = new JLabel(ico);
    				carrier.setBounds(xPx,yPx,ico.getIconWidth(),ico.getIconHeight());
    				smallGrid.add(carrier);
    				smallGrid.repaint();
            	}
        	}
    	}	
    	while(myShipGrid.battleshipPlaced == false){
    		vertNum = rand.nextInt(2);
        	if(vertNum == 0){
        		xRand = rand.nextInt(7);
        		yRand = rand.nextInt(10);
        		ico = new ImageIcon(Constants.BATTLESHIP);
        		
            	switch (xRand){
            	case 0:
            		xPx = 7;
            		break;
            	case 1:
            		xPx = 36;
            		break;
            	case 2:
            		xPx = 70;
            		break;
            	case 3:
            		xPx = 105;
            		break;
            	case 4:
            		xPx = 135;
            		break;
            	case 5:
            		xPx = 167;
            		break;
            	case 6:
            		xPx = 200;
            		break;
            	}
            	switch (yRand){
            	case 0:
            		yPx = 1;
            		break;
            	case 1:
            		yPx = 34;
            		break;
            	case 2:
            		yPx = 66;
            		break;
            	case 3:
            		yPx = 99;
            		break;
            	case 4:
            		yPx = 131;
            		break;
            	case 5:
            		yPx = 164;
            		break;
            	case 6:
            		yPx = 196;
            		break;
            	case 7:
            		yPx = 229;
            		break;
            	case 8:
            		yPx = 261;
            		break;
            	case 9:
            		yPx = 293;
            		break;
            	}
            	myShipGrid.battleshipLocation[0]=xRand;
    			myShipGrid.battleshipLocation[1]=yRand;
            	if(myShipGrid.check('h', 4, myShipGrid.battleshipLocation)){
            		myShipGrid.battleshipPlaced = true;
    				int temp = xRand;
    				for(int i=xRand;i<xRand+4;i++){
    					Constants.myGrid[yRand][temp] = 'B';
    					temp++;
    				}
    				clientGUI.disableClientBattleshipBox();
    				battleship = new JLabel(ico);
    				battleship.setBounds(xPx,yPx,ico.getIconWidth(),ico.getIconHeight());
    				smallGrid.add(battleship);
    				smallGrid.repaint();
    				
            	}
        	}else{
        		ico = new ImageIcon(Constants.BATTLESHIPVERT);
        		xRand = rand.nextInt(10);
        		yRand = rand.nextInt(7);
        		
            	switch (xRand){
            	case 0:
            		xPx = 0;
            		break;
            	case 1:
            		xPx = 32;
            		break;
            	case 2:
            		xPx = 65;
            		break;
            	case 3:
            		xPx = 98;
            		break;
            	case 4:
            		xPx = 131;
            		break;
            	case 5:
            		xPx = 163;
            		break;
            	case 6:
            		xPx = 196;
            		break;
            	case 7:
            		xPx = 227;
            		break;
            	case 8:
            		xPx = 260;
            		break;
            	case 9:
            		xPx = 292;
            		break;
            	}
            	
            	switch (yRand){
            	case 0:
            		yPx = 5;
            		break;
            	case 1:
            		yPx = 37;
            		break;
            	case 2:
            		yPx = 69;
            		break;
            	case 3:
            		yPx = 101;
            		break;
            	case 4:
            		yPx = 134;
            		break;
            	case 5:
            		yPx = 167;
            		break;
            	case 6:
            		yPx = 197;
            		break;
            	}
            	myShipGrid.battleshipLocation[0]=xRand;
    			myShipGrid.battleshipLocation[1]=yRand;
            	if(myShipGrid.check('v', 4, myShipGrid.battleshipLocation)){
            		myShipGrid.battleshipPlaced = true;
    				int temp = yRand;
    				for(int i=yRand;i<yRand+4;i++){
    					Constants.myGrid[temp][xRand] = 'B';
    					temp++;
    				}
    				disableClientBattleshipBox();
    				battleship = new JLabel(ico);
    				battleship.setBounds(xPx,yPx,ico.getIconWidth(),ico.getIconHeight());
    				smallGrid.add(battleship);
    				smallGrid.repaint();
            	}
        	}	
    	}
    	while(myShipGrid.cruiserPlaced == false){
    		vertNum = rand.nextInt(2);
        	if(vertNum == 0){
        		xRand = rand.nextInt(8);
        		yRand = rand.nextInt(10);
        		ico = new ImageIcon(Constants.CRUISER);
        		
            	switch (xRand){
            	case 0:
            		xPx = 7;
            		break;
            	case 1:
            		xPx = 36;
            		break;
            	case 2:
            		xPx = 70;
            		break;
            	case 3:
            		xPx = 105;
            		break;
            	case 4:
            		xPx = 135;
            		break;
            	case 5:
            		xPx = 167;
            		break;
            	case 6:
            		xPx = 200;
            		break;
            	case 7:
            		xPx = 230;
            		break;
            	case 8:
            		xPx = 257;
            		break;
            	}
            	switch (yRand){
            	case 0:
            		yPx = 3;
            		break;
            	case 1:
            		yPx = 36;
            		break;
            	case 2:
            		yPx = 68;
            		break;
            	case 3:
            		yPx = 101;
            		break;
            	case 4:
            		yPx = 133;
            		break;
            	case 5:
            		yPx = 166;
            		break;
            	case 6:
            		yPx = 198;
            		break;
            	case 7:
            		yPx = 231;
            		break;
            	case 8:
            		yPx = 263;
            		break;
            	case 9:
            		yPx = 295;
            		break;
            	}
            	myShipGrid.cruiserLocation[0]=xRand;
    			myShipGrid.cruiserLocation[1]=yRand;
            	if(myShipGrid.check('h', 3, myShipGrid.cruiserLocation)){
            		myShipGrid.cruiserPlaced = true;
    				int temp = xRand;
    				for(int i=xRand;i<xRand+3;i++){
    					Constants.myGrid[yRand][temp] = 'R';
    					temp++;
    				}
    				clientGUI.disableClientCruiserBox();
    				cruiser = new JLabel(ico);
    				cruiser.setBounds(xPx,yPx,ico.getIconWidth(),ico.getIconHeight());
    				smallGrid.add(cruiser);
    				smallGrid.repaint();
    				
            	}
        	}else{
        		ico = new ImageIcon(Constants.CRUISERVERT);
        		xRand = rand.nextInt(10);
        		yRand = rand.nextInt(8);
        		
            	switch (xRand){
            	case 0:
            		xPx = 5;
            		break;
            	case 1:
            		xPx = 37;
            		break;
            	case 2:
            		xPx = 70;
            		break;
            	case 3:
            		xPx = 103;
            		break;
            	case 4:
            		xPx = 136;
            		break;
            	case 5:
            		xPx = 168;
            		break;
            	case 6:
            		xPx = 201;
            		break;
            	case 7:
            		xPx = 232;
            		break;
            	case 8:
            		xPx = 265;
            		break;
            	case 9:
            		xPx = 297;
            		break;
            	}
            	
            	switch (yRand){
            	case 0:
            		yPx = 5;
            		break;
            	case 1:
            		yPx = 37;
            		break;
            	case 2:
            		yPx = 69;
            		break;
            	case 3:
            		yPx = 101;
            		break;
            	case 4:
            		yPx = 134;
            		break;
            	case 5:
            		yPx = 169;
            		break;
            	case 6:
            		yPx = 199;
            		break;
            	case 7:
            		yPx = 231;
            		break;
            	}
            	myShipGrid.cruiserLocation[0]=xRand;
    			myShipGrid.cruiserLocation[1]=yRand;
            	if(myShipGrid.check('v', 3, myShipGrid.cruiserLocation)){
            		myShipGrid.cruiserPlaced = true;
    				int temp = yRand;
    				for(int i=yRand;i<yRand+3;i++){
    					Constants.myGrid[temp][xRand] = 'R';
    					temp++;
    				}
    				disableClientCruiserBox();
    				cruiser = new JLabel(ico);
    				cruiser.setBounds(xPx,yPx,ico.getIconWidth(),ico.getIconHeight());
    				smallGrid.add(cruiser);
    				smallGrid.repaint();
            	}
        	}
    	}
    	
    	while(myShipGrid.subPlaced == false){
    		vertNum = rand.nextInt(2);
        	if(vertNum == 0){
        		xRand = rand.nextInt(8);
        		yRand = rand.nextInt(10);
        		ico = new ImageIcon(Constants.SUBMARINE);
        		
            	switch (xRand){
            	case 0:
            		xPx = 7;
            		break;
            	case 1:
            		xPx = 36;
            		break;
            	case 2:
            		xPx = 70;
            		break;
            	case 3:
            		xPx = 105;
            		break;
            	case 4:
            		xPx = 135;
            		break;
            	case 5:
            		xPx = 167;
            		break;
            	case 6:
            		xPx = 200;
            		break;
            	case 7:
            		xPx = 230;
            		break;
            	}
            	switch (yRand){
            	case 0:
            		yPx = 3;
            		break;
            	case 1:
            		yPx = 36;
            		break;
            	case 2:
            		yPx = 68;
            		break;
            	case 3:
            		yPx = 101;
            		break;
            	case 4:
            		yPx = 133;
            		break;
            	case 5:
            		yPx = 166;
            		break;
            	case 6:
            		yPx = 198;
            		break;
            	case 7:
            		yPx = 231;
            		break;
            	case 8:
            		yPx = 263;
            		break;
            	case 9:
            		yPx = 295;
            		break;
            	}
            	myShipGrid.subLocation[0]=xRand;
    			myShipGrid.subLocation[1]=yRand;
            	if(myShipGrid.check('h', 3, myShipGrid.subLocation)){
            		myShipGrid.subPlaced = true;
    				int temp = xRand;
    				for(int i=xRand;i<xRand+3;i++){
    					Constants.myGrid[yRand][temp] = 'S';
    					temp++;
    				}
    				clientGUI.disableClientSubBox();
    				sub = new JLabel(ico);
    				sub.setBounds(xPx,yPx,ico.getIconWidth(),ico.getIconHeight());
    				smallGrid.add(sub);
    				smallGrid.repaint();
    				
            	}
        	}else{
        		ico = new ImageIcon(Constants.SUBMARINEVERT);
        		xRand = rand.nextInt(10);
        		yRand = rand.nextInt(8);
        		
            	switch (xRand){
            	case 0:
            		xPx = 5;
            		break;
            	case 1:
            		xPx = 37;
            		break;
            	case 2:
            		xPx = 70;
            		break;
            	case 3:
            		xPx = 103;
            		break;
            	case 4:
            		xPx = 134;
            		break;
            	case 5:
            		xPx = 168;
            		break;
            	case 6:
            		xPx = 199;
            		break;
            	case 7:
            		xPx = 232;
            		break;
            	case 8:
            		xPx = 265;
            		break;
            	case 9:
            		xPx = 297;
            		break;
            	}
            	
            	switch (yRand){
            	case 0:
            		yPx = 5;
            		break;
            	case 1:
            		yPx = 37;
            		break;
            	case 2:
            		yPx = 69;
            		break;
            	case 3:
            		yPx = 101;
            		break;
            	case 4:
            		yPx = 134;
            		break;
            	case 5:
            		yPx = 169;
            		break;
            	case 6:
            		yPx = 199;
            		break;
            	case 7:
            		yPx = 231;
            		break;
            	}
            	myShipGrid.subLocation[0]=xRand;
    			myShipGrid.subLocation[1]=yRand;
            	if(myShipGrid.check('v', 3, myShipGrid.subLocation)){
            		myShipGrid.subPlaced = true;
    				int temp = yRand;
    				for(int i=yRand;i<yRand+3;i++){
    					Constants.myGrid[temp][xRand] = 'S';
    					temp++;
    				}
    				disableClientSubBox();
    				sub = new JLabel(ico);
    				sub.setBounds(xPx,yPx,ico.getIconWidth(),ico.getIconHeight());
    				smallGrid.add(sub);
    				smallGrid.repaint();
            	}
        	}	
    	}
    	while(myShipGrid.destroyerPlaced == false){
    		vertNum = rand.nextInt(2);
        	if(vertNum == 0){
        		xRand = rand.nextInt(8);
        		yRand = rand.nextInt(10);
        		ico = new ImageIcon(Constants.DESTROYER);
        		
            	switch (xRand){
            	case 0:
            		xPx = 7;
            		break;
            	case 1:
            		xPx = 36;
            		break;
            	case 2:
            		xPx = 70;
            		break;
            	case 3:
            		xPx = 105;
            		break;
            	case 4:
            		xPx = 135;
            		break;
            	case 5:
            		xPx = 167;
            		break;
            	case 6:
            		xPx = 200;
            		break;
            	case 7:
            		xPx = 230;
            		break;
            	}
            	switch (yRand){
            	case 0:
            		yPx = 3;
            		break;
            	case 1:
            		yPx = 36;
            		break;
            	case 2:
            		yPx = 68;
            		break;
            	case 3:
            		yPx = 101;
            		break;
            	case 4:
            		yPx = 133;
            		break;
            	case 5:
            		yPx = 166;
            		break;
            	case 6:
            		yPx = 198;
            		break;
            	case 7:
            		yPx = 231;
            		break;
            	case 8:
            		yPx = 263;
            		break;
            	case 9:
            		yPx = 295;
            		break;
            	}
            	myShipGrid.destroyerLocation[0]=xRand;
    			myShipGrid.destroyerLocation[1]=yRand;
            	if(myShipGrid.check('h', 2, myShipGrid.destroyerLocation)){
            		myShipGrid.destroyerPlaced = true;
    				int temp = xRand;
    				for(int i=xRand;i<xRand+2;i++){
    					Constants.myGrid[yRand][temp] = 'D';
    					temp++;
    				}
    				clientGUI.disableClientDestroyerBox();
    				destroyer = new JLabel(ico);
    				destroyer.setBounds(xPx,yPx,ico.getIconWidth(),ico.getIconHeight());
    				smallGrid.add(destroyer);
    				smallGrid.repaint();
    				
            	}
        	}else{
        		ico = new ImageIcon(Constants.DESTROYERVERT);
        		xRand = rand.nextInt(10);
        		yRand = rand.nextInt(8);
        		
            	switch (xRand){
            	case 0:
            		xPx = 3;
            		break;
            	case 1:
            		xPx = 36;
            		break;
            	case 2:
            		xPx = 69;
            		break;
            	case 3:
            		xPx = 101;
            		break;
            	case 4:
            		xPx = 132;
            		break;
            	case 5:
            		xPx = 166;
            		break;
            	case 6:
            		xPx = 197;
            		break;
            	case 7:
            		xPx = 230;
            		break;
            	case 8:
            		xPx = 263;
            		break;
            	case 9:
            		xPx = 295;
            		break;
            	}
            	
            	switch (yRand){
            	case 0:
            		yPx = 1;
            		break;
            	case 1:
            		yPx = 33;
            		break;
            	case 2:
            		yPx = 65;
            		break;
            	case 3:
            		yPx = 97;
            		break;
            	case 4:
            		yPx = 130;
            		break;
            	case 5:
            		yPx = 165;
            		break;
            	case 6:
            		yPx = 195;
            		break;
            	case 7:
            		yPx = 227;
            		break;
            	case 8:
            		yPx = 257;
            		break;
            	}
            	myShipGrid.destroyerLocation[0]=xRand;
    			myShipGrid.destroyerLocation[1]=yRand;
            	if(myShipGrid.check('v', 2, myShipGrid.destroyerLocation)){
            		myShipGrid.destroyerPlaced = true;
    				int temp = yRand;
    				for(int i=yRand;i<yRand+2;i++){
    					Constants.myGrid[temp][xRand] = 'D';
    					temp++;
    				}
    				disableClientDestroyerBox();
    				destroyer = new JLabel(ico);
    				destroyer.setBounds(xPx,yPx,ico.getIconWidth(),ico.getIconHeight());
    				smallGrid.add(destroyer);
    				smallGrid.repaint();
            	}
        	}	
    	}
    	myShipGrid.displayGrid();
    }
}