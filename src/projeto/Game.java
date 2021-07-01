package projeto;

import projeto.cartas.*;

import java.util.Random;
import java.util.Scanner;

public class Game {
	private Jogador jogador1;
	private Jogador jogador2;
	private Mesa mesa;

	private int passadas;
	private boolean batalha;
	private boolean exitSelected;

	// ============================== START ==============================


	public void start() {
		exitSelected = batalha = false;
		passadas = 0;

		System.out.println("Game started!\n");

		iniciarMesa();
		iniciarJogadores();
		Jogador atacante = jogador1;
		Jogador defensor = jogador2;


		while (!exitSelected) {

			jogador1.primeiraCompra();
			jogador2.primeiraCompra();

			//Começa o loop
			boolean rodadaIsOver = false;
			while (!rodadaIsOver) {
				
				Jogador aux = atacante;
				atacante = defensor;
				atacante.setTurno(TipoTurno.ATAQUE);
				defensor = aux;
				defensor.setTurno(TipoTurno.DEFESA);
				
				
				defensor.pegarCarta();
				atacante.pegarCarta();
				atacante.ganharMana();
				defensor.ganharMana();

				passadas = 0;
				while (!batalha && passadas != 2) {
					pegarEntrada(atacante, defensor);
					if(batalha == false){
						pegarEntrada(defensor, atacante);
					} else {
						if(defensor.getQtdEvocadas() > 0) {
							perguntarDefesa(defensor, atacante);
						}
					}
				}
				mesa.batalhaMesa(atacante, defensor);
				batalha = false;
				mesa.inverteMesa();
			}
		}
		exitSelected = true;
		System.out.println("Game terminated. Bye!");
	}


	// ============================== Funções de Interação com Jogadores ==============================

	/**
	 * A aplicação obtem as entradas do jogador.
	 * O atacante pode escolher entre: Sumonar, Passar ou Atacar.
	 * O defensor pode escolher entre: Sumonar ou Passar.
	 */
	private void pegarEntrada(Jogador jogando, Jogador observando){
		//Cheat
		jogando.setMana(10);

		jogando.imprimirDadosIniciais();
		System.out.printf("Vez de %s:\n" +
				"[1] - Sumonar\n" +
				"[2] - Passar a vez\n", jogando.getNome());
		if(jogando.getTurno() == TipoTurno.ATAQUE){
			System.out.printf("[3] - Ataque\n\n");
		}
		System.out.println("");

		Scanner scan = new Scanner(System.in);
		int entrada = scan.nextInt();

		if(entrada == 1){
			sumonar(jogando, observando);
			passadas = 0;

		} else if (entrada == 2) {
			System.out.printf("%s passou a vez\n\n", jogando.getNome());
			System.out.println("");
			passadas += 1;

		} else if (entrada == 3 && jogando.getTurno() == TipoTurno.ATAQUE) {
			batalha = true;
			atacar(jogando, observando);
		} else {
			System.out.printf("Comando inválido\n", jogando.getNome());
			System.out.println("");
			pegarEntrada(jogando, observando);
		}
	}


	/**
	 * Se o atacante decidir atacar, então o defensor pode decidir
	 * se defender ou não.
	 */
	private void perguntarDefesa(Jogador defensor, Jogador atacante){
		System.out.println("Turno de Defesa\nEscolha uma opção:");
		System.out.printf("[1] Defender \t [2] Passar\n");
		Scanner scan = new Scanner(System.in);
		int entrada = scan.nextInt();
		boolean comandoValido = false;
		while(!comandoValido)
			if(entrada == 1){
				defender(defensor, atacante);
				comandoValido = true;
			} else if (entrada == 2){
				comandoValido = true;
			}
			else{
				System.out.println("Comando Inválido.");
			}
	}

	// ============================== Funções de Batalha ==============================

	/**
	 * O atacante escolhe de 1 até no máximo 4 cartas para colocar
	 * no seu lado da mesa.
	 */
	private void atacar(Jogador atacante, Jogador defensor) {
		int entrada;
		Scanner scan;
		Carta cartaEscolhida;
		int numEscolhidas = 0;
		boolean terminado = false;

		if (atacante.getQtdEvocadas() >= 1) {
			while ((atacante.getQtdEvocadas() >= 1 && mesa.getQtdAtacantes() < 4) && !terminado) {
				
				if(numEscolhidas > 0) {
					System.out.println("[0] - Finalizar jogada");
				}
				atacante.imprimeEvocadas();
				
				scan = new Scanner(System.in);
				entrada = scan.nextInt();
				if(entrada == 0) {
					terminado = true;
				}
				else {
					cartaEscolhida = atacante.escolherCartaBatalha(entrada);
					if (cartaEscolhida != null) {
						mesa.adicionarAtacante((Unidade) cartaEscolhida);
						numEscolhidas++;
					} 
					else {
						System.out.println("Entrada Inválida.");
					}
				}
			}
		} else {
			batalha = false;
			System.out.println("Você não tem cartas para atacar.");
			pegarEntrada(atacante, defensor);
		}
	}

	/**
	 * No turno que o defensor escolhe se defender,
	 * ele deve escolher quais as unidades sumonadas vão batalhar
	 * contra cada uma das unidades de ataque.
	 */
	private void defender(Jogador defensor, Jogador atacante) {
		int entrada, posicao;
		Scanner scan;
		Carta cartaEscolhida;

		if(defensor.getQtdEvocadas() >= 1){
			while(mesa.getQtdAtacantes() > mesa.getQtdDefensores() && defensor.getQtdEvocadas() >= 1){
				defensor.imprimeEvocadas();

				scan = new Scanner(System.in);
				entrada = scan.nextInt();

				cartaEscolhida = defensor.escolherCartaBatalha(entrada);
				if (cartaEscolhida != null) {
					mesa.printMesa();
					System.out.println("Escolha a posição do defensor\n");
					posicao = scan.nextInt();

					if (posicao < 1 || posicao > mesa.getQtdAtacantes()) {
						System.out.println("Posição inválida");
					} else {
						mesa.adicionarDefensor((Unidade) cartaEscolhida, posicao);
					}
				} else {
					System.out.println("Entrada Inválida.");
				}
			}
		} else {
			System.out.println("Você não tem cartas para defender.");
		}
	}

	/**
	 * O jogador vai sumonar uma carta. Para isso, vai gastar mana e escolher
	 * uma que está em sua mão.
	 */
	private void sumonar(Jogador jogando, Jogador observando){
		Carta cartaEscolhida = jogando.escolherCarta();

		if(cartaEscolhida != null){
			cartaEscolhida.usarCarta(jogando, observando);
		} else {
			pegarEntrada(jogando, observando);
		}
	}



	// ============================== Inicialização ==============================

	private void iniciarMesa(){
		this.mesa = new Mesa();
		mesa.preencheMesa();
	}

	private void iniciarJogadores(){
		this.jogador1 = new Jogador(criarDeckDummy(), "Player1");
		this.jogador2 = new Jogador(criarDeckDummy(), "Player2");

		jogador1.setTurno(TipoTurno.ATAQUE);
		jogador2.setTurno(TipoTurno.DEFESA);
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
				Feitico combate1a1 = new Feitico("Combate um a um", 2);
				deckrandom.add(combate1a1);
			}
		}
		return deckrandom;
	}
}
