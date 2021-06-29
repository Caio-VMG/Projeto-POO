package projeto.cartas.efeitos;

import java.util.ArrayList;

import projeto.Jogador;
import projeto.cartas.Carta;
import projeto.cartas.Unidade;

public class BuffAliadosInvocados extends Efeito {
	private int poder;
	private int vida;
	
	public BuffAliadosInvocados(int poder, int vida) {
		this.poder = poder;
		this.vida = vida;
	}
	
	public void aplicarEfeito(Jogador atacante, Jogador defensor, Jogador beneficiado) {
		ArrayList<Carta> aux = beneficiado.getEvocadas();
		for(int i = 0; i < aux.size(); i++) {
			Unidade aux2 = (Unidade)aux.get(i);
			aux2.aumentarDano(this.poder);
			aux2.aumentarVida(this.vida);
			aux2.addEfeito(this);
		}
	}

	@Override
	public void removerEfeito(Unidade unidade) {}

	@Override
	public void ativarEfeito(Jogador jogador) {}
	
	
	
	
}
