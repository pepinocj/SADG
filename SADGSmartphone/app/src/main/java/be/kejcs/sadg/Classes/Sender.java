package be.kejcs.sadg.Classes;

import android.os.Looper;
import android.util.Log;

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
    private Player player;
    private ConnectionFactory connectionFactory;
    private CommunicationCenter communicationCenter;
    private Thread thread;

    private void resetThread(){
        Thread t  = new Thread(new Runnable() {
            @Override
            public void run() {
                //  while(true){
                try{
                    setUpConnection();
                }catch (IOException | NoConnectionMadeException| TimeoutException e){
                    e.printStackTrace();
                    Looper.prepare();
                    communicationCenter.signalCommunicationException();
                }
                // }
            }
        });


           // this.thread.interrupt();
            this.thread = t;

    }

    public Sender(ConnectionFactory connectionFactory, Player player,CommunicationCenter communicationCenter) {
       this.connectionFactory = connectionFactory;
        this.player = player;
        idUser = player.key;
        this.communicationCenter = communicationCenter;
        //resetThread();
    }

    public void resetUserID(String s){
        this.idUser = s;
    }

    public void setUpConnection()throws IOException, TimeoutException, NoConnectionMadeException{
        if(connection != null && !connection.isOpen()){
            connection.close();

        }
        this.connectionFactory.setHost(player.ip);
        connection = this.connectionFactory.newConnection();
        //Opstellen van channel
        channel = connection.createChannel();

        if(channel == null){
            throw new NoConnectionMadeException();
        }
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");

    }

    public void run(){


            resetThread();
            this.thread.start();





    }

    @Override
    public void sendVerify(String id1, String id2) throws IOException, NoConnectionMadeException {
        String message = id1+","+id2;
        if(channel == null|| !connection.isOpen()){
            throw new NoConnectionMadeException();
        }
        channel.basicPublish(EXCHANGE_NAME, "verify", null, message.getBytes());
    }

    @Override
    public void beginAsPlayer(String id) throws IOException, NoConnectionMadeException {

        if(channel == null || !connection.isOpen()){
            throw new NoConnectionMadeException();
        }

        channel.basicPublish(EXCHANGE_NAME, "addPlayer", null, (idUser+":"+id).getBytes());
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

        @Override
        public void removeAsPlayer(String id) throws IOException, NoConnectionMadeException {
            if(channel == null|| !connection.isOpen()){
                throw new NoConnectionMadeException();
            }
                Log.d("REMOVE", "REMOVING AS PLAYER");
                channel.basicPublish(EXCHANGE_NAME,"removePlayer", null, id.getBytes());
            }
}
