public class Coordenada {
    private int x;
    private int y;

    public Coordenada(int nx, int ny){
        x = nx;
        y = ny;
    }
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Coordenada){
            Coordenada coordenada = (Coordenada) obj;
            return this.x == coordenada.getX() && this.y == coordenada.getY();
        }
        return false;
    }
}
