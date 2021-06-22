package projeto.cartas.efeitos;

import projeto.Jogador;

public abstract class Efeito {
	//Atributos???
	
	public Efeito() {
		//finge que faz alguma coisa
	}
	
	public abstract void aplicarEfeito(Jogador atacante, Jogador defensor);
}
