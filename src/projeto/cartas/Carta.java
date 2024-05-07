package projeto.cartas;

import projeto.Jogador;

public abstract class Carta {
    private String nome;
    private int custo;
    
    public Carta(String nome, int custo) {
    	this.nome = nome;    	
    	this.custo = custo;
    }

    public abstract void usarCarta(Jogador jogador1, Jogador jogador2);

    public abstract boolean canSummon(int manaAtual, int manaFeitico);

    public abstract int calcularCustoManaFeitico(int manaFeitico);

    public abstract int calcularCustoNormal(int manaFeitico, int manaNormal);

    public abstract void printDetalhes();

    public abstract void mostrarCarta();

    public String getNome() {
    	return this.nome;
    }
    
    public int getCusto() {
    	return custo;
    }
    
    public int getDano() {
    	return 0;
    }
    
    public abstract boolean ehElusivo();

    public abstract boolean ehTrocavel();

    /**
     * Devolve a unidade.
     * Devolve null se a carta for um feitico.
     */
    public abstract Carta getUnidade();

    public void printCarta(){
        System.out.printf(" %s (%d) \t ", this.nome, this.custo);
    }

}
