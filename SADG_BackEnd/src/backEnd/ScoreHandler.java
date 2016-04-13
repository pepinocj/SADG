package backEnd;

public class ScoreHandler {
	
	public ScoreHandler(){
		
	}

	public boolean handleScore(GameMode gameMode, String pers1, String pers2, GameState currentState) {
		return regularMatch(pers1, pers2, currentState); 
	}
	
	private boolean regularMatch(String pers1, String pers2, GameState currentState) {
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

	public MatchType handleScore(MolMode gameMode, String pers1, String pers2, GameState currentState) {
		int secondsPassed = (int) ((System.currentTimeMillis() - currentState.timeStarted) * 60);

		boolean pers1IsMol = currentState.getSongAssignments().get(pers1).equals("MolSong"); //TODO verander naar echte molsongcode
		boolean pers2IsMol = currentState.getSongAssignments().get(pers2).equals("MolSong");// niet zo mooie code sorry :(

		if(pers1IsMol || pers2IsMol){
			System.out.println("Successful molmatch!");
			String mol = "";
			String victim = "";
			if(pers1IsMol){ mol = pers1; victim = pers2;}
			else{ mol = pers2; victim = pers1;}
			currentState.addToScore(mol, secondsPassed); //TODO eventueel score systeem hier aanpassen
			currentState.addToScore(victim, -secondsPassed);
			return MatchType.MOLMATCH;

		}
		
		boolean success = regularMatch(pers1, pers2, currentState);
		if(success){return MatchType.SUCCESS;}
		else{return MatchType.FAIL;}	
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
