package projeto;

import projeto.cartas.*;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Game {
	private Jogador jogador1;
	private Jogador jogador2;
	private boolean exitSelected;
	Mesa mesa;
	
	private void iniciarBatalha(Jogador atacante, Jogador defensor) {
		mesa.batalha(atacante, defensor);
	}

	public void start() {
		exitSelected = false;
		System.out.println("Game started!");
		System.out.println("");
		mesa = new Mesa();
		this.jogador1 = new Jogador(criarDeckDummy(), "Player1");
		this.jogador2 = new Jogador(criarDeckDummy(), "Player2");
		//IniciarJogadores(jogador1, jogador2);
	
		
		while (!exitSelected) {
			//Começa o jogo

			jogador1.primeiraCompra();// j tem incluso a possibilidade de trocar as cartas
			jogador2.primeiraCompra();

			Jogador atacante = jogador1;
			jogador1.setTurno(TipoTurno.ATAQUE);
			Jogador defensor = jogador2;
			jogador2.setTurno(TipoTurno.DEFESA);

			//ComeÃ§a o loop
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
					passou = 0;

					// drawBoard();
					passou += pegarEntradaAtacante(atacante, defensor);

					// drawBoard();
					passou += pegarEntradaDefensor(atacante, defensor);
					if(passou == 3) {
						iniciarBatalha(atacante, defensor);
					}
					mesa.inverteMesa();
				}
				//mesa.inverteMesa();
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

	private int pegarEntradaAtacante(Jogador atacante, Jogador defensor){
		//cheat
		atacante.setMana(10);

		System.out.printf("Jogador %s (atacante):\nmana: %d\nvida do nexus: %d\n", atacante.getNome()
				,atacante.getMana(),atacante.getVida());
		
		System.out.printf("Vez de %s:\n[1] - Sumonar\n[2] - "
				+ "Passar a vez\n[3] - Atacar\n", atacante.getNome());
		Scanner scan = new Scanner(System.in);
		int entrada = scan.nextInt();
		if(entrada == 1){
			Carta cartaEscolhida = atacante.escolherCarta();
			if(cartaEscolhida != null){
				cartaEscolhida.usarCarta(atacante, defensor);
			} else {
				return pegarEntradaAtacante(atacante, defensor);
			}
		} else if (entrada == 2) {
			atacante.passar();
			System.out.printf("%s passou a vez\n", atacante.getNome());
			System.out.println("");
		
		} else if (entrada == 3) {
			Carta cartaEscolhida = atacante.atacar();
			if (cartaEscolhida != null) {
				//talvez seja interessante criar um enum pras posições?
				mesa.adicionarAtacante((Unidade) cartaEscolhida);	
				return 2;
			}				
			
		} else {
			System.out.printf("%s esse comando não é válido\n", atacante.getNome());
			return pegarEntradaAtacante(atacante, defensor);
		}
		return 1;
	}

	private int pegarEntradaDefensor(Jogador atacante, Jogador defensor) {
		//cheat
			defensor.setMana(10);

			System.out.printf("Jogador %s (defensor):\nmana: %d\nvida do nexus: %d\n", defensor.getNome(), 
					defensor.getMana(),defensor.getVida());
			
			System.out.printf("Vez de %s:\n[1] - Sumonar\n[2] - "
					+ "Passar a vez\n[3] - Defender\n", defensor.getNome());
			
			Scanner scan = new Scanner(System.in);
			int entrada = scan.nextInt();
			
			if(entrada == 1){
				Carta cartaEscolhida = defensor.escolherCarta();
				if(cartaEscolhida != null){
					cartaEscolhida.usarCarta(defensor, atacante);
				}
			} else if (entrada == 2) {
				return 1;
			
			} else if (entrada == 3) {
				Carta cartaEscolhida = defensor.defender();
				System.out.println("Escolha a posição do defensor (de 1 a 4)\n");
				Scanner ler = new Scanner(System.in);
				int posicao = ler.nextInt();
				if(posicao < 1 || posicao > 4) {
					System.out.println("Posição inválida");
				}
				else {
					mesa.adicionarDefensor((Unidade) cartaEscolhida, posicao);
				}
			} else {
				System.out.printf("%s esse comando não é válido\n", defensor.getNome());
				return pegarEntradaDefensor(atacante, defensor);
			}
			//scan.close();
		//defensor nao pode usar feitico
		return 1;
	}
}
