package projeto.cartas.efeitos;

import projeto.Jogador;
import projeto.cartas.Unidade;

//Efeito 8
public class AtacaTodosInimigos extends Efeito {
	private int poder;
	private int vida;
	
	public AtacaTodosInimigos(int poder, int vida) {
		this.poder = poder;
		this.vida = vida;
	}

	@Override
	public void aplicarEfeito(Jogador atacante, Jogador defensor, Jogador beneficiado) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removerEfeito(Unidade unidade) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ativarEfeito(Jogador jogador) {
		// TODO Auto-generated method stub
		
	}

	/*
	public void aplicarEfeito(Jogador atacante, Jogador defensor, Jogador beneficiado) {
		for(int i = 0; i < )
	}
	 */
}
