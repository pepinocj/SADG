import java.util.List;
import java.util.Map;


public interface Sender {

	// Starten van een nieuwe spelronde waarbij de mogelijkheid er is om elke speelronde
	// met een subset of een nieuwe set van peeps te spelen. 
	public abstract void initiateRound(List<Person> persons);
	
	// Zend de link/index van de te spelen muziek naar de juiste gebruiker.
	// Gebruikers zijn al eerder doorgegeven in initiateRound(..). Link kan in de vorm van een
	// string zijn
	public abstract void sendMusic(Map<Person,Link> music);
	
	// Starten van een ronde aka start spelen van muziek over x aantal seconden. 
	public abstract void startRound(int seconds);
	
	//Als laatste nodige match is gevonden (vb na eerste drie matchen stop), zend een
	// signaal naar alle spelers dat deze ronde stopt, aka laat de muziek stoppen, scorebord
	// komt tevoorschijn?
	public abstract void stopRound();
	
	//De twee mensen feliciteren die als eerste elkaar gevonden hebben?? Optioneel
	public abstract void announceWinner(Person p1, Person p2);
	
}
