package projeto.cartas.efeitos;

import java.util.ArrayList;
import java.util.Scanner;

import projeto.Jogador;
import projeto.Leitor;
import projeto.cartas.Carta;
import projeto.cartas.Unidade;

//Efeito 2
public class BuffAliadoInvocado extends Efeito {
	private int poder;
	private int vida;
	private int duracao;
	
	public BuffAliadoInvocado(int poder, int vida) {
		this.poder = poder;
		this.vida = vida;
		this.duracao = 1;
		
	}
	
	
	private void imprimeEvocadas(Jogador jogador) {
		ArrayList<Carta> aux = jogador.getEvocadas();
		for(int i = 0; i < aux.size(); i++) {
	   		System.out.printf("[%d] - %s\n", i + 1, aux.get(i).getNome());
	   	}
	   	System.out.println("");
	}


	/**
	 * Retorna true se o efeito for aplicado corretamente,
	 * retorna false se o efeito não pode ser aplicado.
	 */
	@Override
	public void aplicarEfeito(Jogador atacante, Jogador defensor, Jogador beneficiado) {

		System.out.printf("Escolha uma carta para ganhar +%d de dano e +%d de vida\n", this.poder, this.vida);
		imprimeEvocadas(beneficiado);

		int escolha = Leitor.lerInt();

		while(escolha > beneficiado.getEvocadas().size() || escolha < 1) {
			System.out.println("Escolha inválida");
			Leitor.lerInt();
		}

		Unidade aux = (Unidade)beneficiado.getEvocadas().get(escolha - 1);
		aux.aumentarDano(this.poder);
		aux.aumentarVida(this.vida);
		aux.addEfeito(this);
	}

	@Override
	public void removerEfeito(Unidade unidade) {
		unidade.diminuiDano(this.poder);
		unidade.diminuiVida(this.vida);
		unidade.removerEfeito(this);
	}


	@Override
	public void ativarEfeitoKill(Jogador jogador) {}
	
	@Override
	public void ativarEfeitoMorte(Jogador jogador) {}


	@Override
	public void passouRodada(Unidade unidade) {
		this.duracao-=1;
		if (this.duracao == 0) {
			removerEfeito(unidade);
		}
		
	}

}
