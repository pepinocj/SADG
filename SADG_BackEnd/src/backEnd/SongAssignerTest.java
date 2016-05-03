package backEnd;

import static org.junit.Assert.*;

import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;

public class SongAssignerTest {

	@Test
	public void regularAssignTest() {
		SongAssigner sass = new SongAssigner();
		GameMode gameMode = new RegularMode();
		GameState gameState = new GameState();
		gameState.addPlayer(new Person("Jos"));
		gameState.addPlayer(new Person("Mark"));
		gameState.addPlayer(new Person("Jef"));
		gameState.addPlayer(new Person("Nancy"));
		gameState.addPlayer(new Person("Martine"));
		gameState.addPlayer(new Person("Cyriel"));

		Map<String, Integer> songs = sass.assignSongs(gameMode, gameState);
		for (Entry<String, Integer> entry : songs.entrySet())
		{
		    System.out.println(entry.getKey() + "   " + entry.getValue());
		}
	}
	
	@Test
	public void oneAssignTest() {
		System.out.println("enkel jos test");
		SongAssigner sass = new SongAssigner();
		GameMode gameMode = new MolMode();
		GameState gameState = new GameState();
		gameState.addPlayer(new Person("Jos"));
		Map<String, Integer> songs = sass.assignSongs(gameMode, gameState);
		for (Entry<String, Integer> entry : songs.entrySet())
		{
		    System.out.println(entry.getKey() + "   " + entry.getValue());
		}
	}
	
	@Test
	public void molAssignTest() {
		System.out.println("moltest");
		SongAssigner sass = new SongAssigner();
		GameMode gameMode = new MolMode();
		GameState gameState = new GameState();
		gameState.addPlayer(new Person("Jos"));
		gameState.addPlayer(new Person("Mark"));
		gameState.addPlayer(new Person("Jef"));
		gameState.addPlayer(new Person("Nancy"));
		gameState.addPlayer(new Person("Martine"));

		Map<String, Integer> songs = sass.assignSongs(gameMode, gameState);
		for (Entry<String, Integer> entry : songs.entrySet())
		{
		    System.out.println(entry.getKey() + "   " + entry.getValue());
		}
	}

}
