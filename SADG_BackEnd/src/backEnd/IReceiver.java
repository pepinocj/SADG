package backEnd;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public interface IReceiver {
	
	public abstract void setupConnection() throws IOException, TimeoutException;
	
	public abstract void receive() throws IOException;

	public abstract void closeCommunication();
}
