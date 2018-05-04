/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author garce
 */
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
    
}
