import java.util.ArrayList;
import java.util.Random;

public class Map {
	public static final int MAP_COLUMNS = 20;
	public static final int MAP_ROWS = 20;
	private static String [][]mapa;
	private ArrayList<Coordenada> cartorios; // guarda as coordenadas dos cartórios

	public Map() {
		this.mapa = new String[MAP_ROWS][MAP_COLUMNS];
		this.cartorios = new ArrayList<Coordenada>();
	}

	public static void initialize() {
		for(int i = 0; i<MAP_ROWS; i++) {
			for(int j = 0; j<MAP_COLUMNS; j++) {
				mapa[i][j] = "_ ";
			}
		}
	}
	
	public String getConteudo(int x, int y){
            return mapa[x][y];
        }

	public String getConteudo(Coordenada c){ return mapa[c.getX()][c.getY()]; }
        
	public int getColumns(){
		return MAP_COLUMNS;
	}
	
	public int getRows(){
		return MAP_ROWS;
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

	public void posicionarCandidatos(ArrayList<Candidato> candidatos){
		Random random = new Random();
		int x = random.nextInt(MAP_ROWS);
		int y = random.nextInt(MAP_COLUMNS);
		for (Candidato candidato: candidatos) {
			while(!mapa[x][y].equalsIgnoreCase("_ ")){
				x = random.nextInt(MAP_ROWS);
				y = random.nextInt(MAP_COLUMNS);
			}
			candidato.setPosicaoAtual(new Coordenada(x, y));
			addCadindato(candidato);
			candidato.initialize(this);
		}
	}

	public ArrayList<Coordenada> getCartorios(){
		return cartorios;
	}

	public void addParedes(int x, int y){
		if((mapa[x][y].equalsIgnoreCase("_ "))){ // . = nada
			mapa[x][y] = "@ ";
		}
	}

	public static void printMap() {
		for(int i = 0; i<MAP_ROWS; i++) {
			for(int j = 0; j<MAP_COLUMNS; j++) {
				System.out.print(mapa[i][j]);
			}
			System.out.println();
		}
		System.out.println("========================================================================");
	}

	public void atualizarCandidato(Candidato candidato, Coordenada posicaoAnterior) throws InterruptedException {
		this.mapa[posicaoAnterior.getX()][posicaoAnterior.getY()] = "_ ";
		addCadindato(candidato);
	}

	private void addCadindato(Candidato c) {
		String genero = c.getGenero() == Genero.MASCULINO ? "M" : "F";
		mapa[c.getPosicaoAtual().getX()][c.getPosicaoAtual().getY()] = genero + c.getId();;
	}

	private void addCartorio(int x, int y){
		mapa[x][y] = "C ";
		Coordenada c = new Coordenada(x,y);
		cartorios.add(c);
	}

	private boolean lugarValidoCartorio(int x, int y){
		if(!mapa[x][y].equalsIgnoreCase("_ ")){
			return false;
		}
		if((x + 1) != MAP_ROWS && mapa[x + 1][y].equals("@ "))
			return true;
		if(x != 0 && mapa[x - 1][y].equals("@ "))
			return true;
		if((y + 1) != MAP_COLUMNS && mapa[x][y + 1].equals("@ "))
			return true;
		if(y != 0 && mapa[x][y - 1].equals("@ "))
			return true;
		return false;
	}
	
}
