
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
        ArrayList<Candidato> candidatos = configIniciais.getCandidato();
        map.addParedes(1, 1);
        map.addParedes(1, 2);
        map.addParedes(1, 3);
        map.addParedes(10, 1);
        map.addParedes(8, 1);
        map.addParedes(5, 1);
        map.addParedes(7, 7);
        map.addParedes(7, 8);
        map.gerarCartorios(configIniciais.getNumCartorios());
        map.posicionarCandidatos(candidatos);
        map.printMap();
        try {
            executarCiclo(candidatos, map);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //candidatos.add(teste);
        //map.posicionarCandidatos(candidatos);
//        map.printMap();
//        ArrayList<Coordenada> te = teste.getVizinhos(15, 15);
//        for (int i = 0; i < te.size(); i++) {
//            System.out.println(te.get(i).getX() + "," + te.get(i).getY());
//        }
//        ArrayList<Coordenada> t = candidatos.get(0).caminhoAEstrela(new Coordenada(1, 10));
//        for (int i = 0; i < t.size(); i++) {
//            System.out.println(t.get(i).getX() + "," + t.get(i).getY());
//        }
    }

    public static void executarCiclo(ArrayList<Candidato> candidatos, Map map) throws InterruptedException {
        for(int x = 0; x <= 10; x++) {
            for (Candidato candidato: candidatos) {
                candidato.caminhar();
            }
            map.printMap();
            Thread.currentThread().sleep(1000);
        }
    }
}
