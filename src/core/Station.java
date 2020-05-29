package core;

import java.util.HashMap;

/** 
 * Represents an abstract station.
 * @author Mathieu Sibué
*/
public abstract class Station {
	
	/*ATTRIBUTES*/
	private static int counterToGenerateIDs = 0;
	private int ID;
	private boolean isOnline;
	private boolean isTerminalOutOfOrder;
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
		isOnline = true;
		isTerminalOutOfOrder = false;
		ID = this.generateID();
		totalNbOfRentOps = 0;
		totalNbOfReturnOps = 0;
	}
	
	
	/*METHODS*/
	//getters and setters
	public boolean isOnline() {
		return isOnline;
	}

	public void setOnline(boolean isOnline) {
		this.isOnline = isOnline;
	}

	public boolean isTerminalOutOfOrder() {
		return isTerminalOutOfOrder;
	}

	public void setTerminalOutOfOrder(boolean isTerminalOutOfOrder) {
		this.isTerminalOutOfOrder = isTerminalOutOfOrder;
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

	public void setParkingSlots(HashMap<Integer, ParkingSlot> parkingSlots) {
		this.parkingSlots = parkingSlots;
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
				+ "- its parking slots are " + parkingSlots;
	}
	
	//custom methods
	/**
	 * Increments the static counter to generate a unique ID for each Station.
	 * @return int: the incremented static counter, i.e. a new ID
	 */
	public int generateID() {
		counterToGenerateIDs += 1;
		return counterToGenerateIDs;
	}

	/**
	 * Identifies a user via the station's terminal.
	 * @param User: the user willing to rent a bike
	 * @return boolean: true if identification worked, false otherwise
	 */
	public boolean identifyUser(User user) /*throws RuntimeException*/ {
		
		//en fait, ne pas forcément lui faire retourner qlq chose ! thrower une erreur
		//A FINIR
		
		// faire un check pour voir si des bicycles sont dispos ; renvoyer false sinon
		if (!isOnline) {
			throw new RuntimeException("The station is offline");
		}
		int nbOfParkingSlotsOutOfOrder = 0;
		for (ParkingSlot parkingSlot: parkingSlots.values()) {
			if (parkingSlot.isOutOfOrder()) nbOfParkingSlotsOutOfOrder++;
		}
		if (nbOfParkingSlotsOutOfOrder == parkingSlots.size()) {
			
		}
		//|| isTerminalOutOfOrder && "toutes les places out of service"
		if (!isOnline) {
			System.out.println("Station "+ID+" is offline");
			return false;
			//on set isOnline en false
			//throw new RuntimeException("Station  is offline");
		} else {
			if (user.getRegistrationCard() != null) {
				System.out.println("User "+user.getID()+" registrated with "+user.getRegistrationCard().getClass()+" card.");
			} else {
				System.out.println("User "+user.getID()+" registrated with his credit card.");
			}
			return true;
		}
	}
	
	/**
	 * Charges a user for its bicycle trip depending on the station's fees.
	 * @param User: the user willing to return a bike after using it
	 * @param double: the time (in minutes) spent on the bike
	 */
	abstract void chargeUser(User user, int duration);
	//comment gérer le cas où le user n'a pas de carte ? genre pour la tarification... FAIRE EN FONCTION DU TYPE DE STATION

}
