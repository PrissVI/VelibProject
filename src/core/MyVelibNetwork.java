package core;

import java.util.HashMap;
import java.util.Random;

public class MyVelibNetwork {
	private double radius;
	private HashMap<Integer,Bicycle> bicycles = new HashMap<Integer, Bicycle>();
	private HashMap<Integer,Station> stations = new HashMap<Integer, Station>();
	private HashMap<Integer,User> users = new HashMap<Integer, User>();
	
	public void addStations(int nbStations, int nbParkingSlots) {
		for (int i = 0; i < nbStations; i++) {
			HashMap<Integer, ParkingSlot> parkingSlots = new HashMap<Integer, ParkingSlot>() ;
			for(int j = 0; j <= nbParkingSlots; j++) {
				ParkingSlot parkingSlot = new ParkingSlot();
				parkingSlots.put(parkingSlot.getID(), parkingSlot);
			}
			Random random = new Random();
			double randomAngle = random.nextDouble()*2*Math.PI;
			double x = radius*Math.cos(randomAngle);
			double y = radius*Math.sin(randomAngle);
			double typeOfStation = random.nextDouble();
			if(typeOfStation >= 0.5) {
				Station station = new StdStation(x, y, parkingSlots);
				stations.put(station.getID(), station);
			}
			else {
				Station station = new PlusStation(x, y, parkingSlots);
				stations.put(station.getID(), station);
			}
		}
	}


}
