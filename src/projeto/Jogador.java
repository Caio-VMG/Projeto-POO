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
    protected final int maxEvocadas = 6;
    protected final int maxUnidades = 4;

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
        for(int i = 0; i < maxUnidades; i++){
            pegarCarta();
        }
        trocarCartas();
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
                    System.out.println("Esta carta já foi trocada.\n");
                }
            }
            if (terminou && trocadas > 0) {
                Impressora imp = new Impressora();
                imp.imprimeMao(this);
            }
        }
    }


    //========================= Evocação de Cartas =========================


    /**
     * O jogador escolhe a carta que vai sumonar.
     * Se houver mana o suficiente, retorna a carta escolhida.
     */
    public Carta escolherCarta(){
        Carta carta;
        Impressora impressora = new Impressora();
        impressora.imprimeMao(this);
        System.out.println("Digite -1 para voltar.");
        System.out.println("Digite 0 para ver detalhes das cartas.");
        int entrada = Leitor.lerInt();

        if(entrada == 0) {
            printDetalhesCartas();
            entrada = Leitor.lerInt();
        }

        if(entrada - 1 < mao.size() && entrada > 0) {
            carta = mao.get(entrada - 1);
            if (carta.canSummon(manaAtual, manaFeitico)) {
                atualizarMana(carta);
                return mao.remove(entrada - 1);
            } else {
                System.out.println("Faltou mana\n");
                return null;
            }
        } else if (entrada == -1){
            System.out.println();
        } else {
        	System.out.println("Entrada inválida\n");
        }
        return null;
    }


    /**
     * Atualizar valores de mana após a compra de uma carta.
     */
    protected void atualizarMana(Carta carta){
        manaFeitico = carta.calcularCustoManaFeitico(manaFeitico);
        manaAtual = carta.calcularCustoNormal(manaFeitico, manaAtual);
    }

    /**
     * Escolhe duas cartas para serem substituidas, entre uma evocada e uma ainda nao
     * evocada.
     * Retorna true se a substituicao for bem sucedido, devolve false do contrario.
     */
    public boolean definirSubstituicao(){
        int entrada;
        boolean finished = false;
        Carta substituta = null;
        Carta evocada = null;

        System.out.println("Escolha a carta nova carta.");
        Impressora impressora = new Impressora();
        impressora.imprimeMao(this);
        while(!finished){
            entrada = Leitor.lerInt();
            if(checarEntradaMao(entrada)){
                substituta = mao.get(entrada - 1);
                finished = true;
            } else {
                System.out.println("A troca não é válida.");
            }
        }

        finished = false;
        System.out.println("Escolha a carta que sera trocada.");
        imprimeEvocadas();
        while(!finished){
            entrada = Leitor.lerInt();
            if(checarEntradaEvocadas(entrada)){
                evocada = evocadas.get(entrada - 1);
                finished = true;
            } else {
                System.out.println("A troca não é válida.");
            }
        }

        return substituirCarta(evocada, substituta);
    }

    /**
     * Checa se a entrada passada é condizente com os indices da mao.
     * Retorna true se for o caso, false do contrario.
     */
    private boolean checarEntradaMao(int entrada){
        if(entrada > 0 && entrada <= mao.size()){
            return mao.get(entrada - 1).ehTrocavel();
        } else {
            return false;
        }
    }

    /**
     * Checa se a entrada corresponde a uma carta evocada.
     */
    private boolean checarEntradaEvocadas(int entrada){
        if(entrada > 0 && entrada < getQtdEvocadas()){
            return true;
        } else {
            return false;
        }
    }

    /**
     * Se o jogador já tem 6 cartas evocadas, então
     * pode escolher substituir uma das evocadas por uma carta da sua mão
     * pagando apenas a diferença na mana.
     * Retorna true se a substituicao for bem sucedida.
     * Retorna false se nao for bem sucedida.
     */
    private boolean substituirCarta(Carta evocada, Carta substituta){
        int diferenca = substituta.getCusto() - evocada.getCusto();
        boolean trocou = true;
        if(diferenca >= 0){
            if(diferenca <= manaAtual){
                manaAtual -= diferenca;
                trocarCartaEvocada(evocada, substituta);
            } else {
                System.out.println("Não há mana suficiente.");
                trocou = false;
            }
        } else {
            trocarCartaEvocada(evocada, substituta);
        }
        return trocou;
    }


    /**
     * Troca uma carta evocada por uma carta da mao.
     */
    private void trocarCartaEvocada(Carta evocada, Carta substituta){
        evocadas.remove(evocada);
        mao.remove(substituta);
        mao.add(evocada);
        evocadas.add(substituta);
    }

    /**
     * Quando o jogador não possui mana suficiente pra evocar nenhuma carta
     * e não possui nenhuma carta evocada, então ele não tem nenhuma jogada possível.
     * PS: esse método ainda não está sendo usado.
     
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
	*/

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
        if(valor >= 3) {
        	this.manaFeitico = 3;
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
    }

    /**
     * Mostra as cartas evocadas, diferente da funcao imprimeEvocadas essa funcao
     * tem o interesse.
     */
    public void mostrarEvocadas() {
        for(Carta evocada: evocadas) {
            evocada.mostrarCarta();
        }
    }


    /**
     * Imprime todas as cartas que estão na mão do jogador
     * com um respectivo índice.
     */
    /*private void imprimeMao() {
        System.out.println();
        System.out.printf("Mão de %s:\n", this.nome);
        System.out.println("=================================================================================");
        for(int i = 0; i < mao.size(); i++) {
            mao.get(i).printCarta(i + 1);
        }
        System.out.println();
        System.out.println("=================================================================================");
        System.out.println();
    }*/

    private void printDetalhesCartas(){
        for(Carta carta: mao){
            carta.printDetalhes();
            System.out.println();
        }
        System.out.println();
    }


    //========================= Getters =========================

    public boolean isConsciente(){
        return true;
    }

    public String getTurnoString(){
        if(turno == TipoTurno.ATAQUE){
            return "Ataque";
        } else {
            return "Defesa";
        }
    }

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
    
    public void addCartaMao(Carta carta) {
    	this.mao.add(carta);
    }


    public int tomarDecisao(){
        return 2;
    }

    public void sumonarAleatoriamente(Jogador jogando, Jogador observando){

    }

}
