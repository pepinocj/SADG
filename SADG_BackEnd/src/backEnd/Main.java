package backEnd;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class Main {
	
	
	private static Game game;
	private static Receiver receiver;
	
	public static void main (String[] args)
	  {
		
		game = new Game();
		try {
			receiver = new Receiver("localhost", game);
		} catch (IOException | TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    // create a scanner so we can read the command-line input
	    Scanner scanner = new Scanner(System.in);

	    System.out.println("Welcome fellow dancers!");

	    System.out.println("Write \"start\" when all users are added to start the game.");
	    System.out.println("Write \"quit\" to quit the game.");

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
		    else{ System.out.println("Invalid command.");}

	    }

	  }

}
