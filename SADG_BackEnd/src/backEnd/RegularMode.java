package backEnd;

import java.util.ArrayList;
import java.util.Map;

public class RegularMode extends GameMode {

	
	@Override
	public Map<String, Integer> assignSongs(SongPicker songPicker,GameState gameState) {
		ArrayList<Person> players = gameState.getPlayers();
		int nbOfSongsNeeded = players.size()/2;
		System.out.println("nb of songs needed = " + nbOfSongsNeeded);
		ArrayList<Integer> songs = songPicker.pickSongs(nbOfSongsNeeded, gameState.level, gameState.genre);
		System.out.println("we picked these songs: ");
		for(Integer song : songs){
			System.out.println(song);
		}
		System.out.println("We picked these songs: " + songs.toString());
		songs.addAll(songs); 
		assert(players.size() == songs.size());
		Map<String,Integer> playerSongMap = assignToPlayers(songs, players);
		return playerSongMap;
	}

	@Override
	public MatchType handleScore(String pers1, String pers2, GameState currentState) {
		boolean result = super.regularMatch(pers1, pers2, currentState);
		if (result){return MatchType.SUCCESS;}
		else {return MatchType.FAIL;}
	}

	
}
