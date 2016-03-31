
import java.util.Map;



public class Game {
	
	GameState currentState;
	GameMode gameMode;
	SongPicker songPicker;
	ScoreHandler scoreHandler;
	ISender sender;
	
	public Game (){
		gameMode = new RegularMode();
		songPicker = new SongPicker();
		scoreHandler = new ScoreHandler();
		currentState = new GameState();
	}
	
	public void addPlayer(Person person){
		currentState.addPlayer(person);
		System.out.println("Person " + person.getUserName() + " was added.");
	}
	
	
	public void startNewRound(){
		System.out.println("Start new round with assignment:");
		Map<Person, String> songAssignments = songPicker.pickSongs(gameMode, currentState.getPlayers());
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
	
	

 
