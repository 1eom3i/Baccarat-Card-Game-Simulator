/**
 * A representation of cards in hand
 * @author leo
 *
 */
public class Hand extends CardCollection {
	
	@Override
	/**
	 * @return a String with the information in Hand
	 */
	public String toString() {
		String CardsName = "";
		
		for(int i = 0; i < cards.size(); i++)
			CardsName += String.format("%c%c ",cards.get(i).getRank(), cards.get(i).getSuit());
		
		return CardsName;		
	}
	
	/**
	 * <p>If total value in Hand is greater than 10, the value should be the rightmost digit.<p>
	 * @return a value of cards in Hand
	 */
	public int value() {
		int total = 0;
		
		for(int i = 0; i < cards.size(); i++) {
			cards.get(i);
			if( Math.min(Card.getRanks().indexOf(cards.get(i).getRank()) + 1, 10) == 10)
				total += 0;
			else {
				total += Math.min(Card.getRanks().indexOf(cards.get(i).getRank()) + 1, 10);
			}
		}
		if(total>9)
			return total%10;
		else
			return total;
	}
}
