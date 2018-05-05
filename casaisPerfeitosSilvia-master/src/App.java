import sun.security.krb5.Config;

import java.io.IOException;
import java.util.ArrayList;

public class App {
	
	public static void main(String[] args) {
        ConfigIniciais configIniciais = new ConfigIniciais();
		try {
			configIniciais = Leitor.lerArquivo("teste.txt");
		} catch (NotTxtException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
		}
        Map map = new Map();
		map.initialize();
		//map.printMap();
                ArrayList<Candidato> candidatos = configIniciais.getCandidato();
                map.addCadindato(5, 5, candidatos.get(0));
                map.addParedes(1, 1);
                map.addParedes(1, 2);
                map.addParedes(1, 3);
                map.addParedes(10, 1);
                map.addParedes(8, 1);
                map.addParedes(5, 1);
                map.addParedes(7, 7);
                map.addParedes(7, 8);
                map.addCartorios(10, 10);
                map.printMap();
	}

}
