package be.kejcs.sadg.Classes;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by Karen on 3/04/2016.
 */
public class Receiver implements IReceiver {
    private static final String EXCHANGE_NAME = "game_info";

    private Channel channel;
    private Connection connection;
    private String queueName;
    private String userId;
    private DanceGame danceGame;

    public Receiver(String ipaddress, String userId,DanceGame danceGame) throws IOException, TimeoutException{
        ConnectionFactory factory = new ConnectionFactory();
        // Voor testen ipaddress = "localhost"
        factory.setHost(ipaddress);
        // Initialisatie van connection
        connection = factory.newConnection();
        //Opstellen van channel
        channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");
        queueName = channel.queueDeclare().getQueue();
        this.userId = userId;
        this.danceGame = danceGame;
    }

    @Override
    public void receiveMusic() throws IOException {
        String designatedChannel = userId + "music";
        channel.queueBind(queueName, EXCHANGE_NAME, designatedChannel);
        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                String music = new String(body, "UTF-8");
                //TODO doorgeven aan een klasse
                System.out.println("Received: " + envelope.getRoutingKey() + ": " + music + ".");
            }
        };
        channel.basicConsume(queueName, true, consumer);
    }

    @Override
    public void startNewRound() throws IOException {
        String designatedChannel = userId + "time";
        channel.queueBind(queueName, EXCHANGE_NAME, designatedChannel);
        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                String time = new String(body, "UTF-8");
                //TODO doorgeven aan een klasse
                System.out.println("Received: " + envelope.getRoutingKey() + ": " + time + ".");
            }
        };
        channel.basicConsume(queueName, true, consumer);
    }

    @Override
    public void stopRound() throws IOException {
        String designatedChannel = userId + "stop";
        channel.queueBind(queueName, EXCHANGE_NAME, designatedChannel);
        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                String stopMessage = new String(body, "UTF-8");
                //TODO doorgeven aan een klasse
                System.out.println("Received: " + envelope.getRoutingKey() + ": " + stopMessage + ".");
            }
        };
        channel.basicConsume(queueName, true, consumer);
    }

    @Override
    public void receiveWinnersOfRound() throws IOException {
        String designatedChannel = userId + "results";
        channel.queueBind(queueName, EXCHANGE_NAME, designatedChannel);
        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                String resultsRound = new String(body, "UTF-8");
                //TODO doorgeven aan een klasse
                System.out.println("Received: " + envelope.getRoutingKey() + ": " + resultsRound + ".");
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
