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
	    System.out.println("Write \"rounds\" to choose the number of rounds in one game.");
	    System.out.println("Write \"level\" to choose the difficulty of the game.");
	    System.out.println("Write \"genre\" to pick the genre of the songs.");

	    
	    receiver.receiveThread.start();
	    
	    // get their input as a String
	    while(true){
		    String command = scanner.next().toLowerCase();
		    
		    if(command.equals("start")){
		    	game.startGame();
		    }
		    else if (command.equals("quit")){
		    	game.quitGame();
		    	receiver.closeCommunication();
		    	scanner.close();
		    }
		    else if (command.equals("level")){
		    	System.out.println("Write E for easy or H for hard");
			    String level = scanner.next().toLowerCase();
			    if(level.equals("e")){game.currentState.level = Level.EASY;} //who needs getters and setters
			    else{game.currentState.level= Level.HARD;}
		    }
		    else if (command.equals("level")){
		    	System.out.println("Write H for Hiphop, P for Pop or D for Dubstep");
			    String genre = scanner.next().toLowerCase();
			    if(genre.equals("h")){game.currentState.genre = Genre.HIPHOP;} //who needs getters and setters
			    else if (genre.equals("p")){game.currentState.genre = Genre.POP;}
			    else{game.currentState.genre = Genre.DUBSTEP;}
		    }
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
