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

    @Override
    /**
     * O bot realiza a primeira compra ao iniciar o jogo.
     */
    public void primeiraCompra(){
        for(int i = 0; i < super.maxUnidades; i++){
            pegarCarta();
        }
    }

    @Override
    public boolean isConsciente() {
        return false;
    }

    /**
     * O bot ira determinar as decisoes possiveis
     * e escolher uma dessas aleatoriamente.
     */
    @Override
    public int tomarDecisao(){
        ArrayList<Integer> decisoesPossiveis = new ArrayList<>();
        ArrayList<Carta> possiveisEvocacoes = checarMao();

        if(!possiveisEvocacoes.isEmpty()){
            decisoesPossiveis.add(1);
        }
        if(super.getQtdEvocadas() > 0){
            decisoesPossiveis.add(3);
        }
        decisoesPossiveis.add(2);

        Random random = new Random();
        int valor = random.nextInt(possiveisEvocacoes.size()) + 1;

        return decisoesPossiveis.get(valor);
    }

    /**
     * Sumona uma das cartas da mao aleatoriamente.
     */
    @Override
    public void sumonarAleatoriamente(){
        ArrayList<Carta> possiveisEvocacoes = checarMao();
        Random random = new Random();
        int valor = random.nextInt(possiveisEvocacoes.size());
        Carta carta = possiveisEvocacoes.get(valor);
        atualizarMana(carta);
        super.getMao().remove(carta);
        super.getEvocadas().add(carta);
    }


    /**
     * Adiciona todas as cartas da mao que podem ser
     * evocadas em uma lista e retorna essa lista.
     */
    private ArrayList<Carta> checarMao(){
        ArrayList<Carta> possiveisCartas = new ArrayList<>();
        ArrayList<Carta> mao = super.getMao();
        for(Carta carta: mao){
            if(super.getMana() >= carta.getCusto()){
                possiveisCartas.add(carta);
            }
        }
        return possiveisCartas;
    }


    @Override
    public Carta escolherCartaBatalha(int entrada){
        ArrayList<Carta> evocadas = super.getEvocadas();
        if(evocadas.size() == 0){
            return null;
        } else {
            Random random = new Random();
            int valor = random.nextInt(evocadas.size());
            return evocadas.get(valor);
        }
    }



}
