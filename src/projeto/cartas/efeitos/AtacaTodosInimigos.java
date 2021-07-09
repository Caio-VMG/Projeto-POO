package projeto.cartas.efeitos;

import java.util.ArrayList;
import java.util.Scanner;

import projeto.Jogador;
import projeto.cartas.Carta;
import projeto.cartas.Unidade;

//Efeito 8
public class AtacaTodosInimigos extends Efeito {
	private int poder;
	private int vida;
	
	public AtacaTodosInimigos(int poder, int vida) {

		super.nome = "AtacaTodosInimigos";

		this.poder = poder;
		this.vida = vida;
	}	

	@Override
	public void aplicarEfeito(Jogador atacante, Jogador defensor, Unidade escolhida, TipoChamada tipo) {
		if(defensor.getQtdEvocadas() > 0) {
			ArrayList<Carta> afetado = defensor.getEvocadas();
			for(int i = 0; i < defensor.getQtdEvocadas(); i++) {
				Unidade danificado = (Unidade)afetado.get(i);
				danificado.sofrerDano(escolhida.getDano());
			}	
		}
		else {
			defensor.sofrerDanoNexus(escolhida.getDano());
		}
	}

	@Override
	public void removerEfeito(Unidade unidade) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ativarEfeitoKill(Jogador jogador) {
		// TODO Auto-generated method stub
		
	}
	
	public void ativarEfeitoMorte(Jogador jogador) {
		// TODO Auto-generated method stub
		
	}
	/*
	public void aplicarEfeito(Jogador atacante, Jogador defensor, Jogador beneficiado) {
		for(int i = 0; i < )
	}
	 */

	@Override
	public void passouRodada(Unidade unidade) {
		// TODO Auto-generated method stub
		
	}
}
