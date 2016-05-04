package be.kejcs.sadg.Classes;

import android.util.Log;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private static String SYSTEMTIME ="systemTime";

    private Channel channel;
    private Connection connection;
    private String queueName;
    private String userId;
    private final CommunicationCenter communicationCenter;
    private ConnectionFactory connectionFactory;
    private Thread thread;

    private void resetThread(){
        Thread t  = new Thread(new Runnable() {
            @Override
            public void run() {
                //  while(true){
                try{
                    setUpConnection();
                }catch (IOException | TimeoutException e){
                    e.printStackTrace();
                }
                // }
            }
        });


        this.thread = t;

    }

    public Receiver(ConnectionFactory connectionFactory, String userId,CommunicationCenter communicationCenter) {

        this.connectionFactory =connectionFactory;
        this.userId = userId;
        this.communicationCenter = communicationCenter;
        //resetThread();

    }
    
    public void resetUserID(String s){
        this.userId = s;
    }

    public void run(){
            resetThread();
            this.thread.start();


    }




    public void setUpConnection()throws IOException, TimeoutException{

        if(connection != null){
            connection.close();

        }
        connection = this.connectionFactory.newConnection();
        //Opstellen van channel
        channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");
        queueName = channel.queueDeclare().getQueue();


        List<String> keys = Arrays.asList(STOP,START,MUSIC,RESULTS,VERIFYRESULTS,SYSTEMTIME);
        for(String k:keys){
            channel.queueBind(queueName,EXCHANGE_NAME,userId + "."+k);
        }
        channel.queueBind(queueName, EXCHANGE_NAME, "broadCastStart"); //TODO BROADCAST

        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                String key = envelope.getRoutingKey();

                if(key.equals("broadCastStart")){
                    communicationCenter.startRound();//TODO BROADCAST
                }else if(key.equals("scores")) {
                    // Map<PlayerName,ScoreInString> players scores
                    Map<String, String> playersScores = new HashMap<String, String>();
                    for(String playerAndScore : message.split("/")){
                        String[] playerScoreDivided = message.split(",");
                        playersScores.put(playerScoreDivided[0],playerScoreDivided[1]);
                    }
                    communicationCenter.receiveScores(playersScores);
                }
                else {

                    String[] splitted = key.split("\\.");


                    key = splitted[1];
                    if (key.equals(STOP)) {
                        communicationCenter.stopRound();
                    } else if (key.equals(START)) {
                        communicationCenter.setStart(message);
                        communicationCenter.startRound();
                    } else if (key.equals(VERIFYRESULTS)) {
                        communicationCenter.handleVerifyResults(message);
                    } else if (key.equals(RESULTS)) {
                        communicationCenter.handleResults(message);
                    } else if (key.equals(USERNAME)) {
                        communicationCenter.changeUsername(message);
                    } else if (key.equals(MUSIC)) {
                        communicationCenter.setSong(message);
                    } else if (key.equals(SYSTEMTIME)) {

                        communicationCenter.setSystemTime(message);
                    }
                }
            }





                //communicationCenter.setSong(music);

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
