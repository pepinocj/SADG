package backEnd;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.*;

public class Receiver implements IReceiver {

	private static final String EXCHANGE_NAME = "game_info";

	private Connection connection;
	private Channel channel;
	private String queueName;

	private Game game;

	public Receiver(String ipaddress, Game game) throws IOException, TimeoutException{
		this.game = game;

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(ipaddress);
		connection = factory.newConnection();
		channel = connection.createChannel();

		//Routing tutorial
		channel.exchangeDeclare(EXCHANGE_NAME, "direct");
		queueName = channel.queueDeclare().getQueue();

	}

	@Override
	public void verify() throws IOException {
		channel.queueBind(queueName, EXCHANGE_NAME, "verify");
		Consumer consumer = new DefaultConsumer(channel){
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope,
					AMQP.BasicProperties properties, byte[] body) throws IOException {
				String two_names = new String(body, "UTF-8");
				String[] parts = two_names.split(",");
				game.verifyMatch(parts[0], parts[1]);
				System.out.println("Received: " + envelope.getRoutingKey() + "with names " + parts[0] + " and " + parts[1] + ".");
			}
		};
		channel.basicConsume(queueName, true, consumer);

	}

	@Override
	public void newPlayer() throws IOException {
		channel.queueBind(queueName, EXCHANGE_NAME, "addPlayer");
		Consumer consumer = new DefaultConsumer(channel){
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope,
					AMQP.BasicProperties properties, byte[] body) throws IOException {
				String name = new String(body, "UTF-8");
				game.addPlayer(name);
				System.out.println("Received: " + envelope.getRoutingKey() + ": " + name + ".");
			}
		};
		channel.basicConsume(queueName, true, consumer);

	}

	@Override
	public void closeCommunication() {
		try {
			channel.close();
			connection.close();
		} catch (IOException | TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
