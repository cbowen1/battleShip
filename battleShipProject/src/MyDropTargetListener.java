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
	                System.out.println(droppedShip);

	                
	                if (ico != null) {
	                	if(droppedShip == Constants.CARRIER){
	                		System.out.println("Place carrier at " + dropPoint.x);
	                		placeCarrier(ico,dropPoint,p,event);
	                	}else if(droppedShip == Constants.BATTLESHIP){
	                		System.out.println("Place Battleship");
	                		placeBattleship(ico,dropPoint,p,event);
	                	}else if(droppedShip == Constants.CRUISER){
	                		placeCruiser(ico,dropPoint,p,event);
	                	}else if(droppedShip == Constants.DESTROYER){
	                		placeDestroyer(ico,dropPoint,p,event);
	                	}else if(droppedShip == Constants.SUBMARINE){
	                		placeSubmarine(ico,dropPoint,p,event);
	                	}	
	                
	                }
	            } else {
	                event.rejectDrop();
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            event.rejectDrop();
	        }

	    }

		private void placeCarrier(Icon ico, Point dropPoint2, JPanel p2, DropTargetDropEvent event) {
			int xPx = dropPoint.x;
			int yPx = dropPoint.y;
			int xGrid=0;
			int yGrid=0;
			System.out.println(xPx);
			if(xPx>227){
				dropPoint.x = 167;
			}else if(xPx > 162){
				dropPoint.x = 135;
			}else{
				dropPoint.x = 100;
			}
				//if(xPx > null){
					
				//}
				//xGrid = 5;
				//System.out.println("You want to place the carrier in..." + xGrid + ","+ yGrid);
				JLabel ship = new JLabel(ico);
				ship.setBounds(dropPoint.x, dropPoint.y, ico.getIconWidth(), ico.getIconHeight());
				p.add(ship);
				p.revalidate();
				p.repaint();
            	event.dropComplete(true);		
			//}

		}
		private void placeBattleship(Icon ico, Point dropPoint2, JPanel p2, DropTargetDropEvent event) {
			JLabel ship = new JLabel(ico);
    		ship.setBounds(dropPoint.x, dropPoint.y, ico.getIconWidth(), ico.getIconHeight());
    		p.add(ship);
    		p.revalidate();
            p.repaint();
            event.dropComplete(true);	
		}
		private void placeCruiser(Icon ico, Point dropPoint2, JPanel p2, DropTargetDropEvent event) {
			JLabel ship = new JLabel(ico);
    		ship.setBounds(dropPoint.x, dropPoint.y, ico.getIconWidth(), ico.getIconHeight());
    		p.add(ship);
    		p.revalidate();
            p.repaint();
            event.dropComplete(true);	
		}
		private void placeDestroyer(Icon ico, Point dropPoint2, JPanel p2, DropTargetDropEvent event) {
			JLabel ship = new JLabel(ico);
    		ship.setBounds(dropPoint.x, dropPoint.y, ico.getIconWidth(), ico.getIconHeight());
    		p.add(ship);
    		p.revalidate();
            p.repaint();
            event.dropComplete(true);	
		}
		private void placeSubmarine(Icon ico, Point dropPoint2, JPanel p2, DropTargetDropEvent event) {
			JLabel ship = new JLabel(ico);
    		ship.setBounds(dropPoint.x, dropPoint.y, ico.getIconWidth(), ico.getIconHeight());
    		p.add(ship);
    		p.revalidate();
            p.repaint();
            event.dropComplete(true);	
		}			

   
	}