package projeto.cartas;

import java.util.ArrayList;

import projeto.Jogador;
import projeto.cartas.efeitos.Efeito;

public class Unidade extends Carta{
    protected int vidaMaxima;
	protected int vida;
    protected int poder;
    private ArrayList<Efeito> efeitos;
    private int kills;
    private boolean elusivo;
    private boolean furia;
    private boolean ataqueDuplo;
    private int poderFuria;
    private int vidaFuria;

    //Construtor para quando a carta tem efeito e tem tra�o
    public Unidade(String nome, int custo, int vida, int poder) {
    	super(nome, custo);
    	this.vida = vida;
    	this.vidaMaxima = vida;
    	this.poder = poder;
    	this.efeitos = new ArrayList<>();
    	this.kills = 0;
    	this.elusivo = false;
    	this.furia = false;
    	this.ataqueDuplo = false;
    }
    
    public static void batalhaIndividual(Unidade unidade1, Unidade unidade2){
        unidade1.vida -= unidade2.poder;
        unidade2.vida -= unidade1.poder;
        if(unidade1.vida <= 0) {
        	if (unidade2.getFuria() && unidade2.getVida() >= 0) {
        		unidade2.ativarFuria();
        	}
        	unidade2.kills++;
        }
        if(unidade2.vida <= 0 && unidade1.getVida() >= 0) {
        	if (unidade1.getFuria()) {
        		unidade1.ativarFuria();
        	}
        	unidade1.kills++;
        }
    }

    @Override
    public void usarCarta(Jogador jogador1, Jogador jogador2) {
		jogador1.sumonar(this);
		//Se a carta tiver efeitos, a gente coloca pra ativar aqui
		//pensar como implementar efeitos que ativam na morte
    }

    public void confereEfeitoKill(Jogador jogador) {
    	for(int i = 0; i < efeitos.size(); i++) {
    		efeitos.get(i).ativarEfeitoKill(jogador);
    	}
    }
    
    public void confereEfeitoMorte(Jogador jogador) {
    	for(int i = 0; i < efeitos.size(); i++) {
    		efeitos.get(i).ativarEfeitoKill(jogador);
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

	//======================== Getters ========================
	
	public int getDano() {
		return poder;
	}

	public int getVida() {
		return vida;
	}
	
	public int getVidaMaxima() {
		return vidaMaxima;
	}
	
	public boolean ehElusivo() {
		return elusivo;
	}
	
	public boolean getFuria() {
		return furia;
	}
	
	public boolean getAtaqueDuplo() {
		return ataqueDuplo;
	}
	
	@Override
    public Carta getUnidade(){
    	return this;
	}	
	
	//======================== Setters ========================
	
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
	
	public void addElusivo() {
		this.elusivo = true;
	}
	
	public void addAtaqueDuplo() {
		this.ataqueDuplo = true;
	}
	
	public void addFuria(int poder, int vida) {
		this.furia = true;
		this.poderFuria = poder;
		this.vidaFuria = vida;
	}
	
	public void ativarFuria() {
		if (furia) {
			this.vida += this.vidaFuria;
			this.poder += this.poderFuria;
		}
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
