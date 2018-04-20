
public class Map {
	public static final int MAP_COLUMNS = 20;
	public static final int MAP_ROWS = 20;
	static String [][]mapa;
	
	public Map() {
		mapa = new String[MAP_ROWS][MAP_COLUMNS];
	}
	
	public static void initialize() {
		for(int i = 0; i<mapa.length; i++) {
			for(int j = 0; j<MAP_COLUMNS; j++) {
				mapa[i][j] = ".";
			}
		}
	}
	
	public static void printMap() {
		for(int i = 0; i<MAP_ROWS; i++) {
			for(int j = 0; j<MAP_COLUMNS; j++) {
				System.out.print(mapa[i][j]);
			}
			System.out.println();
		}
	}
}
