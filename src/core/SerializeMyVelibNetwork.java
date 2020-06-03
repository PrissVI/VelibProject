package core;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;


public class SerializeMyVelibNetwork {
	
	public static void serializeNetwork(MyVelibNetwork network, String fileName) {
		try {
			FileOutputStream fileOut = new FileOutputStream(fileName);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(network);
			out.close();
			fileOut.close();
			System.out.println("Serialized data is saved in "+fileName);

		} catch(IOException i) {
			System.err.println(i.getMessage());
		}		
	}
}
