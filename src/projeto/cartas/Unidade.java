package projeto.cartas;

import java.util.ArrayList;

import projeto.Jogador;

public class Unidade extends Carta{
    private int vida;
    private int poder;
    private ArrayList<Efeito> efeitos;
    private ArrayList<Traco> tracos;

    //Construtor para quando a carta tem efeito e tem traço
    public Unidade(String nome, int custo, int vida, int poder, Efeito efeito, Traco traco) {
    	super(nome, custo);
    	this.vida = vida;
    	this.poder = poder;
    	this.efeitos = new ArrayList<>();
    	this.tracos = new ArrayList<>();
    	tracos.add(traco);
    	efeitos.add(efeito);
    }
    
  //Construtor para quando a carta tem efeito e não tem traço
    public Unidade(String nome, int custo, int vida, int poder, Efeito efeito) {
    	super(nome, custo);
    	this.vida = vida;
    	this.poder = poder;
    	this.efeitos = new ArrayList<>();
    	efeitos.add(efeito);
    }
    
    //Construtor para quando a carta não tem efeito mas tem traço
    public Unidade(String nome, int custo, int vida, int poder, Traco traco ) {
    	super(nome, custo);
    	this.vida = vida;
    	this.poder = poder;
    	this.efeitos = new ArrayList<>();
    	this.tracos = new ArrayList<>();
    	tracos.add(traco);
    }  
    
  //Construtor para quando a carta não tem efeito e não tem traço
    public Unidade(String nome, int custo, int vida, int poder) {
    	super(nome, custo);
    	this.vida = vida;
    	this.poder = poder;
    	this.efeitos = new ArrayList<>();
    }
    
    public static void batalhar(Unidade unidade1, Unidade unidade2){
        unidade1.vida -= unidade2.poder;
        unidade2.vida -= unidade1.poder;
    }

    public int getPoder() {
        return poder;
    }
    
    public int getVida() {
        return vida;
    }
    
    public void usarCarta(Jogador jogador, int entrada) {
    	if(jogador.getMana() >= jogador.getMao().get(entrada).getCusto()) {
			jogador.getEvocadas().add(jogador.getMao().get(entrada));
			jogador.setMana(jogador.getMao().get(entrada).getCusto());
		}
		else {
			System.out.println("Você não possui mana suficiente");
		}
    }
    
    
}
