package projeto;

import projeto.cartas.*;

import java.util.Random;
import java.util.Scanner;

public class Game {

	private Jogador jogador1;
	private Jogador jogador2;

	private boolean exitSelected;

	public void start() {
		exitSelected = false;
		System.out.println("Game started!");
		IniciarJogadores(jogador1, jogador2);

		while (!exitSelected) {

			//Começa o jogo

			jogador1.primeiraCompra();// j tem incluso a possibilidade de trocar as cartas
			jogador1.trocarCartas();
			jogador2.primeiraCompra();
			jogador2.trocarCartas();

			Jogador atacante = jogador1;
			jogador1.setTurno(TipoTurno.ATAQUE);
			Jogador defensor = jogador2;
			jogador2.setTurno(TipoTurno.DEFESA);

			//Começa o loop
			boolean rodadaIsOver = false;
			while (!rodadaIsOver) {

				Jogador aux = atacante;
				atacante = defensor;
				atacante.setTurno(TipoTurno.ATAQUE);
				defensor = aux;
				defensor.setTurno(TipoTurno.DEFESA);

				defensor.comprarCarta();
				atacante.comprarCarta();
				atacante.ganharMana();
				defensor.ganharMana();

				int passou = 0;

				while (passou != 2) {
					// Usar inteiros como comandos.

					// drawBoard();
					pegarEntradaAtacante(atacante);

					// drawBoard();
					pegarEntradaDefensor(defensor);
				}
			}

		}

		exitSelected = true;
		System.out.println("Game terminated. Bye!");

		//fim do loop
        	/*
            1. Dois jogadores,
            2. Pescar 4 cartas,
            3. Trocar 4 cartas,
            --> Loop
            4. Iniciar rodada (Jogador 1: atacante, Jogador 2: defensor),
                4.1. Dar + uma carta para cada.
                4.2. Ganha mana,
            5. Iniciar turno Jogador 1,
                5.1. Sumonar cartas ou passa,

            6. Iniciar turno Jogador 2,
                6.1. Sumonar cartas ou passa,
             */
	}
	
	private Deck criarDeckDummy() {
		int j;
		Deck deckrandom = new Deck("Dummy");
		Random random = new Random();
		for (int i = 0; i<40; i++) {			
		    int valor = random.nextInt(11);
		    if (valor == 0) {
		    	Campeao garen = new Campeao("Garen", 5, 5, 5);
		    	deckrandom.add(garen);
		    } else if (valor == 1) {
		    	Unidade tiana = new Unidade("Tiana", 8, 7, 7);
		    	deckrandom.add(tiana);
		    } else if (valor == 2) {
		    	Unidade vanguarda = new Unidade("Vanguarda", 4, 3, 3);
		    	deckrandom.add(vanguarda);
		    } else if (valor == 3) {
		    	Unidade duelista = new Unidade("Duelista", 3, 2, 3);
		    	deckrandom.add(duelista);
		    } else if (valor == 4) {
		    	Unidade defensor = new Unidade("Defensor", 2, 2, 2);
		    	deckrandom.add(defensor);
		    } else if (valor == 5) {
		    	Unidade poro = new Unidade("Poro", 1, 1, 2);
		    	deckrandom.add(poro);
		    } else if (valor == 6) {
		    	Unidade poroD = new Unidade("Poro Defensor", 1, 2, 1);
		    	deckrandom.add(poroD);
		    } else if (valor == 7) {
		    	Feitico julgamento = new Feitico("Julgamento", 8);
		    	deckrandom.add(julgamento);
		    } else if (valor == 8) {
		    	Feitico valorR = new Feitico("Valor Redobrado", 6);
		    	deckrandom.add(valorR);
		    } else if (valor == 9) {
		    	Feitico golpeC = new Feitico("Golpe Certeiro", 1);
		    	deckrandom.add(golpeC);
		    } else{
		    	Feitico combate1a1 = new Feitico("Comabte um a um", 2);
		    	deckrandom.add(combate1a1);
		    }		    
		}
		return deckrandom;
	}

	private void pegarEntradaAtacante(Jogador atacante){
		Scanner scan = new Scanner(System.in);
		int entrada = scan.nextInt();

		if(entrada == 1){
			atacante.sumonar();
		} else if (entrada == 2) {
			atacante.usarFeitico();
		} else if (entrada == 3) {
			atacante.passar();
		} else if (entrada == 4) {
			atacante.atacar();
		}
	}

	private void pegarEntradaDefensor(Jogador defensor) {
		Scanner scan = new Scanner(System.in);
		int entrada = scan.nextInt();

		if(entrada == 1){
			defensor.sumonar();
		} else if (entrada == 2) {
			defensor.defender();
		} else if (entrada == 3) {
			defensor.passar();
		}
	}

    private void IniciarJogadores(Jogador jogador1, Jogador jogador2){
		//talvez seja necessrio salvar a referencia do deck
		jogador1 = new Jogador(criarDeckDummy());
		jogador2 = new Jogador(criarDeckDummy());
	}
}
