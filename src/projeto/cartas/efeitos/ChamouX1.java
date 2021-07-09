package projeto.cartas.efeitos;

import java.util.ArrayList;
import java.util.Scanner;

import projeto.Jogador;
import projeto.cartas.Carta;
import projeto.cartas.Unidade;

// Efeito 6
public class ChamouX1 extends Efeito {

	public ChamouX1(){
		super.nome = "Chamou X1";
	}
	
	private static void mensagemMorte(Unidade derrotada) {
		System.out.printf("%s foi derrotado(a)\n", derrotada.getNome());
		System.out.println();
	}
	
	@Override
	public void aplicarEfeito(Jogador atacante, Jogador defensor, Unidade escolhida, TipoChamada tipo) {
		System.out.println("Escolha um oponente para um combate imediato");
		defensor.imprimeEvocadas();
		Scanner ler = new Scanner(System.in);
		int escolha = ler.nextInt();
		while(escolha > defensor.getEvocadas().size() || escolha < 1) {
			System.out.println("Escolha inv�lida");
			escolha = ler.nextInt();
		}
		Unidade afetado = (Unidade)defensor.getEvocadas().get(escolha - 1);
		Unidade.batalhaIndividual(escolhida, afetado);
		
		if(escolhida.getVida() <= 0) {
			mensagemMorte(escolhida);
		}
		if(afetado.getVida() <= 0) {
			mensagemMorte(afetado);
		}
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
