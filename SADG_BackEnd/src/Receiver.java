import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;

public class Receiver implements IReceiver {
	private Connection connection;
	private Channel channel;
	
	public Receiver(String ipaddress) throws IOException, TimeoutException{
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(ipaddress);
		connection = factory.newConnection();
		channel = connection.createChannel();
		channel.queueDeclare("verify", false, false, false, null);
		
	}

	@Override
	public void verify(String id1, String id2) {
		// TODO Auto-generated method stub

	}

}
