package backEnd;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;
import backEnd.GameState.Genre;
import backEnd.GameState.Level;

public class Main {
	
	
	private static Game game;
	private static Receiver receiver;
	
	
	public static void main (String[] args)
	  {
		game = new Game();

		receiver = new Receiver("localhost", game);
	
	    // create a scanner so we can read the command-line input
	    Scanner scanner = new Scanner(System.in);

	    System.out.println("Welcome fellow dancers!");

	    System.out.println("Write \"start\" when all users are added to start the game.");
	    System.out.println("Write \"quit\" to quit the game.");
	    System.out.println("Write \"level\" to choose the difficulty of the game.");
	    System.out.println("Write \"players\" to get the current players.");
	    System.out.println("Write \"rounds\" to choose the number of rounds.");
	    System.out.println("Write \"matches\" to choose the number of succesful matches before a round stops.");




	    
	    receiver.receiveThread.start();
	    
	    // get their input as a String
	    while(true){
		    String command = scanner.next().toLowerCase();
		    
		    if(command.equals("start")){
		    	game.startGame();
		    }
		    else if (command.equals("players")) {
		    	for( Person person : game.currentState.players){
		    		System.out.println(person.getUserName().split(":")[1]);

		    	}
		    }
		    else if (command.equals("rounds")) {
		    	System.out.println("How many rounds?");
		    	String roundsString = scanner.next().toLowerCase();
		    	try {
		    		int rounds = Integer.parseInt(roundsString);
		    		if(rounds < 1 ){
		    			System.out.println("The number should be greater than 0.");
		    		}
		    		else{
		    			game.maxRoundCount = (rounds);
		    		}
		    	}
		    	catch (NumberFormatException e) {
		    		System.out.println("This is not a number.");	
		    	}
		    }
		    
		    else if (command.equals("matches")) {
		    	System.out.println("How many successful matches?");
		    	String matchesString = scanner.next().toLowerCase();
		    	try {
		    		int matches = Integer.parseInt(matchesString);
		    		if(matches < 1 ){
		    			System.out.println("The number should be greater than 0.");
		    		}
		    		else{
		    			game.maxSuccessCount = (matches);
		    		}
		    	}
		    	catch (NumberFormatException e) {
		    		System.out.println("This is not a number.");	
		    	} 	
		    }
		   
		    
		    else if (command.equals("quit")){
		    	game.quitGame();
		    	receiver.closeCommunication();
		    	scanner.close();
		    }
		    else if (command.equals("level")){
		    	System.out.println("Write E for easy, M for medium, or H for hard");
			    String level = scanner.next().toLowerCase();
			    if(level.equals("e")){game.currentState.level = Level.EASY;} //who needs getters and setters
			    else if(level.equals("m")){game.currentState.level = Level.MEDIUM;}
			    else{game.currentState.level= Level.HARD;}
		    }
//		    else if (command.equals("genre")){
//		    	System.out.println("Write H for Hiphop, P for Pop or D for Dubstep");
//			    String genre = scanner.next().toLowerCase();
//			    if(genre.equals("h")){game.currentState.genre = Genre.HIPHOP;} //who needs getters and setters
//			    else if (genre.equals("p")){game.currentState.genre = Genre.POP;}
//			    else{game.currentState.genre = Genre.DUBSTEP;}
//		    }
		    else if (command.equals("help")){
		    	System.out.println("Write \"start\" when all users are added to start the game.");
		 	    System.out.println("Write \"quit\" to quit the game.");
		 	    System.out.println("Write \"rounds\" to choose the number of rounds in one game.");
		 	    System.out.println("Write \"level\" to choose the difficulty of the game.");
		 	    System.out.println("Write \"genre\" to pick the genre of the songs.");
		 	    }
		    else{ System.out.println("Invalid command.");}

	    }

	  }

}