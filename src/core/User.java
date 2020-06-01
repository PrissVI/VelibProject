package core;

import java.util.Date;

/**
 * Represents a user of the myVelib network
 * @author Mathieu Sibué
 */
public class User extends Person {

	private static final long serialVersionUID = -8651543750430116639L;
	private int ID;
	private static int counter = 0; //for the ID
	private String name;
	double x;
	double y;
	private Bicycle rentedBicycle;
	private double creditCardBalance;
	private Card registrationCard;
	//for statistics:
	private double myVelibTotalCharges;
	private int totalNbOfRides;
	private int totalTimeSpentOnBike; //in minutes
	
	
	
	//Constructor with name and coordinates
	public User(String name, double x, double y) {
		super();
		counter++;
		ID = counter;
		this.name = name;
		this.x = x;
		this.y = y;
		//already initialized to 0 by default
//		myVelibTotalCharges = 0;
//		totalNbOfRides = 0;
//		totalTimeSpentOnBike = 0;
	}

	//Constructor with name, coordinates and credit card balance
	public User(String name, double x, double y, double creditCardBalance) {
		super();
		counter++;
		ID = counter;
		this.name = name;
		this.x = x;
		this.y = y;
		this.creditCardBalance = creditCardBalance;
		//already initialized to 0 by default
//		myVelibTotalCharges = 0;
//		totalNbOfRides = 0;
//		totalTimeSpentOnBike = 0;
	}
	
	//Constructor with name, coordinates, credit card balance and registration card
	public User(String name, double x, double y, double creditCardBalance, Card registrationCard) {
		super();
		counter++;
		ID = counter;
		this.name = name;
		this.x = x;
		this.y = y;
		this.creditCardBalance = creditCardBalance;
		this.registrationCard = registrationCard;
		//already initialized to 0 by default
//		myVelibTotalCharges = 0;
//		totalNbOfRides = 0;
//		totalTimeSpentOnBike = 0;
	}
	


	//Getters and Setters
	public void setMyVelibTotalCharges(double myVelibTotalCharges) {
		this.myVelibTotalCharges = myVelibTotalCharges;
	}

	public void setTotalNbOfRides(int totalNbOfRides) {
		this.totalNbOfRides = totalNbOfRides;
	}

	public void setTotalTimeSpentOnBike(int totalTimeSpentOnBike) {
		this.totalTimeSpentOnBike = totalTimeSpentOnBike;
	}	
	
	public double getMyVelibTotalCharges() {
		return myVelibTotalCharges;
	}

	public int getTotalNbOfRides() {
		return totalNbOfRides;
	}

	public int getTotalTimeSpentOnBike() {
		return totalTimeSpentOnBike;
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
	
	public double getCreditCardBalance() {
		return creditCardBalance;
	}

	public void setCreditCardBalance(double creditCardBalance) {
		this.creditCardBalance = creditCardBalance;
	}
	
	public Card getRegistrationCard() {
		return registrationCard;
	}
	
	public void setRegistrationCard(Card registrationCard) {
		this.registrationCard = registrationCard;
	}
	
	public int getID() {
		return ID;
	}
	
	public String getName() {
		return name;
	}
	
	public Bicycle getRentedBicycle() {
		return rentedBicycle;
	}

	public void setRentedBicycle(Bicycle rentedBicycle) {
		this.rentedBicycle = rentedBicycle;
	}
	
	//hashCode and equals (equals not useful since each user has a unique ID
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ID;
		long temp;
		temp = Double.doubleToLongBits(creditCardBalance);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(myVelibTotalCharges);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((registrationCard == null) ? 0 : registrationCard.hashCode());
		result = prime * result + ((rentedBicycle == null) ? 0 : rentedBicycle.hashCode());
		result = prime * result + totalNbOfRides;
		result = prime * result + totalTimeSpentOnBike;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}
	
	//toString
	@Override
	public String toString() {
		return "\nUser " + ID + ":\n"
				+ "- user name: " + name + "\n"
				+ "- situated at (" + x + ", " + y + ")" + "\n"
				+ "- " + (rentedBicycle == null? "not currently renting a bicycle": "renting bicycle " + rentedBicycle.getID()) + "\n"
				+ "- credit card balance: " + creditCardBalance + "\n"
				+ "- " + (registrationCard == null? "no registration card": "registration card of type " + registrationCard.getClass()) + "\n"
				+ "- total myVelib charges: " + myVelibTotalCharges + "\n"
				+ "- total myVelib rides: " + totalNbOfRides + "\n"
				+ "- total time spent on bike: " + totalTimeSpentOnBike + "\n";
	}
	
	//custom methods 
	/**
	 * Rents a bicycle available in the considered station.
	 * @param	Station where the user wants to rent a bicycle
	 */
	public void rentBicycle(Station station, Date rentDate) throws RuntimeException {
		if (x != station.getX() || y != station.getY()) {
			throw new RuntimeException("Cannot rent bicycle if station " + station.getID() + " not reached.");
		}
		if (!station.isOnline()) {
			throw new RuntimeException("Cannot rent bicycle if station " + station.getID() + " if offline.");
		}
		if (rentedBicycle != null) {
			throw new RuntimeException("Cannot rent bicycle if user is already renting one.");
		}
		else {	
			station.identifyUser(this);
			for (ParkingSlot ps: station.getParkingSlots().values()) {
				if (!ps.isOutOfOrder() && ps.getBicycleStored() != null) {
					this.setRentedBicycle(ps.getBicycleStored());
					//
					rentedBicycle.setRentDate(rentDate);
					//
					ps.setBicycleStored(null,rentDate);
					station.setTotalNbOfRentOps(station.getTotalNbOfRentOps()+1);
					return;
				}
			}
			throw new RuntimeException("Cannot rent bicycle if station " + station.getID() + " has no available bicycle.");
		}
	}
	
	/**
	 * Returns the user's rented bicycle in the considered station.
	 * @param Station: station where the user wants to return the bike
	 */
	public void returnBicycle(Station station, Date returnDate) throws RuntimeException {
		if (x != station.getX() || y != station.getY()) {
			throw new RuntimeException("Cannot return bicycle if station " + station.getID() + " not reached.");
		}
		if (!station.isOnline()) {
			throw new RuntimeException("Cannot return bicycle if station " + station.getID() + " if offline. Go to another one.");
		}
		if (rentedBicycle == null) {
			throw new RuntimeException("Cannot return bicycle if user is not currently renting one.");
		}
		else {
			for (ParkingSlot ps: station.getParkingSlots().values()) {
				if (!ps.isOutOfOrder() && ps.getBicycleStored() == null) {
					//
					int tripDuration = ActivityLog.getDateDiff(rentedBicycle.getRentDate(),returnDate);
					if (tripDuration<0) {
						throw new RuntimeException("Return date is anterior to rent date... check date anteriority");
					}
					//
					station.chargeUser(this, tripDuration); 
					//
					rentedBicycle.setRentDate(null);
					//
					ps.setBicycleStored(rentedBicycle,returnDate);
					this.setRentedBicycle(null);
					this.setTotalNbOfRides(totalNbOfRides + 1);
					this.setTotalTimeSpentOnBike(totalTimeSpentOnBike + tripDuration);
					station.setTotalNbOfReturnOps(station.getTotalNbOfReturnOps()+1);
					return;
				}
			}
			throw new RuntimeException("Cannot return bicycle if station " + station.getID() + " has no free parking slots. Go to another one.");
		} 
	}
}
