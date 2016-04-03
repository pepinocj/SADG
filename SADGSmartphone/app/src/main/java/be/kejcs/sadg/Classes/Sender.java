package be.kejcs.sadg.Classes;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by Karen on 3/04/2016.
 */
public class Sender implements  ISender{
    private static final String EXCHANGE_NAME = "receive_user";

    private Channel channel;
    private Connection connection;

    private String idUser;

    public Sender(String ipAdress, String user) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        // Voor testen ipaddress = "localhost"
        factory.setHost(ipaddress);
        // Initialisatie van connection
        connection = factory.newConnection();
        //Opstellen van channel
        channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");
        idUser = user;
    }
    @Override
    public void sendVerify(String id1, String id2) throws IOException {

    }

    @Override
    public void beginAsPlayer(String id) throws IOException {

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
