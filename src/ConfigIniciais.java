import java.util.ArrayList;

public class ConfigIniciais {
    private int numCasais;
    private int numCartorios;
    private ArrayList<Candidato> candidatos;

    public ConfigIniciais(int numCasais, int numCartorios, ArrayList<Candidato> candidatos) {
        this.numCasais = numCasais;
        this.numCartorios = numCartorios;
        this.candidatos.addAll(candidatos);
    }

    public ConfigIniciais(){
        this.candidatos = new ArrayList<Candidato>();
    }

    public int getNumCasais() {
        return numCasais;
    }

    public int getNumCartorios() {
        return numCartorios;
    }

    public ArrayList<Candidato> getCandidato() {
        return candidatos;
    }

    public void setNumCasais(int numCasais) {
        this.numCasais = numCasais;
    }

    public void setNumCartorios(int numCartorios) {
        this.numCartorios = numCartorios;
    }

    public void setCandidato(ArrayList<Candidato> candidatos) {
        this.candidatos.addAll(candidatos);
    }

    public void adicionarCandidato(Candidato candidato) {
        this.candidatos.add(candidato);
    }
}
