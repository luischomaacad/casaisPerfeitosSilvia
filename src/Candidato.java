//import com.sun.tools.javah.Gen;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Candidato {

    private int id;
    private ArrayList<Integer> preferencias;
    private Genero genero;
    private Map mapa;
    private Candidato conjuge;
    private EstadoCivil estadoCivil;
    private Coordenada posicaoAtual;
    private Destino destino;
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

    //region CAMINHAMENTO
    //classe que define um caminho para onde o candidato quer chegar, seja o caminho para um cartório ou para um outro candidato
    public class Destino {

        private ArrayList<Coordenada> caminho;
        private boolean ateCandidato;
        private boolean ateCartorio;

        public Destino(ArrayList<Coordenada> caminho, boolean ateCandidato, boolean ateCartorio) {
            this.caminho = caminho;
            this.ateCandidato = ateCandidato;
            this.ateCartorio = ateCartorio;
        }

        public ArrayList<Coordenada> getCaminho() {
            return caminho;
        }

        public Coordenada getDestinoFinal() {
            return caminho.get(0);
        }

        public boolean isAteCandidato() {
            return ateCandidato;
        }

        public boolean isAteCartorio() {
            return ateCartorio;
        }
    }

    public void setDestino(Destino d){
        destino = d;
    }
    
    public void acao() {
        procurarCandidatos(); //procura candidatos próximos e, se achar, define ele como seu destino
        caminhar();
    }

    public void procurarCandidatos() {
        Coordenada coordenada = existeCandidatosProximos();
        if (coordenada != null) {
            if(this.estadoCivil == EstadoCivil.SOLTEIRO) {
                System.out.println("ENCONTROU ALGUEM");
                this.destino = new Destino(this.caminhoAteCandidato(coordenada), true, false);
            } else {
                Candidato candidato = this.obterCandidato(coordenada);
                if (preferencias.indexOf(candidato) > preferencias.indexOf(conjuge)){
                    System.out.println("ENCONTROU ALGUEM");
                    this.destino = new Destino(this.caminhoAteCandidato(coordenada), true, false);
                }
            }
        }
    }

    //retorna a coordenada do primeiro candidato que encontrar até 2 casas de distância
    public Coordenada existeCandidatosProximos() {
        ArrayList<Coordenada> coordenadas = getVizinhos(this.posicaoAtual.getX(), this.posicaoAtual.getY(), 2);
        for (Coordenada coordenada : coordenadas) {
            if (this.genero == Genero.MASCULINO && this.mapa.getConteudo(coordenada).contains("F")
                    || this.genero == Genero.FEMININO && this.mapa.getConteudo(coordenada).contains("M")) {
                if (this.estadoCivil == EstadoCivil.SOLTEIRO) {
                    return coordenada;
                }
            }
        }
        return null;
    }

    public void caminhar() {
        if (destino == null) {
            caminharReto(); //caminhar de forma linear quando não tiver destino conforme especificado no enunciado
        } else {
            if (encontrarDestino()) {                                                     //se chegar ao destino
                if (this.destino.isAteCandidato()) {                                      //e este destino for um candidato
                    this.fazerProposta(this.obterCandidato(this.destino.getDestinoFinal()));
                }
            }
        }
    }

    //vai caminhar para a próxima casa do caminho até o destino, retornando true se encontrar o destino
    public boolean encontrarDestino() {
        for (int i = (this.destino.getCaminho().size() - 1); i > 0; i--) {
            if (this.posicaoAtual.equals(this.destino.getCaminho().get(i))) {
                setPosicaoAtual(this.destino.getCaminho().get(i - 1));
                if (i == 2) {
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    public void caminharReto() {
        int x = this.posicaoAtual.getX();
        int y = this.posicaoAtual.getY();
        switch (this.direcao) {
            case CIMA:
                if (verificarCoordenada(new Coordenada(x - 1, y))) {
                    setPosicaoAtual(new Coordenada(x - 1, y));
                } else {
                    this.direcao = Direcao.BAIXO;
                    setPosicaoAtual(new Coordenada(x + 1, y));
                }
                break;
            case BAIXO:
                if (verificarCoordenada(new Coordenada(x + 1, y))) {
                    setPosicaoAtual(new Coordenada(x + 1, y));
                } else {
                    this.direcao = Direcao.CIMA;
                    setPosicaoAtual(new Coordenada(x - 1, y));
                }
                break;
            case ESQUERDA:
                if (verificarCoordenada(new Coordenada(x, y - 1))) {
                    setPosicaoAtual(new Coordenada(x, y - 1));
                } else {
                    this.direcao = Direcao.DIREITA;
                    setPosicaoAtual(new Coordenada(x, y + 1));
                }
                break;
            case DIREITA:
                if (verificarCoordenada(new Coordenada(x, y + 1))) {
                    setPosicaoAtual(new Coordenada(x, y + 1));
                } else {
                    this.direcao = Direcao.ESQUERDA;
                    setPosicaoAtual(new Coordenada(x, y - 1));
                }
                break;
        }
    }

    public boolean verificarCoordenada(Coordenada coordenada) {
        if (coordenada.getY() >= this.mapa.getColumns() || coordenada.getY() < 0) {
            return false;
        }
        if (coordenada.getX() >= this.mapa.getRows() || coordenada.getX() < 0) {
            return false;
        }
        if (!mapa.getConteudo(coordenada.getX(), coordenada.getY()).equals(Map.ESPACO_VAZIO)) {
            return false;
        }
        return true;
    }

    public ArrayList<Coordenada> caminhoAteCandidato(Coordenada coordenada) {
        int x = getPosicaoAtual().getX();
        int y = getPosicaoAtual().getY();
        int xC = coordenada.getX();
        int yC = coordenada.getY();
        if (this.getPosicaoAtual().getX() - xC == 2) {
            x = this.getPosicaoAtual().getX() - 1;
        } else if (this.getPosicaoAtual().getX() - xC == -2) {
            x = this.getPosicaoAtual().getX() + 1;
        }
        if (this.getPosicaoAtual().getY() - yC == 2) {
            y = this.getPosicaoAtual().getY() - 1;
        } else if (this.getPosicaoAtual().getY() - yC == -2) {
            y = this.getPosicaoAtual().getY() + 1;
        }
        ArrayList<Coordenada> coordenadas = new ArrayList<Coordenada>();
        coordenadas.add(coordenada);
        coordenadas.add(new Coordenada(x, y));
        coordenadas.add(this.posicaoAtual);
        return coordenadas;
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

        public void addPai(Custos x) {
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

    private Custos aEstrela(Coordenada destino) {
        ArrayList<Custos> aberta = new ArrayList<Custos>();
        ArrayList<Custos> fechada = new ArrayList<Custos>();
        aberta.add(new Custos(posicaoAtual, 0.0, null)); // Coloca a posicaoAtual do Candidato na lista aberta
        while (!aberta.isEmpty()) { //Enquanto a lista aberta nao for vazia, continua olhando
            Custos atual = aberta.get(0); //o Atual é a localização atual que estamos olhando
            if (destino.getX() == atual.getC().getX() && destino.getY() == atual.getC().getY()) {
                return atual; // se chegou no destino, retorna o caminho
            }
            ArrayList<Coordenada> vizinhos = getVizinhos(atual.getC().getX(), atual.getC().getY(), 1);
            fechada.add(atual);
            aberta.remove(0);
            for (int j = 0; j < vizinhos.size(); j++) {
                double dEuclidiana = distanciaEuclidiana(vizinhos.get(j), destino);
                boolean verifica = false; // variavel que avisa se ja coloquei o vizinho na lista aberta ou nao 
                boolean proximo = false;
                // Faz a verificacao para ver se não tem esse vizinho na lista aberta
                for (int k = 0; k < fechada.size(); k++) {
                    if ((fechada.get(k).getC().getX() == vizinhos.get(j).getX()) && (fechada.get(k).getC().getY() == vizinhos.get(j).getY())) {
                        proximo = true;
                        break;
                    }
                }
                if (proximo == false) {
                    for (int k = 0; k < aberta.size(); k++) {
                        if ((aberta.get(k).getC().getX() == vizinhos.get(j).getX()) && (aberta.get(k).getC().getY() == vizinhos.get(j).getY())) {
                            if (aberta.get(k).getCusto() >= dEuclidiana + atual.getCusto()) {
                                aberta.add(new Custos(vizinhos.get(j), dEuclidiana + atual.getCusto(), atual));
                                aberta.remove(k);
                                verifica = true;
                                break;
                            }
                        }
                    }
                    if (verifica == false) // se não tenho esse vizinho na lista aberta coloco ele na lista aberta
                    {
                        aberta.add(new Custos(vizinhos.get(j), dEuclidiana + atual.getCusto(), atual));
                    }
                }
            }
        }
        return null;
    }

    private ArrayList<Coordenada> inverteLista(ArrayList<Coordenada> r) {
        ArrayList<Coordenada> fim = new ArrayList<Coordenada>();
        for (int i = 0; i < r.size(); i++) {
            fim.add(r.get(i));
        }
        return fim;
    }

    private Coordenada melhorCartorio() {
        ArrayList<Coordenada> cartorios = mapa.getCartorios();
        ArrayList<Double> valores = new ArrayList<Double>();
        for (int i = 0; i < cartorios.size(); i++) {
            valores.add(distanciaEuclidiana(posicaoAtual, cartorios.get(i)));
        }
        double auxMin = valores.get(0);
        int indiceMin = 0;
        for (int i = 0; i < valores.size(); i++) {
            if (auxMin >= valores.get(i)) {
                auxMin = valores.get(i);
                indiceMin = i;
            }
        }
        return cartorios.get(indiceMin);
    }

    public void caminhoAEstrela() {
        Coordenada d = melhorCartorio();
        Custos destino = aEstrela(d);
        System.out.println("Entrei no Aux, o destino é (" + destino.getC().getX() + "," + destino.getC().getY() + ")");
        Custos aux = destino.getPai();
        ArrayList<Coordenada> r = new ArrayList<Coordenada>();
        r.add(destino.getC());
        while (aux != null) { // se o pai foi NULL então é o inicio 
            r.add(aux.getC());
            aux = aux.getPai();
        }
        //https://www.youtube.com/watch?v=s29WpBi2exw
        this.setDestino(new Destino(inverteLista(r),false,true)); //diz o novo destino do candidato 
    }

    //endregion
    //region Relacao Com Agentes
    public void verificarCandidato(Candidato candidato) {
        if (preferencias.indexOf(candidato) > preferencias.indexOf(conjuge)
                || this.estadoCivil == EstadoCivil.SOLTEIRO) {
//            caminharAteCandidato(candidato);
        }
    }

    // private caminharAteCandidato(candidato c){}
    public boolean aceitarProposta(Candidato candidato) {
        if (this.estadoCivil == EstadoCivil.SOLTEIRO
                || preferencias.indexOf(candidato) > preferencias.indexOf(conjuge)) {
            if (conjuge != null) {
                this.formarDivorcio();
            }
            this.estadoCivil = EstadoCivil.NOIVADO;
            this.conjuge = candidato;
//          destino = fazer caminho ate cartorio usando a*
            return true;
        }
        return false;
    }

    public void fazerProposta(Candidato candidato) {
        if (this.estadoCivil == EstadoCivil.SOLTEIRO
                || preferencias.indexOf(candidato) > preferencias.indexOf(conjuge)) {
            if (candidato.aceitarProposta(this)) {
                if (this.conjuge != null) {
                    this.formarDivorcio();
                }
                this.estadoCivil = EstadoCivil.NOIVADO;
                this.conjuge = candidato;
                System.out.println("PROPOSTA ACEITA!");
//              destino = fazer caminho ate cartorio usando a*
            }
            else if(this.estadoCivil == EstadoCivil.NOIVADO || this.estadoCivil == EstadoCivil.DIVORCIO) { //quando o usuário faz proposta para outro e é recusado, ele deve redefinir seu destino
                //destino = fazer caminho ate cartorio usando a*
            } else { //caso não estava indo ao cartório, setar destino como null para voltar a andar linearmente
                this.destino = null;
            }
        }
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
    private void atualizarMapa(Coordenada posicaoAnterior) {
        this.mapa.atualizarCandidato(this, posicaoAnterior);
    }

    private Candidato obterCandidato(Coordenada coordenada){
        ArrayList<Candidato> candidatos = this.mapa.getCandidatos();
        for (Candidato candidato : candidatos) {
            if (candidato.getPosicaoAtual().equals(coordenada)) {
                return candidato;
            }
        }
        return null;
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
        if (aux != null) {
            this.atualizarMapa(aux);
        }
    }

    public Coordenada getPosicaoAtual() {
        return posicaoAtual;
    }
    //endregion
}
