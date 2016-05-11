package backEnd;

import java.util.ArrayList;
import java.util.Map;

public class MolMode extends GameMode {

	@Override
	public Map<String, Integer> assignSongs(SongPicker songPicker, GameState gameState) {
		ArrayList<Person> players = gameState.getPlayers();
		int nbOfSongsNeeded = players.size()/2  ;
		ArrayList<Integer> songs = songPicker.pickSongs(nbOfSongsNeeded, gameState.level, gameState.genre);
		System.out.println("we picked these songs: ");
		for(Integer song : songs){
			System.out.println(song);
		}
		songs.addAll(songs);
		songs.add(0);
		assert(players.size() == songs.size());
		Map<String,Integer> playerSongMap = assignToPlayers(songs, players);
		return playerSongMap;
	}
	
	public MatchType handleScore( String pers1, String pers2, GameState currentState) {
		int secondsPassed = (int) ((System.currentTimeMillis() - currentState.timeStarted) * 60);

		boolean pers1IsMol = currentState.getSongAssignments().get(pers1).equals("0"); //TODO verander naar echte molsongcode
		boolean pers2IsMol = currentState.getSongAssignments().get(pers2).equals("0");// niet zo mooie code sorry :(

		if(pers1IsMol || pers2IsMol){
			System.out.println("Successful molmatch!");
			String mol = "";
			String victim = "";
			if(pers1IsMol){ mol = pers1; victim = pers2;}
			else{ mol = pers2; victim = pers1;}
			currentState.addToScore(mol, secondsPassed); //TODO eventueel score systeem hier aanpassen
			currentState.addToScore(victim, -secondsPassed);
			return MatchType.MOLMATCH;

		}
		
		boolean success = regularMatch(pers1, pers2, currentState);
		if(success){return MatchType.SUCCESS;}
		else{return MatchType.FAIL;}	
	}



}

