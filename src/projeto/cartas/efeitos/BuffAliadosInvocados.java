package projeto.cartas.efeitos;

import java.util.ArrayList;

import projeto.Jogador;
import projeto.cartas.Carta;
import projeto.cartas.Unidade;

// Efeito 1
public class BuffAliadosInvocados extends Efeito {
	private int poder;
	private int vida;
	
	public BuffAliadosInvocados(int poder, int vida) {
		this.poder = poder;
		this.vida = vida;
		super.nome = "Buff Aliados Invocados";
	}
	
	public void aplicarEfeito(Jogador atacante, Jogador defensor, Unidade escolhida) {
		escolhida.aumentarDano(this.poder);
		escolhida.aumentarVida(this.vida);
		escolhida.addEfeito(this);
	}

	@Override
	public void removerEfeito(Unidade unidade) {}

	@Override
	public void ativarEfeitoKill(Jogador jogador) {}
	
	@Override
	public void ativarEfeitoMorte(Jogador jogador) {}

	@Override
	public void passouRodada(Unidade unidade) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
}
