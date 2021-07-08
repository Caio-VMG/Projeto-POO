package projeto.decks;

public class DeckFactory {

    public static Deck obterDeck(int i){
        Deck deck = null;
        if(i == 0){
            deck = new Demacia("deckDummy");
        }
        // else if(i == 2)
        // retorna outro tipo de deck
        return deck;
    }
}
