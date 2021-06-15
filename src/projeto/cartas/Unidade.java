package projeto.cartas;

public class Unidade extends Carta{
    private int vida;
    private int poder;

    public static void batalhar(Unidade unidade1, Unidade unidade2){
        unidade1.vida -= unidade2.poder;
        unidade2.vida -= unidade1.poder;
    }

    public int getPoder() {
        return poder;
    }
}
