import java.util.ArrayList;
import java.util.Random;

public class Map {
	public static final int MAP_COLUMNS = 20;
	public static final int MAP_ROWS = 20;
	static String [][]mapa;
	ArrayList<Coordenada> cartorios; // guarda as coordenadas dos cartórios

	public Map() {
		this.mapa = new String[MAP_ROWS][MAP_COLUMNS];
		this.cartorios = new ArrayList<Coordenada>();
	}

	public static void initialize() {
		for(int i = 0; i<MAP_ROWS; i++) {
			for(int j = 0; j<MAP_COLUMNS; j++) {
				mapa[i][j] = ".";
			}
		}
	}

	public void gerarCartorios(int numCartorios){
		Random random = new Random();
		int x = random.nextInt(MAP_ROWS);
		int y = random.nextInt(MAP_COLUMNS);
		for(int i = 0; i < numCartorios; i++){
			while(!lugarValidoCartorio(x, y)){
				x = random.nextInt(MAP_ROWS);
				y = random.nextInt(MAP_COLUMNS);
			}
			addCartorio(x, y);
		}
	}

	public void addCartorio(int x, int y){
		mapa[x][y] = "C";
		Coordenada c = new Coordenada(x,y);
		cartorios.add(c);
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

	private boolean lugarValidoCartorio(int x, int y){
		if(!mapa[x][y].equalsIgnoreCase(".")){
			return false;
		}
		if((x + 1) != MAP_ROWS && mapa[x + 1][y].equals("@"))
			return true;
		if(x != 0 && mapa[x - 1][y].equals("@"))
			return true;
		if((y + 1) != MAP_COLUMNS && mapa[x][y + 1].equals("@"))
			return true;
		if(y != 0 && mapa[x][y - 1].equals("@"))
			return true;
		return false;
	}
}
