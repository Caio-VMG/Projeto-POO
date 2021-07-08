package projeto.cartas.efeitos;
import projeto.Jogador;
import projeto.cartas.Unidade;

public abstract class Efeito {
	protected String nome;
	
	public abstract void aplicarEfeito(Jogador atacante, Jogador defensor, Jogador beneficiado);
	
	public abstract void removerEfeito(Unidade unidade);
	
	public abstract void ativarEfeitoKill(Jogador jogador);
	
	public abstract void ativarEfeitoMorte(Jogador jogador);
	
	public abstract void passouRodada(Unidade unidade);

	public void printNome(){
		System.out.printf("%s",nome);
	}
}
