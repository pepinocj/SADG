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
	//TODO slecht want om userIds correct geupdate te houden, moet
	// Game tegen ons zeggen wanneer via Sender een user zegt van: remove mij
	// Alternatief elke keer lijst van persons doorgeven wat ook maar meh is
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

	@Override
	public void sendMusic(Map<String, String> music) throws IOException { //username en songname 
		for(Map.Entry<String, String> entry: music.entrySet()){
			String designatedChannel = entry.getKey() + "music";
			channel.basicPublish(EXCHANGE_NAME, designatedChannel, null, entry.getValue().getBytes());
			userIds.add(entry.getKey());
		}
	}

	@Override
	public void startRound(long time) throws IOException {
		String timeToString = String.valueOf(time);
		for(String id: userIds){
			String designatedChannel = id + "start";
			channel.basicPublish(EXCHANGE_NAME, designatedChannel, null, timeToString.getBytes());
		}
	}

	@Override
	public void stopRound() throws IOException {
		String timeToStop = "stop";
		for(String id: userIds){
			String designatedChannel = id + "stop";
			channel.basicPublish(EXCHANGE_NAME, designatedChannel, null, timeToStop.getBytes());
		}
	}

	@Override
	public void announceWinner(String person) throws IOException {
		String congrats = "Congratulations! You won this round!";
		String nope = "The round is finished, but you are not first. Better luck next time!";
		for(String id: userIds){
			if(person == id ){
				String designatedChannel = id + "results";
				channel.basicPublish(EXCHANGE_NAME, designatedChannel, null, congrats.getBytes());
			}
			else{
				String designatedChannel = id + "results";
				channel.basicPublish(EXCHANGE_NAME, designatedChannel, null, nope.getBytes());
			}
		}
	}

	@Override
	public void closeCommunication() {
		// TODO Auto-generated method stub
		try {
			userIds.clear();
			channel.close();
			connection.close();
		} catch (IOException | TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void reportVerification(String id1, String id2,
			boolean stateOfSuccess) throws IOException {
		if(stateOfSuccess){
			String congrats = "Congrats, you found your partner!";
			String designatedChannel1 = id1 + "verifyResults";
			String designatedChannel2 = id2 + "verifyResults";
			channel.basicPublish(EXCHANGE_NAME, designatedChannel1, null, congrats.getBytes());
			channel.basicPublish(EXCHANGE_NAME, designatedChannel2, null, congrats.getBytes());
			
		}
		else{
			String oeps = "This is not the right partner!";
			String designatedChannel1 = id1 + "verifyResults";
			String designatedChannel2 = id2 + "verifyResults";
			channel.basicPublish(EXCHANGE_NAME, designatedChannel1, null, oeps.getBytes());
			channel.basicPublish(EXCHANGE_NAME, designatedChannel2, null, oeps.getBytes());
		}
	}

}
