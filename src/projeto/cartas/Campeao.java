package projeto.cartas;

import projeto.Jogador;

public class Campeao extends Unidade{
	//atributos???
	
	//Come�a com efeito e tra�o
	public Campeao(String nome, int custo, int vida, int poder, Efeito efeito, Traco traco) {
		super(nome, custo, vida, poder, efeito, traco);
	}
	
	//Come�a com efeito mas sem tra�o
	public Campeao(String nome, int custo, int vida, int poder, Efeito efeito) {
		super(nome, custo, vida, poder, efeito);
	}
	
	//Come�a sem efeito e com tra�o
	public Campeao(String nome, int custo, int vida, int poder, Traco traco) {
		super(nome, custo, vida, poder, traco);
	}
	
	//Come�a sem efeito e sem tra�o
	public Campeao(String nome, int custo, int vida, int poder) {
		super(nome, custo, vida, poder);
	}
	
	public void usarCarta(Jogador jogador, int entrada) {
    	if(jogador.getMana() >= jogador.getMao().get(entrada).getCusto()) {
			jogador.getEvocadas().add(jogador.getMao().get(entrada));
			jogador.setMana(jogador.getMao().get(entrada).getCusto());
		}
		else {
			System.out.println("Voc� n�o possui mana suficiente");
		}
    }
	
	
}
