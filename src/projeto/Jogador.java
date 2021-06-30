package projeto;

import projeto.cartas.Carta;
import projeto.cartas.TipoTurno;

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

    //const.
    private final int maxEvocadas = 6;
    private final int maxUnidades = 4;

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


    //========================= Manipulação de Cartas/Deck =========================

    /**
     * O jogador pega a primeira carta do deck e coloca em sua mão.
     */
    public void pegarCarta(){
        Carta carta = deck.getCarta(0);
        mao.add(carta);
        deck.remove(carta);
    }

    /**
     * Substitui a carta da mao (de indice passado) por uma carta
     * do Deck.
     */
    private void substituirCartadaMao(int indice){
        Carta removida = mao.remove(indice);
        deck.add(removida);

        Carta adicionada = deck.comprarCarta();
        mao.add(indice, adicionada);
    }


    /**
     * O jogador realiza a primeira compra ao iniciar o jogo.
     */
    public void primeiraCompra(){
        Scanner scan = new Scanner(System.in);

        for(int i = 0; i < maxUnidades; i++){
            pegarCarta();
        }
        trocarCartas();
        System.out.println("Pressiona qualquer botão para avançar.");
        scan.next();
    }


    /**
     * Após comprar as 4 cartas iniciais, o jogador tem a oportunidade
     * de troca-las.
     * Uma carta pode ser trocada uma única vez.
     */
    public void trocarCartas(){
        int i;
        boolean erro = true;
        boolean terminou = false;
        Scanner scan;

        ArrayList<Integer> cartasTrocadas = new ArrayList<>();
        imprimeMao();
        System.out.println("Quais cartas serão trocadas? (Digite 0 quando finalizar)");

        int trocadas = 0;

        do{
            while(!terminou && trocadas < maxUnidades) {
                try {
                    scan = new Scanner(System.in);
                    i = scan.nextInt();
                    if (i == 0) {
                        terminou = true;
                    } else {                    	
                        if (!cartasTrocadas.contains(i - 1)) {
                            substituirCartadaMao(i-1);
                            trocadas++;
                        } else {
                            System.out.println("Esta carta já foi trocada.");
                        }
                    }
                    erro = false;
                    if (terminou) {
                        imprimeMao();
                    }
                } catch (Exception InputMismatchException) {
                    System.out.println("Entrada Inválida");
                }
            }
        }while(erro);
    }


    //========================= Evocação de Cartas =========================


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


    /**
     * A carta passada é adicionada na lista de cartas evocadas do jogador.
     */
    public void sumonar(Carta carta){
        this.evocadas.add(carta);
    }

    //========================= Funções de Batalha =========================

    /**
     * Coloca uma carta na mesa para batalhar.
     */
    public Carta escolherCartaBatalha(int entrada) {
        if(entrada <= getQtdEvocadas()){
            Carta cartaEscolhida = evocadas.get(entrada - 1);
            evocadas.remove(entrada - 1);
            return cartaEscolhida;
        }
        return null;
    }


    //========================= Alteração de Dados =========================

    /**
     * Causa dano x de ao nexus do jogador, onde x é o parâmetro.
     */
    public void sofrerDanoNexus(int dano){
        this.nexus -= dano;
    }

    /**
     * Toda vez que o jogador ganha mana no ínicio da rodada,
     * a sua capacidade é aumentada. O máximo é de 10 ptos. de mana.
     */
    public void ganharMana() {
        if (this.manaTotal < 10) {
            this.manaTotal +=1;
        }
        this.manaAtual = this.manaTotal;
    }


    //========================= Impressão =========================

    /**
     * Imprime os dados do Jogador no ínicio de um turno.
     * Nome, Tipo de Turno, mana e vida do nexus
     */
    public void imprimirDadosIniciais(){
        System.out.printf("Jogador %s ", nome);
        if(turno == TipoTurno.ATAQUE) {
            System.out.printf("(atacante):\n");
        } else {
            System.out.printf("(defensor):\n");
        }
        System.out.printf("mana: %d\nvida do nexus: %d\n",manaAtual, nexus);
    }

    /**
     * Imprime cada carta evocada do jogador com um respectivo índice.
     */
    public void imprimeEvocadas() {
        System.out.printf("Cartas evocadas de %s:\n", this.nome);
        for(int i = 0; i < evocadas.size(); i++) {
            System.out.printf("[%d] - %s\n", i + 1, evocadas.get(i).getNome());
        }
        System.out.println("");
    }


    /**
     * Imprime todas as cartas que estão na mão do jogador
     * com um respectivo índice.
     */
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


    //========================= Getters =========================


    public String getNome() {
        return this.nome;
    }

    public int getMana() {
        return this.manaAtual;
    }

    public int getVida() {
        return this.nexus;
    }

    public TipoTurno getTurno() {
        return turno;
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


    //========================= Setters  =========================


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
