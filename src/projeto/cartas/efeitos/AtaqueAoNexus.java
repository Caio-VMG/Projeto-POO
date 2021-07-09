 package projeto.cartas.efeitos;

import projeto.Jogador;
import projeto.cartas.Unidade;

public class AtaqueAoNexus extends Efeito{
    private int dano;

    public AtaqueAoNexus(int dano){
        this.dano = dano;
        super.nome = "AtaqueAoNexus";
    }

    @Override
    public void aplicarEfeito(Jogador atacante, Jogador defensor, Unidade escolhida) {
        defensor.sofrerDanoNexus(escolhida.getDano());
    }

	@Override
	public void removerEfeito(Unidade unidade) {}

	@Override
	public void ativarEfeitoKill(Jogador jogador) {}
	
	@Override
	public void ativarEfeitoMorte(Jogador jogador) {}

	@Override
	public void passouRodada(Unidade unidade) {
		// TODO Auto-generated method stub
		
	}
}
