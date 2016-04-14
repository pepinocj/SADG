package backEnd;

public class ScoreHandler {
	
	public ScoreHandler(){
		
	}

	public MatchType handleScore(GameMode gameMode, String pers1, String pers2, GameState currentState) {
		return gameMode.handleScore(pers1, pers2, currentState);
	}

}
