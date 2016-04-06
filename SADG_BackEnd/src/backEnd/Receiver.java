package backEnd;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.*;

public class Receiver implements IReceiver {

	private static final String EXCHANGE_NAME = "game_info";
	private static final List<String> keys = Arrays.asList("verify", "addPlayer"); 

	private ConnectionFactory factory;
	private Connection connection;
	private Channel channel;
	private String queueName;

	private Game game;

	public Thread receiveThread;


	public Receiver(String ipaddress, Game game){
		this.game = game;

		this.factory = new ConnectionFactory();
		this.factory.setHost(ipaddress);
		this.receiveThread = new Thread(new Runnable(){
			@Override
			public void run(){
				try {
					setupConnection();
				} catch (IOException | TimeoutException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					try {
						receive();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		});
	}

	@Override
	public void setupConnection() throws IOException, TimeoutException{
		connection = factory.newConnection();
		channel = connection.createChannel();
		//Routing tutorial
		channel.exchangeDeclare(EXCHANGE_NAME, "direct");
		queueName = channel.queueDeclare().getQueue();
		for(String key: keys){
			channel.queueBind(queueName, EXCHANGE_NAME, key);
		}
	}

	@Override
	public void receive() throws IOException{
		Consumer consumer = new DefaultConsumer(channel){
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope,
					AMQP.BasicProperties properties, byte[] body) throws IOException {
				String message = new String(body, "UTF-8");
				if(envelope.getRoutingKey().equals("verify")){
					String[] parts = message.split(",");
					System.out.println("Received: " + envelope.getRoutingKey() + "with names " + parts[0] + " and " + parts[1] + ".");
					game.verifyMatch(parts[0], parts[1]);
				}
				if(envelope.getRoutingKey().equals("addPlayer")){
					System.out.println("Received: " + envelope.getRoutingKey() + ": " + message + ".");
					game.addPlayer(message);
				}
				else{
					System.out.println("error!!!");
				}
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
