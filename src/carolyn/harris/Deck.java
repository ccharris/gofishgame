package carolyn.harris;

import java.util.Collections;
import java.util.LinkedList;
 
public class Deck {
    private final LinkedList<Card> deck= new LinkedList<Card>();
 
    public Deck() {
        for (Suit s : Suit.values())
            for (Rank r : Rank.values())
                deck.add(new Card(s, r));
    }
    public void deckShuffle(){
    	Collections.shuffle(deck);
    }
    
    public Card deal() {
        return deck.poll();
    }
 
    public String toString(){
        return deck.toString();
    }
    
    public LinkedList<Card> getDeck(){
    	return deck;
    }
}
