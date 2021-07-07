package projeto;

import projeto.cartas.Carta;

import java.util.ArrayList;

public class Impressora {


    public void imprimeMao(Jogador jogador){
        System.out.println();
        System.out.printf("Mão de %s:\n", jogador.getNome());

        ArrayList<Carta> Mao = jogador.getMao();
        int tamanho = calcularCaracteres(Mao);

        imprimeBarra(tamanho);
        int i = 1;
        for(Carta carta: Mao){
            if(i % 5 == 0){
                System.out.println();
            }
            System.out.printf("[%d] %s (%d) \t ", i , carta.getNome(), carta.getCusto());
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

    private int calcularCaracteres(ArrayList<Carta> Mao){
        int tamanho = 0;
        for(Carta carta: Mao){
            String nome = carta.getNome();
            tamanho += 4 + nome.length() + 5 + 3 + 1;
        }
        return tamanho;
    }
    /*
    private void imprimeMao() {
        System.out.println();
        System.out.printf("Mão de %s:\n", this.nome);
        System.out.println("=================================================================================");
        for(int i = 0; i < mao.size(); i++) {
            mao.get(i).printCarta(i + 1);
        }
        System.out.println();
        System.out.println("=================================================================================");
        System.out.println();
    }
     */

    /*
        public void printCarta(int i){
        System.out.printf("[%d] %s (%d) \t ", i , this.nome, this.custo);
     */
}
