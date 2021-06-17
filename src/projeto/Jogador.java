package projeto;

import projeto.cartas.Carta;
import projeto.cartas.TipoTurno;
import projeto.cartas.Unidade;

import java.util.ArrayList;
import java.util.Scanner;

public class Jogador {
    private int nexus;
    private int manaAtual;
    private int manaTotal;
    private int manaFeitico;
    private Deck deck;
    private TipoTurno turno;

    private ArrayList<Carta> mao;
    private ArrayList<Carta> evocadas;

    public Jogador(Deck deck){
        this.deck = deck;
        this.manaTotal = 0;
        this.manaAtual = 0;
        this.manaFeitico = 0;
        this.nexus = 20;
        mao = new ArrayList<>();
        evocadas = new ArrayList<>();
    }

    public void sofrerDano(Unidade unidade){
        this.nexus -= unidade.getPoder();
    }
    
    
    public void ganharMana() {
    	if (this.manaTotal < 10) {
    		this.manaTotal +=1;
    	}    	
    	this.manaAtual = this.manaTotal;
    }

    public void comprarCarta(){
        Carta carta = deck.getCarta(0);
        mao.add(carta);
        deck.remove(carta);
    }

    public void trocarCartas(){
        // print as 4 primeiras cartas e digite os indices a serem trocados.
        boolean terminou = false;
        ArrayList<Integer> cartasTrocadas = new ArrayList<>();

        while(!terminou){
            int i;// Me da um indice da carta (0,1,2,3) ou (-1) se nao deseja trocar.

            if(i == -1){
                terminou = true;
            } else {
                if(!cartasTrocadas.contains(i)){
                    cartasTrocadas.add(i);
                    Carta carta = mao.remove(i);
                    deck.add(carta);
                }
            }
        }
    }

    public void primeiraCompra(){
        for(int i = 0; i < 4; i++){
            comprarCarta();
        }
        trocarCartas();
    }
    
    public void sumonar() {
    	
    }

    public void setDeck(Deck deck){
    	this.deck = deck;
    }

    public void setTurno(TipoTurno turno) {
        this.turno = turno;
    }
}
