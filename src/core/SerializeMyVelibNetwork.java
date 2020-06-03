package core;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * SerializeMyVelibNetwork is a class that is used to write an instance of MyVelibNetwork (state and structure) into a file in the form of a sequence of bytes.
 * 
 * @author Ali Raïki
 */
public class SerializeMyVelibNetwork {
	
	/**
	 * This method takes a network and writes its state and structure in the form of sequence of bytes into the file fileName.
	 * @param network
	 * 				The instance of MyVelibNetwork that we want to serialize
	 * @param fileName
	 * 				The file in which to save the serialization
	 */
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
