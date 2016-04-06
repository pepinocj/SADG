package backEnd;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeoutException;
 
public class Game {
	
	final static int maxSuccessCount = 1;
	
	GameState currentState;
	GameMode gameMode;
	SongAssigner songAssigner;
	ScoreHandler scoreHandler;
	ISender sender;
	
	int successCount; 

	public Game (){
		gameMode = new RegularMode();
		songAssigner = new SongAssigner();
		scoreHandler = new ScoreHandler();
		currentState = new GameState();
		String ipAddress = "localhost";
		try {
			sender = new Sender(ipAddress);
		} catch (IOException | TimeoutException e) {
			e.printStackTrace();
		}
	}
	
	public void quitGame() {
		sender.closeCommunication();
	}

	public void startGame() {
		startNewRound();
	}
	
	public void addPlayer(String namePerson){
		Person person = new Person(namePerson);
		currentState.addPlayer(person);
		System.out.println("Person " + person.getUserName() + " was added.");
	}
	
	
	public void startNewRound(){
		System.out.println("Start new round with assignment:");
		successCount = 0;
		Map<String, String> songAssignments = songAssigner.assignSongs(gameMode, currentState);
		long timeToStart = System.currentTimeMillis(); //hier nog plus 10 seconden doen ofzo
		try {
			sender.sendMusic(songAssignments);
			sender.startRound(timeToStart);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		currentState.setSongAssignments(songAssignments);
		currentState.setStartTime(timeToStart); 
		System.out.println("Start groovin'!");
	}
	
	public void verifyMatch(String pers1, String pers2) throws IOException{
		boolean success = scoreHandler.handleScore(gameMode, pers1, pers2, currentState);
		if(success){ successCount++ ;}
		sender.reportVerification(pers1, pers2, success);
		
		if(successCount >= maxSuccessCount){
			String leader = getLeader();
			sender.announceWinner(leader);
			sender.stopRound();
		}
	}
		
	private String getLeader(){
		Map<String, Integer> scores = currentState.getScores();
		int highScore = 0;
		String leader = "nobody";
		
		for (Map.Entry<String, Integer> entry : scores.entrySet())
		{
		   if(entry.getValue()>highScore){
			   leader = entry.getKey();
			   highScore = entry.getValue();
		   }
		}
		return leader;
	}
	
	
	public Map<String, Integer> getScores(){
		return currentState.getScores();
	}
}