package backEnd;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;


public class Sender implements ISender {
	private static final String EXCHANGE_NAME = "game_info";

	private Channel channel;
	private Connection connection;
	private List<String> userIds;
	
	public Sender(String ipaddress) throws IOException, TimeoutException{
		ConnectionFactory factory = new ConnectionFactory();
		// Voor testen ipaddress = "localhost"
		factory.setHost(ipaddress);
		// Initialisatie van connection
		connection = factory.newConnection();
		//Opstellen van channel
		channel = connection.createChannel();
		channel.exchangeDeclare(EXCHANGE_NAME, "direct");
		userIds = new ArrayList<String>();
	}

	public void removeUser(String name){
		for(int i=0; i<userIds.size(); i++){
			if(userIds.get(i).equals(name)){
				userIds.remove(i);
			}
		}
	}
	
	@Override
	public void sendMusic(Map<String, Integer> music){ //username en songname 
		for(Map.Entry<String, Integer> entry: music.entrySet()){
			String designatedChannel = entry.getKey() + ".music";
			//channel.basicPublish(EXCHANGE_NAME, designatedChannel, null, (Integer.toString(entry.getValue())).getBytes());
			sendMessage(designatedChannel, Integer.toString(entry.getValue()));
			userIds.add(entry.getKey());
		}
	}
	
	private void sendMessage(String designatedChannel, String message){
		try {
			channel.basicPublish(EXCHANGE_NAME, designatedChannel, null, message.getBytes());
		} catch (IOException e) {
			 System.out.println("Failed to send message:" + message + " to channel: " + designatedChannel);
			 sendMessage(designatedChannel, message);
		}
	}

	@Override
	public void startRound(long time) {
		String timeToString = String.valueOf(time);
		//		for(String id: userIds){ //TODO
		//			String designatedChannel = id + ".start";
		//			channel.basicPublish(EXCHANGE_NAME, designatedChannel, null, timeToString.getBytes());
		//		}

		try {
			channel.basicPublish(EXCHANGE_NAME, "broadCastStart", null, timeToString.getBytes());
		} catch (IOException e) {
			System.out.println("Failed to start new round. Trying again.");
			startRound(time);
		}
	}

	@Override
	public void stopRound(){
		String timeToStop = "stop";
		for(String id: userIds){
			String designatedChannel = id + ".stop";
			sendMessage(designatedChannel, timeToStop);
			//channel.basicPublish(EXCHANGE_NAME, designatedChannel, null, timeToStop.getBytes());
		}
	}

	@Override
	public void announceWinner(String person) {
		String congrats = "Congratulations! You won this round!";
		String nope = "The round is finished, but you are not first. Better luck next time!";
		for(String id: userIds){
			if(person == id ){
				String designatedChannel = id + ".results";
				sendMessage(designatedChannel, congrats);
				//channel.basicPublish(EXCHANGE_NAME, designatedChannel, null, congrats.getBytes());
			}
			else{
				String designatedChannel = id + ".results";
				sendMessage(designatedChannel, nope);
				//channel.basicPublish(EXCHANGE_NAME, designatedChannel, null, nope.getBytes());
			}
		}
	}

	@Override
	public void sendScoreBoard(Map<String, Integer> scores){
		String giantString = "";
		for (Map.Entry<String, Integer> entry : scores.entrySet()){
			giantString += entry.getKey() + "," + entry.getValue() + "/"; 
		}
		try {
			channel.basicPublish(EXCHANGE_NAME, "scores", null, giantString.getBytes());
		} catch (IOException e) {
			System.out.println("Failed to send scoreboard. Trying again.");
			sendScoreBoard(scores);
		}
	}
	
	@Override
	public void closeCommunication() {
		try {
			userIds.clear();
			channel.close();
			connection.close();
		} catch (IOException | TimeoutException e) {
			System.out.println("Failed to close communication. Trying again.");
			closeCommunication();
		}
	}

	@Override
	public void reportVerification(String id1, String id2,
			boolean stateOfSuccess) {
		if(stateOfSuccess){
			String congrats = "Congrats, you found your partner!";
			String designatedChannel1 = id1 + ".verifyResults";
			String designatedChannel2 = id2 + ".verifyResults";
			
			try {
				channel.basicPublish(EXCHANGE_NAME, designatedChannel1, null, congrats.getBytes());
				channel.basicPublish(EXCHANGE_NAME, designatedChannel2, null, congrats.getBytes());
			} catch (IOException e) {
				System.out.println("Failed to send verification to the users: " + id1+ " and " + id2 +". Trying again.");
				reportVerification(id1, id2, stateOfSuccess);
			}

		}
		else{
			String oeps = "This is not the right partner!";
			String designatedChannel1 = id1 + ".verifyResults";
			String designatedChannel2 = id2 + ".verifyResults";
			try {
				channel.basicPublish(EXCHANGE_NAME, designatedChannel1, null, oeps.getBytes());
				channel.basicPublish(EXCHANGE_NAME, designatedChannel2, null, oeps.getBytes());
			} catch (IOException e) {
				System.out.println("Failed to send verification to the users: " + id1+ " and " + id2 +". Trying again.");
				reportVerification(id1, id2, stateOfSuccess);
			}	
		}
	}

	@Override
	public void reportMolVerification(String mol, String victim) {
		String congrats = "Congrats, you deceived someone";
		String designatedChannel1 = mol + ".verifyResults";
		String oeps = "You were fooled!";
		String designatedChannel2 = victim + ".verifyResults";
		try {
			channel.basicPublish(EXCHANGE_NAME, designatedChannel1, null, congrats.getBytes());
			channel.basicPublish(EXCHANGE_NAME, designatedChannel2, null, oeps.getBytes());
		} catch (IOException e) {
			System.out.println("Failed to send mol verification to the users: " + mol + " and " + victim +". Trying again.");
			reportMolVerification(mol, victim);
		}	
	}

	@Override
	public void chooseNewName(String id) {
		String message = "Please chose another username.";
		String designatedKey = id + ".username";
		try {
			channel.basicPublish(EXCHANGE_NAME, designatedKey, null, message.getBytes());
		} catch (IOException e) {
			System.out.println("Failed to send request for changing name to user: " + id +". Trying again.");
			chooseNewName(id);
		}
	}

}
