package backEnd;

public class Person implements Comparable<Person>{
	
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
		return this.userName.split(":")[1];
	}

	public boolean equals(Object obj){
		if(obj instanceof Person)
		{
		   Person otherPerson = (Person) obj;
		   String thisName = this.userName.split(":")[1];
		   String otherName = otherPerson.userName.split(":")[1];

		   if(thisName.equals(otherName)){return true;}
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

	public String getQueue() {
		return this.userName.split(":")[0];
	}
}
