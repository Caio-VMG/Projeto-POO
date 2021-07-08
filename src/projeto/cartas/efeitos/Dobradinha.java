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
	public void aplicarEfeito(Jogador atacante, Jogador defensor, Jogador beneficiado) {
		//precisamos tratar o caso em que o beneficiado não tem nenhuma carta evocada
		if(beneficiado.getQtdEvocadas() > 0){
			System.out.printf("Escolha uma carta para ganhar o efeito Dobradinha\n");
			imprimeEvocadas(beneficiado);
			Scanner ler = new Scanner(System.in);
			int escolha = ler.nextInt();
			while(escolha > beneficiado.getEvocadas().size() || escolha < 1) {
				System.out.println("Escolha inv�lida");
				escolha = ler.nextInt();
			}
			Unidade aux = (Unidade)beneficiado.getEvocadas().get(escolha - 1);
			aux.aumentarDano(aux.getDano());
			aux.aumentarVidaMaxima(aux.getVidaMaxima());
		}
		//aux.addEfeito(this);
		//acho que não precisa adicionar na lista de efeitos, já que o efeito é instantâneo, certo?
		
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
