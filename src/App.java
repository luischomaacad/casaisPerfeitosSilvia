
import java.io.IOException;
import java.util.ArrayList;

public class App {

    public static void main(String[] args) {
        double startTime = System.currentTimeMillis();
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
        map.addParedes(8, 1);
        map.addParedes(8, 2);
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
        double endTime = System.currentTimeMillis();
        System.out.println("A execuçao levou " + (endTime - startTime)/1000 + " segundos");

    }

    public static void executarCiclo(ArrayList<Candidato> candidatos, Map map) throws InterruptedException {
        boolean parada = true;
        int iteracao = 0;
        int limiteDeIteracao = 30;
        int limiteQualidade = 4;
        while (parada) {
            for (Candidato candidato : candidatos) {

                Thread.currentThread().sleep(500);

                candidato.acao();
            }
            map.printMap();
            Thread.currentThread().sleep(1000);
             //map.printMap();
             if(map.qualidadeCasais() == limiteQualidade){
              parada = true;
              System.out.println("Parei pela qualidade dos casais estipulada(" + limiteQualidade +")");
              Candidato.printFinal();
             }
             iteracao = iteracao + 1;
            if (iteracao == limiteDeIteracao) {
                parada = false;
                System.out.println("Parei pelo número de iterações que foi " + iteracao + " iterações.");
                Candidato.printFinal();
            }

        }

    }



}
