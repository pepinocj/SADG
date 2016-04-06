package backEnd;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import backEnd.GameState.Genre;
import backEnd.GameState.Level;


public class SongPicker {

	List<String> availableSongs =  Arrays.asList("0", "1", "2","3", "4");
	
	public SongPicker(){
		//availableSongs.add("Dessert");
		//availableSongs.add("Hotline Bling");
	}

	//Aanname dat er meer liedjes zijn dan er nodig zijn
	public ArrayList<String> pickSongs(int nbOfSongsNeeded, Level level, Genre genre) {
		ArrayList<String> result = new ArrayList<String>();
		ArrayList<String> temp = new ArrayList<String>();
		temp.addAll(availableSongs);
		if(temp.size() < nbOfSongsNeeded){ //TODO hier nog wat code, gewoon zorgen dat er gewoon genoeg liedjes zijn
			System.out.println("dear me er zijn te weinig songs. ERRROOORRORORORRRR");
			Math.ceil(temp.size()/nbOfSongsNeeded);
		}
		temp = filter(temp, level, genre);
		int i = 0;
		while(i<= nbOfSongsNeeded){
			int size = temp.size();
			Random r = new Random();
			int random = r.nextInt(size);
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
