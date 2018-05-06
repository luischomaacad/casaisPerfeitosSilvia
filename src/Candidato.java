import com.sun.tools.javah.Gen;

import java.util.ArrayList;

public class Candidato {

    private int id;
    private ArrayList<Integer> preferencias;
    private Genero genero;
    private Map mapa;
    private Candidato conjuge;
    private EstadoCivil estadoCivil;
    private Coordenada posicaoAtual;
    private Coordenada destino;
    private Direcao direcao;

    public Candidato(int id, ArrayList<Integer> preferencias, Genero genero) {
        this.id = id;
        this.preferencias.addAll(preferencias);
        this.genero = genero;
    }

    public Candidato() {
        this.preferencias = new ArrayList<Integer>();
    }

    public void initialize(Map mapa) {
        this.mapa = mapa;
        this.estadoCivil = EstadoCivil.SOLTEIRO;
        this.direcao = Direcao.getDirecaoAleatoria();
    }

    //region Caminhamento
    public void caminhar(){
        if(destino == null)
            caminharReto();
        Coordenada coordenada = existeCandidatosProximos();
        if(coordenada != null){
            System.out.println("ENCONTROU ALGUEM");
//            caminhoAEstrela(coordenada);
        }

    }

    public Coordenada existeCandidatosProximos(){
        ArrayList<Coordenada> coordenadas = getVizinhos(this.posicaoAtual.getX(), this.posicaoAtual.getY(), 2);
        for (Coordenada coordenada: coordenadas) {

            if(this.genero == Genero.MASCULINO && this.mapa.getConteudo(coordenada).contains("F")
                    || this.genero == Genero.FEMININO && this.mapa.getConteudo(coordenada).contains("M")) {
                if (this.estadoCivil == EstadoCivil.SOLTEIRO)
                    return coordenada;

            }
        }
        return null;
    }

    public void caminharReto(){
        int x = this.posicaoAtual.getX();
        int y = this.posicaoAtual.getY();
        switch (this.direcao) {
            case CIMA:
                if(verificarCoordenada(new Coordenada(x - 1, y)))
                    setPosicaoAtual(new Coordenada(x - 1, y));
                else {
                   this.direcao = Direcao.BAIXO;
                   setPosicaoAtual(new Coordenada(x + 1, y));
                }
                break;
            case BAIXO:
                if(verificarCoordenada(new Coordenada(x + 1, y)))
                    setPosicaoAtual(new Coordenada(x + 1, y));
                else {
                    this.direcao = Direcao.CIMA;
                    setPosicaoAtual(new Coordenada(x - 1, y));
                }
                break;
            case ESQUERDA:
                if(verificarCoordenada(new Coordenada(x, y - 1)))
                    setPosicaoAtual(new Coordenada(x, y - 1));
                else {
                    this.direcao = Direcao.DIREITA;
                    setPosicaoAtual(new Coordenada(x, y - 1));
                }
                break;
            case DIREITA:
                if(verificarCoordenada(new Coordenada(x, y + 1)))
                    setPosicaoAtual(new Coordenada(x, y + 1));
                else {
                    this.direcao = Direcao.ESQUERDA;
                    setPosicaoAtual(new Coordenada(x, y - 1));
                }
                break;
        }
    }

    public boolean verificarCoordenada(Coordenada coordenada) {
        if(coordenada.getY() >= this.mapa.getColumns() || coordenada.getY() < 0)
            return false;
        if(coordenada.getX() >= this.mapa.getRows() || coordenada.getX() < 0)
            return false;
        if(!mapa.getConteudo(coordenada.getX(), coordenada.getY()).equals("_ "))
            return false;
        return true;
    }

    //endregion

    //region Algoritmo AEstrela
    public ArrayList<Coordenada> getVizinhos(int x, int y, int dimensao) {
        ArrayList<Coordenada> viz = new ArrayList<Coordenada>();
        int xa = x - dimensao; // Variavel auxiliar para X
        int ya = y - dimensao; // Variavel auxiliar para Y
        int range = (dimensao * 2) + 1;

        for (int i = 0; i < range; i++) { // Controla  o X (x - 1, x , x + 1)
            //System.out.println("vendo o xa " + xa);
            if (xa >= 0 && xa < mapa.getRows()) { // Vejo se não está fora do tabuleiro
                //System.out.println("entrei no xa " + xa);
                for (int j = 0; j < range; j++) { // Controla o Y (y - 1, y , y + 1)
                    //System.out.println("vendo o ya " + ya);
                    if (ya >= 0 && ya < mapa.getColumns()) { // Vejo se não está fora do tabuleiro
                        //System.out.println("entrei no ya " + ya);
                        //System.out.println("Conteudo " + mapa.getConteudo(xa, ya) + "| ");
                        if (!mapa.getConteudo(xa, ya).equals("@ ")) { // Se o campo que eu estou olhando não é uma parede
                            if (!(x == xa && y == ya)) { // Se for a propria posicao de entrada, entao nao pode colocar como vizinho
                                Coordenada c = new Coordenada(xa, ya);
                                viz.add(c);
                                //System.out.println("Vizinho encontrado coordenada: " + mapa.getColumns() + "," + mapa.getRows());
                            }
                        }
                    }
                    ya = ya + 1; //Soma +1 em Y
                }
                ya = y - dimensao; // Começa o Y de novo para olhar tudo em volta!
            }
            xa = xa + 1; //Soma + em X
        }
        return viz;
    }

    private double distanciaEuclidiana(Coordenada i, Coordenada d) {
        int xi = i.getX();
        int yi = i.getY();
        int xd = d.getX();
        int yd = d.getY();
//        Sejam os pontos A=(Xa, Ya) B=(Xb, Yb)
//        então a distancia dá-se por
//        d² = (Xa-Xb)² + (Ya-Yb)²
//        Isolando:
//        d = raiz ( (Xa-Xb)² + (Ya-Yb)² )
        double auxx = Math.pow(xi - xd, 2);
        double auxy = Math.pow(yi - yd, 2);
        return Math.sqrt(auxx + auxy);
    }

    private class Custos {

        private Coordenada c;
        private Custos pai;
        private double custo;

        public Custos getPai() {
            return pai;
        }

        public void addPai(Custos x){
            pai = x;
        }

        public Coordenada getC() {
            return c;
        }

        public double getCusto() {
            return custo;
        }
        public Custos(Coordenada coord, double cust, Custos p) {
            c = coord;
            custo = cust;
            pai = p;
        }
    }

    private Custos aEstrela(Coordenada destino){
        ArrayList<Custos> aberta = new ArrayList<Custos>();
        ArrayList<Custos> fechada = new ArrayList<Custos>();
        aberta.add(new Custos(posicaoAtual, 0.0, null));
        for (int i = 0; i < aberta.size(); i++) {
            Custos atual = aberta.get(i);
            if (destino.getX() == atual.getC().getX() && destino.getY() == atual.getC().getY()) {
                System.out.println("final");
                return atual; // se chegou no destino, retorna o caminho
            }
            ArrayList<Coordenada> vizinhos = getVizinhos(atual.getC().getX(), atual.getC().getY(), 1);
            fechada.add(atual);
            aberta.remove(i);
            for (int j = 0; j < vizinhos.size(); j++) {
                double dEuclidiana = distanciaEuclidiana(vizinhos.get(j), destino);
                boolean verifica = false; // variavel que avisa se ja coloquei o vizinho na lista aberta ou nao 
                boolean proximo = false;
                // Faz a verificacao para ver se não tem esse vizinho na lista aberta
                for (int k = 0; k < fechada.size(); k++) {
                     if ((fechada.get(k).getC().getX() == vizinhos.get(j).getX()) && (fechada.get(k).getC().getY() == vizinhos.get(j).getY())) {
                         proximo = true;
                     }
                }
                if(proximo == false){
                    for (int k = 0; k < aberta.size(); k++) {

                        //System.out.println("Entrei aqui " + (aberta.get(k).getC().getX() + " == " + vizinhos.get(j).getX()) + " e " + (aberta.get(k).getC().getY() + " == " + vizinhos.get(j).getY()));
                        if ((aberta.get(k).getC().getX() == vizinhos.get(j).getX()) && (aberta.get(k).getC().getY() == vizinhos.get(j).getY())) {
                            
                            if (aberta.get(k).getCusto() >= dEuclidiana) {
                                aberta.add(new Custos(vizinhos.get(j), dEuclidiana, atual));
                                aberta.remove(k);
                                verifica = true;
                                //System.out.println("Entrei aqui");
                                break;
                            }
                        }
                    }
                    if (verifica == false) // se não tenho esse vizinho na lista aberta coloco ele na lista aberta
                    {
                        aberta.add(new Custos(vizinhos.get(j), dEuclidiana, atual));
                    }
                }
            }
        }
        return null;
    }

    private ArrayList<Coordenada> inverteLista(ArrayList<Coordenada> r){
        ArrayList<Coordenada> fim = new ArrayList<Coordenada>();
        for (int i = 0; i < r.size(); i++) {
            fim.add(r.get(i));
        }
        return fim;
    }
    
    public ArrayList<Coordenada> caminhoAEstrela(Coordenada d){
        Custos destino = aEstrela(d);
        Custos aux = destino.getPai();
        ArrayList<Coordenada> r = new ArrayList<Coordenada>();
        r.add(destino.getC());
        while(aux != null){ // se o pai foi NULL então é o inicio 
            r.add(aux.getC());
            aux = aux.getPai();
        }
        //https://www.youtube.com/watch?v=s29WpBi2exw
        return inverteLista(r);
    }

    //endregion

    //region Relacao Com Agentes
    public void verificarCandidato(Candidato candidato) {
        if (preferencias.indexOf(candidato) > preferencias.indexOf(conjuge)
                || this.estadoCivil == EstadoCivil.SOLTEIRO) {
//            caminharAteCandidato(candidato);
        }
    }

    public boolean aceitarProposta(Candidato candidato) {
        if (this.estadoCivil == EstadoCivil.SOLTEIRO
                || preferencias.indexOf(candidato) > preferencias.indexOf(conjuge)) {
            this.formarDivorcio();
            return true;
        }
        return false;
    }

    public void fazerProposta(Candidato candidato) {
        if (candidato.aceitarProposta(this)) {
            this.formarDivorcio();
//            caminharAteCartorio();
        }
    }

    public void formarCasal(Candidato conjugue) {
        this.conjuge = conjugue;
        this.estadoCivil = EstadoCivil.NOIVADO;
    }

    public void formarDivorcio() {
        this.estadoCivil = EstadoCivil.DIVORCIO;
        this.conjuge.setEstadoCivil(EstadoCivil.DIVORCIO);
    }

    public void casar(Candidato conjuge) {
        this.conjuge = conjuge;
        this.estadoCivil = EstadoCivil.CASADO;
    }

    //endregion

    public void atualizarMapa(Coordenada posicaoAnterior){
        try {
            this.mapa.atualizarCandidato(this, posicaoAnterior);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //region Getters&Setters

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

    public void setEstadoCivil(EstadoCivil estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public void setPosicaoAtual(Coordenada posicaoAtual) {
        Coordenada aux = this.posicaoAtual;
        this.posicaoAtual = posicaoAtual;
        if(aux != null)  this.atualizarMapa(aux);
    }

    public Coordenada getPosicaoAtual() {
        return posicaoAtual;
    }

    //endregion
}
