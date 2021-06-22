package projeto;

import projeto.cartas.Carta;
import java.util.ArrayList;

import com.unicamp.mc322.lab10.app.restaurante.Lanche;

public class Deck {
    private String nome;
    private ArrayList<Carta> cartas;

    public Deck(String nome){
        this.nome = nome;
        this.cartas = new ArrayList<Carta>();
    }

    public void remove(Carta carta){
        cartas.remove(carta);
    }

    public void add(Carta carta){
    	cartas.add(carta);
    }

    public Carta getCarta(int i){
        return cartas.get(i);
        
    }

    public ArrayList<Carta> getCartas() {
        return cartas;
    }
}
