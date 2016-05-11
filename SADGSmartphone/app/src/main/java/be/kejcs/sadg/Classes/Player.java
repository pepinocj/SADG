package be.kejcs.sadg.Classes;

/**
 * Created by Josi on 4/04/2016.
 */
public class Player {
    public static final String IP_ADRESS =  "192.168.43.184";// "192.168.0.105";// "192.168.43.184";
    public int id;
    public String name;
    public String ip;
    public String key;
    public Player(String name, String ip,String android_id){
        this.name = name;
        this.id = 0;
        this.ip = ip;
        this.key = android_id;
    }
}
