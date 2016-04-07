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
    private ConnectionFactory connectionFactory;
    private Thread thread;

    public Sender(ConnectionFactory connectionFactory, String user) {
       this.connectionFactory = connectionFactory;
        idUser = user;
        this.thread = new Thread(new Runnable() {
            @Override
            public void run() {

                    try{
                        setUpConnection();

                    }catch (IOException | TimeoutException e){
                        e.printStackTrace();
                    }

            }
        });
    }

    public void resetUserID(String s){
        this.idUser = s;
    }

    public void setUpConnection()throws IOException, TimeoutException{

        connection = this.connectionFactory.newConnection();
        //Opstellen van channel
        channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");

    }

    public void run(){
        this.thread.start();
    }

    @Override
    public void sendVerify(String id1, String id2) throws IOException {
        String message = id1+","+id2;
        channel.basicPublish(EXCHANGE_NAME, "verify", null, message.getBytes());
    }

    @Override
    public void beginAsPlayer(String id) throws IOException {
        if (!idUser.equals(id)) {
            idUser = id;
        }
        channel.basicPublish(EXCHANGE_NAME, "addPlayer", null, id.getBytes());
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
