
import java.util.ArrayList;


public class Map {
	public static final int MAP_COLUMNS = 20;
	public static final int MAP_ROWS = 20;
	static String [][]mapa;
        ArrayList<Coordenada> cartorios; // guarda as coordenadas dos cartórios
	
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
	public void addCartorios(int x, int y){
            if((mapa[x][y].equalsIgnoreCase("."))){ // . = nada 
                mapa[x][y] = "C";
                Coordenada c = new Coordenada(x,y);
                cartorios.add(c);
            }
        }
        
        public ArrayList<Coordenada> getCartorios(){
            return cartorios;
        }
        
        public void addParedes(int x, int y){
            if((mapa[x][y].equalsIgnoreCase("."))){ // . = nada 
                mapa[x][y] = "@";
            }
        }
        
        public void addCadindato(int x, int y, Candidato c){
            if((mapa[x][y].equalsIgnoreCase("."))){ 
                mapa[x][y] = String.valueOf(c.getId());
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
