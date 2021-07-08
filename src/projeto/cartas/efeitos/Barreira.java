package projeto.cartas.efeitos;

import java.util.ArrayList;
import java.util.Scanner;

import projeto.Jogador;
import projeto.cartas.Carta;
import projeto.cartas.Unidade;

//Efeito 11
public class Barreira extends Efeito{
	private int duracao;
	
	public Barreira(){
		this.duracao = 1;
		super.nome = "Barreira";
	}	

	@Override
	public void aplicarEfeito(Jogador atacante, Jogador defensor, Unidade escolhida, TipoChamada tipo) {
		if (!escolhida.getEfeitos().contains(this)) {
			escolhida.addEfeito(this);
		}
		escolhida.setBarreira(true);
	}

	@Override
	public void removerEfeito(Unidade unidade) {
		unidade.removerEfeito(this);
		unidade.setBarreira(false);
	}
	
	@Override
	public void ativarEfeitoKill(Jogador jogador) {}
	
	@Override
	public void ativarEfeitoMorte(Jogador jogador) {}


	@Override
	public void passouRodada(Unidade unidade) {
		this.duracao -= 1;
		if (this.duracao == 0) {
			removerEfeito(unidade);
		}
		
	}
}
