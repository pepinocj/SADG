package be.kejcs.sadg.Classes;

/**
 * Created by Josi on 4/04/2016.
 */
public class Player {
    public static final String IP_ADRESS = "10.0.3.2";
    public int id;
    public String name;
    public String ip;
    public Player(String name, String ip){
        this.name = name;
        this.id = 0;
        this.ip = ip;
    }
}
