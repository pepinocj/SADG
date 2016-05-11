package backEnd;
import java.io.IOException;
import java.util.List;
import java.util.Map;


public interface ISender {
	// Zend de link/index van de te spelen muziek naar de juiste gebruiker.
	// Gebruikers zijn al eerder doorgegeven in initiateRound(..). Link kan in de vorm van een
	// string zijn
	public abstract void sendMusic(Map<String,Integer> music);
	
	// Starten van een ronde aka start spelen van muziek over x aantal seconden. 
	//TODO uittesten of dat dan effectief tegelijk begint, anders absoluut tijdstip gebruiken
	public abstract void startRound(long timeToStart);
		
	//Als laatste nodige match is gevonden (vb na eerste drie matchen stop), zend een
	// signaal naar alle spelers dat deze ronde stopt, aka laat de muziek stoppen, scorebord
	// komt tevoorschijn?
	public abstract void stopRound() throws IOException;
	
	//Winner van de ronde announcen
	public abstract void announceWinner(String person);
	
	public abstract void reportMolVerification(String mol, String victim);
	
	public abstract void reportVerification(String id1, String id2, boolean stateOfSuccess);
	
	public abstract void chooseNewName(String id, String message);
	
	//Eenmaal spelen gedaan is: sluiten van communicatie
	public abstract void closeCommunication();
	
	//Lelijke fix voor consistentie
	public abstract void removeUser(String name);

	public void sendScoreBoard(Map<String, Integer> scores);
}
