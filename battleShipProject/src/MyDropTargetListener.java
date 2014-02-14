import java.awt.Component;
import java.awt.Point;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;

import javax.swing.Icon;
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
	                String droppedShip = ico.toString();

	                if (ico != null) {
	                	if(droppedShip == Constants.CARRIER){
	                		dropPoint = placeCarrier(ico,dropPoint,p,event);
	                	}else if(droppedShip == Constants.BATTLESHIP){
	                		dropPoint = placeBattleship(ico,dropPoint,p,event);
	                	}else if(droppedShip == Constants.CRUISER){
	                		dropPoint = placeCruiser(ico,dropPoint,p,event);
	                	}else if(droppedShip == Constants.DESTROYER){
	                		dropPoint = placeDestroyer(ico,dropPoint,p,event);
	                	}else if(droppedShip == Constants.SUBMARINE){
	                		dropPoint = placeSub(ico,dropPoint,p,event);
	                	}	
	        			JLabel ship = new JLabel(ico);
	            		ship.setBounds(dropPoint.x, dropPoint.y, ico.getIconWidth(), ico.getIconHeight());
	            		p.add(ship);
	            		p.revalidate();
	                    p.repaint();
	                    event.dropComplete(true);
	                }
	            } else {
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
				myShipGrid.carrierPlaced = true;
				return dropPoint;	
			}
		
		}
		private Point placeBattleship(Icon ico, Point dropPoint2, JPanel p2, DropTargetDropEvent event) {
			if(myShipGrid.battleshipPlaced){
				return null;
			}else{
				int xPx = dropPoint.x;
				int yPx = dropPoint.y;
				int xGrid=0;
				int yGrid=0;
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
				myShipGrid.battleshipPlaced = true;
				return dropPoint;
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
				System.out.println(xPx);
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
				myShipGrid.cruiserPlaced = true;
				return dropPoint;	
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
				System.out.println(xPx);
				if(xPx > 228){
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
				myShipGrid.subPlaced = true;
				return dropPoint;	
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
				System.out.println(xPx);
				if(xPx > 261){
					dropPoint.x = 259;
				}else if (xPx >228){
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
				myShipGrid.destroyerPlaced = true;
				return dropPoint;	
			}
		}   
	}