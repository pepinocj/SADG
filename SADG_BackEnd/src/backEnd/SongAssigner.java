package backEnd;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class SongAssigner {
	
	SongPicker songPicker = new SongPicker();

	public Map<String, String> assignSongs(GameMode gameMode, GameState gameState) {
		System.out.println("oei overloading");
		return null;
	}
	
	public Map<String, String> assignSongs(RegularMode gameMode, GameState gameState) {
		ArrayList<Person> players = gameState.getPlayers();
		int nbOfSongsNeeded = players.size()/2;
		ArrayList<String> songs = songPicker.pickSongs(nbOfSongsNeeded, gameState.level, gameState.genre);
		System.out.println("We picked these songs: " + songs.toString());
		songs.addAll(songs); 
		assert(players.size() == songs.size());
		Map<String,String> playerSongMap = assignToPlayers(songs, players);
		return playerSongMap;
	}
	
	//rekent effe op oneven aantal mensen
	public Map<String, String> assignSongs(MolMode gameMode, GameState gameState) {
		ArrayList<Person> players = gameState.getPlayers();

		int nbOfSongsNeeded = players.size()/2; 
		ArrayList<String> songs = songPicker.pickSongs(nbOfSongsNeeded, gameState.level, gameState.genre);
		System.out.println("We picked these songs: " + songs.toString());
		songs.addAll(songs); 
		songs.add("molsong");//TODO dit moet uiteraard veranderen naar de molsongcode fzo
		assert(players.size() == songs.size());
		Map<String,String> playerSongMap = assignToPlayers(songs, players);
		return playerSongMap;	
	}

	public Map<String, String> assignSongs(TeamMode gameMode, GameState gameState) {
		ArrayList<Person> players = gameState.getPlayers();
		int nbOfSongsNeeded = 2;  //rekent op slechts twee teams
		ArrayList<String> twoSongs = songPicker.pickSongs(nbOfSongsNeeded, gameState.level, gameState.genre);
		System.out.println("We picked these songs: " + twoSongs.toString());
		
		ArrayList<String> songs = new ArrayList<String>();
		for(int i=0; i<=((players.size()-1)/2); i++){
			songs.addAll(twoSongs);
		}
		
		assert(players.size() == songs.size() || players.size() +1 == songs.size()); //depends on even of oneven
		Map<String,String> playerSongMap = assignToPlayers(songs, players);
		return playerSongMap;	
	}
	
	
	
	
	private Map<String, String> assignToPlayers(ArrayList<String> songs, ArrayList<Person> players) {
		Map<String,String> result = Collections.<String,String>emptyMap(); 
		for( Person player : players){
			int size = songs.size();
			int random = (int) Math.random()*size;
			result.put(player.getUserName(),songs.get(random));
			songs.remove(random);
		}
		return result;
	}
	

	
	
	
}
