package projeto.cartas;

import projeto.Jogador;
import projeto.cartas.efeitos.Efeito;

public class Feitico extends Carta{
	private Efeito efeito;
	
	public Feitico(String nome, int custo/*, Efeito efeito*/) {
		super(nome, custo);
		//this.efeito = efeito;
	}
	
	@Override
	public void usarCarta(Jogador atacante, Jogador defensor) {

	}


	@Override
	public Carta getUnidade(){
		return null;
	}
}
