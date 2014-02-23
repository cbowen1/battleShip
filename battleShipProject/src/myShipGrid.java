
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
			if(game.runner=="client"){
				clientGUI.displayText("Error:Ship Overlap Error");	
			}else{
				serverGUI.displayText("Error:Ship Overlap Error");
			}
			
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
	
	public static boolean checkForHit(int x,int y){
		boolean hit = false;
		char gridValue = Constants.myGrid[y][x];
		if(gridValue != '-' || gridValue !='X'){
			hit = true;
		}
		return hit;
	}
	public static int getXPx(int x) {
		if(x == 9){
			x = 292;
		}else if(x == 8){
			x = 262;
		}else if(x == 7){
			x = 230;
		}else if(x == 6){
			x = 196;
		}else if(x == 5){
			x = 168;
		}else if(x == 4){
			x = 130;
		}else if(x == 3){
			x = 96;
		}else if(x == 2){
			x = 64;
		}else if(x == 1) {
			x = 31;
		}else{
			x = 0;
		}
		return x;
	}
	public static int getYPx(int y) {
		if(y == 0){
			y = -1;
		}else if(y == 1){
			y = 32;
		}else if(y == 2){
			y = 64;
		}else if(y == 3){
			y = 97;
		}else if(y == 4){
			y = 128;
		}else if(y == 5){
			y = 162;
		}else if(y == 6){
			y = 194;
		}else if(y == 7){
			y = 227;
		}else if(y == 8){
			y = 259;
		}else{
			y = 292;
		}	
		return y;
	}
	
	public static int mainXpoint(int x){
		if(x<67){
			x = 0;
		}else if(x<112){
			x = 1;
		}else if(x<157){
			x = 2;
		}else if(x<203){
			x = 3;
		}else if(x<249){
			x = 4;
		}else if(x<295){
			x = 5;
		}else if(x<341){
			x = 6;
		}else if(x<387){
			x = 7;
		}else if(x<433){
			x = 8;
		}else{
			x = 9;
		}
		return x;
	}
	public static int getBIGxPx(int x) {
		switch (x){
		case 0:
			x = 30;
			break;
		case 1:
			x = 72;
			break;
		case 2:
			x = 120;
			break;
		case 3:
			x = 167;
			break;
		case 4:
			x = 213;
			break;
		case 5:
			x = 261;
			break;
		case 6:
			x = 301;
			break;
		case 7:
			x = 350;
			break;
		case 8:
			x = 396;
			break;
		case 9:
			x = 438;
			break;
		}
		return x;
	}
	public static int getBIGyPx(int y){
		switch (y){
		case 0:
			y = 30;
			break;
		case 1:
			y = 75;
			break;
		case 2:
			y = 122;
			break;
		case 3:
			y = 168;
			break;
		case 4:
			y = 214;
			break;
		case 5:
			y = 261;
			break;
		case 6:
			y = 306;
			break;
		case 7:
			y = 352;
			break;
		case 8:
			y = 398;
			break;
		case 9:
			y = 445;
			break;
		}
		return y;
	}
}
