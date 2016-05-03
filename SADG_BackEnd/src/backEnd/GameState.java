package backEnd;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;




public class GameState {

	
	public enum Genre {
	    ALL, POP, HIPHOP, DUBSTEP}; //ook in main aanpassen voor user input

	public enum Level {
		ALL, EASY, MEDIUM, HARD};
	    
	    
	//persons toch hier want bij handlescore moet ge de score vd persons kunnen aanpassen
	ArrayList<Person> players;
	Map<String, String> songAssignments;
	long timeStarted;
	public Genre genre;
	public Level level;
	//add other configuration like level or genre
	
	
	
	public GameState() {
		players = new ArrayList<Person>();
		genre = Genre.ALL;
		level = Level.ALL;
	}
	

	public void addPlayer(Person person){
		players.add(person); //TODO fix this
	}
	
	public void removePlayer(String name){
		for(int i=0; i<players.size(); i++){
			if(players.get(i).getUserName().equals(name)){
				players.remove(i);
			}
		}
	}
	
	public ArrayList<Person> getPlayers(){
		return players;
	}
	
	public void setStartTime(long timeToStart){
		this.timeStarted = timeToStart;
	}

	public Map<String, String> getSongAssignments() {
		return songAssignments;
	}

	public void setSongAssignments(Map<String, String> songAssignments2) {
		this.songAssignments = songAssignments2;
	}
	
	public Map<String, Integer> getScores(){
		Map<String, Integer> scores = new HashMap<>();
		System.out.println("The scores are:");
		
		for( Person person :players){
			scores.put(person.getUserName(), person.getScore());
			System.out.println(person.getUserName() + person.getScore());
		}
		return scores;
	}
	
	
	
	public void addToScore(String name, int nbToAdd){
		for(Person person : players){
			if(person.getUserName().equals(name)){
				person.setScore(person.getScore()+nbToAdd);
			}
		}
	}
	
	//public Map<>
}
