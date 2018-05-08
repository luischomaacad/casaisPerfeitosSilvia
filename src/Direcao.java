import java.util.Random;


public enum Direcao {
    CIMA, BAIXO, ESQUERDA, DIREITA;

    private static final Direcao[] VALUES = values();
    private static final int SIZE = VALUES.length;
    private static final Random RANDOM = new Random();

    public static Direcao getDirecaoHorizontalAleatoria()  {
        return VALUES[(int) ( Math.random() * 2 + 2)];
    }

    public static Direcao getDirecaoVerticalAleatoria()  {
        return VALUES[(int) ( Math.random() * 2 + 0)];
    }

}
