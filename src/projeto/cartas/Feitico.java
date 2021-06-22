package projeto.cartas;

import projeto.Jogador;
import projeto.cartas.efeitos.Efeito;

public class Feitico extends Carta{
	private Efeito efeito;
	
	public Feitico(String nome, int custo/*, Efeito efeito*/) {
		super(nome, custo);
		//this.efeito = efeito;
	}
	
	public void usarFeitico() {
		efeito.ativarEfeito();
	}

	@Override
	public void usarCarta(Jogador atacante, Jogador defensor) {
		System.out.println("Oi caio");
	}
}
