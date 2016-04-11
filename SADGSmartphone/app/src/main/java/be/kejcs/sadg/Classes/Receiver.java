package be.kejcs.sadg.Classes;

import android.util.Log;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * Created by Karen on 3/04/2016.
 */
public class Receiver implements IReceiver {
    private static final String EXCHANGE_NAME = "game_info";

    private static String STOP = "stop";
    private static String START = "start";
    private static String MUSIC = "music";
    private static String RESULTS = "results";
    private static String VERIFYRESULTS = "verifyResults";
    private static String USERNAME ="username";

    private Channel channel;
    private Connection connection;
    private String queueName;
    private String userId;
    private final CommunicationCenter communicationCenter;
    private ConnectionFactory connectionFactory;
    private Thread thread;

    public Receiver(ConnectionFactory connectionFactory, String userId,CommunicationCenter communicationCenter) {

        this.connectionFactory =connectionFactory;
        this.userId = userId;
        this.communicationCenter = communicationCenter;
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
        this.userId = s;
    }

    public void run(){
        this.thread.start();
    }




    public void setUpConnection()throws IOException, TimeoutException{


        connection = this.connectionFactory.newConnection();
        //Opstellen van channel
        channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");
        queueName = channel.queueDeclare().getQueue();


        List<String> keys = Arrays.asList(STOP,START,MUSIC,RESULTS,VERIFYRESULTS);
        for(String k:keys){
            channel.queueBind(queueName,EXCHANGE_NAME,userId + "."+k);
        }
        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                String key = envelope.getRoutingKey();
                Log.d("key", key);
                String [] splitted= key.split("\\.");
                Log.d("key", splitted.toString());

                key = splitted[1];
                if(key.equals(STOP)){
                    communicationCenter.stopRound();
                }else if(key.equals(START)){
                    communicationCenter.setStart(message);
                    communicationCenter.startRound();
                }else if(key.equals(VERIFYRESULTS)){

                }else if(key.equals(RESULTS)){

                }else if(key.equals(MUSIC)){
                    communicationCenter.setSong(message);
                }
                else if(key.equals(USERNAME)){
                    communicationCenter.changeUsername(message);
                }




                System.out.println("Received: " + envelope.getRoutingKey() + ": " + message + ".");
                //communicationCenter.setSong(music);
            }
        };

        channel.basicConsume(queueName, true, consumer);
    }


    public void receiveMusic() throws IOException {
        String designatedChannel = userId + "music";
        channel.queueBind(queueName, EXCHANGE_NAME, designatedChannel);
        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                String music = new String(body, "UTF-8");

                System.out.println("ReceivedReceived: " + envelope.getRoutingKey() + ": " + music + ".");
                //communicationCenter.setSong(music);
            }
        };
        channel.basicConsume(queueName, true, consumer);

    }




    public void startNewRound() throws IOException {
        String designatedChannel = userId + "time";
        channel.queueBind(queueName, EXCHANGE_NAME, designatedChannel);
        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                String time = new String(body, "UTF-8");
                //communicationCenter.setStart(time);
                System.out.println("ReceivedNewRound: " + envelope.getRoutingKey() + ": " + time + ".");
            }
        };
        channel.basicConsume(queueName, true, consumer);
    }


    public void stopRound() throws IOException {
        String designatedChannel = userId + "stop";
        channel.queueBind(queueName, EXCHANGE_NAME, designatedChannel);
        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                String stopMessage = new String(body, "UTF-8");

                System.out.println("ReceivedSTOP: " + envelope.getRoutingKey() + ": " + stopMessage + ".");
                //communicationCenter.stopRound();
            }
        };
        channel.basicConsume(queueName, true, consumer);
    }


    public void receiveWinnersOfRound() throws IOException {
        String designatedChannel = userId + "results";
        channel.queueBind(queueName, EXCHANGE_NAME, designatedChannel);
        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                String resultsRound = new String(body, "UTF-8");
                //TODO doorgeven aan een klasse
                System.out.println("ReceivedWINNER: " + envelope.getRoutingKey() + ": " + resultsRound + ".");
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
