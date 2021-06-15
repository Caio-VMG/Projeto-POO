package projeto;

public class Game {

    private boolean exitSelected;

    public void start(){
        exitSelected = false;
        System.out.println("Game started!");

        while(!exitSelected) {
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
            exitSelected = true;
        }

        System.out.println("Game terminated. Bye!");
    }
}
