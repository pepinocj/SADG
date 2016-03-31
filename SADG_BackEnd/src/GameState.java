import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class GameState {

	//persons toch hier want bij handlescore moet ge de score vd persons kunnen aanpassen
	ArrayList<Person> players;
	Map<Person, String> songAssignments;
	long timeStarted;
	
	public GameState() {
		players = new ArrayList<Person>();
	}
	

	public void addPlayer(Person person){
		players.add(person);
	}
	
	public ArrayList<Person> getPlayers(){
		return players;
	}
	
	public void setStartTime(long timeToStart){
		this.timeStarted = timeToStart;
	}

	public Map<Person, String> getSongAssignments() {
		return songAssignments;
	}

	public void setSongAssignments(Map<Person, String> songAssignments) {
		this.songAssignments = songAssignments;
	}
	
	public Map<String, Integer> getScores(){
		Map<String, Integer> scores = Collections.emptyMap();
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
}
