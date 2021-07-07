package projeto.cartas.efeitos;

import java.util.ArrayList;
import java.util.Scanner;

import projeto.Jogador;
import projeto.cartas.Carta;
import projeto.cartas.Unidade;

//Efeito 9
public class MorreuComprou extends Efeito {

int duracao = 1;
	
	private void imprimeEvocadas(Jogador jogador) {
		ArrayList<Carta> aux = jogador.getEvocadas();
		for(int i = 0; i < aux.size(); i++) {
	   		System.out.printf("[%d] - %s\n", i + 1, aux.get(i).getNome());
	   	}
	   	System.out.println("");
	}
	
	@Override
	public void aplicarEfeito(Jogador atacante, Jogador defensor, Jogador beneficiado) {
		System.out.printf("Escolha uma carta para ganhar o efeito MorreuComprou\n");
		imprimeEvocadas(beneficiado);
		Scanner ler = new Scanner(System.in);
		int escolha = ler.nextInt();
		while(escolha > beneficiado.getEvocadas().size() || escolha < 1) {
			System.out.println("Escolha inv�lida");
			escolha = ler.nextInt();
		}
		Unidade aux = (Unidade)beneficiado.getEvocadas().get(escolha - 1);
		aux.addEfeito(this);
	}
	
	@Override
	public void ativarEfeitoMorte(Jogador jogador) {
		System.out.printf("O jogador %s ganhou uma carta devido ao efeito Morreu Comprou! \n",jogador.getNome());
		System.out.println();
		jogador.pegarCarta();
	}
	
	public void ativarEfeitoKill(Jogador jogador) {
	}
	
	@Override
	public void removerEfeito(Unidade unidade) {
		unidade.removerEfeito(this);
	}

	@Override
	public void passouRodada(Unidade unidade) {
		this.duracao -=1;
		if (this.duracao == 0) {
			removerEfeito(unidade);
		}
		
	}

}
