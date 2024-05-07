package projeto;

import java.util.Random;

import projeto.cartas.*;
import projeto.decks.DeckFactory;

public class Game {
	private Jogador jogador1;
	private Jogador jogador2;
	private Mesa mesa;

	private int passadas;
	private boolean batalha;

	// ============================== START ==============================


	public void start() {
		boolean exitSelected = batalha = false;
		passadas = 0;
		iniciarMesa();
		
		escolherModo();		
		
		Jogador atacante = jogador1;
		Jogador defensor = jogador2;


		while (!exitSelected) {
			jogador1.primeiraCompra();
			jogador2.primeiraCompra();
			
			int numRodada = 1;
			boolean rodadaIsOver = false;
			while (!rodadaIsOver) { // Loop das rodadas
				
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
				imprimirTabuleiro();
				while (!batalha && passadas != 2) { // Loop dos turnos
					realizarTurno(atacante, defensor);
					if(passadas == 2){
						break;
					}
					imprimirTabuleiro();
					if(!batalha){
						realizarTurno(defensor, atacante);
					} else {
						if(defensor.getQtdEvocadas() > 0) {
							if(defensor.isConsciente()){
								perguntarDefesa(defensor);
							} else {
								simularDefesa(defensor);
							}
						}
					}
					imprimirTabuleiro();
				}

				if(batalha)
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
		System.out.println("Fim de Jogo! Obrigado por Jogar!");
		}
	}


	// ============================== Funções de Turnos ==============================


	/**
	 * Se jogando for um jogador propriamente, a entrada dele é coletada.
	 * Se for um bot, um comportamento de decisão é simulado
	 */
	private void realizarTurno(Jogador jogando, Jogador observando){
		if(jogando.isConsciente()){
			pegarEntrada(jogando, observando);
		} else {
			simularComportamento(jogando, observando);
		}
	}


	// ============================== Funções de Decisão dos Jogadores ==============================


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
				System.out.printf("[3] - Ataque\n\n");
			}

			entrada = Leitor.lerInt();
			finished = true;

			if(entrada == 1){
				if(jogando.getQtdEvocadas() == 6){
					finished = substituirCartas(jogando);
				} else {
					finished = sumonar(jogando, observando);
					if(finished){
						passadas = 0;
					}
				}
			} else if (entrada == 2) {
				System.out.printf("%s passou a vez\n\n", jogando.getNome());
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
	private void perguntarDefesa(Jogador defensor){
		int entrada;

		System.out.printf("Turno de Defesa do %s\nEscolha uma opção:\n", defensor.getNome());
		System.out.printf("[1] Defender \t [2] Passar\n");

		entrada = Leitor.lerInt();

		boolean valido = false;
		do{
			if(entrada == 1){
				defender(defensor);
				valido = true;
			} else if (entrada == 2) {
				valido = true;
			} else {
				System.out.println("Entrada inválida.");
			}
		}while(!valido);

	}


	/**
	 * Quando atinge o limite de cartas evocadas, o jogador tem a opcao de substituir.
	 * Retorna true se essa opcao for selecionada
	 * Retorna false do contrario.
	 */
	private boolean substituirCartas(Jogador jogando){
		System.out.printf("Atingiu o limite de cartas evocadas\n");
		System.out.printf("Deseja substituir uma carta?\n[1] Sim [2] Não\n");
		int entrada = Leitor.lerInt();

		boolean trocou = false;
		boolean finished = false;

		while(!finished){
			finished = true;
			if(entrada == 1){
				trocou = jogando.definirSubstituicao();
			} else if(entrada == 2){

			} else{
				finished = false;
				System.out.println("Entrada Inválida");
			}
		}
		return trocou;
	}

	// ============================== Funções de Simulação dos Bots ==============================

	/**
	 * Um bot escolhe tomar uma decisao em um determinado turno.
	 * Entre sumonar, passar a vez ou atacar.
	 * O bot sempre toma uma decisao condizente com o que é possível fazer.
	 */
	private void simularComportamento(Jogador jogando, Jogador observando){
		int decisao = jogando.tomarDecisao();
		if(decisao == 1){
			jogando.sumonarAleatoriamente(jogando, observando);
			passadas = 0;
		} else if (decisao == 2){
			System.out.printf("%s passou a vez\n\n", jogando.getNome());
			passadas += 1;
		} else {
			boolean finished = false;
			int cartasEscolhidas = 0;
			while(!finished){
				Carta escolhida = jogando.escolherCartaBatalha(0);
				if(escolhida == null && cartasEscolhidas != 0) {
					finished = true;
				}else if(mesa.getQtdAtacantes() == 4 && escolhida != null){
					finished = true;
					jogando.getMao().add(escolhida);
				} else if(escolhida != null) {
					cartasEscolhidas++;
					mesa.adicionarAtacante((Unidade) escolhida);
				}
			}
			batalha = true;
		}
	}


	private void simularDefesa(Jogador defensor){
		boolean finished = false;
		while(!finished){
			Carta escolhida = defensor.escolherCartaBatalha(0);
			if(escolhida != null){
				int pos = defensor.escolherPosicao(mesa);
				if(pos == -1){
					defensor.addCartaMao(escolhida);
					finished = true;
				} else {
					mesa.adicionarDefensor((Unidade) escolhida, pos + 1);
				}
			} else {
				finished = true;
			}
		}
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
	private void defender(Jogador defensor) {
		int entrada, posicao;
		Carta cartaEscolhida;
		boolean terminado = false;

		if(defensor.getQtdEvocadas() >= 1){
			while(mesa.getQtdAtacantes() > mesa.getQtdDefensores() && defensor.getQtdEvocadas() >= 1 && !terminado){
				defensor.imprimeEvocadas();

				entrada = Leitor.lerInt();
				cartaEscolhida = defensor.escolherCartaBatalha(entrada);
				if (cartaEscolhida != null) {
					mesa.printMesa();
					System.out.println("Escolha a posição do defensor ou digite 0 para cancelar a defesa\n");
					posicao = Leitor.lerInt();
					
					if(posicao == 0) {
						terminado = true;
					}
					
					while ((posicao < 1 || posicao > mesa.getQtdAtacantes() || !mesa.posEhValida(posicao, cartaEscolhida)) && !terminado) {
						System.out.println("Posição inválida");
						mesa.printMesa();
						System.out.println("Escolha a posição do defensor ou digite 0 para cancelar a defesa\n");
						posicao = Leitor.lerInt();
						if(posicao == 0) {
							terminado = true;
						}
					}
					if(!terminado) {
						mesa.adicionarDefensor((Unidade) cartaEscolhida, posicao);
						terminado = true;
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

	/**
	 * Imprime o tabuleiro mostrando todas as cartas evocadas e a mesa de batalha.
	 */
	private void imprimirTabuleiro(){
		System.out.println("\n\n___________________________________");
		System.out.printf("Evocadas de %s (Vida: %d|Mana: %d)\n", jogador1.getNome(),jogador1.getVida(), jogador1.getMana());
		jogador1.mostrarEvocadas();
		System.out.println("\n####################################");
		System.out.printf("Campo de %s\n", jogador1.getTurnoString());
		mesa.mostrarLado(jogador1);
		System.out.println("\n");
		mesa.mostrarLado(jogador2);
		System.out.printf("\nCampo de %s\n", jogador2.getTurnoString());
		System.out.println("####################################");
		jogador2.mostrarEvocadas();
		System.out.printf("\nEvocadas de %s (Vida: %d|Mana: %d)\n", jogador2.getNome(),jogador2.getVida(), jogador2.getMana());
		System.out.println("___________________________________");
	}


	// ============================== Inicialização ==============================

	/**
	 * A mesa é preenchida com cartas vazias.
	 */
	private void iniciarMesa(){
		this.mesa = new Mesa();
		mesa.preencheMesa();
	}

	/**
	 * Ao iniciar o modo de jogo é definido, lendo a entrada do jogador.
	 */
	private void escolherModo() {
		boolean finished = false;
		while (!finished) {
			System.out.println("Escolha o modo de jogo: \n"
					+ "[1] - Jogador VS BOT\n"
					+ "[2] - Jogador VS Jogador\n"
					+ "[3] - BOT VS BOT\n");
			int entrada = Leitor.lerInt();
			finished = true;
			if(entrada == 1){
				iniciarJogadores(1);
			} else if (entrada == 2) {
				iniciarJogadores(2);
			} else if (entrada == 3) {
				iniciarJogadores(3);
			} else {
				System.out.printf("Comando inválido\n\n");
				finished = false;
			}
		}
	}


	/**
	 * Função responsável por inicializar os jogadores.
	 * Recebe um inteiro, que representa o modo de jogo e define que tipo de jogadores
	 * devem ser inicializados
	 */
	private void iniciarJogadores(int modo){
		Random random = new Random();
		int deckRandom = random.nextInt(2) + 1;
		int deckRandom2 = random.nextInt(2) + 1;
		if (modo == 1) {	
			this.jogador1 = escolherDeck("Player1");
			this.jogador2 = new Bot(DeckFactory.obterDeck(deckRandom), "Player2");			
		} else if (modo == 2) {			
			this.jogador1 = escolherDeck("Player1");
			this.jogador2 = escolherDeck("Player2");
		} else if (modo == 3) {
			this.jogador1 = new Bot(DeckFactory.obterDeck(deckRandom), "Player1");		
			this.jogador2 = new Bot(DeckFactory.obterDeck(deckRandom2), "Player2");		
		}
		
		jogador1.setTurno(TipoTurno.ATAQUE);
		jogador2.setTurno(TipoTurno.DEFESA);
	}

	/**
	 * Função na qual o jogador escolhe o seu deck, recebe uma String
	 * que representa o nome do jogador e realiza uma leitura
	 * para que o jogador consiga especificar qual deck quer usar
	 */
	public Jogador escolherDeck(String player) {
		boolean finished = false;
		Jogador jogador;
		while (!finished) {
			System.out.printf("Escolha o seu deck %s: \n"
					+ "[1] - Demacia\n"
					+ "[2] - Noxus\n", player);
			int entrada = Leitor.lerInt();
			finished = true;
			if (entrada == 1) {
				jogador = new Jogador(DeckFactory.obterDeck(1), player);
				return jogador;				
			} else if (entrada == 2){
				jogador = new Jogador(DeckFactory.obterDeck(2), player);
				return jogador;
			} else {
				System.out.printf("Comando inválido\n\n");
				finished = false;
			}
		}
		return null;
	}
}
