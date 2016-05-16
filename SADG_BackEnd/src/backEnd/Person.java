package backEnd;

public class Person implements Comparable<Person>{
	
	private String userName;
	private String androidId;
	private int score = 0;
	
	public Person( String userName, String androidId){
		this.userName = userName;
		this.androidId = androidId;
		
	}

	public int getScore() {
		return score;
	}

	public String getAndroidId(){
		return androidId;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
	public String getUserName(){
		return userName;
	}

	public boolean equals(Object obj){
		if(obj instanceof Person)
		{
		   Person otherPerson = (Person) obj;
		   if(this.userName.equals(otherPerson.userName)){return true;}
		   else {return false;}
		}
		else
		{
			return false;
		}

	}

	@Override
	public int compareTo(Person comparePerson) {
		int compareScore = comparePerson.getScore();
		//descending order
		return this.getScore() - compareScore;
	}
}
