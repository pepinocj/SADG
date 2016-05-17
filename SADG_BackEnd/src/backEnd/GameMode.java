package backEnd;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public abstract class GameMode {

	Random r = new Random();
	
	public Map<String, Integer> assignToPlayers(ArrayList<Integer> songs, ArrayList<Person> players) {
		Map<String,Integer> result = new HashMap<String,Integer>(); 
		for( Person player : players){
			int size = songs.size();
			int random = r.nextInt(size);
			System.out.println(player.getUserName()+": "+songs.get(random));
			result.put(player.getUserName(),songs.get(random));
			songs.remove(random);
		}
		return result;
	}
	
	
	public boolean regularMatch(String pers1, String pers2, GameState currentState) {
		boolean isMatch = checkMatch(pers1, pers2, currentState);
		int secondsPassed = (int) ((System.currentTimeMillis()/1000 - currentState.timeStarted)/1000 );
		System.out.println("seconds passed = " + secondsPassed);
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
	
	public abstract Map<String, Integer> assignSongs(SongPicker songPicker, GameState gameState); 
	public abstract MatchType handleScore(String pers1, String pers2, GameState currentState);
}
