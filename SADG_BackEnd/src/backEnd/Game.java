package backEnd;

import java.util.Map;



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
	}
	
	public void addPlayer(Person person){
		currentState.addPlayer(person);
		System.out.println("Person " + person.getUserName() + " was added.");
	}
	
	
	public void startNewRound(){
		System.out.println("Start new round with assignment:");
		Map<String, String> songAssignments = songAssigner.assignSongs(gameMode, currentState);
		sender.sendMusic(songAssignments);
		long timeToStart = System.currentTimeMillis(); //hier nog plus 10 seconden doen ofzo
		sender.startRound(timeToStart);
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
	
	

 
