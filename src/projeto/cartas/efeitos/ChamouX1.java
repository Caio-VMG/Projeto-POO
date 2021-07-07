package projeto.cartas.efeitos;

import java.util.ArrayList;
import java.util.Scanner;

import projeto.Jogador;
import projeto.cartas.Carta;
import projeto.cartas.Unidade;

// Efeito 6
public class ChamouX1 extends Efeito {
	
	private void imprimeEvocadas(Jogador jogador) {
		ArrayList<Carta> aux = jogador.getEvocadas();
		for(int i = 0; i < aux.size(); i++) {
	   		System.out.printf("[%d] - %s\n", i + 1, aux.get(i).getNome());
	   	}
	   	System.out.println("");
	}
	
	private static void mensagemMorte(Unidade derrotada) {
		System.out.printf("%s foi derrotado(a)\n", derrotada.getNome());
		System.out.println("");
	}
	
	@Override
	public void aplicarEfeito(Jogador atacante, Jogador defensor, Jogador beneficiado) {
		System.out.printf("Escolha um aliado para um combate imediato\n");
		imprimeEvocadas(beneficiado);
		Scanner ler = new Scanner(System.in);
		int escolha = ler.nextInt();
		while(escolha > beneficiado.getEvocadas().size() || escolha < 1) {
			System.out.println("Escolha inv�lida");
			escolha = ler.nextInt();
		}
		Unidade aux = (Unidade)beneficiado.getEvocadas().get(escolha - 1);
		
		System.out.printf("Escolha um adversário para um combate imediato\n");
		Jogador naoBeneficiado;
		if(beneficiado.equals(atacante)) {
			naoBeneficiado = defensor;
		}
		else {
			naoBeneficiado = atacante;
		}
		imprimeEvocadas(naoBeneficiado);
		escolha = ler.nextInt();
		while(escolha > naoBeneficiado.getEvocadas().size() || escolha < 1) {
			System.out.println("Escolha inv�lida");
			escolha = ler.nextInt();
		}
		Unidade aux2 = (Unidade)naoBeneficiado.getEvocadas().get(escolha - 1);
		Unidade.batalhaIndividual(aux, aux2);
		
		if(aux.getVida() <= 0) {
			mensagemMorte(aux);
		}
		if(aux2.getVida() <= 0) {
			mensagemMorte(aux2);
		}
	}

	@Override
	public void removerEfeito(Unidade unidade) {}

	@Override
	public void ativarEfeitoKill(Jogador jogador) {}
	
	@Override
	public void ativarEfeitoMorte(Jogador jogador) {}

}
