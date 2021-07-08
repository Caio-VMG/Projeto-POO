package projeto.cartas.efeitos;

import java.util.ArrayList;
import java.util.Scanner;

import projeto.Jogador;
import projeto.cartas.Carta;
import projeto.cartas.Unidade;

//Efeito 7
public class UnidadeAtacaNexus extends Efeito {

	public UnidadeAtacaNexus(){
		super.nome = "Unidade Ataca Nexus";
	}

	private static void imprimeEvocadas(Jogador jogador) {
		ArrayList<Carta> aux = jogador.getEvocadas();
		for(int i = 0; i < aux.size(); i++) {
	   		System.out.printf("[%d] - %s\n", i + 1, aux.get(i).getNome());
	   	}
	   	System.out.println("");
	}
	
	@Override
	public void aplicarEfeito(Jogador atacante, Jogador defensor, Jogador beneficiado) {
		System.out.printf("Escolha uma unidade atacar o nexus inimigo\n");
		imprimeEvocadas(beneficiado);
		Scanner ler = new Scanner(System.in);
		int escolha = ler.nextInt();
		while(escolha > beneficiado.getEvocadas().size() || escolha < 1) {
			System.out.println("Escolha inv�lida");
			escolha = ler.nextInt();
		}
		Unidade aux = (Unidade)beneficiado.getEvocadas().get(escolha - 1);
		Jogador naoBeneficiado;
		if(beneficiado.equals(atacante)) {
			naoBeneficiado = defensor;
		}
		else {
			naoBeneficiado = atacante;
		}
		naoBeneficiado.sofrerDanoNexus(aux.getDano());
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
