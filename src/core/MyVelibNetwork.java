package core;

import java.util.HashMap;
import java.util.Random;

public class MyVelibNetwork {
	private double side;
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
	
	public void addUser(String name, String cardType) {
			Random random = new Random();
			double x = random.nextDouble()*this.getSide();
			double y = random.nextDouble()*this.getSide();
			double creditCardBalance = random.nextDouble()*500;
			if(cardType.toLowerCase() == "vmax") {
				User user = new User("Random", x, y, creditCardBalance, (Card) new Vmax());
				this.getUsers().put(user.getID(), user);
			}
			else if(cardType.toLowerCase() == "vlibre") {
				User user = new User("Random", x, y, creditCardBalance, (Card) new Vlibre());
				this.getUsers().put(user.getID(), user);
			}
			else {
				User user = new User("Random", x, y, creditCardBalance);
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
		HashMap.Entry<Integer,Station> stationEntry = this.getStations().entrySet().iterator().next();
		int nbOfParkingSlots = stationEntry.getValue().getParkingSlots().size();
		int totalParkingSlots = nbOfParkingSlots*this.getStations().size();
		double percentage = (double) nbOfBikes/totalParkingSlots;
		addBicyclePercentage(percentage);
	}
	
	@Override
	public String toString() {
		return "MyVelibNetwork [side=" + side + ", bicycles=" + bicycles + ", stations=" + this.getStations() + ", users=" + users
				+ "]";
	}
	
	public void planning(double x1, double y1, double x2, double y2, String bicycleType, RidePlanning planType) {
		planType.planRide(x1, y1, x2, y2, bicycleType, this);
	}

	public static void main(String[] args) {
		MyVelibNetwork network = new MyVelibNetwork(10);
		network.addStations(10, 10);
		network.addUsers(10);
		network.addBicycleNumber(20);
		//System.out.println(network);
		
		network.planning(5, 5, 8, 8, "electrical", new BasicPlanning());
		network.planning(5, 5, 8, 8, "electrical", new AvoidPlus());
		network.planning(5, 5, 8, 8, "electrical", new PreferPlus());
	}
	

}
