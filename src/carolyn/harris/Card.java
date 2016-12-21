package carolyn.harris;

public class Card {
	private final Suit suit;
    private final Rank rank;
 
    public Card(Suit s, Rank r) {
        suit = s;
        rank = r;
    }
 
    public String toString() {
        return rank + " of " + suit;
    }

	public Suit getSuit() {
		return suit;
	}

	public Rank getRank() {
		return rank;
	}
    
}
