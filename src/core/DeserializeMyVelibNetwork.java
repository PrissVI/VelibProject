package core;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.IOException;

public class DeserializeMyVelibNetwork {
	
	public static void deserializeNetwork(String fileName) {
		MyVelibNetwork network = null;

		try {
			FileInputStream fileIn = new FileInputStream(fileName);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			network = (MyVelibNetwork) in.readObject(); //casting
			in.close();
			fileIn.close();
			System.out.println("Deserialized MyVelibNetwork...");
			System.out.println("Network : " + network );
		} catch(IOException i) {

			System.err.println(i.getMessage());

		} catch(ClassNotFoundException c) {

			System.err.println(c.getMessage());

		}

	}

}