package core;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.io.Serializable;

/**
 * MyVelibNetwork is a class that is used to create the network where all the simulations will take place, that will be populated with stations, users, and bicycles. 
 * 
 * @author Ali Raïki & Mathieu Sibué
 */
public class MyVelibNetwork implements Serializable {
	private static final long serialVersionUID = 312658585L;
	//ATTRIBUTES
	private int ID;
	private static int counter = 0; //for the ID
	private String name;
	private double side;
	private HashMap<Integer,Bicycle> bicycles;
	private HashMap<Integer,Station> stations;
	private HashMap<Integer,User> users;
	
	//CREATE FACTORIES
	private static AbstractFactory slotFactory = FactoryProducer.createFactory("SLOT");
	private static AbstractFactory personFactory = FactoryProducer.createFactory("PERSON");
	private static AbstractFactory stationFactory = FactoryProducer.createFactory("STATION");
	private static AbstractFactory cardFactory = FactoryProducer.createFactory("CARD");
	private static AbstractFactory bicycleFactory = FactoryProducer.createFactory("BICYCLE");
	
	//CONSTRUCTOR
	public MyVelibNetwork(String name, double side) {
		counter++;
		this.ID = counter;
		this.name = name;
		this.side = side;
		this.bicycles = new HashMap<Integer, Bicycle>();
		this.stations = new HashMap<Integer, Station>();
		this.users = new HashMap<Integer, User>();
	}
	
	//Getters and Setters
	public int getID() {
		return ID;
	}

	public double getSide() {
		return side;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
	
	public static AbstractFactory getPersonFactory() {
		return personFactory;
	}

	/**
	 * Adds a number of stations and parking slots to the object.
	 * @param nbStations
	 * 				Number of stations to add to the object.
	 * @param nbParkingSlots
	 * 				Number of parking slots to add in each station.
	 */
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
			double x = random.nextDouble()*this.getSide(); //Uniformly distributing the stations in the network.
			double y = random.nextDouble()*this.getSide(); //Uniformly distributing the stations in the network.
			double typeOfStation = random.nextDouble();
			//50% chance to be a standard station
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
			else {//50% chance to be a plus station
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

	/**
	 * Adds a number of users to the object.
	 * @param nbUsers
	 * 				Number of users to add to the object.
	 */
	public void addUsers(int nbUsers) {
		for (int i = 0; i < nbUsers; i++) {
			Random random = new Random();
			double x = random.nextDouble()*this.getSide(); //Uniformly distributing the users in the network.
			double y = random.nextDouble()*this.getSide(); //Uniformly distributing the users in the network.
			double creditCardBalance = random.nextDouble()*500;
			double cardType = random.nextDouble();
			
			ArrayList<Object> params = new ArrayList<Object>();
			params.add("USER");
			params.add("User"+i);
			params.add(x);
			params.add(y);
			params.add(creditCardBalance);
			
			//Uniformly distributing the different types of cards
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
	
	/**
	 * Adds a user to the object.
	 * @param name
	 * 				Name of the user.
	 * @param cardType
	 * 				Card type of the user.
	 */
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
			
			if(cardType.equalsIgnoreCase("VMAX")) {
				ArrayList<Object> cardParams = new ArrayList<Object>();
				cardParams.add("VMAX");
				Card card = cardFactory.createCard(cardParams);
				params.add(card);
				User user = (User) personFactory.createPerson(params);
				//User user = new User("Random", x, y, creditCardBalance, (Card) new Vmax());
				this.getUsers().put(user.getID(), user);
				System.out.println("Successfully created user with ID " + user.getID() +".");
			}
			else if(cardType.equalsIgnoreCase("VLIBRE")) {
				ArrayList<Object> cardParams = new ArrayList<Object>();
				cardParams.add("VLIBRE");
				Card card = cardFactory.createCard(cardParams);
				params.add(card);
				User user = (User) personFactory.createPerson(params);
				//User user = new User("Random", x, y, creditCardBalance, (Card) new Vlibre());
				this.getUsers().put(user.getID(), user);
				System.out.println("Successfully created user with ID " + user.getID() +".");
			}
			else if(cardType.equalsIgnoreCase("NONE")) {
				User user = (User) personFactory.createPerson(params);
				//User user = new User("Random", x, y, creditCardBalance);
				this.getUsers().put(user.getID(), user);
				System.out.println("Successfully created user with ID " + user.getID() +".");
			}
			else {
				throw new RuntimeException("This type of card does not exist");
			}
	}
	
	/**
	 * Adds a number of bicycles to the object, and assigning them to parking slots.
	 * @param nbOfBikes
	 * 				Number of bicycles to be added
	 */
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
		
		//If more bicycles than parking slots available, throw exception
		if (nbParkingSlotsAvailable < nbOfBikes) {
			throw new RuntimeException("There are not enough parking slots availables");
		}
		else {
			HashMap<Integer, Bicycle> newBikes = new HashMap<Integer, Bicycle>();
			for (int i = 0; i < totalBicycles; i++) {
				Random random = new Random();
				double typeOfBicycle = random.nextDouble();
				//70% of mechanical bicycles
				if (typeOfBicycle >= 0.3) {
					ArrayList<Object> params = new ArrayList<Object>();
					params.add("MECHANICAL");
					Bicycle bike = bicycleFactory.createBicycle(params);
					//Bicycle bike = new MechanicalBike();
					this.getBicycles().put(bike.getID(), bike);
					newBikes.put(bike.getID(), bike);
				}
				else {//30% of electrical bicycles
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
			//Randomly populate the stations
			while(newBikes.size() != 0) {
				//Choose a random station and a random parking slot
				int randomStationIndex = generator.nextInt(stations.length); //The key of a random station
				Station randomStation = this.getStations().get(stations[randomStationIndex]);
				Object[] parkingSlots = randomStation.getParkingSlots().keySet().toArray();
				int randomSlotIndex = generator.nextInt(parkingSlots.length); //The key of a random parking slot in the station
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
	
	/**
	 * Populates a percentage of the available parking slots.
	 * @param percentage
	 */
	public void addBicyclePercentage(double percentage) {
		int nbParkingSlotsAvailable = 0;
		
		for (Station station : this.getStations().values()) {
			for (ParkingSlot slot : station.getParkingSlots().values()) {
				if (slot.getBicycleStored()==null && !slot.isOutOfOrder()) {
					nbParkingSlotsAvailable++;
				}
			}
		}
		//Compute the number of bikes to add
		int nbOfBikes = (int) (percentage*nbParkingSlotsAvailable);
		this.addBicycleNumber(nbOfBikes); //Use the method addBicyclesNumber to add the bicycles
	}
	
	public ArrayList<Station> planning(double x1, double y1, double x2, double y2, String bicycleType, RidePlanning planType) {
		return planType.planRide(x1, y1, x2, y2, bicycleType, this);
	}
	
	@Override
	public String toString() {
		return "MyVelibNetwork [side=" + side + ", bicycles=" + bicycles + ", stations=" + this.getStations() + ", users=" + users
				+ "]";
	}
	
	/**
	 * Method to return the earliest and latest recorded activities in the network so far.
	 * @return ArrayList<Date> with earliest and latest recorded activities
	 */
	public ArrayList<Date> getEarliestAndLatestActivity() {
		if (stations == null || stations.size() == 0) {
			throw new RuntimeException("No available station yet.");
		}
		ArrayList<ActivityLog> possibleInfDates = new ArrayList<ActivityLog>();
		ArrayList<ActivityLog> possibleSupDates = new ArrayList<ActivityLog>();
		for (Station st: stations.values()) {
			if (st.getParkingSlots()!=null && st.getParkingSlots().size()>0) {
				HashMap<Integer,ParkingSlot> stps = st.getParkingSlots();
				for (ParkingSlot ps: stps.values()) {
					ArrayList<ActivityLog> als = ps.getActivityLogs();
					if (als != null && als.size() != 0) {
						Date origin = ActivityLog.getDate(0, 0, 0, 0, 0, 0);
						for (ActivityLog al: als) {
							if (!al.getDate().equals(origin)) {
								possibleInfDates.add(al);
								break;
							}
						}
						if (!als.get(als.size()-1).getDate().equals(origin)) {
							possibleSupDates.add(als.get(als.size()-1));
						}
					}
				}
			}
		}
		if (possibleInfDates.size() == 0 || possibleSupDates.size() == 0) {
			throw new RuntimeException("Not enough user activity logs to define earliest non-null and latest activity.");
		}
		Comparator<ActivityLog> dateComp = new DateComparatorForActivityLogs();
		possibleInfDates.sort(dateComp);
		possibleSupDates.sort(dateComp);
		ArrayList<Date> res = new ArrayList<Date>();
		res.add(possibleInfDates.get(0).getDate());
		res.add(possibleSupDates.get(possibleSupDates.size()-1).getDate());
		if (ActivityLog.getDateDiff(res.get(0), res.get(1))<=0) {
			throw new RuntimeException("Not enough user activity logs to define earliest non-null and latest activity.");
		}
		return res;
	}
	
	/**
	 * Computes some statistics for user given his userID
	 * @param userID (int)
	 * @return
	 * @throws RuntimeException
	 */
	public String getUserBalance(int userID) throws RuntimeException {
		if (!users.containsKey(userID)) {
			throw new RuntimeException("User " + userID + " not in network " + ID + ".");
		}
		User myUser = users.get(userID); 
		return myUser.getStatistics();
	}
	
	/**
	 * Computes some statistics for a station given its stationID
	 * @param stationID (int)
	 * @param infDate (Date, that has to be created by the CLUI from a user input parsed)
	 * @param supDate (Date, that has to be created by the CLUI from a user input parsed)
	 * @return the station's statistics
	 * @throws RuntimeException
	 */
	public String getStationBalance(int stationID, Date infDate, Date supDate) throws RuntimeException {
		if (infDate == null || supDate == null) {
			throw new RuntimeException("Dates have to be in valid format.");
		} else if (!stations.containsKey(stationID)) {
			throw new RuntimeException("Station " + stationID + " not in network " + ID + ".");
		}
		Station myStation = stations.get(stationID);
		return myStation.getStatistics(infDate, supDate);
	}
	
	/**
	 * Computes some statistics for a station given its stationID; overloaded second version that searches for oldest activity and latest activity for infDate and supDate
	 * @param stationID (int)
	 * @return the station's statistics
	 * @throws RuntimeException
	 */
	public String getStationBalance(int stationID) throws RuntimeException {
		if (!stations.containsKey(stationID)) {
			throw new RuntimeException("Station " + stationID + " not in network " + ID + ".");
		}
		ArrayList<Date> infAndSupDates = this.getEarliestAndLatestActivity();
		Date infDate = infAndSupDates.get(0);
		Date supDate = infAndSupDates.get(1);
		Station myStation = stations.get(stationID);
		return myStation.getStatistics(infDate, supDate);
	}
	
	/**
	 * Sorts stations according to policies defined by the client
	 * @param choice (String, can only take two values for now: "MOST USED" and "LEAST OCCUPIED"
	 * @return
	 * @throws RuntimeException
	 */
	public ArrayList<Station> sortStations(String choice) throws RuntimeException {
		if (choice == null) {
			throw new RuntimeException("Incorrect sorting policy.");
		}
		if (choice.equalsIgnoreCase("MOST USED")) {
			SortingStrategy ss = new SortMostUsed();
			return ss.sort(stations);
		} else if (choice.equalsIgnoreCase("LEAST OCCUPIED WITH DATES")) {
			SortingStrategy ss = new SortLeastOccupied();
			return ss.sort(stations);			
		} else if (choice.equalsIgnoreCase("LEAST OCCUPIED")) {
			ArrayList<Date> infAndSupDates = this.getEarliestAndLatestActivity();
			Date infDate = infAndSupDates.get(0);
			Date supDate = infAndSupDates.get(1);
			SortingStrategy ss = new SortLeastOccupied(infDate,supDate);
			return ss.sort(stations);		
		}
		throw new RuntimeException("Incorrect sorting policy.");
	}
	

}
