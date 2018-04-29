import sun.security.krb5.Config;

import java.io.IOException;

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
		map.printMap();
	}

}

