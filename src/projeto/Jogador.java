package projeto;

import projeto.cartas.Carta;
import projeto.cartas.TipoTurno;
import projeto.cartas.Unidade;

import java.util.ArrayList;

public class Jogador {
    private int nexus;
    private int mana;
    private int manaFeitico;
    private Deck deck;
    private TipoTurno turno;

    private ArrayList<Carta> mao;
    private ArrayList<Carta> evocadas;

    public Jogador(Deck deck){
        this.deck = deck;

        this.mana = 0;
        this.manaFeitico = 0;
        this.nexus = 20;
        mao = new ArrayList<>();
        evocadas = new ArrayList<>();
    }

    public void sofrerDano(Unidade unidade){
        this.nexus -= unidade.getPoder();
    }

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
