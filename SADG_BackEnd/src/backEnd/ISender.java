package backEnd;
import java.io.IOException;
import java.util.List;
import java.util.Map;


public interface ISender {
	
	//TODO in receiver een addplayer? en een removeplayer en er zo een mini interface moeten zijn om genre 
	//en difficulty te kiezen (en score op te tonen), maar das voor later
	
	// Zend de link/index van de te spelen muziek naar de juiste gebruiker.
	// Gebruikers zijn al eerder doorgegeven in initiateRound(..). Link kan in de vorm van een
	// string zijn
	public abstract void sendMusic(Map<String,String> music) throws IOException;
	
	// Starten van een ronde aka start spelen van muziek over x aantal seconden. 
	//TODO uittesten of dat dan effectief tegelijk begint, anders absoluut tijdstip gebruiken
	public abstract void startRound(long timeToStart) throws IOException;
	
	//Als laatste nodige match is gevonden (vb na eerste drie matchen stop), zend een
	// signaal naar alle spelers dat deze ronde stopt, aka laat de muziek stoppen, scorebord
	// komt tevoorschijn?
	public abstract void stopRound() throws IOException;
	
	//Winner van de ronde announcen
	public abstract void announceWinner(String person) throws IOException;
	
	public abstract void reportVerification(String id1, String id2, boolean stateOfSuccess) throws IOException;
	
	//Eenmaal spelen gedaan is: sluiten van communicatie
	public abstract void closeCommunication();
	
}
