package core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

public class MyVelibNetworkWithFactoryPattern {
	private double side;
	private static AbstractFactory slotFactory = FactoryProducer.createFactory("SLOT");
	private static AbstractFactory personFactory = FactoryProducer.createFactory("PERSON");
	private static AbstractFactory stationFactory = FactoryProducer.createFactory("STATION");
	private static AbstractFactory cardFactory = FactoryProducer.createFactory("CARD");
	private static AbstractFactory bicycleFactory = FactoryProducer.createFactory("BICYCLE");
	private HashMap<Integer,Bicycle> bicycles;
	private HashMap<Integer,Station> stations;
	private HashMap<Integer,User> users;
	
	public MyVelibNetworkWithFactoryPattern(double side) {
		this.side = side;
		this.bicycles = new HashMap<Integer, Bicycle>();
		this.stations = new HashMap<Integer, Station>();
		this.users = new HashMap<Integer, User>();
	}
	
	public double getSide() {
		return side;
	}

	/*
	public void setSide(double side) {
		this.side = side;
	}
	*/

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
				ArrayList<Object> params = new ArrayList<Object>();
				params.add("PARKING");
				ParkingSlot parkingSlot = (ParkingSlot) slotFactory.createSlot(params);
				//ParkingSlot parkingSlot = new ParkingSlot();
				parkingSlots.put(parkingSlot.getID(), parkingSlot);
			}
			Random random = new Random();
			double x = random.nextDouble()*this.getSide();
			double y = random.nextDouble()*this.getSide();
			double typeOfStation = random.nextDouble();
			
			if(typeOfStation >= 0.5) {
				ArrayList<Object> params = new ArrayList<Object>();
				params.add("STANDARD");
				params.add(x);
				params.add(y);
				params.add(parkingSlots);
				Station station = stationFactory.createStation(params);
				//Station station = new StdStation(x, y, parkingSlots);
				this.getStations().put(station.getID(), station);
			}
			else {
				ArrayList<Object> params = new ArrayList<Object>();
				params.add("PLUS");
				params.add(x);
				params.add(y);
				params.add(parkingSlots);
				Station station = stationFactory.createStation(params);
				//Station station = new PlusStation(x, y, parkingSlots);
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
			
			ArrayList<Object> params = new ArrayList<Object>();
			params.add("USER");
			params.add("Random");
			params.add(x);
			params.add(y);
			params.add(creditCardBalance);
			
			if(cardType <= 0.33) {
				User user = (User) personFactory.createPerson(params);
				//User user = new User("Random", x, y, creditCardBalance);
				this.getUsers().put(user.getID(), user);
			}
			else if(cardType>=0.66) {
				ArrayList<Object> cardParams = new ArrayList<Object>();
				cardParams.add("VLIBRE");
				Card card = cardFactory.createCard(cardParams);
				params.add(card);
				User user = (User) personFactory.createPerson(params);
				//User user = new User("Random", x, y, creditCardBalance, (Card) new Vlibre());
				this.getUsers().put(user.getID(), user);
			}
			else {
				ArrayList<Object> cardParams = new ArrayList<Object>();
				cardParams.add("VMAX");
				Card card = cardFactory.createCard(cardParams);
				params.add(card);
				User user = (User) personFactory.createPerson(params);
				//User user = new User("Random", x, y, creditCardBalance, (Card) new Vmax());
				this.getUsers().put(user.getID(), user);
			}
		}
	}
	
	public void addUser(String name, String cardType) {
			Random random = new Random();
			double x = random.nextDouble()*this.getSide();
			double y = random.nextDouble()*this.getSide();
			double creditCardBalance = random.nextDouble()*500;
			
			ArrayList<Object> params = new ArrayList<Object>();
			params.add(x);
			params.add(y);
			params.add(creditCardBalance);
			
			if(cardType.toLowerCase() == "vmax") {
				ArrayList<Object> cardParams = new ArrayList<Object>();
				cardParams.add("VMAX");
				Card card = cardFactory.createCard(cardParams);
				params.add(card);
				User user = (User) personFactory.createPerson(params);
				//User user = new User("Random", x, y, creditCardBalance, (Card) new Vmax());
				this.getUsers().put(user.getID(), user);
			}
			else if(cardType.toLowerCase() == "vlibre") {
				ArrayList<Object> cardParams = new ArrayList<Object>();
				cardParams.add("VLIBRE");
				Card card = cardFactory.createCard(cardParams);
				params.add(card);
				User user = (User) personFactory.createPerson(params);
				//User user = new User("Random", x, y, creditCardBalance, (Card) new Vlibre());
				this.getUsers().put(user.getID(), user);
			}
			else {
				User user = (User) personFactory.createPerson(params);
				//User user = new User("Random", x, y, creditCardBalance);
				this.getUsers().put(user.getID(), user);
			}
	}
	
	public void addBicyclePercentage(double percentage) {
		if (percentage>1) {
			throw new RuntimeException("Cannot populate parking slots more than 100% (there are more bicycles than parkingslots available).");
		}
		try {
			HashMap.Entry<Integer,Station> stationEntry = this.getStations().entrySet().iterator().next();
			int nbOfParkingSlots = stationEntry.getValue().getParkingSlots().size();
			int totalParkingSlots = nbOfParkingSlots*this.getStations().size();
			int totalBicycles = (int) (percentage*totalParkingSlots);
			for (int i = 0; i < totalBicycles; i++) {
				Random random = new Random();
				double typeOfBicycle = random.nextDouble();
				if (typeOfBicycle >= 0.3) {
					ArrayList<Object> params = new ArrayList<Object>();
					params.add("MECHANICAL");
					Bicycle bike = bicycleFactory.createBicycle(params);
					//Bicycle bike = new MechanicalBike();
					this.getBicycles().put(bike.getID(), bike);
				}
				else {
					ArrayList<Object> params = new ArrayList<Object>();
					params.add("ELECTRICAL");
					Bicycle bike = bicycleFactory.createBicycle(params);
					//Bicycle bike = new ElectricalBike();
					this.getBicycles().put(bike.getID(), bike);
				}
			}
			
			//Assign bicycle to a parking slot:
			HashMap<Integer,Bicycle> bicyclesCopy = (HashMap<Integer, Bicycle>) this.getBicycles().clone();
			Object[] bicycleCopyKeys = bicyclesCopy.keySet().toArray();
			int j = 0;
			
			Object[] stations = this.getStations().keySet().toArray();
			Random generator = new Random();
			while(bicyclesCopy.size() != 0) {
				//Choose a random station and a random parking slot
				int randomStationIndex = generator.nextInt(stations.length);
				Station randomStation = this.getStations().get(stations[randomStationIndex]);
				Object[] parkingSlots = randomStation.getParkingSlots().keySet().toArray();
				int randomSlotIndex = generator.nextInt(parkingSlots.length);
				//If random parking slot is empty, put a bicycle in it
				if(this.getStations().get(stations[randomStationIndex]).getParkingSlots().get(parkingSlots[randomSlotIndex]).getBicycleStored()==null) {
					Bicycle bicycleToStore = bicyclesCopy.get(bicycleCopyKeys[j]);
					this.getStations().get(stations[randomStationIndex]).getParkingSlots().get(parkingSlots[randomSlotIndex]).setBicycleStored(bicycleToStore);
					bicyclesCopy.remove(bicycleCopyKeys[j]);
					j++;
				}
			}
		} catch (RuntimeException e) {
			System.out.println(e);
			return;
		}
	}
	
	public void addBicycleNumber(int nbOfBikes) {
		int totalBicycles = nbOfBikes;
		for (int i = 0; i < totalBicycles; i++) {
			Random random = new Random();
			double typeOfBicycle = random.nextDouble();
			if (typeOfBicycle >= 0.3) {
				ArrayList<Object> params = new ArrayList<Object>();
				params.add("MECHANICAL");
				Bicycle bike = bicycleFactory.createBicycle(params);
				//Bicycle bike = new MechanicalBike();
				this.getBicycles().put(bike.getID(), bike);
			}
			else {
				ArrayList<Object> params = new ArrayList<Object>();
				params.add("ELECTRICAL");
				Bicycle bike = bicycleFactory.createBicycle(params);
				//Bicycle bike = new ElectricalBike();
				this.getBicycles().put(bike.getID(), bike);
			}
		}
		
		//Assign bicycle to a parking slot:
		HashMap<Integer,Bicycle> bicyclesCopy = (HashMap<Integer, Bicycle>) this.getBicycles().clone();
		Object[] bicycleCopyKeys = bicyclesCopy.keySet().toArray();
		int j = 0;
		
		Object[] stations = this.getStations().keySet().toArray();
		Random generator = new Random();
		while(bicyclesCopy.size() != 0) {
			//Choose a random station and a random parking slot
			int randomStationIndex = generator.nextInt(stations.length);
			Station randomStation = this.getStations().get(stations[randomStationIndex]);
			Object[] parkingSlots = randomStation.getParkingSlots().keySet().toArray();
			int randomSlotIndex = generator.nextInt(parkingSlots.length);
			//If random parking slot is empty, put a bicycle in it
			if(this.getStations().get(stations[randomStationIndex]).getParkingSlots().get(parkingSlots[randomSlotIndex]).getBicycleStored()==null) {
				Bicycle bicycleToStore = bicyclesCopy.get(bicycleCopyKeys[j]);
				this.getStations().get(stations[randomStationIndex]).getParkingSlots().get(parkingSlots[randomSlotIndex]).setBicycleStored(bicycleToStore);
				bicyclesCopy.remove(bicycleCopyKeys[j]);
				j++;
			}
		}
	}
	
	
	public void BasicPlanning(double x1, double y1, double x2, double y2, String bicycleType) {
		double finalDistanceFromStart = Double.POSITIVE_INFINITY;
		double finalDistanceFromFinish = Double.POSITIVE_INFINITY;
		boolean bikeIsAvailable;
		boolean parkingSlotIsAvailable;
		double distanceFromStart;
		double distanceFromFinish;
		ArrayList<Station> startStation = new ArrayList<Station>();
		ArrayList<Station> finishStation = new ArrayList<Station>();
		
		HashMap<Integer, Station> stations = this.getStations();
		
		//Loop for the start station
		for(Station station : stations.values()) {
			//Compute distance and compare to smallest distance yet
			distanceFromStart = Math.sqrt(Math.pow(station.getX()-x1, 2)+Math.pow(station.getY()-y1, 2));
			
			if(distanceFromStart < finalDistanceFromStart) {
				bikeIsAvailable = false;
				//Make sure that a bike of the desired type is available
				for(ParkingSlot parkingSlot : station.getParkingSlots().values()) {
					if (bicycleType.toLowerCase()=="electrical" && parkingSlot.getBicycleStored() instanceof ElectricalBike) {
						bikeIsAvailable = true;
						break;
					}
					if (bicycleType.toLowerCase()=="mechanical" && parkingSlot.getBicycleStored() instanceof MechanicalBike) {
						bikeIsAvailable = true;
						break;
					}
				}
				//If a bike of the desired type is not available, skip this station
				if(!bikeIsAvailable) {
					continue;
				}
				
				finalDistanceFromStart = distanceFromStart;
				startStation.clear();
				startStation.add(station);
				startStation.add(station);
			}
		}
		
		//Loop for the finish station
		for(Station station : stations.values()) {
			//Compute distance and compare to smallest distance yet
			distanceFromFinish = Math.sqrt(Math.pow(station.getX()-x2, 2)+Math.pow(station.getY()-y2, 2));
			
			if(distanceFromFinish < finalDistanceFromFinish) {
				parkingSlotIsAvailable = false;
				//Make sure that a parking Slot is available
				for(ParkingSlot parkingSlot : station.getParkingSlots().values()) {
					if (parkingSlot.getBicycleStored() == null) {
						parkingSlotIsAvailable = true;
						break;
					}
				}
				//If no parking slot is available, skip this station
				if(!parkingSlotIsAvailable) {
					continue;
				}
				finalDistanceFromFinish = distanceFromFinish;
				finishStation.clear();
				finishStation.add(station);
				finishStation.add(station);
			}
		}
		System.out.println("La station la plus proche de votre lieu de départ est :" + startStation.toString());
		System.out.println("La station d'arrivée la plus proche de votre destination est :" + finishStation);
	}	
	
	@Override
	public String toString() {
		return "MyVelibNetwork [side=" + side + ", bicycles=" + bicycles + ", stations=" + this.getStations() + ", users=" + users
				+ "]";
	}

	public static void main(String[] args) {
		MyVelibNetworkWithFactoryPattern network = new MyVelibNetworkWithFactoryPattern(10);
		network.addStations(3, 10);
		network.addUsers(10);
		network.addBicyclePercentage(0.9);
		System.out.println(network);
		
		network.BasicPlanning(5, 5, 8, 8, "electrical");
	}
	

}
