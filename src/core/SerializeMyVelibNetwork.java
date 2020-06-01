package core;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;


public class SerializeMyVelibNetwork {
	
	public static void main(String[] args) {
		
		MyVelibNetwork network = new MyVelibNetwork("Test",10);
		network.addStations(3, 10);
		network.addUsers(10);
		network.addBicyclePercentage(0.9);
		System.out.println(network);
		
		try {
			
			FileOutputStream fileOut = new FileOutputStream("network.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(network);
			out.close();
			fileOut.close();
			System.out.printf("Serialized data is saved in network.ser");
		
		} catch(IOException i) {
			
			i.printStackTrace();
			
		}
	}

}
