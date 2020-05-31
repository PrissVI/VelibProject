package core;

import java.io.Serializable;
import java.util.HashMap;

/** 
 * Represents an abstract station.
 * @author Mathieu Sibué
*/
public abstract class Station implements StationObserver, Serializable  {
	
	private static final long serialVersionUID = 560966235L;
	/*ATTRIBUTES*/
	private static int counterToGenerateIDs = 0;
	private int ID;
	private boolean isOnline;
	private boolean isTerminalOutOfOrder;
	private int nbOfOutOfOrderParkingSlots;
	private double x;
	private double y;
	private HashMap<Integer,ParkingSlot> parkingSlots;
	//registrationFees for user with no registration card
	@SuppressWarnings("serial")
	final static private HashMap<String,Double> feesForUserWithNoCard = new HashMap<String,Double>() {{
		put("mechanical",1.0);
		put("electrical",2.0);
	}};
	//for statistics:
	private int totalNbOfRentOps;
	private int totalNbOfReturnOps;
	
	
	
	/*CONSTRUCTORS*/
	public Station(double x, double y, HashMap<Integer,ParkingSlot> parkingSlots) {
		super();
		this.x = x;
		this.y = y;
		this.parkingSlots = parkingSlots;
		nbOfOutOfOrderParkingSlots = 0;
		for (ParkingSlot ps: this.parkingSlots.values()) {
			ps.registerObserver(this);
			if (ps.isOutOfOrder()) {
				nbOfOutOfOrderParkingSlots += 1;
			}
		}
		isOnline = true;
		isTerminalOutOfOrder = false;
		ID = this.generateID();
		totalNbOfRentOps = 0;
		totalNbOfReturnOps = 0;
	}	
	
	public Station(double x, double y) {
		super();
		this.x = x;
		this.y = y;
		//or just leave it null?
		parkingSlots = new HashMap<Integer,ParkingSlot>();
		nbOfOutOfOrderParkingSlots = 0;
		for (ParkingSlot ps: this.parkingSlots.values()) {
			ps.registerObserver(this);
			if (ps.isOutOfOrder()) {
				nbOfOutOfOrderParkingSlots += 1;
			}
		}
		isOnline = true;
		isTerminalOutOfOrder = false;
		ID = this.generateID();
		totalNbOfRentOps = 0;
		totalNbOfReturnOps = 0;
	}
	
	
	/*METHODS*/
	//getters and setters
	public int getNbOfOutOfOrderParkingSlots() {
		return nbOfOutOfOrderParkingSlots;
	}
	
	public boolean isOnline() {
		return isOnline;
	}

	//no setOnline: indeed, to setOnline to true, it is needed to set isTerminalOutOfOrder to false and repair at least one of the parking slots

	public boolean isTerminalOutOfOrder() {
		return isTerminalOutOfOrder;
	}

	public void setTerminalOutOfOrder(boolean isTerminalOutOfOrder) {
		this.isTerminalOutOfOrder = isTerminalOutOfOrder;
		isOnline = (nbOfOutOfOrderParkingSlots == parkingSlots.size() && isTerminalOutOfOrder)? false: true;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public HashMap<Integer, ParkingSlot> getParkingSlots() {
		return parkingSlots;
	}

	//when parkingSlots HashMap is updated, we need to go through all the parking slots store in 
	public void setParkingSlots(HashMap<Integer, ParkingSlot> parkingSlots) {
		if (this.parkingSlots.equals(parkingSlots)) {
			return;
		}
		this.parkingSlots = parkingSlots;
		nbOfOutOfOrderParkingSlots = 0;
		for (ParkingSlot ps: this.parkingSlots.values()) {
			ps.registerObserver(this);
			if (ps.isOutOfOrder()) {
				nbOfOutOfOrderParkingSlots += 1;
			}
		}
		isOnline = (nbOfOutOfOrderParkingSlots == parkingSlots.size() && isTerminalOutOfOrder)? false: true;
	}

	public int getID() {
		return ID;
	}

	public int getTotalNbOfRentOps() {
		return totalNbOfRentOps;
	}

	public int getTotalNbOfReturnOps() {
		return totalNbOfReturnOps;
	}
	
	public static HashMap<String, Double> getFeesForUserWithNoCard() {
		return feesForUserWithNoCard;
	}

	//equals and hashCode
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ID;
		result = prime * result + (isOnline ? 1231 : 1237);
		result = prime * result + (isTerminalOutOfOrder ? 1231 : 1237);
		result = prime * result + ((parkingSlots == null) ? 0 : parkingSlots.hashCode());
		result = prime * result + totalNbOfRentOps;
		result = prime * result + totalNbOfReturnOps;
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}
	//no point in having an equals method here, with unique IDs
	
	//toString
	@Override
	public String toString() {
		return "Station " + ID + ":\n"
				+ "- the station is " + (isOnline? "online": "offline") + "\n"
				+ "- its payment terminal is " + (isTerminalOutOfOrder? "out of order": "working fine") + "\n"
				+ "- the station is located at (" + x + ", " + y + ")" + "\n"
				+ "- it has " + parkingSlots.size() + " parking slots, " + nbOfOutOfOrderParkingSlots + " of which are out of order.";
	}
	
	//custom methods
	/**
	 * Increments the static counter to generate a unique ID for each Station.
	 * @return int: the incremented static counter, i.e. a new ID
	 */
	final public int generateID() {
		counterToGenerateIDs += 1;
		return counterToGenerateIDs;
	}

	/**
	 * Identifies a user via the station's terminal.
	 * @param User: the user willing to rent a bike
	 */
	public void identifyUser(User user) {
		if (user.getRegistrationCard() != null) {
			System.out.println("User "+user.getID()+" registrated with "+user.getRegistrationCard().getClass()+" card.");
		} else {
			System.out.println("User "+user.getID()+" registrated with his credit card.");
		}
	}
	
	/**
	 * Charges a user for its bicycle trip depending on the station's fees.
	 * @param User: the user willing to return a bike after using it
	 * @param double: the time (in minutes) spent on the bike
	 */
	/*
	abstract void chargeUser(User user, int duration);
	//comment gérer le cas où le user n'a pas de carte ? genre pour la tarification... FAIRE EN FONCTION DU TYPE DE STATION
	*/
	public void chargeUser(User user, int duration) throws RuntimeException {
		if (isTerminalOutOfOrder) {
			throw new RuntimeException("Terminal of station "+ID+" not working: go to closest station");
			//or just print something?
		} else {
			double cost = 0;
			if (user.getRegistrationCard() == null) {
				if (user.getRentedBicycle() instanceof MechanicalBike) {
					cost = duration/60 * feesForUserWithNoCard.get("mechanical");	
				} else if (user.getRentedBicycle() instanceof ElectricalBike) {
					cost = duration/60 * feesForUserWithNoCard.get("electrical");
				}
			} else {
				CardVisitor cardVisitor = new ConcreteCardVisitor();
				Card userCard = user.getRegistrationCard();
				cost = userCard.accept(cardVisitor, duration, user.getRentedBicycle());
			}
			user.setCreditCardBalance(user.getCreditCardBalance() - cost);
			user.setMyVelibTotalCharges(user.getMyVelibTotalCharges() + cost);
		}
	}
	
	//implemented method from interface Observer
	@Override
	public void update(boolean newIsOutOfOrder) {
		if (newIsOutOfOrder) {
			nbOfOutOfOrderParkingSlots += 1;
		} else {
			nbOfOutOfOrderParkingSlots -= 1;
		}
		isOnline = (nbOfOutOfOrderParkingSlots == parkingSlots.size() && isTerminalOutOfOrder)? false: true;
	}

}
