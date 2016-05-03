package backEnd;

import java.util.Map;

public class MolMode extends GameMode {

	@Override
	public Map<String, String> assignSongs(SongPicker songPicker, GameState gameState) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public MatchType handleScore( String pers1, String pers2, GameState currentState) {
		int secondsPassed = (int) ((System.currentTimeMillis() - currentState.timeStarted) * 60);

		boolean pers1IsMol = currentState.getSongAssignments().get(pers1).equals("0"); //TODO verander naar echte molsongcode
		boolean pers2IsMol = currentState.getSongAssignments().get(pers2).equals("0");// niet zo mooie code sorry :(

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



}

