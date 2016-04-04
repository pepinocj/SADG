package be.kejcs.sadg.Classes;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by Karen on 3/04/2016.
 */
public class Sender implements  ISender{
    private static final String EXCHANGE_NAME = "game_info";

    private Channel channel;
    private Connection connection;

    private String idUser;

    public Sender(String ipAdress, String user) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        // Voor testen ipaddress = "localhost"
        factory.setHost(ipAdress);
        // Initialisatie van connection
        connection = factory.newConnection();
        //Opstellen van channel
        channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");
        idUser = user;
    }
    @Override
    public void sendVerify(String id1, String id2) throws IOException {
        String message = id1+","+id2;
        channel.basicPublish(EXCHANGE_NAME, "verify", null, message.getBytes());
    }

    @Override
    public void beginAsPlayer(String id) throws IOException {
        if (idUser == id) {
            channel.basicPublish(EXCHANGE_NAME, "addPlayer", null, id.getBytes());
        }
        else{
            idUser = id;
        }
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
