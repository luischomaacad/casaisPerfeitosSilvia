import java.util.Random;


public enum Direcao {
    CIMA, BAIXO, ESQUERDA, DIREITA;

    private static final Direcao[] VALUES = values();
    private static final int SIZE = VALUES.length;
    private static final Random RANDOM = new Random();

    public static Direcao getDirecaoAleatoria()  {
        return VALUES[RANDOM.nextInt(SIZE)];
    }
}
