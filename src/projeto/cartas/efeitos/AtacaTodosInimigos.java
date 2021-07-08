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
	
	private void imprimeEvocadas(Jogador jogador) {
		ArrayList<Carta> aux = jogador.getEvocadas();
		for(int i = 0; i < aux.size(); i++) {
	   		System.out.printf("[%d] - %s\n", i + 1, aux.get(i).getNome());
	   	}
	   	System.out.println("");
	}	

	@Override
	public void aplicarEfeito(Jogador atacante, Jogador defensor, Jogador beneficiado) {
		//tratar o caso em que o atacante ou defensor não tem nenhuma evocada
		if(atacante.getQtdEvocadas() > 0 && defensor.getQtdEvocadas() > 0) {
			System.out.printf("Escolha uma carta para ganhar Julgar os adversários\n");
			imprimeEvocadas(beneficiado);
			Scanner ler = new Scanner(System.in);
			int escolha = ler.nextInt();
			while(escolha > beneficiado.getEvocadas().size() || escolha < 1) {
				System.out.println("Escolha inv�lida");
				escolha = ler.nextInt();
			}
			Unidade aux = (Unidade)beneficiado.getEvocadas().get(escolha - 1);
			ArrayList<Carta> aux2 = defensor.getEvocadas();
			for(int i = 0; i < defensor.getQtdEvocadas(); i++) {
				Unidade danificado = (Unidade)aux2.get(i);
				danificado.sofrerDano(aux.getDano());
			}
		
			
			
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
