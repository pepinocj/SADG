package backEnd;
import java.io.IOException;
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
	

	public Sender(String ipaddress) throws IOException, TimeoutException{
		ConnectionFactory factory = new ConnectionFactory();
	    // Voor testen ipaddress = "localhost"
		factory.setHost(ipaddress);
	    // Initialisatie van connection
	    connection = factory.newConnection();
	    //Opstellen van channel
	    channel = connection.createChannel();
	    channel.exchangeDeclare(EXCHANGE_NAME, "direct");
	}

	@Override
	public void sendMusic(Map<String, String> music) throws IOException { //username en songname 
		for(Map.Entry<String, String> entry: music.entrySet()){
			channel.basicPublish(EXCHANGE_NAME, entry.getKey(), null, entry.getValue().getBytes());
		}
	}

	@Override
	public void startRound(long time) {
		// TODO Auto-generated method stub

	}

	@Override
	public void stopRound() {
		// TODO Auto-generated method stub

	}

	@Override
	public void announceWinner(Person p1, Person p2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void closeCommunication() {
		// TODO Auto-generated method stub
		try {
			channel.close();
			connection.close();
		} catch (IOException | TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
