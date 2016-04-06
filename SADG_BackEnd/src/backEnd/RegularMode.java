package backEnd;

import java.util.ArrayList;
import java.util.Map;

public class RegularMode extends GameMode {

	@Override
	public Map<String, String> assignSongs(SongPicker songPicker,GameState gameState) {
		
		ArrayList<Person> players = gameState.getPlayers();
		int nbOfSongsNeeded = players.size()/2;
		System.out.println("nb of songs needed = " + nbOfSongsNeeded);
		ArrayList<String> songs = songPicker.pickSongs(nbOfSongsNeeded, gameState.level, gameState.genre);
		System.out.println("We picked these songs: " + songs.toString());
		songs.addAll(songs); 
		assert(players.size() == songs.size());
		Map<String,String> playerSongMap = assignToPlayers(songs, players);
		return playerSongMap;
	}

}
