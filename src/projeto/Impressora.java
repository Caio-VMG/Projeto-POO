package projeto;

import projeto.cartas.Carta;

import java.util.ArrayList;

public class Impressora {


    /**
     * Imprime a mao de um jogador com uma barra separadora
     */
    public void imprimeMao(Jogador jogador){
        System.out.println();
        System.out.printf("Mão de %s:\n", jogador.getNome());

        ArrayList<Carta> mao = jogador.getMao();
        int tamanho = calcularTamanhoBarra(mao);

        imprimeBarra(tamanho);
        int i = 1;
        for(Carta carta: mao){
            if(i % 5 == 0){
                System.out.println();
            }
            if(jogador.getMana() >= carta.getCusto()){
                System.out.printf("**");
            }
            System.out.printf("[%d] ", i);
            carta.printCarta();

            System.out.printf("   ");
            i++;
        }
        System.out.println();
        imprimeBarra(tamanho);
    }

    /**
     * Imprime a barra de separacao
     */
    public void imprimeBarra(int tamanhoBarra){
        for(int i = 0; i < tamanhoBarra; i++){
            System.out.printf("=");
        }
        System.out.println();
    }

    /**
     * Calcula o tamanho da barra que é usada na impressao da mao do jogador
     */
    private int calcularTamanhoBarra(ArrayList<Carta> mao){
        int tamanho = 0;
        int max = 4;
        if (mao.size() < 4) {
        	max = mao.size();
        }
        for(int i = 0; i < max; i++){
            String nome = mao.get(i).getNome();
            tamanho += 4 + nome.length() + 5 + 3 + 8;
        }
        return tamanho;
    }
}
