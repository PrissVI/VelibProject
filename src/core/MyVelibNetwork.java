package core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.io.Serializable;

public class MyVelibNetwork implements Serializable {
	private static final long serialVersionUID = 312658585L;
	private double side;
	private static AbstractFactory slotFactory = FactoryProducer.createFactory("SLOT");
	private static AbstractFactory personFactory = FactoryProducer.createFactory("PERSON");
	private static AbstractFactory stationFactory = FactoryProducer.createFactory("STATION");
	private static AbstractFactory cardFactory = FactoryProducer.createFactory("CARD");
	private static AbstractFactory bicycleFactory = FactoryProducer.createFactory("BICYCLE");
	private HashMap<Integer,Bicycle> bicycles;
	private HashMap<Integer,Station> stations;
	private HashMap<Integer,User> users;
	
	public MyVelibNetwork(double side) {
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
			params.add("User"+i);
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
			params.add("User");
			params.add(name);
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
	
	public void addBicycleNumber(int nbOfBikes) {
		int totalBicycles = nbOfBikes;
		int nbParkingSlotsAvailable = 0;
		
		for (Station station : this.getStations().values()) {
			for (ParkingSlot slot : station.getParkingSlots().values()) {
				if (slot.getBicycleStored()==null && !slot.isOutOfOrder()) {
					nbParkingSlotsAvailable++;
				}
			}
		}
		
		if (nbParkingSlotsAvailable < nbOfBikes) {
			throw new RuntimeException("There are not enough parking slots availables");
		}
		else {
			HashMap<Integer, Bicycle> newBikes = new HashMap<Integer, Bicycle>();
			for (int i = 0; i < totalBicycles; i++) {
				Random random = new Random();
				double typeOfBicycle = random.nextDouble();
				if (typeOfBicycle >= 0.3) {
					ArrayList<Object> params = new ArrayList<Object>();
					params.add("MECHANICAL");
					Bicycle bike = bicycleFactory.createBicycle(params);
					//Bicycle bike = new MechanicalBike();
					this.getBicycles().put(bike.getID(), bike);
					newBikes.put(bike.getID(), bike);
				}
				else {
					ArrayList<Object> params = new ArrayList<Object>();
					params.add("ELECTRICAL");
					Bicycle bike = bicycleFactory.createBicycle(params);
					//Bicycle bike = new ElectricalBike();
					this.getBicycles().put(bike.getID(), bike);
					newBikes.put(bike.getID(), bike);
				}
			}
			
			//Assign bicycle to a parking slot:
			Object[] bicycleKeys = newBikes.keySet().toArray();
			int j = 0;
			
			Object[] stations = this.getStations().keySet().toArray();
			Random generator = new Random();
			while(newBikes.size() != 0) {
				//Choose a random station and a random parking slot
				int randomStationIndex = generator.nextInt(stations.length);
				Station randomStation = this.getStations().get(stations[randomStationIndex]);
				Object[] parkingSlots = randomStation.getParkingSlots().keySet().toArray();
				int randomSlotIndex = generator.nextInt(parkingSlots.length);
				//If random parking slot is empty, put a bicycle in it
				if(this.getStations().get(stations[randomStationIndex]).getParkingSlots().get(parkingSlots[randomSlotIndex]).getBicycleStored()==null && !this.getStations().get(stations[randomStationIndex]).getParkingSlots().get(parkingSlots[randomSlotIndex]).isOutOfOrder()) {
					Bicycle bicycleToStore = newBikes.get(bicycleKeys[j]);
					this.getStations().get(stations[randomStationIndex]).getParkingSlots().get(parkingSlots[randomSlotIndex]).setBicycleStored(bicycleToStore, ActivityLog.getDate(0, 0, 0, 0, 0, 0));
					newBikes.remove(bicycleKeys[j]);
					j++;
				}
			}
		}
	}
	
	public void addBicyclePercentage(double percentage) {
		int nbParkingSlotsAvailable = 0;
		
		for (Station station : this.getStations().values()) {
			for (ParkingSlot slot : station.getParkingSlots().values()) {
				if (slot.getBicycleStored()==null && !slot.isOutOfOrder()) {
					nbParkingSlotsAvailable++;
				}
			}
		}
		
		System.out.println(nbParkingSlotsAvailable);
		int nbOfBikes = (int) (percentage*nbParkingSlotsAvailable);
		System.out.println(nbOfBikes);
		addBicycleNumber(nbOfBikes);
	}
	
	public ArrayList<Station> planning(double x1, double y1, double x2, double y2, String bicycleType, RidePlanning planType) {
		return planType.planRide(x1, y1, x2, y2, bicycleType, this);
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
		network.addBicyclePercentage(0.9);
		System.out.println(network);
		
		network.planning(5, 5, 8, 8, "electrical", new BasicPlanning());
		network.planning(5, 5, 8, 8, "electrical", new AvoidPlus());
		network.planning(5, 5, 8, 8, "electrical", new PreferPlus());
	}
	

}
