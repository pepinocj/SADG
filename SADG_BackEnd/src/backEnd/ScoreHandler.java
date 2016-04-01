package backEnd;

public class ScoreHandler {
	
	public ScoreHandler(){
		
	}

	public boolean handleScore(GameMode gameMode, String pers1, String pers2, GameState currentState) {
		boolean isMatch = checkMatch(pers1, pers2, currentState);
		int secondsPassed = (int) ((System.currentTimeMillis() - currentState.timeStarted) * 60);
		if(isMatch){
			System.out.println("Congratulations! It's a match");
			currentState.addToScore(pers1, secondsPassed);
			currentState.addToScore(pers2, secondsPassed);
			}
		else{
			System.out.println("Too bad, not a match!");
			currentState.addToScore(pers1, -secondsPassed);
			currentState.addToScore(pers2, -secondsPassed);
			}
		
		return isMatch;
	}
	
	public boolean handleScore(MolMode gameMode, String pers1, String pers2, GameState currentState) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	public boolean handleScore(TeamMode gameMode, String pers1, String pers2, GameState currentState) {
		// TODO Auto-generated method stub
		return false;
	}
	
	private boolean checkMatch(String pers1, String pers2, GameState currentState){
		return (currentState.songAssignments.get(pers1) == (currentState.songAssignments.get(pers2)));
	}
	
	public boolean handleScore(GameMode gameMode, int pers1, int pers2, GameState currentState) {
		//oei overloading
		System.out.println("hoe doet ge ook al weer aan mooi programmeren?");
		return false;
	}

}
