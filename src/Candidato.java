import java.util.ArrayList;

public class Candidato {

    private int id;
    private ArrayList<Integer> preferencias;
    private Genero genero;
    private Map mapa;
    private Candidato conjuge;
    private EstadoCivil estadoCivil;

    public Candidato(int id, ArrayList<Integer> preferencias, Genero genero) {
        this.id = id;
        this.preferencias.addAll(preferencias);
        this.genero = genero;
        this.estadoCivil = EstadoCivil.SOLTEIRO;
    }

    public void initialize(Map mapa){
        this.mapa = mapa;
    }

    public void verificarCandidato(Candidato candidato){
        if(preferencias.indexOf(candidato) > preferencias.indexOf(conjuge)
                || this.estadoCivil == EstadoCivil.SOLTEIRO) {
//            caminharAteCandidato(candidato);
        }
    }

    public boolean aceitarProposta(Candidato candidato) {
        if(this.estadoCivil == EstadoCivil.SOLTEIRO
            || preferencias.indexOf(candidato) > preferencias.indexOf(conjuge)) {
            this.formarDivorcio();
            return true;
        }
        return false;
    }

    public void fazerProposta(Candidato candidato){
        if(candidato.aceitarProposta(this)){
            this.formarDivorcio();
//            caminharAteCartorio();
        }
    }


    public void formarCasal(Candidato conjugue){
        this.conjuge = conjugue;
        this.estadoCivil = EstadoCivil.NOIVADO;
    }

    public void formarDivorcio(){
        this.estadoCivil = EstadoCivil.DIVORCIO;
        this.conjuge.setEstadoCivil(EstadoCivil.DIVORCIO);
    }

    public void casar(Candidato conjuge){
        this.conjuge = conjuge;
        this.estadoCivil = EstadoCivil.CASADO;
    }

    public Candidato(){
        this.preferencias = new ArrayList<Integer>();
    }

    public int getId() {
        return id;
    }

    public ArrayList<Integer> getPreferencias() {
        return preferencias;
    }

    public Genero getGenero() {
        return genero;
    }

    public Candidato getConjugue() {
        return conjuge;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPreferencias(ArrayList<Integer> preferencias) {
        this.preferencias.addAll(preferencias);
    }

    public void adicionarPreferencias(int preferencia) {
        this.preferencias.add(preferencia);
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public void setEstadoCivil(EstadoCivil estadoCivil){
        this.estadoCivil = estadoCivil;
    }

}
