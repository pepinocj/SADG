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
	
	
	public abstract Map<String, String> assignSongs(SongPicker songPicker, GameState gameState); 
}
