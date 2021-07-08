package projeto;

import projeto.cartas.*;
import projeto.cartas.efeitos.AtacaTodosInimigos;
import projeto.cartas.efeitos.BuffAliadoInvocado;
import projeto.cartas.efeitos.ChamouX1;
import projeto.cartas.efeitos.CuraUnidade;
import projeto.cartas.efeitos.Dobradinha;
import projeto.decks.Deck;
import projeto.decks.DeckFactory;

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
			
			int numRodada = 1;
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
				
				System.out.printf("Rodada %d\n", numRodada);
				System.out.println();
				atacante.ganharMana(numRodada);
				defensor.ganharMana(numRodada);
				numRodada++;


				passadas = 0;
				while (!batalha && passadas != 2 && !rodadaIsOver) {
					imprimirTabuleiro();
					pegarEntrada(atacante, defensor);
					if(passadas == 2){
						break;
					}
					imprimirTabuleiro();
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
				if(defensor.getVida() <= 0) {
					System.out.printf("O %s foi derrotado, vitória de %s!\n", defensor.getNome(), atacante.getNome());
					System.out.println();
					rodadaIsOver = true;
				}
				else {
					if(atacante.getMana() > 0) {
						atacante.alterarManaFeitico(atacante.getMana());
					}
					if(defensor.getMana() > 0) {
						defensor.alterarManaFeitico(defensor.getMana());
					}
				}
			}
		exitSelected = true;
		System.out.println("Game terminated. Bye!");
		}
	}


	// ============================== Funções de Interação com Jogadores ==============================

	/**
	 * A aplicação obtem as entradas do jogador.
	 * O atacante pode escolher entre: Sumonar, Passar ou Atacar.
	 * O defensor pode escolher entre: Sumonar ou Passar.
	 */
	private void pegarEntrada(Jogador jogando, Jogador observando){
		
		//cheats
		//jogando.alterarManaFeitico(50);
		//jogando.setMana(50);
		
		int entrada;
		boolean finished = false;

		while (!finished){

			jogando.imprimirDadosIniciais();
			System.out.printf("Vez de %s:\n" +
					"[1] - Sumonar\n" +
					"[2] - Passar a vez\n", jogando.getNome());
			if(jogando.getTurno() == TipoTurno.ATAQUE){
				System.out.printf("[3] - Ataque\n");
			}
			System.out.println();

			entrada = Leitor.lerInt();
			finished = true;

			if(entrada == 1){
				finished = sumonar(jogando, observando);
				if(finished == true){
					passadas = 0;
				}
			} else if (entrada == 2) {
				System.out.printf("%s passou a vez\n\n", jogando.getNome());
				System.out.println();
				passadas += 1;
			} else if (entrada == 3 && jogando.getTurno() == TipoTurno.ATAQUE) {
				batalha = true;
				atacar(jogando, observando);
			} else {
				System.out.printf("Comando inválido\n\n");
				finished = false;
			}
		}

	}


	/**
	 * Se o atacante decidir atacar, então o defensor pode decidir
	 * se defender ou não.
	 */
	private void perguntarDefesa(Jogador defensor, Jogador atacante){
		int entrada;

		System.out.printf("Turno de Defesa do %s\nEscolha uma opção:\n", defensor.getNome());
		System.out.printf("[1] Defender \t [2] Passar\n");

		entrada = Leitor.lerInt();

		boolean valido = false;
		do{
			if(entrada == 1){
				defender(defensor, atacante);
				valido = true;
			} else if (entrada == 2) {
				valido = true;
			} else {
				System.out.println("Entrada inválida.");
			}
		}while(!valido);

	}

	// ============================== Funções de Batalha ==============================

	/**
	 * O atacante escolhe de 1 até no máximo 4 cartas para colocar
	 * no seu lado da mesa.
	 */
	private void atacar(Jogador atacante, Jogador defensor) {
		int entrada;
		Carta cartaEscolhida;
		int numEscolhidas = 0;
		boolean terminado = false;

		if (atacante.getQtdEvocadas() >= 1) {
			while ((atacante.getQtdEvocadas() >= 1 && mesa.getQtdAtacantes() < 4) && !terminado) {
				
				if(numEscolhidas > 0) {
					System.out.println("[0] - Finalizar jogada");
				}
				atacante.imprimeEvocadas();

				entrada = Leitor.lerInt();
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
		Carta cartaEscolhida;

		if(defensor.getQtdEvocadas() >= 1){
			while(mesa.getQtdAtacantes() > mesa.getQtdDefensores() && defensor.getQtdEvocadas() >= 1){
				defensor.imprimeEvocadas();

				entrada = Leitor.lerInt();

				cartaEscolhida = defensor.escolherCartaBatalha(entrada);
				if (cartaEscolhida != null) {
					mesa.printMesa();
					System.out.println("Escolha a posição do defensor\n");
					posicao = Leitor.lerInt();

					while (posicao < 1 || posicao > mesa.getQtdAtacantes() || !mesa.posEhValida(posicao, cartaEscolhida)) {
						System.out.println("Posição inválida");
						mesa.printMesa();
						System.out.println("Escolha a posição do defensor\n");
						posicao = Leitor.lerInt();
					} 
					mesa.adicionarDefensor((Unidade) cartaEscolhida, posicao);
					
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
	 * Devolve true se uma carta foi sumonada e false do contrario.
	 */
	private boolean sumonar(Jogador jogando, Jogador observando){
		Carta cartaEscolhida = jogando.escolherCarta();

		if(cartaEscolhida != null){
			cartaEscolhida.usarCarta(jogando, observando);
			return true;
		} else {
			return false;
		}
	}


	// ============================== Impressão do Tabuleiro ==============================

	private void imprimirTabuleiro(){
		System.out.println("___________________________________");
		System.out.printf("Evocadas de %s\n", jogador1.getNome());
		jogador1.mostrarEvocadas();
		System.out.println("\n####################################");
		System.out.printf("Campo de %s\n", jogador1.getTurnoString());
		mesa.mostrarLado(jogador1);
		System.out.println("\n");
		mesa.mostrarLado(jogador2);
		System.out.printf("\nCampo de %s\n", jogador2.getTurnoString());
		System.out.println("####################################");
		jogador2.mostrarEvocadas();
		System.out.printf("\nEvocadas de %s\n", jogador2.getNome());
		System.out.println("___________________________________");
	}


	// ============================== Inicialização ==============================

	private void iniciarMesa(){
		this.mesa = new Mesa();
		mesa.preencheMesa();
	}

	private void iniciarJogadores(){
		this.jogador1 = new Jogador(DeckFactory.obterDeck(0), "Player1");
		this.jogador2 = new Jogador(DeckFactory.obterDeck(0), "Player2");

		jogador1.setTurno(TipoTurno.ATAQUE);
		jogador2.setTurno(TipoTurno.DEFESA);
	}
}
