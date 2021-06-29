package projeto.cartas.efeitos;

import projeto.Jogador;
import projeto.cartas.Unidade;

public class AtaqueAoNexus extends Efeito{
    private int dano;

    public AtaqueAoNexus(int dano){
        this.dano = dano;
    }

    @Override
    public void aplicarEfeito(Jogador atacante, Jogador defensor, Jogador beneficiado) {
        defensor.sofrerDanoNexus(dano);
    }

	@Override
	public void removerEfeito(Unidade unidade) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ativarEfeito(Jogador jogador) {
		// TODO Auto-generated method stub
		
	}
}
