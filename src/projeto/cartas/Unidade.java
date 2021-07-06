package projeto.cartas;

import java.util.ArrayList;

import projeto.Jogador;
import projeto.cartas.efeitos.Efeito;

public class Unidade extends Carta{
    protected int vidaMaxima;
	protected int vida;
    protected int poder;
    private ArrayList<Efeito> efeitos;
    private ArrayList<Traco> tracos;
    private int kills;

    //Construtor para quando a carta tem efeito e tem tra�o
    public Unidade(String nome, int custo, int vida, int poder, Efeito efeito, Traco traco) {
    	super(nome, custo);
    	this.vida = vida;
    	this.vidaMaxima = vida;
    	this.poder = poder;
    	this.efeitos = new ArrayList<>();
    	this.tracos = new ArrayList<>();
    	this.kills = 0;
    	tracos.add(traco);
    	efeitos.add(efeito);
    }
    
  //Construtor para quando a carta tem efeito e n�o tem tra�o
    public Unidade(String nome, int custo, int vida, int poder, Efeito efeito) {
    	super(nome, custo);
    	this.vida = vida;
    	this.vidaMaxima = vida;
    	this.poder = poder;
    	this.efeitos = new ArrayList<>();
    	efeitos.add(efeito);
    }
    
    //Construtor para quando a carta n�o tem efeito mas tem tra�o
    public Unidade(String nome, int custo, int vida, int poder, Traco traco ) {
    	super(nome, custo);
    	this.vida = vida;
    	this.vidaMaxima = vida;
    	this.poder = poder;
    	this.efeitos = new ArrayList<>();
    	this.tracos = new ArrayList<>();
    	tracos.add(traco);
    }  
    
  //Construtor para quando a carta n�o tem efeito e n�o tem tra�o
    public Unidade(String nome, int custo, int vida, int poder) {
    	super(nome, custo);
    	this.vida = vida;
    	this.vidaMaxima = vida;
    	this.poder = poder;
    	this.efeitos = new ArrayList<>();
    }
    
    public static void batalhaIndividual(Unidade unidade1, Unidade unidade2){
        unidade1.vida -= unidade2.poder;
        unidade2.vida -= unidade1.poder;
        if(unidade1.vida <= 0) {
        	unidade2.kills++;
        }
        if(unidade2.vida <= 0) {
        	unidade1.kills++;
        }
    }

    @Override
    public void usarCarta(Jogador jogador1, Jogador jogador2) {
		jogador1.sumonar(this);
		//Se a carta tiver efeitos, a gente coloca pra ativar aqui
		//pensar como implementar efeitos que ativam na morte
    }

    @Override
    public Carta getUnidade(){
    	return this;
	}
    
    public void confereEfeitoKill(Jogador jogador) {
    	for(int i = 0; i < efeitos.size(); i++) {
    		efeitos.get(i).ativarEfeito(jogador);
    	}
    }

	@Override
	public boolean canSummon(int manaAtual, int manaFeitico) {
		if(this.getCusto() > manaAtual){
			return false;
		} else {
			return true;
		}
	}

	public int calcularCustoManaFeitico(int manaFeitico){
    	return manaFeitico;
	}

	public int calcularCustoNormal(int manaFeitico, int manaNormal){
    	return manaNormal - super.getCusto();
	}

	public int getDano() {
		return poder;
	}

	public int getVida() {
		return vida;
	}
	
	public int getVidaMaxima() {
		return this.vidaMaxima;
	}
	
	public void sofrerDano(int danoRecebido) {
		this.vida -= danoRecebido;
	}
	
	public void aumentarDano(int danoMais) {
		this.poder += danoMais;
	}
	
	public void aumentarVidaMaxima(int vidaMais) {
		this.vidaMaxima += vidaMais;
		this.vida = this.vidaMaxima;
	}
	
	public void aumentarVida(int vidaMais) {
		this.vida += vidaMais;
		if(this.vida > this.vidaMaxima) {
			this.vida = this.vidaMaxima;
		}
	}
	
	public void diminuiDano(int vidaMenos) {
		this.poder -= vidaMenos;
	}
	
	public void diminuiVida(int vidaMenos) {
		this.vida -= vidaMenos;
	}
	
	public void addEfeito(Efeito efeito) {
		this.efeitos.add(efeito);
	}
	
	public void removerEfeito(Efeito efeito) {
		this.efeitos.remove(efeito);
	}

	/**
	 * Imprime a unidade seguindo o padrão:
	 * Nome - [vida|dano]
	 */
	public void printUnidade(){
		System.out.printf("%s - [%d|%d]", super.getNome(), vida, poder);
	}

    
    
}
