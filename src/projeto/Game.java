package projeto;

import projeto.cartas.TipoTurno;

import java.util.Scanner;

public class Game {

	private Jogador jogador1;
	private Jogador jogador2;

	private boolean exitSelected;

	public void start() {
		exitSelected = false;
		System.out.println("Game started!");
		IniciarJogadores();

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

    private void IniciarJogadores(){
		//talvez seja necessrio salvar a referencia do deck

		Jogador jogador1 = new Jogador(new Deck("deck1"));
		Jogador jogador2 = new Jogador(new Deck("deck2"));
	}
}
