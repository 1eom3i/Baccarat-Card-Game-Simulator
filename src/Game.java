import java.util.Scanner;

/**
 * A representation of Baccarat game
 * @author leo
 *
 */
public class Game {
	
	Shoe shoe;
	Hand handBanker;
	Hand handPlayer;	
	
	/**
	 * play game
	 * @param args 
	 *
	 */
	public static void main(String args[]) {
		Game game =new Game(8);
		System.out.print("start game play \n");
		game.play();
		game = new Game(4);
		System.out.print("----------------------------\n");
		System.out.print("Start game play with prompt \n");
		game.playWithPrompt();
		
	}
	
	/**
	 * Create a Game object
	 * Initialize the shoe, handBanker and handPlayer
	 * @param numDecks
	 */
	public Game(int numDecks) {		
		shoe = new Shoe(numDecks);
		shoe.shuffle();
		handBanker = new Hand();
		handPlayer = new Hand();			
	}
	
	/**
	 * 
	 * <p> Repeatedly calls playRound until the shoe is exhausted. Once the game is finished, display summary statistics.<p>
	 *
	 */
	public void play() {
		int count = 0, count_player = 0, count_banker = 0, count_ties = 0;
		int size = shoe.cards.size();
		for(int i=0;i<size;i++) {
			if(shoe.cards.size()<6) //if the number of cards in shoe is smaller than 6, the game is terminated.
				break;
			else {
				System.out.println("Round "+i);
				playRound();
				if(handPlayer.value() == handBanker.value()){
					System.out.println("Tie!\n");	
					count_ties += 1;				
				}
				else if(handPlayer.value() > handBanker.value()) {
					System.out.println("Player win!\n");
					count_player += 1;
				}
				else if(handPlayer.value() < handBanker.value()) {
					System.out.println("Banker win!\n");	
					count_banker += 1;
				}
				count = i+1;
				
				//clear the cards in Hand after one round
				handBanker.discard();
				handPlayer.discard();	
			}
		}
		
		System.out.println( count + " rounds played");
		System.out.println(count_player + " player wins");
		System.out.println(count_banker + " banker wins");
		System.out.println(count_ties + " ties");
	}
	
	/**
	 * 
	 *  <p> A similar way to play, except that it should end each round by asking the user if they want to play another round,
	 *	terminating the game if they respond negatively.<p>
	 */
	public void playWithPrompt() {
		int count = 0, count_player = 0, count_banker = 0, count_ties = 0;
		Scanner over = new Scanner(System.in);
		char yn;
		
		for(int i=0;i<shoe.cards.size();i++) {
			if(shoe.cards.size()<6) //if the number of cards in shoe is smaller than 6, the game is terminated.
				break;
			else {
				System.out.println("Round "+i);
				playRound();
				if(handPlayer.value() == handBanker.value()){
					System.out.println("Tie!\n");	
					count_ties += 1;				
				}
				else if(handPlayer.value() > handBanker.value()) {
					System.out.println("Player win!\n");
					count_player += 1;
				}
				else if(handPlayer.value() < handBanker.value()) {
					System.out.println("Banker win!\n");	
					count_banker += 1;
				}
				count = i;
				
				//clear the cards in Hand after one round
				handBanker = new Hand();
				handPlayer = new Hand();	
			}
			
			count=i+1;
			System.out.print("Another round? (y/n) : ");	
			yn=over.next().charAt(0);
			if(yn=='n') {
				over.close();
				break;	
			}
		}
		
		System.out.println( + count + " rounds played");
		System.out.println(count_player + " player wins");
		System.out.println(count_banker + " banker wins");
		System.out.println(count_ties + " ties");
		
		
	}
	
	/**
	 * 
	 *  <p> Play a realistic round of Baccarat, following the full Punto Banco rules<p>
	 *
	 */
	public void playRound() {

		Card topCard = shoe.deal();
		
		BaccaratCard one = new BaccaratCard( topCard.getRank(), topCard.getSuit());
		topCard = shoe.deal();
		BaccaratCard two = new BaccaratCard( topCard.getRank(), topCard.getSuit());
		topCard = shoe.deal();
		BaccaratCard three = new BaccaratCard( topCard.getRank(), topCard.getSuit());
		topCard = shoe.deal();
		BaccaratCard four = new BaccaratCard( topCard.getRank(),topCard.getSuit());
		
		handPlayer.add(one);
		handPlayer.add(two);
		handBanker.add(three);
		handBanker.add(four);	
		
		System.out.println("Player: " + handPlayer.toString() + " = " + handPlayer.value());
		System.out.println("Banker: " + handBanker.toString() + " = " + handBanker.value());
		
		if(handPlayer.value()==8 || handPlayer.value() == 9 || handBanker.value()==8 || handBanker.value() == 9) {
			//do nothing if the value in either Player's hand or Banker's hand is 8 or 9
		}
		else {
			
			//If the player has an initial total of 0-5, he draws a third card
			if(handPlayer.value()<=5 && handPlayer.value()>=0) {
				
				System.out.println("Dealing third card to player...");
				topCard = shoe.deal();
				BaccaratCard five = new BaccaratCard( topCard.getRank(), topCard.getSuit());
				handPlayer.add(five);		
				
				//If the banker total is 2 or less, then the banker draws a third card
				if(handBanker.value()<=2 && handBanker.value()>=0) {
					System.out.println("Dealing third card to banker...");
					topCard = shoe.deal();
					BaccaratCard six = new BaccaratCard( topCard.getRank(), topCard.getSuit());
					handBanker.add(six);			
				}
				//If the banker total is 3, then the banker draws a third card unless the player's third card was an 8
				else if(handBanker.value()==3 && five.value()!=8) {
					System.out.println("Dealing third card to banker...");
					topCard = shoe.deal();
					BaccaratCard six = new BaccaratCard( topCard.getRank(), topCard.getSuit());
					handBanker.add(six);			
				}
				//If the banker total is 4, then the banker draws a third card if the player's third card was 2,3,4,5,6,7
				else if(handBanker.value()==4 && five.value()>=2 && five.value()<=7) {
					System.out.println("Dealing third card to banker...");
					topCard = shoe.deal();
					BaccaratCard six = new BaccaratCard( topCard.getRank(), topCard.getSuit());
					handBanker.add(six);			
				}
				//If the banker total is 5, then the banker draws a third card if the player's third card was 4,5,6,7
				else if(handBanker.value()==5 && five.value()>=4 && five.value()<=7) {
					System.out.println("Dealing third card to banker...");
					topCard = shoe.deal();
					BaccaratCard six = new BaccaratCard( topCard.getRank(), topCard.getSuit());
					handBanker.add(six);			
				}
				//If the banker total is 6, then the banker draws a third card if the player's third card was 6,7
				else if(handBanker.value()==6 && (five.value()==6 || five.value()==7)) {
					System.out.println("Dealing third card to banker...");
					topCard = shoe.deal();
					BaccaratCard six = new BaccaratCard( topCard.getRank(), topCard.getSuit());
					handBanker.add(six);	
					
				}
				else if(handBanker.value()==7) {
					//do nothing if the banker total is 7					
				}
				
				System.out.println("Player: "+handPlayer.toString()+" = "+handPlayer.value());
				System.out.println("Banker: "+handBanker.toString()+" = "+handBanker.value());
				
			}
			
			// The case when Player does not draw a third card
			else if(handPlayer.value() == 6 || handPlayer.value() == 7){
				if(handBanker.value()==6 || handBanker.value()==7) {
					//do nothing if the banker has an initial total of 6 or 7
				}
				//If the Banker has an initial total of 0-5, he draws a third card 
				else if(handBanker.value()<=5 && handBanker.value()>=0) {
					System.out.println("Dealing third card to banker...");
					topCard = shoe.deal();
					BaccaratCard six = new BaccaratCard( topCard.getRank(), topCard.getSuit());
					handBanker.add(six);	
					System.out.println("Player: "+handPlayer.toString()+" = "+handPlayer.value());
					System.out.println("Banker: "+handBanker.toString()+" = "+handBanker.value());
				}
			}		
	
		}
		
	}

}

