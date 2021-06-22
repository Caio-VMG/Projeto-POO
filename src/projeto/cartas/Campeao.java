package projeto.cartas;

import projeto.Jogador;

public class Campeao extends Unidade{
	//atributos???
	
	//Começa com efeito e traço
	public Campeao(String nome, int custo, int vida, int poder, Efeito efeito, Traco traco) {
		super(nome, custo, vida, poder, efeito, traco);
	}
	
	//Começa com efeito mas sem traço
	public Campeao(String nome, int custo, int vida, int poder, Efeito efeito) {
		super(nome, custo, vida, poder, efeito);
	}
	
	//Começa sem efeito e com traço
	public Campeao(String nome, int custo, int vida, int poder, Traco traco) {
		super(nome, custo, vida, poder, traco);
	}
	
	//Começa sem efeito e sem traço
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
	
	
}
