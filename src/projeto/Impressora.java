package projeto;

import projeto.cartas.Carta;

import java.util.ArrayList;

public class Impressora {


    public void imprimeMao(Jogador jogador){
        System.out.println();
        System.out.printf("Mão de %s:\n", jogador.getNome());

        ArrayList<Carta> Mao = jogador.getMao();
        int tamanho = calcularTamanhoBarra(Mao);

        imprimeBarra(tamanho);
        int i = 1;
        for(Carta carta: Mao){
            if(i % 5 == 0){
                System.out.println();
            }
            System.out.printf("[%d] %s (%d)    ", i , carta.getNome(), carta.getCusto());

            System.out.printf("   ");
            i++;
        }
        System.out.println();
        imprimeBarra(tamanho);
    }

    public void imprimeBarra(int tamanhoBarra){
        for(int i = 0; i < tamanhoBarra; i++){
            System.out.printf("=");
        }
        System.out.println();
    }

    private int calcularTamanhoBarra(ArrayList<Carta> mao){
        int tamanho = 0;
        for(int i = 0; i < 4; i++){
            String nome = mao.get(i).getNome();
            tamanho += 4 + nome.length() + 5 + 3 + 1;
        }
        return tamanho;
    }

    /*
    public void printMesa(){
        for(int i = 0; i < qtdAtacantes; i++){
            System.out.printf("[%d] ",i+1);
            atacantes.get(i).printUnidade();
            //Unidade atacante = atacantes.get(i);
            //Unidade defensor = defensores.get(i);
            //if(atacante != null){
            //System.out.printf("[%d] ",i+1);
            //atacante.printUnidade();
        }
        System.out.printf("\n");
        if(qtdAtacantes < 4) {
            for(int i = 0; i < 4 - qtdAtacantes; i++) {
                System.out.printf("--\n");
            }
        }
        System.out.printf("\t \t");

        for(int i = 0; i < qtdDefensores; i++) {
            System.out.printf("[%d] ",i+1);
            atacantes.get(i).printUnidade();
        }
        System.out.printf("\n");
        if(qtdDefensores < 4) {
            for(int i = 0; i < 4 - qtdDefensores; i++) {
                System.out.printf("--\n");
            }
        }
    }
    */
}
