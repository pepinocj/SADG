package be.kejcs.sadg.Classes;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.*;

/**
 * Created by Karen on 3/04/2016.
 */
public class Receiver implements IReceiver {
    private static final String EXCHANGE_NAME = "receive_user";

    private Channel channel;
    private Connection connection;
    private String queueName;

    public Receiver(String ipaddress) throws IOException, TimeoutException{
        ConnectionFactory factory = new ConnectionFactory();
        // Voor testen ipaddress = "localhost"
        factory.setHost(ipaddress);
        // Initialisatie van connection
        connection = factory.newConnection();
        //Opstellen van channel
        channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");
        queueName = channel.queueDeclare().getQueue();
    }

    @Override
    public void receiveMusic() throws IOException {

    }

    @Override
    public void startNewRound() throws IOException {

    }

    @Override
    public void stopRound() throws IOException {

    }

    @Override
    public void receiveWinnersOfRound() throws IOException {

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
