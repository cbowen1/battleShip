
public class myShipGrid {
	public static boolean carrierPlaced = false;
	public static boolean battleshipPlaced = false;
	public static boolean cruiserPlaced = false;
	public static boolean subPlaced = false;
	public static boolean destroyerPlaced = false;
	
	public static int[] carrierLocation = new int[2];
	public static int[] battleshipLocation = new int[2];
	public static int[] cruiserLocation = new int[2];
	public static int[] subLocation = new int[2];
	public static int[] destroyerLocation = new int[2];
	
	public static boolean check(char orient,int shipSize,int[]loc){
		boolean check = true;
		char ship;
		if(orient == 'h'){
			int xGrid = loc[0];
			for(int i = xGrid;i<xGrid+shipSize;i++){
				ship = Constants.myGrid[loc[1]][i];
				if(ship == 'S'||ship =='C'||ship == 'B'||ship=='R'||ship=='D'){
					check = false;
				}
			}
		}else if(orient == 'v'){
			int yGrid = loc[1];
			for(int i = yGrid;i<yGrid+shipSize;i++){
				ship = Constants.myGrid[i][loc[0]];
				if(ship == 'S'||ship =='C'||ship == 'B'||ship=='R'||ship=='D'){
					check = false;
				}
			}
		}
		if(!check){
			clientGUI.displayText("Error:Ship Overlap Error");
		}
		return check;
	}
	public static void displayGrid(){
		for(int i=0;i<10;i++){
			for(int j=0;j<10;j++){
				System.out.print(Constants.myGrid[i][j] + " ");
			}
			System.out.println();
		}
	}
}

