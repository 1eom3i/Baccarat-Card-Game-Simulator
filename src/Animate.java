import javafx.geometry.Insets;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.animation.*;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.GridPane;



/**
 * Simple JavaFX program to animate a playing card.
 *
 * <p>Provided for use in COMP1721 Coursework 2.</p>
 *
 * @author leo
 */
public class Animate extends Application
{
	 private int count = 0;     // count round
	 private TilePane playercards;
	 private TilePane bankercards;
	 int countTie = 0;   
	 int countWinPlayer = 0;
	 int countWinBanker = 0; 
	 int countCards = 0;       // count cards in shoe
	 int currentdecknum = 0;   // deck number selected
	 
	 
	 Label congratlabel = new Label("Congratulation!");  // game result
	 Game game = null;
	 Scene scene1 = null;
	 Scene scene2 = null;
	 
	 TextArea playerhand;  //player score in one hand
	 TextArea bankerhand;  //banker score in one hand
	
	 /**
	  * 
	  */
	 @Override
	 public void start(Stage primaryStage){
		 //Page of number of deck selection
		 VBox paneForButtons = new VBox(40);
		 paneForButtons.setStyle("-fx-background-color:#66ccff");
	
		 Label baccarat = new Label("Baccarat");
		 baccarat.setStyle("-fx-background-color:pink");
		 baccarat.setFont(Font.font("SansSerif",FontWeight.BOLD, 22));
		 
		 Label prompt = new Label("Please specify number of decks in shoe of card or Exit");
		 prompt.setFont(Font.font("Times New Roman",FontWeight.BOLD, FontPosture.ITALIC, 20));
		 
		 Button fourDecks = new Button("4 decks");
		 Button sixDecks = new Button("6 decks");
		 Button eightDecks = new Button("8 decks");
		 Button exit = new Button("  Exit !   ");
		 
		 paneForButtons.getChildren().add(baccarat);
		 paneForButtons.getChildren().add(prompt);	
		 paneForButtons.getChildren().addAll(fourDecks,sixDecks,eightDecks,exit);
		
		 paneForButtons.setAlignment(Pos.CENTER);
		 paneForButtons.setStyle("-fx-border-color:black");
			
		 scene1 = new Scene(paneForButtons, 640, 400, Color.DARKGREEN);
		 primaryStage.setTitle("Baccarat");
		 primaryStage.setScene(scene1);
		 primaryStage.show();

		 Label scorecard= new Label("Scorecard");
		 scorecard.setFont(Font.font("Times New Roman",FontWeight.BOLD, 14));
		 
		 //Scorecard pane
		 TilePane scorepane = new TilePane();

		 scorepane.setPrefRows(4);      
		 scorepane.setPrefColumns(2); 
		 scorepane.setHgap(1);
		 scorepane.setVgap(1);
		 scorepane.setStyle("-fx-padding: 10; -fx-background-color: orange;");
		 scorepane.setAlignment(Pos.TOP_LEFT);
		 
		 TextField roundnum = new TextField("000");
		 TextField playerwin = new TextField("000");
		 TextField bankerwin = new TextField("000");
		 TextField tie = new TextField("000");		 
		 
		 scorepane.getChildren().addAll(new Label("Round #:"), roundnum);
		 scorepane.getChildren().addAll(new Label("Player win #"), playerwin);
		 scorepane.getChildren().addAll(new Label("Banker win #"), bankerwin);
		 scorepane.getChildren().addAll(new Label("Tie #"), tie);
		 
		 //Shoecard label
		 Label shoecard= new Label("Shoe of card");
		 shoecard.setFont(Font.font("Times New Roman",FontWeight.BOLD, 14));
		 
		 //Shoecard pane		 
		 TilePane shoepane = new TilePane();

		 shoepane.setPrefRows(4);      
		 shoepane.setPrefColumns(2);  
		 shoepane.setHgap(1);
		 shoepane.setVgap(1);
		 shoepane.setStyle("-fx-padding: 10; -fx-background-color: orange;");
		 
		 TextField decknum = new TextField("0");
		 TextField remainnum = new TextField("000");
		 
		 shoepane.getChildren().addAll(new Label("Deck #:"), decknum);
		 shoepane.getChildren().addAll(new Label("Remaining Card #"), remainnum);
		 
		 //Hand
		 Label player = new Label("        Player");
		 player.setFont(Font.font("Times New Roman",FontWeight.BOLD, 14));
		 Label banker = new Label("        Banker");
		 banker.setFont(Font.font("Times New Roman",FontWeight.BOLD, 14));
		 
		 //Player score in hand
		 playerhand = new TextArea();
		 playerhand.setPrefColumnCount(2);
		 playerhand.setPrefRowCount(3);
		 playerhand.setWrapText(true);
		 playerhand.setStyle("-fx-text-fill:red");
		 playerhand.setFont(Font.font("Times",30));
		 
		 //Banker score in hand
		 bankerhand = new TextArea();
		 bankerhand.setPrefColumnCount(2);
		 bankerhand.setPrefRowCount(3);
		 bankerhand.setWrapText(true);
		 bankerhand.setStyle("-fx-text-fill:red");
		 bankerhand.setFont(Font.font("Times",30));
			 
		 playercards = new TilePane();	
		 playercards.setPrefRows(1);     
		 playercards.setPrefColumns(3);  
		 playercards.setHgap(5);
		 playercards.setVgap(5);
		 playercards.setStyle("-fx-padding: 10; -fx-background-color: green;");
		
		 bankercards = new TilePane();
		 bankercards.setPrefRows(1);      // suits
		 bankercards.setPrefColumns(3);  // ranks
		 bankercards.setHgap(5);
		 bankercards.setVgap(5);
		 bankercards.setStyle("-fx-padding: 10; -fx-background-color: green;");
		
		//play button 
		 Button playbtn = new Button("Play One Round!");		
         playbtn.setFont(Font.font("Times New Roman",FontWeight.BOLD, 14));
		 
         //Play Another Round Box		 
		 VBox anotherRnd = new VBox(20);
		 HBox yornbtn = new HBox(20);
		 yornbtn.setAlignment(Pos.CENTER);
		 anotherRnd.setStyle("-fx-background-color:pink");
		 anotherRnd.setAlignment(Pos.CENTER);
		
		 //create a note for user to choose to continue to play or end
		 Label anotherrndmsg = new Label("Do you want to play another round ?");
		 Button ysbtn = new Button("Yes");
		 Button nobtn = new Button("No"); 
		 yornbtn.getChildren().addAll(ysbtn,nobtn);
		 
		 anotherRnd.getChildren().add(congratlabel);
		 anotherRnd.getChildren().add(anotherrndmsg);
		 anotherRnd.getChildren().add(yornbtn);
		 
		 //Play Round Pane
		 GridPane playrndPane = new GridPane();
		 playrndPane.setAlignment(Pos.CENTER);
		 playrndPane.setPadding(new Insets(3, 3, 3, 3));
		 playrndPane.setHgap(5.5);
		 playrndPane.setVgap(5.5);

	     // Place nodes in the pane
		 playrndPane.add(scorecard, 0, 0);
		 playrndPane.add(scorepane, 0, 1);
		 playrndPane.add(shoecard, 8, 0);
		 playrndPane.add(shoepane, 8, 1);
		    
		 playrndPane.add(playerhand, 5, 8);
		 playrndPane.add(bankerhand, 7,8);
		 playrndPane.add(player,5,9);
		 playrndPane.add(banker, 7, 9);
		 playrndPane.add(playercards,0,8);
		 playrndPane.add(bankercards,8,8);
		 playrndPane.add(playbtn, 6, 10);
		 
		// Event when click 4 decks button
	    fourDecks.setOnAction(new EventHandler<ActionEvent>() {
	    	public void handle(ActionEvent e) {			
				scene2 = new Scene(playrndPane,1280, 800, Color.DARKGREEN);
				primaryStage.setTitle("Baccarat");
				primaryStage.setScene(scene2);
				primaryStage.setResizable(false);
		    	primaryStage.show();
		    	currentdecknum = 4;
		        game = new Game(currentdecknum);
            }	 
	    });
	    
	 // Event when click 6 decks button
	    sixDecks.setOnAction(new EventHandler<ActionEvent>() {
	    	public void handle(ActionEvent e) {
				scene2 = new Scene(playrndPane,1280, 800, Color.DARKGREEN);
		    	primaryStage.setTitle("Baccarat");
		    	primaryStage.setScene(scene2);
		    	primaryStage.setResizable(false);
		    	primaryStage.show();
		    	currentdecknum = 6;
		    	game = new Game(currentdecknum);
            }	 
	    });
	    
	 // Event when click 8 decks button
	    eightDecks.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {	
				scene2 = new Scene(playrndPane,1280, 800, Color.DARKGREEN);
		    	primaryStage.setTitle("Baccarat");
		    	primaryStage.setScene(scene2);
		    	primaryStage.setResizable(false);
		    	primaryStage.show();
		    	currentdecknum = 8;
		    	game = new Game(currentdecknum);
            }	 
	    });
	    
	 // Event when click exit button
	    exit.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				System.out.print(" \n Exit Baccarat");
				System.exit(0);
	    	 }
		});
	    
	 // Event when click play one round button
	    playbtn.setOnAction(new EventHandler<ActionEvent>() {					
	 		public void handle(ActionEvent e) {
	 			count += 1;	
	 			game.handBanker = new Hand();
	 			game.handPlayer = new Hand();
	 			game.playRound();
	 			countCards = game.shoe.cards.size();
	 			TilePaneApp(game.handPlayer,game.handBanker);
	 			roundnum.setText(String.format("%d", count));
	 			playerwin.setText(String.format("%d", countWinPlayer));
	 			bankerwin.setText(String.format("%d", countWinBanker));
	 			tie.setText(String.format("%d", countTie));
	 			decknum.setText(String.format("%d", currentdecknum));
	 			remainnum.setText(String.format("%d", countCards));
	 			playrndPane.add(anotherRnd, 6, 10);
	 		}
	 	});
	    	 	
	 // Event when click yes button
	    ysbtn.setOnAction(new EventHandler<ActionEvent>() {
	    	 public void handle(ActionEvent e) {
	    		playrndPane.getChildren().removeAll(anotherRnd);
	    		//clear player and banker cards in one hand
	    		playercards.getChildren().clear();
	    		bankercards.getChildren().clear();
	    		//clear player and banker score in one hand
	    		playerhand.clear();
	    		bankerhand.clear();
    		 }
    	});
	    
	 // Event when click no button
	    nobtn.setOnAction(new EventHandler<ActionEvent>() {
	    	public void handle(ActionEvent e) { 
	    		primaryStage.close();
	    		System.out.print("\n Exit Baccarat");
	    		System.exit(0);
	    	}
	    });
	    
	 }
	 
	 /**
	  * 
	  * @param Player
	  * @param Banker 
	  *
	  */
	 public void TilePaneApp(Hand Player,Hand Banker) {

		//deal 1st and 2nd cards for player and Banker
		   
		   if (Player.size() >= 2) {
			   for(int i= 0; i<=1; i++) {
				   PlayerCardTransition(Player, i);   
			   }
		   }
		   else {
			   System.out.print("# of card issued to Player is less than 2");	 	   
		   }
		    
		   if (Banker.size()>= 2) {
			   for(int j=0; j<=1; j++) {
				   BankerCardTransition(Banker, j);
			   }
		   }
		   else {
			   System.out.print("# of card issued to Banker is less than 2");
			   }
		   
		 // deal 3rd card
		   if (Player.size() == 3)
		   {
			   // wait for some time for dealing 3rd cards
			   try {
				Thread.sleep(5000);
				System.out.print("Starting deal 3rd card");
			   } catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			   	}
			  
			   PlayerCardTransition(Player, 2);
			   
		   }
		   if (Banker.size() == 3) {
			   try {
				   //Wait for some time for dealing 3rd card
					Thread.sleep(5000);
					System.out.print("Starting deal 3rd card");
				   } catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				   }
				   BankerCardTransition(Banker,2);
				   
		   }
			   
		   playerhand.setText( String.format("%d",Player.value() ) );
		   bankerhand.setText( String.format("%d",Banker.value() ) );
			   
		   // animate to display player and banker scores in one hand
		   FadeTransition ftplayer = new FadeTransition(Duration.millis(1500), playerhand);
		   ftplayer.setFromValue(1);
		   ftplayer.setToValue(0.1);
		   ftplayer.setCycleCount(2);
		   ftplayer.setAutoReverse(true);
		   ftplayer.play();
		   FadeTransition ftbanker = new FadeTransition(Duration.millis(2000), bankerhand);
		   ftbanker.setFromValue(1);
		   ftbanker.setToValue(0.1);
		   ftbanker.setCycleCount(2);
		   ftbanker.setAutoReverse(true);
		   ftbanker.play();
					   
		   if(Player.value() == Banker.value()){	
				countTie += 1;	
				congratlabel.setText("Congratulation! Player and Banker tie");
		   }
		   else if(Player.value() > Banker.value()) {
				countWinPlayer += 1;
				congratlabel.setText("Congratulation! Player win");
		   }
		   else if(Player.value() < Banker.value()) {
				countWinBanker += 1;
				congratlabel.setText("Congratulation! Banker win");
		   }			   	
	  }
	 
	 /**
	  * animate to deal with player card
	  * @param Player
	  * @param i 
	  *
	  */
	 
	public void PlayerCardTransition(Hand Player, int i) {
		
		Card card_player = new Card(Player.cards.get(i).getRank(), Player.cards.get(i).getSuit());
		Image image_player = card_player.getImage();
		   
		if (image_player != null) {
			ImageView viewPlayer = new ImageView(image_player);
			playercards.getChildren().add(viewPlayer);
			TranslateTransition translate2 = new TranslateTransition(Duration.millis(1500));
			translate2.setToX(10);
			translate2.setToY(10);
		
			RotateTransition rotate = new RotateTransition(Duration.millis(1500));
			rotate.setToAngle(180);
		
			// Apply the transitions in parallel, in both directions, forever
		
			ParallelTransition transition = new ParallelTransition(viewPlayer, translate2, rotate);
			transition.setCycleCount(1);
			transition.setAutoReverse(true);
			transition.play();
		}
		else {
			// Just in case images can't be found
			playercards.getChildren().add(new Label(card_player.toString()));
		}
	}
	
	//animate to deal with banker card
	public void BankerCardTransition(Hand Banker, int j) {
		Card card_banker = new Card(Banker.cards.get(j).getRank(), Banker.cards.get(j).getSuit());
		Image image_banker = card_banker.getImage();
		if (image_banker != null) {
			ImageView viewBanker = new ImageView(image_banker);
		    bankercards.getChildren().add(viewBanker);
		    TranslateTransition translate = new TranslateTransition(Duration.millis(1500));
	    	translate.setToX(10);
	    	translate.setToY(10);

	    	RotateTransition rotate = new RotateTransition(Duration.millis(1500));
	    	rotate.setToAngle(180);

	    	// Apply the transitions in parallel, in both directions, forever
	    	ParallelTransition transition = new ParallelTransition(viewBanker, translate, rotate);
	    	transition.setCycleCount(1);
	    	transition.setAutoReverse(true);
	    	transition.play();
		}
		else {
		    // Just in case images can't be found
		    bankercards.getChildren().add(new Label(card_banker.toString()));
	    }
	}

  public static void main(String[] args)
  {
    launch(args);
  }
  
}