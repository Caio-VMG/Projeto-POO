package projeto.cartas.efeitos;

import projeto.Jogador;
import projeto.cartas.Unidade;

//Efeito 3
public class MatouComprou extends Efeito {
	int duracao = 1;

	public MatouComprou(){
		super.nome = "Matou Comprou";
	}

	
	@Override
	public void aplicarEfeito(Jogador atacante, Jogador defensor, Unidade escolhida, TipoChamada tipo) {
		if (!escolhida.getEfeitos().contains(this)) {
			escolhida.addEfeito(this);
		}
	}
	
	@Override
	public void ativarEfeitoKill(Jogador jogador) {
		System.out.printf("O jogador %s ganhou uma carta devido ao efeito Matou Comprou! \n",jogador.getNome());
		jogador.pegarCarta();
	}
	
	@Override
	public void ativarEfeitoMorte(Jogador jogador) {
	}
	
	@Override
	public void removerEfeito(Unidade unidade) {
		unidade.removerEfeito(this);
		
	}

	@Override
	public void passouRodada(Unidade unidade) {
		this.duracao -=1;
		if (this.duracao == 0) {
			removerEfeito(unidade);
		}
		
	}

	

}
