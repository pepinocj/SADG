package backEnd;
import java.util.ArrayList;

import backEnd.GameState.Genre;
import backEnd.GameState.Level;


public class SongPicker {

	ArrayList<String> availableSongs = new ArrayList<String>();
	
	public SongPicker(){
		availableSongs.add("Dessert");
		availableSongs.add("Hotline Bling");
	}

	//Aanname dat er meer liedjes zijn dan er nodig zijn
	public ArrayList<String> pickSongs(int nbOfSongsNeeded, Level level, Genre genre) {
		ArrayList<String> result = new ArrayList<String>();
		ArrayList<String> temp = new ArrayList<String>();
		if(temp.size() < nbOfSongsNeeded){ //TODO zorgen dat er gewoon genoeg liedjes zijn
			Math.ceil(temp.size()/nbOfSongsNeeded);
		}
		temp = filter(temp, level, genre);
		int i = 0;
		while(i<= nbOfSongsNeeded){
			int size = temp.size();
			int random = (int) Math.random()*size;
			result.add(temp.get(random));
			temp.remove(random);
			i++;
		}
		return result;	
	}

	
	private ArrayList<String> filter(ArrayList<String> temp, Level level, Genre genre) {
		//TODO werkelijk filteren
		return temp;
	}
	
	
	

}
