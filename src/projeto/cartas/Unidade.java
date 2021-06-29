package projeto.cartas;

import java.util.ArrayList;

import projeto.Jogador;
import projeto.cartas.efeitos.Efeito;

public class Unidade extends Carta{
    protected int vida;
    protected int poder;
    private ArrayList<Efeito> efeitos;
    private ArrayList<Traco> tracos;
    private int kills;

    //Construtor para quando a carta tem efeito e tem tra�o
    public Unidade(String nome, int custo, int vida, int poder, Efeito efeito, Traco traco) {
    	super(nome, custo);
    	this.vida = vida;
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
    	this.poder = poder;
    	this.efeitos = new ArrayList<>();
    	efeitos.add(efeito);
    }
    
    //Construtor para quando a carta n�o tem efeito mas tem tra�o
    public Unidade(String nome, int custo, int vida, int poder, Traco traco ) {
    	super(nome, custo);
    	this.vida = vida;
    	this.poder = poder;
    	this.efeitos = new ArrayList<>();
    	this.tracos = new ArrayList<>();
    	tracos.add(traco);
    }  
    
  //Construtor para quando a carta n�o tem efeito e n�o tem tra�o
    public Unidade(String nome, int custo, int vida, int poder) {
    	super(nome, custo);
    	this.vida = vida;
    	this.poder = poder;
    	this.efeitos = new ArrayList<>();
    }
    
    public void batalhar(Unidade unidade1, Unidade unidade2){
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

	public int getDano() {
		return poder;
	}

	public int getVida() {
		return vida;
	}
	
	public void sofrerDano(int danoRecebido) {
		this.vida -= danoRecebido;
	}
	
	public void aumentarDano(int danoMais) {
		this.poder += danoMais;
	}
	
	public void aumentarVida(int vidaMais) {
		this.vida += vidaMais;
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

    
    
}
