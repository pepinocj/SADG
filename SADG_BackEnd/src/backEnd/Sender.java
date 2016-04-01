package backEnd;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

public class Sender implements ISender {
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
	    //Declaraties van alle queues
	    channel.queueDeclare("startRound", false, false, false, null);
	    channel.queueDeclare("musicQueue", false, false, false, null);
	    channel.queueDeclare("initRound", false, false, false, null);
	    channel.queueDeclare("stopRound", false, false, false, null);
	}
	
	@Override
	public void initiateRound(List<Person> persons) {
		// TODO Auto-generated method stub

	}

	@Override
	public void sendMusic(Map<String, String> music) { //username en songname 
		// TODO Auto-generated method stub

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

}
