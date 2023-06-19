/**
 * A representation of one card
 * @author leo
 */
public class BaccaratCard extends Card {
	
	/**
	 *  Creates a BaccaratCard object. Inherit from Card class.
	 * @param rank
	 * @param suit
	 */
	public BaccaratCard(char rank, char suit) {
		super( rank, suit );
	}
	
	/**
	 * Creates a BaccaratCard object, given rank and suit as a string.
	 * @param code
	 */
	public BaccaratCard(String code) {
		super(code);
	}
	
	/**
	 * When card is T,J,Q,K,A, the value is 0. Otherwise, the value is face value.
	 * @return The value of one card
	 */
	@Override
	public int value() {
		
		if( Math.min(getRanks().indexOf(getRank()) + 1, 10) == 10)
			return 0;
		else
			return Math.min(getRanks().indexOf(getRank()) + 1, 10);		 
	}

}
