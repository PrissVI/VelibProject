package core;

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
		myVelibTotalCharges = 0;
		totalNbOfRides = 0;
		totalTimeSpentOnBike = 0;
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
		myVelibTotalCharges = 0;
		totalNbOfRides = 0;
		totalTimeSpentOnBike = 0;
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
		myVelibTotalCharges = 0;
		totalNbOfRides = 0;
		totalTimeSpentOnBike = 0;
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
		return "User " + ID + ":\n"
				+ "- user name: " + name + "\n"
				+ "- situated at (" + x + ", " + y + ")" + "\n"
				+ "- " + (rentedBicycle == null? "not currently renting a bicycle": "renting bicycle " + rentedBicycle.getID()) + "\n"
				+ "- credit card balance: " + creditCardBalance + "\n"
				+ "- " + (registrationCard == null? "no registration card": "rgistration card of type " + registrationCard.getClass()) + "\n"
				+ "- total myVelib charges: " + myVelibTotalCharges + "\n"
				+ "- total myVelib rides: " + totalNbOfRides + "\n"
				+ "- total time spent on bike: " + totalTimeSpentOnBike;
	}
	
	//custom methods 
	/**
	 * Rents a bicycle available in the considered station.
	 * @param	Station where the user wants to rent a bicycle
	 */
	public void rentBicycle(Station station) throws RuntimeException {
		if (x != station.getX() || y != station.getY()) {
			throw new RuntimeException("Cannot rent bicycle if station " + station.getID() + " not reached.");
		}
		if (!station.isOnline()) {
			throw new RuntimeException("Cannot rent bicycle if station " + station.getID() + " if offline.");
		}
		if (rentedBicycle != null) {
			throw new RuntimeException("Cannot rent bicycle if user is already renting one.");
		}
		try {	//what's the point of having a try-catch there if identifyUser does not send errors? Let it throw errors as in other cases!
			station.identifyUser(this);		//can throw error in certain cases? NO
			for (ParkingSlot ps: station.getParkingSlots().values()) {
				if (!ps.isOutOfOrder() && ps.getBicycleStored() != null) {
					this.setRentedBicycle(ps.getBicycleStored());
					ps.setBicycleStored(null);
					return;
				}
			}
			throw new RuntimeException("Cannot rent bicycle if station " + station.getID() + " has no available bicycle.");
		} catch (RuntimeException e) {
			System.out.println(e.getMessage());
			return;
		}
	}
	
	/**
	 * Returns the user's rented bicycle in the considered station.
	 * @param Station: station where the user wants to return the bike
	 */
	public void returnBicycle(Station station, int tripDuration) throws RuntimeException {
		if (x != station.getX() || y != station.getY()) {
			throw new RuntimeException("Cannot return bicycle if station " + station.getID() + " not reached.");
		}
		if (!station.isOnline()) {
			throw new RuntimeException("Cannot return bicycle if station " + station.getID() + " if offline. Go to another one.");
		}
		if (rentedBicycle == null) {
			throw new RuntimeException("Cannot return bicycle if user is not currently renting one.");
		}
		try {	//what's the point of having a try-catch there if identifyUser does not send errors? Let it throw errors as in other cases!
			for (ParkingSlot ps: station.getParkingSlots().values()) {
				if (!ps.isOutOfOrder() && ps.getBicycleStored() == null) {
					station.chargeUser(this, tripDuration); 	//doit pouvoir throw des exceptions ici (si terminal ne fonctionne pas par ex)
					ps.setBicycleStored(rentedBicycle);
					this.setRentedBicycle(null);
					this.setTotalNbOfRides(totalNbOfRides + 1);
					this.setTotalTimeSpentOnBike(totalTimeSpentOnBike + tripDuration);
					return;
				}
			}
			throw new RuntimeException("Cannot return bicycle if station " + station.getID() + " has no free parking slots. Go to another one.");
		} catch (RuntimeException e) {
			System.out.println(e.getMessage());
			return;
		}
	}
}
