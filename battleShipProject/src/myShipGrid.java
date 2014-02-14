
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
	
	static public int[] getCarrierLoc(){
		return carrierLocation;
	}
	static public int[] getBattleshipLocation(){
		return battleshipLocation;
	}
}

