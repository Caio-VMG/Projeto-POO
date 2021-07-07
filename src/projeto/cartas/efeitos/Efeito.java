package projeto.cartas.efeitos;
import projeto.Jogador;
import projeto.cartas.Unidade;

public abstract class Efeito {
	//Atributos???
	
	public Efeito() {
		//finge que faz alguma coisa
	}
	
	public abstract void aplicarEfeito(Jogador atacante, Jogador defensor, Jogador beneficiado);
	
	public abstract void removerEfeito(Unidade unidade);
	
	public abstract void ativarEfeitoKill(Jogador jogador);
	
	public abstract void ativarEfeitoMorte(Jogador jogador);
}
