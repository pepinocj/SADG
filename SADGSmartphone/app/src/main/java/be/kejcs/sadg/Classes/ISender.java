package be.kejcs.sadg.Classes;

import java.io.IOException;

/**
 * Created by Karen on 3/04/2016.
 */
public interface ISender {

    public abstract void sendVerify(String id1, String id2)throws IOException;

    public abstract void beginAsPlayer(String id) throws IOException;

    public abstract void removeAsPlayer(String id) throws IOException;

    public abstract void closeCommunication();

}
