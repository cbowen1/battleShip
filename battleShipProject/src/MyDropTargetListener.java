/**************************************
 * MyDropTargetListener.java
 * Cale Bowen and Ryan Mulligan
 **************************************/

import java.awt.Component;
import java.awt.Point;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

class MyDropTargetListener extends DropTargetAdapter {

	private DropTarget dropTarget;
	private Point dropPoint;
	private JPanel p;

	public MyDropTargetListener(JPanel dropPanel) {
		p = dropPanel;
		dropTarget = new DropTarget(dropPanel, DnDConstants.ACTION_COPY, this, true, null);
	}

	@Override
	public void drop(DropTargetDropEvent event) {
		try {
			dropTarget = (DropTarget) event.getSource();
			Component ca = (Component) dropTarget.getComponent();
			dropPoint = ca.getMousePosition();

			//data that is transfered to new panel (ship image file)
			Transferable tr = event.getTransferable();

			//checks that the data being dragged is an imageFlavor
			if (event.isDataFlavorSupported(DataFlavor.imageFlavor)) {
				Icon ico = (Icon) tr.getTransferData(DataFlavor.imageFlavor);
				// turns icon into a string to check which ship was dragged and dropped
				String droppedShip = ico.toString();
				if (ico != null) {
					if(droppedShip == Constants.CARRIER){
						if(Ship.orientation == 'v'){			// if orientation is vertical, 
							ico = (Icon) new ImageIcon(Constants.CARRIERVERT);     //set ico to the vertical image
						}
						dropPoint = placeCarrier(ico,dropPoint,p,event);
					}else if(droppedShip == Constants.BATTLESHIP){
						if(Ship.orientation == 'v'){
							ico = (Icon) new ImageIcon(Constants.BATTLESHIPVERT);
						}
						dropPoint = placeBattleship(ico,dropPoint,p,event);
					}else if(droppedShip == Constants.CRUISER){
						if(Ship.orientation == 'v'){
							ico = (Icon) new ImageIcon(Constants.CRUISERVERT);
						}
						dropPoint = placeCruiser(ico,dropPoint,p,event);
					}else if(droppedShip == Constants.DESTROYER){
						if(Ship.orientation == 'v'){
							ico = (Icon) new ImageIcon(Constants.DESTROYERVERT);
						}
						dropPoint = placeDestroyer(ico,dropPoint,p,event);
					}else if(droppedShip == Constants.SUBMARINE){
						if(Ship.orientation == 'v'){
							ico = (Icon) new ImageIcon(Constants.SUBMARINEVERT);
						}
						dropPoint = placeSub(ico,dropPoint,p,event);
					}	
					if(dropPoint != null){
						JLabel ship = new JLabel(ico);
						ship.setBounds(dropPoint.x, dropPoint.y, ico.getIconWidth(), ico.getIconHeight());

						p.add(ship);			// add ship to the board
						p.revalidate();
						p.repaint();
						event.dropComplete(true);
					}
				}
			} else {			// if they don't drag to board, don't move the ship
				event.rejectDrop();
			}
		} catch (Exception e) {
			e.printStackTrace();
			event.rejectDrop();
		}

	}

	private Point placeCarrier(Icon ico, Point dropPoint2, JPanel p2, DropTargetDropEvent event) {
		if(myShipGrid.carrierPlaced){
			return null;
		}else{
			int xPx = dropPoint.x;
			int yPx = dropPoint.y;
			int xGrid=0;
			int yGrid=0;
			if(Ship.orientation == 'v'){
				// snaps ship to center of x axis
				if(xPx > 292){
					dropPoint.x = 286;
					xGrid = 9;
				}else if(xPx>261){
					dropPoint.x = 254;
					xGrid = 8;
				}else if(xPx>231){
					dropPoint.x = 221;
					xGrid = 7;
				}else if(xPx>201){
					dropPoint.x = 190;
					xGrid = 6;
				}else if(xPx>171){
					dropPoint.x = 157;
					xGrid = 5;
				}else if(xPx>131){
					dropPoint.x = 125;
					xGrid = 4;
				}else if(xPx>97){
					dropPoint.x = 92;
					xGrid = 3;
				}else if(xPx>66){
					dropPoint.x = 59;
					xGrid = 2;
				}else if(xPx > 26) {
					dropPoint.x = 26;
					xGrid = 1;
				}else{
					dropPoint.x = -6;
					xGrid = 0;
				}
				// snaps ship to center of y axis
				if(yPx < 30){
					dropPoint.y = 2;
					yGrid = 0;
				}else if(yPx < 64){
					dropPoint.y = 34;
					yGrid = 1;
				}else if(yPx < 96){
					dropPoint.y = 66;
					yGrid = 2;
				}else if(yPx < 129){
					dropPoint.y = 98;
					yGrid = 3;
				}else if(yPx < 161){
					dropPoint.y = 131;
					yGrid = 4;
				}else{
					dropPoint.y = 164;
					yGrid = 5;
				}
				myShipGrid.carrierLocation[0]=xGrid;
				myShipGrid.carrierLocation[1]=yGrid;
				if(!myShipGrid.check('v', 5, myShipGrid.carrierLocation)){		// if there is a ship already there
					return null;
				}else{													// if there is not a ship already there
					int temp = yGrid;
					for(int i=yGrid;i<yGrid+5;i++){
						Constants.myGrid[temp][xGrid] = 'C';			// set positions of C on the grid
						temp++;
					}
					myShipGrid.carrierPlaced = true;
					if(game.getRunner()=="client"){						// check if this was client or server
						clientGUI.disableClientCarrierBox();			// disable setting of client ship
					}else{												
						serverGUI.disableCarrierBox();					// disable setting of server ship
					}
					return dropPoint;
				}
			}else{

				//snaps the ship to the center of the x axis
				if(xPx>162){
					dropPoint.x = 167;
					xGrid = 5;
				}else if(xPx > 133){
					xGrid = 4;
					dropPoint.x = 135;
				}else if(xPx > 100){
					xGrid = 3;
					dropPoint.x = 105;
				}else if (xPx > 66){
					xGrid = 2;
					dropPoint.x = 70;
				}else if (xPx > 33){
					xGrid = 1;
					dropPoint.x = 36; 
				}else{
					xGrid = 0;
					dropPoint.x = 7;
				}
				//this will snap the ship to the center of the y axis
				if(yPx < 34){
					dropPoint.y = -3;
					yGrid = 0;
				}else if(yPx < 64){
					yGrid = 1;
					dropPoint.y = 30;
				}else if(yPx < 97){
					yGrid = 2;
					dropPoint.y = 62;
				}else if(yPx < 129){
					yGrid = 3;
					dropPoint.y = 95;
				}else if(yPx < 161){
					yGrid = 4;
					dropPoint.y = 126;
				}else if(yPx < 194){
					yGrid = 5;
					dropPoint.y = 160;
				}else if(yPx < 225){
					yGrid = 6;
					dropPoint.y = 192;
				}else if(yPx < 259){
					yGrid = 7;
					dropPoint.y = 225;
				}else if(yPx < 291){
					yGrid = 8;
					dropPoint.y = 257;
				}else{
					yGrid = 9;
					dropPoint.y = 290;
				}	
				myShipGrid.carrierLocation[0]=xGrid;
				myShipGrid.carrierLocation[1]=yGrid;
				if(!myShipGrid.check('h', 5, myShipGrid.carrierLocation)){
					return null;
				}else{
					myShipGrid.carrierPlaced = true;
					int temp = xGrid;
					for(int i=xGrid;i<xGrid+5;i++){
						Constants.myGrid[yGrid][temp] = 'C';
						temp++;
					}
					if(game.getRunner()=="client"){
						clientGUI.disableClientCarrierBox();
					}else{
						serverGUI.disableCarrierBox();
					}
					return dropPoint;
				}
			}
		}

	}
	private Point placeBattleship(Icon ico, Point dropPoint2, JPanel p2, DropTargetDropEvent event) {
		if(myShipGrid.battleshipPlaced){
			return null;
		}else{
			int xGrid=0;
			int yGrid=0;
			int xPx = dropPoint.x;
			int yPx = dropPoint.y;
			if(Ship.orientation == 'v'){
				if(xPx > 292){
					dropPoint.x = 292;
					xGrid = 9;
				}else if(xPx>261){
					dropPoint.x = 260;
					xGrid = 8;
				}else if(xPx>231){
					dropPoint.x = 227;
					xGrid = 7;
				}else if(xPx>201){
					dropPoint.x = 196;
					xGrid = 6;
				}else if(xPx>171){
					dropPoint.x = 163;
					xGrid = 5;
				}else if(xPx>131){
					dropPoint.x = 131;
					xGrid = 4;
				}else if(xPx>97){
					dropPoint.x = 98;
					xGrid = 3;
				}else if(xPx>66){
					dropPoint.x = 65;
					xGrid = 2;
				}else if(xPx > 26) {
					dropPoint.x = 32;
					xGrid = 1;
				}else{
					dropPoint.x = 0;
					xGrid = 0;
				}
				if(yPx < 30){
					dropPoint.y = 5;
					yGrid = 0;
				}else if(yPx < 64){
					dropPoint.y = 37;
					yGrid = 1;
				}else if(yPx < 96){
					dropPoint.y = 69;
					yGrid = 2;
				}else if(yPx < 129){
					dropPoint.y = 101;
					yGrid = 3;
				}else if(yPx < 161){
					dropPoint.y = 134;
					yGrid = 4;
				}else if(yPx < 196){
					dropPoint.y = 167;
					yGrid = 5;
				}else{
					dropPoint.y = 197;
					yGrid = 6;
				}
				myShipGrid.battleshipLocation[0]=xGrid;
				myShipGrid.battleshipLocation[1]=yGrid;
				if(!myShipGrid.check('v', 4, myShipGrid.battleshipLocation)){
					return null;
				}else{
					int temp = yGrid;
					for(int i=yGrid;i<yGrid+4;i++){
						Constants.myGrid[temp][xGrid] = 'B';
						temp++;
					}
					myShipGrid.battleshipPlaced = true;
					if(game.getRunner()=="client"){
						clientGUI.disableClientBattleshipBox();
					}else{
						serverGUI.disableBattleshipBox();
					}
					return dropPoint;
				}			
			}else{
				if(xPx > 195){
					dropPoint.x = 200;
					xGrid = 6;
				}else if(xPx>162){
					dropPoint.x = 167;
					xGrid = 5;
				}else if(xPx > 133){
					xGrid = 4;
					dropPoint.x = 135;
				}else if(xPx > 100){
					xGrid = 3;
					dropPoint.x = 105;
				}else if (xPx > 66){
					xGrid = 2;
					dropPoint.x = 70;
				}else if (xPx > 33){
					xGrid = 1;
					dropPoint.x = 36; 
				}else{
					xGrid = 0;
					dropPoint.x = 7;
				}
				//this will snap the ship to the center of the y axis
				if(yPx < 34){
					dropPoint.y = 1;
					yGrid = 0;
				}else if(yPx < 64){
					yGrid = 1;
					dropPoint.y = 34;
				}else if(yPx < 97){
					yGrid = 2;
					dropPoint.y = 66;
				}else if(yPx < 129){
					yGrid = 3;
					dropPoint.y = 99;
				}else if(yPx < 161){
					yGrid = 4;
					dropPoint.y = 131;
				}else if(yPx < 194){
					yGrid = 5;
					dropPoint.y = 164;
				}else if(yPx < 225){
					yGrid = 6;
					dropPoint.y = 196;
				}else if(yPx < 259){
					yGrid = 7;
					dropPoint.y = 229;
				}else if(yPx < 291){
					yGrid = 8;
					dropPoint.y = 261;
				}else{
					yGrid = 9;
					dropPoint.y = 293;
				}
				myShipGrid.battleshipLocation[0]=xGrid;
				myShipGrid.battleshipLocation[1]=yGrid;
				if(!myShipGrid.check('h', 4, myShipGrid.battleshipLocation)){
					return null;
				}else{
					myShipGrid.battleshipPlaced = true;
					int temp = xGrid;
					for(int i=xGrid;i<xGrid+4;i++){
						Constants.myGrid[yGrid][temp] = 'B';
						temp++;
					}
					if(game.getRunner()=="client"){
						clientGUI.disableClientBattleshipBox();
					}else{
						serverGUI.disableBattleshipBox();
					}
					return dropPoint;
				}
			}
		}	
	}

	private Point placeCruiser(Icon ico, Point dropPoint2, JPanel p2, DropTargetDropEvent event) {
		if(myShipGrid.cruiserPlaced){
			return null;
		}else{
			int xPx = dropPoint.x;
			int yPx = dropPoint.y;
			int xGrid=0;
			int yGrid=0;
			if(Ship.orientation == 'v'){
				if(xPx > 292){
					dropPoint.x = 297;
					xGrid = 9;
				}else if(xPx>261){
					dropPoint.x = 265;
					xGrid = 8;
				}else if(xPx>231){
					dropPoint.x = 232;
					xGrid = 7;
				}else if(xPx>201){
					dropPoint.x = 201;
					xGrid = 6;
				}else if(xPx>171){
					dropPoint.x = 168;
					xGrid = 5;
				}else if(xPx>131){
					dropPoint.x = 136;
					xGrid = 4;
				}else if(xPx>97){
					dropPoint.x = 103;
					xGrid = 3;
				}else if(xPx>66){
					dropPoint.x = 70;
					xGrid = 2;
				}else if(xPx > 26) {
					dropPoint.x = 37;
					xGrid = 1;
				}else{
					dropPoint.x = 5;
					xGrid = 0;
				}
				if(yPx < 30){
					dropPoint.y = 5;
					yGrid = 0;
				}else if(yPx < 64){
					dropPoint.y = 37;
					yGrid = 1;
				}else if(yPx < 96){
					dropPoint.y = 69;
					yGrid = 2;
				}else if(yPx < 129){
					dropPoint.y = 101;
					yGrid = 3;
				}else if(yPx < 161){
					dropPoint.y = 134;
					yGrid = 4;
				}else if(yPx < 196){
					dropPoint.y = 169;
					yGrid = 5;
				}else if(yPx < 226){
					dropPoint.y = 199;
					yGrid = 6;
				}else{
					dropPoint.y = 231;
					yGrid = 7;
				}
				myShipGrid.cruiserLocation[0]=xGrid;
				myShipGrid.cruiserLocation[1]=yGrid;
				if(!myShipGrid.check('v', 3, myShipGrid.cruiserLocation)){
					return null;
				}else{
					int temp = yGrid;
					for(int i=yGrid;i<yGrid+3;i++){
						Constants.myGrid[temp][xGrid] = 'R';
						temp++;
					}
					myShipGrid.cruiserPlaced = true;
					if(game.getRunner()=="client"){
						clientGUI.disableClientCruiserBox();
					}else{
						serverGUI.disableCruiserBox();
					}
					return dropPoint;
				}
			}else{

				if(xPx > 228){
					xGrid = 7;
					dropPoint.x = 230;
				}else if(xPx > 195){
					dropPoint.x = 200;
					xGrid = 6;
				}else if(xPx>162){
					dropPoint.x = 167;
					xGrid = 5;
				}else if(xPx > 133){
					xGrid = 4;
					dropPoint.x = 135;
				}else if(xPx > 100){
					xGrid = 3;
					dropPoint.x = 105;
				}else if (xPx > 66){
					xGrid = 2;
					dropPoint.x = 70;
				}else if (xPx > 33){
					xGrid = 1;
					dropPoint.x = 36; 
				}else{
					xGrid = 0;
					dropPoint.x = 7;
				}
				//this will snap the ship to the center of the y axis
				if(yPx < 34){
					dropPoint.y = 3;
					yGrid = 0;
				}else if(yPx < 64){
					yGrid = 1;
					dropPoint.y = 36;
				}else if(yPx < 97){
					yGrid = 2;
					dropPoint.y = 68;
				}else if(yPx < 129){
					yGrid = 3;
					dropPoint.y = 101;
				}else if(yPx < 161){
					yGrid = 4;
					dropPoint.y = 133;
				}else if(yPx < 194){
					yGrid = 5;
					dropPoint.y = 166;
				}else if(yPx < 225){
					yGrid = 6;
					dropPoint.y = 198;
				}else if(yPx < 259){
					yGrid = 7;
					dropPoint.y = 231;
				}else if(yPx < 291){
					yGrid = 8;
					dropPoint.y = 263;
				}else{
					yGrid = 9;
					dropPoint.y = 295;
				}
				myShipGrid.cruiserLocation[0]=xGrid;
				myShipGrid.cruiserLocation[1]=yGrid;
				if(!myShipGrid.check('h', 3, myShipGrid.cruiserLocation)){
					return null;
				}else{
					myShipGrid.cruiserPlaced = true;
					int temp = xGrid;
					for(int i=xGrid;i<xGrid+3;i++){
						Constants.myGrid[yGrid][temp] = 'R';
						temp++;
					}
					if(game.getRunner()=="client"){
						clientGUI.disableClientCruiserBox();
					}else{
						serverGUI.disableCruiserBox();
					}
					return dropPoint;	
				}
			}
		}
	}
	private Point placeSub(Icon ico, Point dropPoint2, JPanel p2, DropTargetDropEvent event) {
		if(myShipGrid.subPlaced){
			return null;
		}else{
			int xPx = dropPoint.x;
			int yPx = dropPoint.y;
			int xGrid=0;
			int yGrid=0;
			if(Ship.orientation == 'v'){
				if(xPx > 292){
					dropPoint.x = 297;
					xGrid = 9;
				}else if(xPx>261){
					dropPoint.x = 265;
					xGrid = 8;
				}else if(xPx>231){
					dropPoint.x = 232;
					xGrid = 7;
				}else if(xPx>201){
					dropPoint.x = 199;
					xGrid = 6;
				}else if(xPx>171){
					dropPoint.x = 168;
					xGrid = 5;
				}else if(xPx>131){
					dropPoint.x = 134;
					xGrid = 4;
				}else if(xPx>97){
					dropPoint.x = 103;
					xGrid = 3;
				}else if(xPx>66){
					dropPoint.x = 70;
					xGrid = 2;
				}else if(xPx > 26) {
					dropPoint.x = 37;
					xGrid = 1;
				}else{
					dropPoint.x = 5;
					xGrid = 0;
				}
				if(yPx < 30){
					dropPoint.y = 5;
					yGrid = 0;
				}else if(yPx < 64){
					dropPoint.y = 37;
					yGrid = 1;
				}else if(yPx < 96){
					dropPoint.y = 69;
					yGrid = 2;
				}else if(yPx < 129){
					dropPoint.y = 101;
					yGrid = 3;
				}else if(yPx < 161){
					dropPoint.y = 134;
					yGrid = 4;
				}else if(yPx < 196){
					dropPoint.y = 169;
					yGrid = 5;
				}else if(yPx < 226){
					dropPoint.y = 199;
					yGrid = 6;
				}else{
					dropPoint.y = 231;
					yGrid = 7;
				}
				myShipGrid.subLocation[0]=xGrid;
				myShipGrid.subLocation[1]=yGrid;
				if(!myShipGrid.check('v', 3, myShipGrid.subLocation)){
					return null;
				}else{
					int temp = yGrid;
					for(int i=yGrid;i<yGrid+3;i++){
						Constants.myGrid[temp][xGrid] = 'S';
						temp++;
					}
					myShipGrid.subPlaced = true;
					if(game.getRunner()=="client"){
						clientGUI.disableClientSubBox();
					}else{
						serverGUI.disableSubBox();
					}
					return dropPoint;
				}
			}else{
				if(xPx > 228){
					xGrid = 7;
					dropPoint.x = 230;
				}else if(xPx > 195){
					dropPoint.x = 200;
					xGrid = 6;
				}else if(xPx>162){
					dropPoint.x = 167;
					xGrid = 5;
				}else if(xPx > 133){
					xGrid = 4;
					dropPoint.x = 135;
				}else if(xPx > 100){
					xGrid = 3;
					dropPoint.x = 105;
				}else if (xPx > 66){
					xGrid = 2;
					dropPoint.x = 70;
				}else if (xPx > 33){
					xGrid = 1;
					dropPoint.x = 36; 
				}else{
					xGrid = 0;
					dropPoint.x = 7;
				}
				//this will snap the ship to the center of the y axis
				if(yPx < 34){
					dropPoint.y = 3;
					yGrid = 0;
				}else if(yPx < 64){
					yGrid = 1;
					dropPoint.y = 36;
				}else if(yPx < 97){
					yGrid = 2;
					dropPoint.y = 68;
				}else if(yPx < 129){
					yGrid = 3;
					dropPoint.y = 101;
				}else if(yPx < 161){
					yGrid = 4;
					dropPoint.y = 133;
				}else if(yPx < 194){
					yGrid = 5;
					dropPoint.y = 166;
				}else if(yPx < 225){
					yGrid = 6;
					dropPoint.y = 198;
				}else if(yPx < 259){
					yGrid = 7;
					dropPoint.y = 231;
				}else if(yPx < 291){
					yGrid = 8;
					dropPoint.y = 263;
				}else{
					yGrid = 9;
					dropPoint.y = 295;
				}
				myShipGrid.subLocation[0]=xGrid;
				myShipGrid.subLocation[1]=yGrid;
				if(!myShipGrid.check('h', 3, myShipGrid.subLocation)){
					return null;
				}else{
					myShipGrid.subPlaced = true;
					int temp = xGrid;
					for(int i=xGrid;i<xGrid+3;i++){
						Constants.myGrid[yGrid][temp] = 'S';
						temp++;
					}
					if(game.getRunner()=="client"){
						clientGUI.disableClientSubBox();
					}else{
						serverGUI.disableSubBox();
					}
					return dropPoint;
				}	
			}
		}
	}
	private Point placeDestroyer(Icon ico, Point dropPoint2, JPanel p2, DropTargetDropEvent event) {
		if(myShipGrid.destroyerPlaced){
			return null;
		}else{
			int xPx = dropPoint.x;
			int yPx = dropPoint.y;
			int xGrid=0;
			int yGrid=0;
			if(Ship.orientation == 'v'){
				if(xPx > 292){
					dropPoint.x = 295;
					xGrid = 9;
				}else if(xPx>259){
					dropPoint.x = 263;
					xGrid = 8;
				}else if(xPx>229){
					dropPoint.x = 230;
					xGrid = 7;
				}else if(xPx>197){
					dropPoint.x = 197;
					xGrid = 6;
				}else if(xPx>163){
					dropPoint.x = 166;
					xGrid = 5;
				}else if(xPx>131){
					dropPoint.x = 132;
					xGrid = 4;
				}else if(xPx>97){
					dropPoint.x = 101;
					xGrid = 3;
				}else if(xPx>66){
					dropPoint.x = 69;
					xGrid = 2;
				}else if(xPx > 26) {
					dropPoint.x = 36;
					xGrid = 1;
				}else{
					dropPoint.x = 3;
					xGrid = 0;
				}
				if(yPx < 30){
					dropPoint.y = 1;
					yGrid = 0;
				}else if(yPx < 64){
					dropPoint.y = 33;
					yGrid = 1;
				}else if(yPx < 96){
					dropPoint.y = 65;
					yGrid = 2;
				}else if(yPx < 129){
					dropPoint.y = 97;
					yGrid = 3;
				}else if(yPx < 161){
					dropPoint.y = 130;
					yGrid = 4;
				}else if(yPx < 196){
					dropPoint.y = 165;
					yGrid = 5;
				}else if(yPx < 226){
					dropPoint.y = 195;
					yGrid = 6;
				}else if(yPx < 258){
					dropPoint.y = 227;
					yGrid = 7;
				}else{
					dropPoint.y = 257;
					yGrid = 8;
				}
				myShipGrid.destroyerLocation[0]=xGrid;
				myShipGrid.destroyerLocation[1]=yGrid;
				if(!myShipGrid.check('v', 2, myShipGrid.destroyerLocation)){
					return null;
				}else{
					int temp = yGrid;
					for(int i=yGrid;i<yGrid+2;i++){
						Constants.myGrid[temp][xGrid] = 'D';
						temp++;
					}
					myShipGrid.destroyerPlaced = true;
					if(game.getRunner()=="client"){
						clientGUI.disableClientDestroyerBox();
					}else{
						serverGUI.disableDestroyerBox();
					}
					return dropPoint;
				}
			}else{
				if(xPx > 261){
					dropPoint.x = 259;
					xGrid = 8;
				}else if (xPx >228){
					xGrid = 7;
					dropPoint.x = 228;
				}else if(xPx > 195){
					dropPoint.x = 193;
					xGrid = 6;
				}else if(xPx>162){
					dropPoint.x = 160;
					xGrid = 5;
				}else if(xPx > 133){
					xGrid = 4;
					dropPoint.x = 128;
				}else if(xPx > 100){
					xGrid = 3;
					dropPoint.x = 98;
				}else if (xPx > 66){
					xGrid = 2;
					dropPoint.x = 63;
				}else if (xPx > 33){
					xGrid = 1;
					dropPoint.x = 29; 
				}else{
					xGrid = 0;
					dropPoint.x = 0;
				}
				//this will snap the ship to the center of the y axis
				if(yPx < 34){
					dropPoint.y = 2;
					yGrid = 0;
				}else if(yPx < 64){
					yGrid = 1;
					dropPoint.y = 35;
				}else if(yPx < 97){
					yGrid = 2;
					dropPoint.y = 67;
				}else if(yPx < 129){
					yGrid = 3;
					dropPoint.y = 100;
				}else if(yPx < 161){
					yGrid = 4;
					dropPoint.y = 132;
				}else if(yPx < 194){
					yGrid = 5;
					dropPoint.y = 165;
				}else if(yPx < 225){
					yGrid = 6;
					dropPoint.y = 197;
				}else if(yPx < 259){
					yGrid = 7;
					dropPoint.y = 230;
				}else if(yPx < 291){
					yGrid = 8;
					dropPoint.y = 262;
				}else{
					yGrid = 9;
					dropPoint.y = 294;
				}
				myShipGrid.destroyerLocation[0]=xGrid;
				myShipGrid.destroyerLocation[1]=yGrid;
				if(!myShipGrid.check('h', 2, myShipGrid.destroyerLocation)){
					return null;
				}else{
					myShipGrid.destroyerPlaced = true;
					int temp = xGrid;
					for(int i=xGrid;i<xGrid+2;i++){
						Constants.myGrid[yGrid][temp] = 'D';
						temp++;
					}
					if(game.getRunner()=="client"){
						clientGUI.disableClientDestroyerBox();
					}else{
						serverGUI.disableDestroyerBox();
					}
					return dropPoint;	
				}
			}
		}  
	}
}