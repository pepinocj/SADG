
public class Person {
	
	private String userName;
	private int score = 0;
	
	public Person( String userName){
		this.userName = userName;
		
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	public String getUserName(){
		return userName;
	}

}
