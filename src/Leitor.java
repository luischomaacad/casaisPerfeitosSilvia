import java.io.*;
import java.util.Scanner;

public class Leitor {


    public static ConfigIniciais lerArquivo(String nameArq) throws NotTxtException, IOException {
        String fileString = "";
        Scanner scanner;
        ConfigIniciais configIniciais = new ConfigIniciais();
        if (!nameArq.contains(".txt")) {
            throw new NotTxtException("Arquivo deve ser txt");
        }
        final Reader reader = new FileReader(nameArq);
        final BufferedReader bufferReader = new BufferedReader(reader);
        String currentLine;
        currentLine = bufferReader.readLine();
        scanner = new Scanner(currentLine);
        configIniciais.setNumCasais(scanner.nextInt());
        configIniciais.setNumCartorios(scanner.nextInt());
        popularCandidatos(Genero.MASCULINO, configIniciais, bufferReader);
        popularCandidatos(Genero.FEMININO, configIniciais, bufferReader);
        reader.close();
        bufferReader.close();
        return configIniciais;
    }


    private static void popularCandidatos(Genero genero, ConfigIniciais configIniciais, BufferedReader bf) throws IOException {
        for(int i = 0; i < configIniciais.getNumCasais(); i++){
            Candidato candidato = new Candidato();
            String currentLine = bf.readLine();
            Scanner scanner = new Scanner(currentLine);
            candidato.setId(scanner.nextInt());
            candidato.setGenero(genero);
            while(scanner.hasNextInt()){
                candidato.adicionarPreferencias(scanner.nextInt());
            }
            configIniciais.adicionarCandidato(candidato);
        }
    }
}