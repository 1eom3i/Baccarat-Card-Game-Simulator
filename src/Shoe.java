import java.util.Collections;
/**
 * A representation of cards in Shoe
 * @author leo
 *
 */
public class Shoe extends CardCollection{
	
	/**
	 * Create a Shoe object with 4-deck or 6-deck or-8-deck cards
	 * @param numDecks
	 * @throws IllegalArgumentException if numDecks is not 4 or 6 or 8.
	 */
	public Shoe(int numDecks) throws IllegalArgumentException{
		if( numDecks == 4 ||numDecks == 6 || numDecks == 8) {
			for(int i=1;i<=numDecks;i++)
				for(int j=0;j<Card.getRanks().size();j++) 
					for(int k=0;k<Card.getSuits().size();k++) 
						cards.add(new Card(Card.getRanks().get(j),Card.getSuits().get(k)));	
		}
		else {
			throw new IllegalArgumentException("Number of Decks should be 4 or 6 or 8!");
		}			
	}
	
	/**
	 * @return a Card topCard with the top card in Shoe.
	 *
	 */
	public Card deal() {
		Card topCard = new Card(cards.get(0).getRank(),cards.get(0).getSuit());
		cards.remove(0);
		return topCard;		
	}
	
	/**
	 *  Shuffle the cards in Shoe
	 *
	 */
	public void shuffle() {
		Collections.shuffle(cards);		
	}
}
