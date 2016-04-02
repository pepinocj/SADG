package backEnd;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeoutException;



public class Game {
	
	GameState currentState;
	GameMode gameMode;
	SongAssigner songAssigner;
	ScoreHandler scoreHandler;
	ISender sender;
	
	public Game (){
		gameMode = new RegularMode();
		songAssigner = new SongAssigner();
		scoreHandler = new ScoreHandler();
		currentState = new GameState();
		String ipAddress = "localhost";
		try {
			sender = new Sender(ipAddress);
		} catch (IOException | TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addPlayer(String namePerson){
		Person person = new Person(namePerson);
		currentState.addPlayer(person);
		System.out.println("Person " + person.getUserName() + " was added.");
	}
	
	
	public void startNewRound(){
		System.out.println("Start new round with assignment:");
		Map<String, String> songAssignments = songAssigner.assignSongs(gameMode, currentState);
		long timeToStart = System.currentTimeMillis(); //hier nog plus 10 seconden doen ofzo
		try {
			sender.sendMusic(songAssignments);
			
			sender.startRound(timeToStart);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		currentState.setSongAssignments(songAssignments);
		currentState.setStartTime(timeToStart); 
		System.out.println("Start groovin'!");
	}
	
	public boolean verifyMatch(String pers1, String pers2){
		return scoreHandler.handleScore(gameMode, pers1, pers2, currentState);	
	}	
	
	public Map<String, Integer> getScores(){
		return currentState.getScores();
	}
}
	
	

 
