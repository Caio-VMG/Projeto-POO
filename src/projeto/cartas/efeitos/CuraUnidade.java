package projeto.cartas.efeitos;

import java.util.ArrayList;
import java.util.Scanner;

import projeto.Jogador;
import projeto.cartas.Carta;
import projeto.cartas.Unidade;

// Efeito 4
public class CuraUnidade extends Efeito {

	public CuraUnidade(){
		super.nome = "Cura Unidade";
	}
	
	@Override
	public void aplicarEfeito(Jogador atacante, Jogador defensor, Unidade escolhida, TipoChamada tipo) {
			escolhida.aumentarVida(escolhida.getVidaMaxima());
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
