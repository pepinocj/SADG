package backEnd;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class Game {

	final static int maxSuccessCount = 1;
	final static int maxRoundCount = 1;

	GameState currentState;
	GameMode gameMode;
	SongAssigner songAssigner;
	ScoreHandler scoreHandler;
	ISender sender;

	int successCount; 
	int roundCount; 

	long startFirstRound;

	public Game (){
		gameMode = new RegularMode();
		songAssigner = new SongAssigner();
		scoreHandler = new ScoreHandler();
		currentState = new GameState();
		roundCount = 0;
		String ipAddress = "localhost";

		startFirstRound =0;
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

	public void removePlayer(String namePerson){
		currentState.removePlayer(namePerson);
		//Lelijke fix maar nodig voor consistentie tenzij bij senden altijd playerlist wordt doorgestuurd.
		sender.removeUser(namePerson);
		System.out.println("Person " + namePerson + " was removed.");
	}

	public void startNewRound(){

		System.out.println("Start new round with assignment:");
		successCount = 0;
		
		if(((currentState.players.size()) % 2 )==1){
			gameMode = new MolMode();
			
		}
		
		Map<String, Integer> songAssignments = songAssigner.assignSongs(gameMode, currentState);
		long systemTime =  System.currentTimeMillis();
		
		

		if(!(startFirstRound > 0)){
			startFirstRound = System.currentTimeMillis();
		}

		long timeToStart = systemTime-startFirstRound+ 10000; //hier nog plus 10 seconden doen ofzo

		timeToStart = 0;//

		sender.sendMusic(songAssignments);
		//sender.sendSystemTime(systemTime); //depricated with thread pause

		try {
			Thread.sleep(7500);                 //10000 milliseconds is one second.
		} catch(InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
		sender.startRound(timeToStart);
		currentState.setSongAssignments(songAssignments);
		currentState.setStartTime(timeToStart); 
		System.out.println("Start groovin'!");
	}


	public void verifyMatch(String pers1, String pers2) throws IOException{
		if(!currentState.players.contains(pers1) || !currentState.players.contains(pers2)){System.out.println("OMG er is een van de spelers illegaal whaat");}
		MatchType successType = scoreHandler.handleScore(gameMode, pers1, pers2, currentState);
		boolean success = false;
		if(successType == MatchType.SUCCESS){
			successCount++ ;
			success = true; }

		if (successType == MatchType.MOLMATCH){
			successCount++ ;
			String mol;
			String victim;
			if(currentState.getSongAssignments().get(pers1).equals("MolSong")){
				mol = pers1;
				victim = pers2;
			}
			else{
				mol = pers2;
				victim =pers1;}
			sender.reportMolVerification(mol, victim);
		}

		else{
			sender.reportVerification(pers1, pers2, success); //delete for better performance
		}
		System.out.println("Verification: " +successType);


		if(successCount >= maxSuccessCount){
			String leader = getLeader();
			sender.announceWinner(leader); // delete for better performance
			sender.stopRound();
			roundCount++;
			if(roundCount <= maxRoundCount){
				this.startNewRound();
			}
			//TODO end of game mooier afhandelen
			else{
				System.out.println("Game Over! Congratulations, "+ getLeader());}
			roundCount = 0;
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