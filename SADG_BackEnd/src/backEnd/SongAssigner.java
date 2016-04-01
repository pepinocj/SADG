package backEnd;
import java.util.ArrayList;
import java.util.Map;

public class SongAssigner {
	
	SongPicker songPicker = new SongPicker();

	public Map<String, String> assignSongs(GameMode gameMode, GameState gameState) {
		int nbOfSongsNeeded = getNbNeeded(gameMode, gameState);
		ArrayList<String> songs = songPicker.pickSongs(nbOfSongsNeeded, gameState.level, gameState.genre);
		System.out.println("We picked these songs: " + songs.toString());
		
		return null;
	}
	

	private int getNbNeeded(GameMode gameMode, GameState state){
		System.out.println("oei oei overloading");
		return -1;
	}
	
	private int getNbNeeded(RegularMode gameMode, GameState state){
		return state.getPlayers().size()/2;
	}
	
	private int getNbNeeded(MolMode gameMode, GameState state){
		//TODO implement
		return -1;
	}
	
	private int getNbNeeded(TeamMode gameMode, GameState state){
		return -1;
		//TODO implement
	}
	
}
