package projeto.cartas.efeitos;

import projeto.Jogador;

public class AtaqueAoNexus extends Efeito{
    private int dano;

    public AtaqueAoNexus(int dano){
        this.dano = dano;
    }

    @Override
    public void aplicarEfeito(Jogador atacante, Jogador defensor) {
        defensor.sofrerDanoNexus(dano);
    }
}
