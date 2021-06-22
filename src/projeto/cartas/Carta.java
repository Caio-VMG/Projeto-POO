package projeto.cartas;

import projeto.Jogador;

public abstract class Carta {
    private String nome;
    private int custo;
    
    public Carta(String nome, int custo) {
    	this.nome = nome;    	
    	this.custo = custo;
    }
    
    public String getNome() {
    	return this.nome;
    }
    
    public int getCusto() {
    	return custo;
    }
    
    public abstract void usarCarta(Jogador atacante, Jogador defensor);
    	
    /*
    public abstract void sumonar()
     */
    /* * */
}
