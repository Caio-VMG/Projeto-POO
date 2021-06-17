package projeto.cartas;

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
	
	
}
