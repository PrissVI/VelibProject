package core;

import java.util.HashMap;
import java.util.Iterator;
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

	public void addUsers(int nbUsers) {
		for (int i = 0; i < nbUsers; i++) {
			Random random = new Random();
			double randomAngle = random.nextDouble()*2*Math.PI;
			double x = radius*Math.cos(randomAngle);
			double y = radius*Math.sin(randomAngle);
			double creditCardBalance = random.nextDouble()*500;
			double cardType = random.nextDouble();
			if(cardType <= 0.33) {
				User user = new User("Random", x, y, creditCardBalance);
				users.put(user.getID(), user);
			}
			else if(cardType>=0.66) {
				User user = new User("Random", x, y, creditCardBalance, (Card) new Vlibre());
				users.put(user.getID(), user);
			}
			else {
				User user = new User("Random", x, y, creditCardBalance, (Card) new Vmax());
				users.put(user.getID(), user);
			}
		}
	}
	
	public void addBicycles(double pourcentage) {
		HashMap.Entry<Integer,Station> stationEntry = stations.entrySet().iterator().next();
		int nbParkingSlots = stationEntry.getValue().getParkingSlots().size()*stations.size();
		int nbBicycles = (int) pourcentage*nbParkingSlots;
		for (int i = 0; i < nbBicycles; i++) {
			Random random = new Random();
			double typeOfBicycle = random.nextDouble();
			if (typeOfBicycle >= 0.5) {
				Bicycle bike = new MechanicalBike();
				bicycles.put(bike.getID(), bike);
			}
			else {
				Bicycle bike = new ElectricalBike();
				bicycles.put(bike.getID(), bike);
			}
		}
		
		//Assign bicycle to a parking slot:
		for(Station station : stations.values()) {
			HashMap<Integer, ParkingSlot> parkingSlots = station.getParkingSlots();
			for(ParkingSlot slot : parkingSlots.values()) {
				//slot.setBicycleStored(bicycles);
			}
		}
	}
	

}
