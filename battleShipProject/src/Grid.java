

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Grid extends JFrame {
	private Square[][] playerGrid; // board
	//if which == 0 we are looking at the enemy board
	//if which == 1 we are looking at our own board
	private int which;
	private int boxSize;
	JTextArea textArea;

	public Grid(JPanel playerPanel,JTextArea textBox,int width,int which){ 
		textArea = textBox;
		boxSize = width;
		//playerPanel = new JPanel(); // set up panel for squares in board
	    playerPanel.setLayout( new GridLayout( 10, 10, 0, 0 ) );	
	    playerGrid = new Square[10][10]; // create Grid
	    
	    //playerPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Player Board"));
	    // loop over the rows in the board
	    for ( int row = 0; row < playerGrid.length; row++ ) 
	    {
	       // loop over the columns in the board
	       for ( int column = 0; column < playerGrid[ row ].length; column++ ) 
	       {
	          // create square
	          playerGrid[ row ][ column ] = new Square( " ", row, column);
	          playerPanel.add( playerGrid[ row ][ column ] ); // add square
	          new MyDropTargetListener(playerGrid[row][column]);
	          
	       } // end inner for
	    } // end outer for

	      
	  //  setVisible( true ); // show window
	      
	}
	
	   // private inner class for the squares on the board
	   private class Square extends JPanel 
	   {
	      private String contents; // mark to be drawn in this square
	      private int xCord;
	      private int yCord;// location of square
	   
	      public Square( String squareContents, int x, int y )
	      {
	    	  contents = squareContents; // set mark for this square
	    	 
	    	  xCord = x;// set location of this square 
	    	  yCord = y;// set location of this square

	         addMouseListener( 
	            new MouseAdapter() 
	            {
	               public void mouseReleased( MouseEvent e )
	               {
	                  setCurrentSquare( Square.this ); // set current square

	               } // end method mouseReleased

				private Object getSquareLocation() {
					Square curr = new Square(contents, xCord, yCord);
					return curr;
				}

				private void setCurrentSquare(Square square) {
					if (which == 0){
						game.shootInfo="#!";
						game.shootInfo = game.shootInfo + xCord+yCord;
						textArea.setText(textArea.getText() +game.shootInfo + "\n");
					}
				}
	            } // end anonymous inner class
	         ); // end call to addMouseListener
	         	      
	      } // end Square constructor


	      // return preferred size of Square
	      public Dimension getPreferredSize() 
	      { 
	         return new Dimension( boxSize, boxSize ); // return preferred size
	      } // end method getPreferredSize

	      // return minimum size of Square
	      public Dimension getMinimumSize() 
	      {
	         return getPreferredSize(); // return preferred size
	      } // end method getMinimumSize

	      // draw Square
	      public void paintComponent( Graphics g )
	      {
	         super.paintComponent( g );

	         g.drawRect( 0, 0, boxSize-1, boxSize-1 ); // draw square
	         //g.drawString( contents, 11, 20 ); // draw mark   
	      } // end method paintComponent
	   } // end inner-class Square
	
}
