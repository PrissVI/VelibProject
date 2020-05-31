package core;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.IOException;

public class DeserializeMyVelibNetwork {
	
	public static void main(String[] args) {

		MyVelibNetwork network = null;

		try {

			FileInputStream fileIn = new FileInputStream("network.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			network = (MyVelibNetwork) in.readObject(); //casting
			in.close();
			fileIn.close();

		} catch(IOException i) {

			i.printStackTrace();
			return;

		} catch(ClassNotFoundException c) {

			System.out.println("PersistentTime class not found");
			c.printStackTrace();
			return;

		}

		System.out.println("Deserialized PersistentTime...");
		System.out.println("Date : " + network );
	}
}