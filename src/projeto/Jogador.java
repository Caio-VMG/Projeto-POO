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
    
    /*
    public void primeiraCompra()
    */
    
    /*
     public void comprarCarta()
      */
    
    /*
    public void evocarCarta()
    */

    /*
    public void finalizarTurno()
     */

    /*
    setDeck()
     */
}
