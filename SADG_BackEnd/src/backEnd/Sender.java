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
	private static final String EXCHANGE_NAME = "send_info";

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
			channel.basicPublish(EXCHANGE_NAME, entry.getKey(), null, entry.getValue().getBytes());
			userIds.add(entry.getKey());
		}
	}

	@Override
	public void startRound(long time) throws IOException {
		String timeToString = String.valueOf(time);
		for(String id: userIds){
			channel.basicPublish(EXCHANGE_NAME, id, null, timeToString.getBytes());
		}
	}

	@Override
	public void stopRound() throws IOException {
		String timeToStop = "stop";
		for(String id: userIds){
			channel.basicPublish(EXCHANGE_NAME, id, null, timeToStop.getBytes());
		}
	}

	@Override
	public void announceWinner(Person p1, Person p2) throws IOException {
		String congrats = "Congratulations! You won this round!";
		String nope = "Better luck in the next round!";
		for(String id: userIds){
			if(p1.getUserName() == id || p2.getUserName() == id){
				channel.basicPublish(EXCHANGE_NAME, id, null, congrats.getBytes());
			}
			else{
				channel.basicPublish(EXCHANGE_NAME, id, null, nope.getBytes());
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

}
