package projeto.cartas.efeitos;

import projeto.Jogador;
import projeto.cartas.Unidade;

//Efeito 7
public class UnidadeAtacaNexus extends Efeito {

	public UnidadeAtacaNexus(){
		super.nome = "Unidade Ataca Nexus";
	}
	
	@Override
	public void aplicarEfeito(Jogador atacante, Jogador defensor, Unidade escolhida, TipoChamada tipo) {
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
