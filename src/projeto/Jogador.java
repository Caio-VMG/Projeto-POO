package projeto;

import projeto.cartas.Carta;
import projeto.cartas.TipoTurno;
import projeto.cartas.Unidade;
import java.util.ArrayList;
import java.util.Scanner;

public class Jogador {
	private String nome;
    private int nexus;
    private int manaAtual;
    private int manaTotal;
    private int manaFeitico;
    private Deck deck;
    private TipoTurno turno;

    private ArrayList<Carta> mao;
    private ArrayList<Carta> evocadas; // Cartas que ja foram compradas.

    public Jogador(Deck deck, String nome){
    	this.nome = nome;
        this.deck = deck;
        this.manaTotal = 0;
        this.manaAtual = 0;
        this.manaFeitico = 0;
        this.nexus = 20;
        mao = new ArrayList<>();
        evocadas = new ArrayList<>();
    }

    /**
     * Causa dano x de ao nexus do jogador, onde x é o parâmetro.
     */
    public void sofrerDanoNexus(int dano){
        this.nexus -= dano;
    }
    
    private void imprimeMao() {
        System.out.println();
        System.out.printf("Mão de %s:\n", this.nome);
        System.out.println("=================================================================================");
    	for(int i = 0; i < mao.size(); i++) {
    		System.out.printf("[%d] %s \t ", i + 1,mao.get(i).getNome());
    	}
        System.out.println();
    	System.out.println("=================================================================================");
        System.out.println();
    }
    
    private void imprimeEvocadas() {
    	System.out.printf("Cartas evocadas de %s:\n", this.nome);
    	for(int i = 0; i < evocadas.size(); i++) {
    		System.out.printf("[%d] - %s\n", i + 1, evocadas.get(i).getNome());
    	}
    	System.out.println("");
    }

    public void ganharMana() {
    	if (this.manaTotal < 10) {
    		this.manaTotal +=1;
    	}    	
    	this.manaAtual = this.manaTotal;
    }

    public void comprarCarta(){
        Carta carta = deck.getCarta(0);
        mao.add(carta);
        deck.remove(carta);
    }

    /**
     * No
     */
    public void trocarCartas(){
        int i;
        boolean erro = true;
        boolean terminou = false;
        boolean trocou = false;
        Scanner scan = new Scanner(System.in);

        ArrayList<Integer> cartasTrocadas = new ArrayList<>();
        imprimeMao();
        System.out.println("Quais cartas serão trocadas? (Digite 0 quando finalizar)");

        int trocadas = 0;

        do{
            while(!terminou && trocadas < 4) {
                try {
                    i = scan.nextInt();
                    if (i == 0) {
                        terminou = true;
                    } else {
                        trocou = true;

                        if (!cartasTrocadas.contains(i - 1)) {
                            substituirCartadaMao(i-1);
                            trocadas++;
                        } else {
                            System.out.println("Esta carta já foi trocada.");
                        }
                    }
                    erro = false;
                } catch (Exception InputMismatchException) {
                    System.out.println("Entrada Inválida");
                }
                if (terminou) {
                    imprimeMao();
                }
            }
        }while(erro);
    }

    /**
     * Substitui a carta da mao (de indice passado).
     */
    private void substituirCartadaMao(int indice){
        Carta removida = mao.remove(indice);
        deck.add(removida);

        Carta adicionada = deck.comprarCarta();
        mao.add(indice, adicionada);
    }

    public void primeiraCompra(){
        for(int i = 0; i < 4; i++){
            comprarCarta();
        }
        trocarCartas();
    }

    /**
     * A carta passada é adicionada na lista de cartas evocadas do jogador.
     */
    public void sumonar(Carta carta){
        this.evocadas.add(carta);
    }

    /**
     * O jogador escolhe a carta que vai sumonar.
     * Se houver mana o suficiente, retorna a carta escolhida.
     */
    public Carta escolherCarta(){
        imprimeMao();
        Scanner scan = new Scanner(System.in);
        int entrada = scan.nextInt();
        Carta carta = mao.get(entrada - 1);

        if(canSummon(carta)) {
            manaAtual -= carta.getCusto();
            return mao.remove(entrada - 1);
        } else {
            System.out.println("Faltou mana");
            return null;
        }
    }

    /**
     * O defensor escolhe a unidade para evocar.
     */
    public Carta escolherUnidade(){
        int entrada = 0;
        imprimeMao();
        Scanner scan = new Scanner(System.in);

        Carta carta = null;
        while(carta == null){
            entrada = scan.nextInt();
            if(entrada == 0){
                return null;
            }
            carta = mao.get(entrada - 1);
            carta = carta.getUnidade();
        }
        
        //precisa de escolherUnidade quando já temos escolherCarta?

        if(canSummon(carta)) {
            manaAtual -= carta.getCusto();
            return mao.remove(entrada - 1);
        } else {
            System.out.println("Não há mana suficiente.");
            return null;
        }
    }



    /**
     * Retorna true se o jogador tem mana o suficiente para sumonar a carta.
     * Retorn false do contrario.
     */
    private boolean canSummon(Carta carta){
        if(manaAtual >= carta.getCusto()){
            return true;
        } else {
            return false;
        }
    }


    /*
    public void usarCarta() {
    	System.out.printf("Selecione a carta:\n");
    	imprimeMao();
    	Scanner scan = new Scanner(System.in);
		int entrada = scan.nextInt();
		mao.get(entrada - 1).usarCarta(this, entrada - 1);
		scan.close();
    }
     */
    
    public void passar() {}
    
    public Carta atacar() {
    	System.out.println("Escolha a carta para atacar:\n");
    	imprimeEvocadas();
    	Scanner scan = new Scanner(System.in);
        int entrada = scan.nextInt();
    	Carta cartaEscolhida = evocadas.get(entrada - 1);
    	evocadas.remove(entrada - 1);
    	return cartaEscolhida;
    }
    
    public Carta defender() {
    	System.out.println("Escolha a carta para defender:\n");
    	imprimeEvocadas();
    	Scanner scan = new Scanner(System.in);
        int entrada = scan.nextInt();
    	Carta cartaEscolhida = evocadas.get(entrada - 1);
    	evocadas.remove(entrada - 1);
    	return cartaEscolhida;
    }

    //========================= Getters & Setters =========================

    public String getNome() {
        return this.nome;
    }

    public int getMana() {
        return this.manaAtual;
    }

    public int getVida() {
        return this.nexus;
    }

    public ArrayList<Carta> getMao() {
    	return this.mao;
    }
    
    public ArrayList<Carta> getEvocadas(){
    	return this.evocadas;
    }
    
    public int getQtdEvocadas() {
    	return this.evocadas.size();
    }
    
    public void setMana(int pontos) {
    	this.manaAtual += pontos;
    }

    public void setDeck(Deck deck){
        this.deck = deck;
    }

    public void setTurno(TipoTurno turno) {
        this.turno = turno;
    }

}
