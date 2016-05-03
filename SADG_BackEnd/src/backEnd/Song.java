package backEnd;

public class Song {
	//dance gaat van 0 tot 2 waarbij 0 geen distinctive dance is en 2 wel , en 1 kinda
	private int distDance;
	private int id;
	private int startAt;
//	private String title;
//	private String artist;
//	private String genre;
//	private float bpm;
	
	//add genre en bpm voor filtering, en add artist en title wanneer we songs willen displayen. title
	public Song(int id, int distDance, int startAt){
		this.id = id;
		this.distDance = distDance;
		this.startAt = startAt;
	}

	public int getId(){ return id; }
	public int getDance() {return distDance; }
	public int getStartAt(){return startAt;}

}
