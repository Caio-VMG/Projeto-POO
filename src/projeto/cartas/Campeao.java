package projeto.cartas;

import projeto.Jogador;
import projeto.cartas.efeitos.Efeito;

public class Campeao extends Unidade{
	int ataques = 0;
	
	//atributos???

	public Campeao(String nome, int custo, int vida, int poder, Efeito efeito, Traco traco) {
		super(nome, custo, vida, poder, efeito, traco);
	}

	public Campeao(String nome, int custo, int vida, int poder, Efeito efeito) {
		super(nome, custo, vida, poder, efeito);
	}

	public Campeao(String nome, int custo, int vida, int poder, Traco traco) {
		super(nome, custo, vida, poder, traco);
	}

	public Campeao(String nome, int custo, int vida, int poder) {
		super(nome, custo, vida, poder);
	}
	
	public void usarCarta(Jogador jogador, int entrada) {
    	if(jogador.getMana() >= jogador.getMao().get(entrada).getCusto()) {
			jogador.getEvocadas().add(jogador.getMao().get(entrada));
			jogador.setMana(jogador.getMao().get(entrada).getCusto());
		}
		else {
			System.out.println("Você não possui mana suficiente");
		}
    }
	
	public int getDano() {
		return this.poder;
	}
	
	public void sofrerDano(int danoRecebido) {
		this.vida -= danoRecebido;
	}
}
