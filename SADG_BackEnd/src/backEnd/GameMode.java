package backEnd;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class GameMode {

	public Map<String, String> assignToPlayers(ArrayList<String> songs, ArrayList<Person> players) {
		Map<String,String> result = new HashMap<String,String>(); 
		for( Person player : players){
			int size = songs.size();
			int random = (int) Math.random()*size;
			result.put(player.getUserName(),songs.get(random));
			songs.remove(random);
		}
		return result;
	}
	
	
	public boolean regularMatch(String pers1, String pers2, GameState currentState) {
		boolean isMatch = checkMatch(pers1, pers2, currentState);
		int secondsPassed = (int) ((System.currentTimeMillis() - currentState.timeStarted) * 60);
		if(isMatch){
			System.out.println("Congratulations! It's a match");
			currentState.addToScore(pers1, secondsPassed);
			currentState.addToScore(pers2, secondsPassed);
			}
		else{
			System.out.println("Too bad, not a match!");
			currentState.addToScore(pers1, -secondsPassed);
			currentState.addToScore(pers2, -secondsPassed);
			}
		
		return isMatch;
	}
	
	
	private boolean checkMatch(String pers1, String pers2, GameState currentState){
		System.out.println("check songs");
		System.out.println("song 1 = " + currentState.songAssignments.get(pers1));
		System.out.println("song 2 = " + currentState.songAssignments.get(pers2));

		return (currentState.songAssignments.get(pers1).equals(currentState.songAssignments.get(pers2)));
	}
	
	public abstract Map<String, String> assignSongs(SongPicker songPicker, GameState gameState); 
	public abstract MatchType handleScore(String pers1, String pers2, GameState currentState);
}
