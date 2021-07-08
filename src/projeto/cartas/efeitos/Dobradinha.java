package projeto.cartas.efeitos;

import java.util.ArrayList;
import java.util.Scanner;

import projeto.Jogador;
import projeto.cartas.Carta;
import projeto.cartas.Unidade;


// Efeito 5
public class Dobradinha extends Efeito {

	public Dobradinha(){
		super.nome = "Dobradinha";
	}

	private void imprimeEvocadas(Jogador jogador) {
		ArrayList<Carta> aux = jogador.getEvocadas();
		for(int i = 0; i < aux.size(); i++) {
	   		System.out.printf("[%d] - %s\n", i + 1, aux.get(i).getNome());
	   	}
	   	System.out.println();
	}	
	
	
	@Override
	public void aplicarEfeito(Jogador atacante, Jogador defensor, Unidade escolhida) {
		escolhida.aumentarDano(escolhida.getDano());
		escolhida.aumentarVidaMaxima(escolhida.getVidaMaxima());
		}
		//aux.addEfeito(this);
		//acho que não precisa adicionar na lista de efeitos, já que o efeito é instantâneo, certo?

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
