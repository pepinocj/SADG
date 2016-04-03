package backEnd;

import java.io.IOException;

public interface IReceiver {

	// 2 personen willen checken of ze hetzelfde liedje hebben: string die komt van het decoderen
	// van de qr-code versturen via communicatiekanaal en checken of ze juist zijn in de verifier klasse.
	public abstract void verify() throws IOException;

	//Nieuwe speler die wilt meedoen
	public abstract void newPlayer() throws IOException;

	public abstract void closeCommunication();
}
