package backEnd;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Random;

public class SongAssigner {
	
	SongPicker songPicker = new SongPicker();
	
	
	public Map<String, Integer> assignSongs(GameMode gameMode, GameState gameState) {
		return gameMode.assignSongs(songPicker, gameState);
	}
	
	
	
	private Map<String, Integer> assignToPlayers(ArrayList<Integer> songs, ArrayList<Person> players) {
		Map<String,Integer> result = Collections.<String,Integer>emptyMap(); 
		for( Person player : players){
			int size = songs.size();
			Random r = new Random();
			int random = r.nextInt(size);
			//System.out.println("random nummer = " + random);
			result.put(player.getUserName(),songs.get(random));
			songs.remove(random);
		}
		return result;
	}
	

	
	
	
}
