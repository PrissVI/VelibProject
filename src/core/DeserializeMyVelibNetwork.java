package core;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

import clui.CommandLine;

import java.io.IOException;

/**
 * DeserializeMyVelibNetwork is a class that is used to read a previously serialized instance of MyVelibNetwork from a file and recreated it in the main memory.
 * 
 * @author Ali Raïki
 */
public class DeserializeMyVelibNetwork {
	
	/**
	 * This method takes a file that represents a serialization of an instance of MyVelibNetwork, and recreates it in the main memory.
	 * @param fileName
	 * 				The name of the serialization file
	 * @return the deserialized myVelib network
	 */
	public static MyVelibNetwork deserializeNetwork(String fileName) {
		MyVelibNetwork network = null;

		try {
			FileInputStream fileIn = new FileInputStream(fileName);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			network = (MyVelibNetwork) in.readObject(); //casting
			in.close();
			fileIn.close();
			System.out.println("Deserialized MyVelibNetwork...");
			System.out.println("Network : " + network );
			return network;
		} catch(IOException i) {

			System.err.println(i.getMessage());

		} catch(ClassNotFoundException c) {

			System.err.println(c.getMessage());

		}
		return network;
	}

}