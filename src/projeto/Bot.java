package projeto;

import projeto.cartas.Carta;
import projeto.cartas.TipoTurno;
import projeto.cartas.Unidade;
import projeto.decks.Deck;

import java.util.ArrayList;
import java.util.Random;

public class Bot extends Jogador {

    public Bot(Deck deck, String nome) {
        super(deck, nome);
    }

    //================== Manejamento de cartas ==================

    /**
     * O bot realiza a primeira compra ao iniciar o jogo.
     */
    @Override
    public void primeiraCompra(){
        for(int i = 0; i < super.maxUnidades; i++){
            pegarCarta();
        }
    }

    /**
     * Adiciona todas as cartas da mao que podem ser
     * evocadas em uma lista e retorna essa lista.
     */
    private ArrayList<Carta> checarMao(){
        ArrayList<Carta> possiveisCartas = new ArrayList<>();
        ArrayList<Carta> mao = super.getMao();
        for(Carta carta: mao){
            if(super.getMana() >= carta.getCusto() && carta.ehTrocavel()){
                possiveisCartas.add(carta);
            }
        }
        return possiveisCartas;
    }

    //====================== Tomada de Decisoes ======================


    /**
     * O bot ira determinar as decisoes possiveis
     * e escolher uma dessas aleatoriamente.
     */
    @Override
    public int tomarDecisao(){
        int valor = 0;
        ArrayList<Integer> decisoesPossiveis = new ArrayList<>();
        ArrayList<Carta> possiveisEvocacoes = checarMao();

        if(!possiveisEvocacoes.isEmpty()){
            decisoesPossiveis.add(1);
        }
        if(super.getQtdEvocadas() > 0 && super.getTurno() == TipoTurno.ATAQUE){
            decisoesPossiveis.add(3);
        }
        decisoesPossiveis.add(2);

        Random random = new Random();
        if(possiveisEvocacoes.size() > 0){
            valor = random.nextInt(decisoesPossiveis.size());
        }

        return decisoesPossiveis.get(valor);
    }

    /**
     * Sumona uma das cartas da mao aleatoriamente.
     */
    @Override
    public void sumonarAleatoriamente(Jogador jogando, Jogador observando){
        ArrayList<Carta> possiveisEvocacoes = checarMao();
        Random random = new Random();
        int valor = random.nextInt(possiveisEvocacoes.size());
        Carta carta = possiveisEvocacoes.get(valor);
        atualizarMana(carta);
        super.getMao().remove(carta);
        carta.usarCarta(jogando, observando);
    }

    /**
     * Escolhe uma das cartas sumonadas aleatoriamente. A função
     * pode retornar null se todas as cartas tiverem sido evocadas ou
     * se o Bot decidir não sumonar mais cartas.
     */
    @Override
    public Carta escolherCartaBatalha(int entrada){
        Random random = new Random();
        ArrayList<Carta> evocadas = super.getEvocadas();

        int opcao = random.nextInt(2);// Aqui é aleatório se ele vai escolher algum personagem ou nao.
        if(evocadas.size() == 0 || opcao == 1){
            return null;
        } else {
            int valor = random.nextInt(evocadas.size());
            Carta escolhida = evocadas.get(valor);
            evocadas.remove(escolhida);
            return escolhida;
        }
    }

    @Override
    public int escolherPosicao(Mesa mesa){
        boolean temPosValidas = false;
        ArrayList<Integer> posicoes = new ArrayList<>();
        for(int i = 0;i < mesa.getQtdAtacantes(); i++){
            Unidade atacante = mesa.getAtacante(i);
            Unidade defensor = mesa.getDefensor(i);
            if(atacante != null && defensor == null) {
                posicoes.add(i);
                temPosValidas = true;
            }
        }
        if(temPosValidas){
            Random random = new Random();
            return posicoes.get(random.nextInt(posicoes.size()));
        } else {
            return -1;
        }
    }

    @Override
    public boolean isConsciente() {
        return false;
    }

}
