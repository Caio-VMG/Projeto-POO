package projeto.cartas.efeitos;

import java.util.ArrayList;
import java.util.Scanner;

import projeto.Jogador;
import projeto.cartas.Carta;
import projeto.cartas.Unidade;

//Efeito 10
public class ZeraPoderInimigo extends Efeito {
	private Unidade afetado;
	private int danoDoAfetado;
	private int duracao = 1;

	public ZeraPoderInimigo(){
		super.nome = "Zera Pode Inimigo";
	}
	
	@Override
	public void aplicarEfeito(Jogador atacante, Jogador defensor, Unidade escolhida, TipoChamada tipo) {
		System.out.printf("Escolha um adversário para zerar o poder\n");
		defensor.imprimeEvocadas();
		Scanner ler = new Scanner(System.in);
		int escolha = ler.nextInt();
		while(escolha > defensor.getEvocadas().size() || escolha < 1) {
			System.out.println("Escolha inv�lida");
			escolha = ler.nextInt();
		}
		Unidade aux = (Unidade)defensor.getEvocadas().get(escolha - 1);
		this.afetado = aux;
		this.danoDoAfetado = aux.getDano();
		aux.diminuiDano(aux.getDano());
	}

	@Override
	public void removerEfeito(Unidade unidade) {
		afetado.aumentarDano(danoDoAfetado);
	}

	@Override
	public void ativarEfeitoKill(Jogador jogador) {}
	
	@Override
	public void ativarEfeitoMorte(Jogador jogador) {}

	@Override
	public void passouRodada(Unidade unidade) {
		this.duracao -=1;
		if (this.duracao == 0) {
			removerEfeito(unidade);
		}
		
	}

}
