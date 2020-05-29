package core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

public class MyVelibNetwork {
	private double side;
	private HashMap<Integer,Bicycle> bicycles = new HashMap<Integer, Bicycle>();
	private HashMap<Integer,Station> stations = new HashMap<Integer, Station>();
	private HashMap<Integer,User> users = new HashMap<Integer, User>();
	
	public MyVelibNetwork(double side) {
		this.side = side;
		this.bicycles = new HashMap<Integer, Bicycle>();
		this.stations = new HashMap<Integer, Station>();
		this.users = new HashMap<Integer, User>();
	}
	
	public double getSide() {
		return side;
	}

	public void setSide(double side) {
		this.side = side;
	}

	public HashMap<Integer, Bicycle> getBicycles() {
		return bicycles;
	}

	public void setBicycles(HashMap<Integer, Bicycle> bicycles) {
		this.bicycles = bicycles;
	}

	public HashMap<Integer, Station> getStations() {
		return stations;
	}

	public void setStations(HashMap<Integer, Station> stations) {
		this.stations = stations;
	}

	public HashMap<Integer, User> getUsers() {
		return users;
	}



	public void setUsers(HashMap<Integer, User> users) {
		this.users = users;
	}


	public void addStations(int nbStations, int nbParkingSlots) {
		for (int i = 0; i < nbStations; i++) {
			HashMap<Integer, ParkingSlot> parkingSlots = new HashMap<Integer, ParkingSlot>() ;
			for(int j = 0; j < nbParkingSlots; j++) {
				ParkingSlot parkingSlot = new ParkingSlot();
				parkingSlots.put(parkingSlot.getID(), parkingSlot);
			}
			Random random = new Random();
			double x = random.nextDouble()*this.getSide();
			double y = random.nextDouble()*this.getSide();
			double typeOfStation = random.nextDouble();
			if(typeOfStation >= 0.5) {
				Station station = new StdStation(x, y, parkingSlots);
				this.getStations().put(station.getID(), station);
			}
			else {
				Station station = new PlusStation(x, y, parkingSlots);
				this.getStations().put(station.getID(), station);
			}
		}
	}

	public void addUsers(int nbUsers) {
		for (int i = 0; i < nbUsers; i++) {
			Random random = new Random();
			double x = random.nextDouble()*this.getSide();
			double y = random.nextDouble()*this.getSide();
			double creditCardBalance = random.nextDouble()*500;
			double cardType = random.nextDouble();
			if(cardType <= 0.33) {
				User user = new User("Random", x, y, creditCardBalance);
				this.getUsers().put(user.getID(), user);
			}
			else if(cardType>=0.66) {
				User user = new User("Random", x, y, creditCardBalance, (Card) new Vlibre());
				this.getUsers().put(user.getID(), user);
			}
			else {
				User user = new User("Random", x, y, creditCardBalance, (Card) new Vmax());
				this.getUsers().put(user.getID(), user);
			}
		}
	}
	
	public void addBicycles(double pourcentage) {
		HashMap.Entry<Integer,Station> stationEntry = this.getStations().entrySet().iterator().next();
		int nbOfParkingSlots = stationEntry.getValue().getParkingSlots().size();
		System.out.println(nbOfParkingSlots);
		int totalParkingSlots = nbOfParkingSlots*this.getStations().size();
		System.out.println(totalParkingSlots);
		int totalBicycles = (int) (pourcentage*totalParkingSlots);
		System.out.println(totalBicycles);
		for (int i = 0; i < totalBicycles; i++) {
			Random random = new Random();
			double typeOfBicycle = random.nextDouble();
			if (typeOfBicycle >= 0.3) {
				Bicycle bike = new MechanicalBike();
				this.getBicycles().put(bike.getID(), bike);
			}
			else {
				Bicycle bike = new ElectricalBike();
				this.getBicycles().put(bike.getID(), bike);
			}
		}
		
		//Assign bicycle to a parking slot:
		HashMap<Integer,Bicycle> bicyclesCopy = (HashMap<Integer, Bicycle>) this.getBicycles().clone();
		Object[] bicycleCopyKeys = bicyclesCopy.keySet().toArray();
		int i= 0;
		int j = 0;
		while(bicyclesCopy.size() != 0) {
			for(Station station : this.getStations().values()) {
				HashMap<Integer, ParkingSlot> parkingSlotsMap = station.getParkingSlots();
				Object[] parkingSlotsKeys = parkingSlotsMap.keySet().toArray();
				Bicycle bicycleToStore = bicyclesCopy.get(bicycleCopyKeys[j]);
				station.getParkingSlots().get(parkingSlotsKeys[i]).setBicycleStored(bicycleToStore);
				bicyclesCopy.remove(bicycleCopyKeys[j]);
				j++;
				System.out.println(bicyclesCopy);
			}
			i++;
		}
	}
	
	
	
	public void BasicPlanning(double x1, double y1, double x2, double y2, String bicycleType) {
		double finalDistanceFromStart = Double.POSITIVE_INFINITY;
		double finalDistanceFromFinish = Double.POSITIVE_INFINITY;
		boolean bikeIsAvailable;
		double distanceFromStart;
		double distanceFromFinish;
		ArrayList<Double> startStation = new ArrayList<Double>();
		ArrayList<Double> finishStation = new ArrayList<Double>();
		
		HashMap<Integer, Station> stations = this.getStations();
		for(Station station : stations.values()) {
			distanceFromStart = Math.sqrt(Math.pow(station.getX()-x1, 2)+Math.pow(station.getY()-y1, 2));
			distanceFromFinish = Math.sqrt(Math.pow(station.getX()-x2, 2)+Math.pow(station.getY()-y2, 2));
			
			for(ParkingSlot parkingSlot : station.getParkingSlots().values()) {
				if (bicycleType.toLowerCase()=="electrical" && parkingSlot.getBicycleStored() instanceof ElectricalBike) {
					bikeIsAvailable = true;
					break;
				}
			}
			
			if(distanceFromStart < finalDistanceFromStart) {
				finalDistanceFromStart = distanceFromStart;
				startStation.clear();
				startStation.add(station.getX());
				startStation.add(station.getY());
			}
			if(distanceFromFinish < finalDistanceFromFinish) {
				finalDistanceFromFinish = distanceFromFinish;
				finishStation.clear();
				finishStation.add(station.getX());
				finishStation.add(station.getY());
			}

		}
		
		System.out.println("La station la plus proche de votre lieu de départ est :" + startStation);
		System.out.println("La station d'arrivée la plus proche de votre destination est :" + finishStation);
	}
	
	
	
	@Override
	public String toString() {
		return "MyVelibNetwork [side=" + side + ", bicycles=" + bicycles + ", stations=" + this.getStations() + ", users=" + users
				+ "]";
	}

	public static void main(String[] args) {
		MyVelibNetwork network = new MyVelibNetwork(10);
		network.addStations(3, 10);
		network.addUsers(10);
		network.addBicycles(0.7);
		System.out.println(network);
	}
	

}
