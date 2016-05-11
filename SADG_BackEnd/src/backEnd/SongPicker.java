package backEnd;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.opencsv.CSVReader;

import backEnd.GameState.Genre;
import backEnd.GameState.Level;

public class SongPicker {

	List<Song> availableSongs =  new ArrayList<Song>();
	private CSVReader reader;
	//new Song()
	
	public SongPicker()  {
		
		try {
		reader = new CSVReader(new FileReader("SADGSONGS.csv"));
		} catch (FileNotFoundException e) {
			System.out.println("oh boy er is geen reader");
			e.printStackTrace();
		}
		
	     String [] nextLine;
	     int i = 1; //want molsong is 0
	 
		try {
			while ((nextLine = reader.readNext()) != null) {
				 int ddance = 0;
				 String danceEntry = nextLine[5].trim().toLowerCase();
				 if (danceEntry.equals("yes")){ddance = 2;}
				 else if (danceEntry.equals("kinda")){ddance = 1;}
//			 System.out.println(nextLine[0] + " " + nextLine[5] + " " + nextLine[4]); //cheeeck	
//    	 	 System.out.println(nextLine[0] + " " + ddance + " " + startAt);
				 availableSongs.add(new Song(i, ddance));
				 i++;
				}
		     reader.close();

		} catch (NumberFormatException e) {
			System.out.println("oh boy a numberformatexception in Songpicker. This is in the csv parser");
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("oh boy an IOExcepiton in Songpicker. This is in the csv parser");
			e.printStackTrace();
		}

	}

	//Aanname dat er meer liedjes zijn dan er nodig zijn
	public ArrayList<Integer> pickSongs(int nbOfSongsNeeded, Level level, Genre genre) {
		ArrayList<Integer> result = new ArrayList<Integer>();
		ArrayList<Song> temp = new ArrayList<Song>();
		temp.addAll(availableSongs);
//		if(temp.size() < nbOfSongsNeeded){ //TODO hier nog wat code, gewoon zorgen dat er gewoon genoeg liedjes zijn
//			System.out.println("dear me er zijn te weinig songs. ERRROOORRORORORRRR");
//			Math.ceil(temp.size()/nbOfSongsNeeded);
//		}
		temp = filter(temp, level, genre);
		int i = 0;
		while(i< nbOfSongsNeeded){
			int size = temp.size();
			Random r = new Random();
			int random = r.nextInt(size);
			result.add(temp.get(random).getId());
			temp.remove(random);
			i++;
		}
		return result;	
	}

	
	private ArrayList<Song> filter(ArrayList<Song> temp, Level level, Genre genre) {
		int distDance = 0;
		 switch (level) {
         case ALL: return temp;
         case EASY:  distDance = 2;
                  break;
         case MEDIUM: distDance = 1;
                  break;
         case HARD:  distDance = 0;
                  break;
		 }
        ArrayList<Song> result = new ArrayList<Song>();
        
        for(Song song : temp ){
        	if (song.getDance() == distDance){
        		result.add(song);
        		}
        	}
        
		return result;
	}
	
	
	

}
