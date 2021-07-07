package projeto.decks;

import projeto.cartas.Carta;
import java.util.ArrayList;


public class Deck {
    private String nome;
    private ArrayList<Carta> cartas;

    public Deck(String nome){
        this.nome = nome;
        this.cartas = new ArrayList<>();
    }

    /**
     * Remove do Deck a carta passada.
     */
    public void remove(Carta carta){
        cartas.remove(carta);
    }


    /**
     * Adiciona uma carta no último lugar do deck.
     */
    public void add(Carta carta){
    	cartas.add(carta);
    }

    /**
     * Compra a carta na primeira posição do deck.
     */
    public Carta comprarCarta(){
        Carta carta = cartas.get(0);
        remove(carta);
        return carta;
    }

    public Carta getCarta(int i){
        return cartas.get(i);
        
    }

    public ArrayList<Carta> getCartas() {
        return cartas;
    }
}
