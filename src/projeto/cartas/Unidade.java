package projeto.cartas;

import java.util.ArrayList;

import projeto.Jogador;
import projeto.cartas.efeitos.Efeito;
import projeto.cartas.efeitos.TipoChamada;

public class Unidade extends Carta{
    protected int vidaMaxima;
	protected int vida;
    protected int poder;
    private ArrayList<Efeito> efeitos;    
    private boolean elusivo;
    private boolean furia;
    private boolean ataqueDuplo;
    private boolean barreira;
    private int poderFuria;
    private int vidaFuria;
    protected int kills;
    protected int ataques = 0;
	protected int danoSofrido = 0;
	protected int danoCausado = 0;

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
    	
    	if (unidade1.getBarreira()) {
    		unidade1.setBarreira(false);
    		unidade2.ataques+=1;
    	} else {
    		atualizaStatus(unidade1, unidade2);
    	}
    	
    	if (unidade2.getBarreira()) {
    		unidade2.setBarreira(false);
    		unidade1.ataques +=1;
    	} else {
    		atualizaStatus(unidade2, unidade1);
    	}
    	
    	confereFuria(unidade1, unidade2);
        
    }
    
    private static void atualizaStatus(Unidade unidade1, Unidade unidade2) {
    	 unidade1.vida -= unidade2.poder;
		 unidade1.danoSofrido += unidade2.poder;
		 unidade2.ataques +=1;
		 unidade2.danoCausado += unidade2.poder;
    }

    private static void confereFuria(Unidade unidade1, Unidade unidade2) {
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
		for (int i = 0; i < efeitos.size(); i++) {
			efeitos.get(i).aplicarEfeito(jogador1, jogador2, this, TipoChamada.EVOCADA);
		}
    }

    public void confereEfeitoKill(Jogador jogador) {
    	for(int i = 0; i < efeitos.size(); i++) {
    		efeitos.get(i).ativarEfeitoKill(jogador);
    	}
    }
    
    public void confereEfeitoMorte(Jogador jogador) {
    	for(int i = 0; i < efeitos.size(); i++) {
    		efeitos.get(i).ativarEfeitoMorte(jogador);
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
	
	public void passarTurnoEfeitos() {
		for (int i = 0; i < efeitos.size(); i++) {
			efeitos.get(i).passouRodada(this);
		}
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
	
	public boolean getBarreira() {
		return barreira;
	}
	
	@Override
    public Carta getUnidade(){
    	return this;
	}
	
	public ArrayList<Efeito> getEfeitos(){
		return this.efeitos;
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
	
	public void setBarreira(boolean t) {
		this.barreira = t;
	}
	
	public void removerEfeito(Efeito efeito) {
		this.efeitos.remove(efeito);
	}

	/**
	 * Imprime a unidade seguindo o padrão:
	 * Nome - [vida|dano]
	 */
	public void printUnidade(){
		System.out.printf("%s - [%d|%d] ", super.getNome(), vida, poder);
	}

	@Override
	public void printDetalhes() {
		System.out.printf("%s [%d|%d] (%d)", super.getNome(), vida, poder, super.getCusto());
		if(!efeitos.isEmpty())	{
			System.out.printf(" - Efeitos: ");
			for (Efeito efeito: efeitos){
				efeito.printNome();
				System.out.printf(" ");
			}
		}

	}

	@Override
	public void printCarta(){
		System.out.printf("%s [%d|%d] (%d)", super.getNome(), vida, poder, super.getCusto());
	}

	public void mostrarCarta(){
		printUnidade();
	}

	public int getSizeString(){
		return super.getNome().length() + 3 + 3 + 4;
	}

	public boolean ehTrocavel(){
		return true;
	}
    
	public boolean ehEvoluivel() {
		return false;
	}
    
}
