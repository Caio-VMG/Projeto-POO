package projeto;

import projeto.cartas.Carta;
import projeto.cartas.TipoTurno;
import projeto.decks.Deck;

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
        //Scanner scan = new Scanner(System.in);

        for(int i = 0; i < maxUnidades; i++){
            pegarCarta();
        }
        trocarCartas();
        //System.out.println("Pressiona qualquer botão para avançar.");
        //scan.next();
    }


    /**
     * Após comprar as 4 cartas iniciais, o jogador tem a oportunidade
     * de troca-las.
     * Uma carta pode ser trocada uma única vez.
     */
    public void trocarCartas(){
        int i;
        boolean terminou = false;

        ArrayList<Integer> cartasTrocadas = new ArrayList<>();
        //imprimeMao();
        Impressora impressora = new Impressora();
        impressora.imprimeMao(this);
        System.out.println("Quais cartas serão trocadas? (Digite 0 quando finalizar)");
        System.out.println();

        int trocadas = 0;

        while(!terminou && trocadas < maxUnidades) {
            i = Leitor.lerInt();
            if (i == 0) {
                terminou = true;
            }else if(i > 4 || i< 0){
                System.out.println("Índice inválido");
            } else {
                if (!cartasTrocadas.contains(i - 1)) {
                    cartasTrocadas.add(i-1);
                    substituirCartadaMao(i-1);
                    trocadas++;
                } else {
                    System.out.println("Esta carta já foi trocada.");
                    System.out.println();
                }
            }
            if (terminou && trocadas > 0) {
                imprimeMao();
            }
        }
    }


    //========================= Evocação de Cartas =========================


    /**
     * O jogador escolhe a carta que vai sumonar.
     * Se houver mana o suficiente, retorna a carta escolhida.
     */
    public Carta escolherCarta(){
        int entrada = 0;
        Impressora impressora = new Impressora();
        impressora.imprimeMao(this);

        boolean leituraValida = false;
        do {
            try {
                Scanner scan = new Scanner(System.in);
                entrada = scan.nextInt();
                leituraValida = true;
            } catch (Exception InputMismatchException) {
                System.out.println("Entrada inválida");
            }
        }while(!leituraValida);

        Carta carta;

        if(entrada <= mao.size()) {
        	carta = mao.get(entrada - 1);
        	if(carta.canSummon(manaAtual, manaFeitico)) {
        	    manaFeitico = carta.calcularCustoManaFeitico(manaFeitico);
        	    manaAtual = carta.calcularCustoNormal(manaAtual, manaFeitico);
                return mao.remove(entrada - 1);
            } else {
                System.out.println("Faltou mana");
                System.out.println();
                return null;
            }
        }
        else {
        	System.out.println("Entrada inválida");
        	System.out.println();
        }
        return null;
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
     * Quando o jogador não possui mana suficiente pra evocar nenhuma carta
     * e não possui nenhuma carta evocada, então ele não tem nenhuma jogada possível.
     * PS: esse método ainda não está sendo usado.
     */
    private boolean podeJogar() {
    	int count = 0;
    	for(int i = 0; i < mao.size(); i++) {
    		Carta aux = mao.get(i);
    		if(!mao.get(i).canSummon(manaAtual, manaFeitico)) {
    			count++;
    		}
    	}
    	if((count == mao.size()) && this.evocadas.size() == 0) {
    		return false;
    	}
    	return true;
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
     * a sua capacidade é aumentada. O máximo é de 10 ptos. de mana por rodada.
     */
    public void ganharMana(int valor) {
        // Inicio da nova rodada
    	if(valor > 10) {
    		this.manaAtual += 10;
    	}
    	else {
    		this.manaAtual += valor;
    	}
        //this.manaAtual = this.manaTotal;
        this.manaTotal = this.manaAtual;
    }


    /**
     * Até 3 pontos de mana de feitiço que sobraram
     * podem ser guardados para a próxima rodada.
     * A mana de feitiço é guardada entre as rodadas.
     */
    public void alterarManaFeitico(int valor){
        if(valor > 3) {
        	this.manaFeitico += 3;
        }
        else {
        	this.manaFeitico += valor;
        }
        this.manaAtual = 0;
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
        System.out.printf("mana: %d\nmana feitico: %d\nvida do nexus: %d\n",manaAtual, manaFeitico, nexus);
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
            mao.get(i).printCarta(i + 1);
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
    	this.manaAtual = pontos;
    }

    public void setDeck(Deck deck){
        this.deck = deck;
    }

    public void setTurno(TipoTurno turno) {
        this.turno = turno;
    }

}
