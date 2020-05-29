package core;

/** 
 * Represents a parking slot in a station.
 * @author Mathieu Sibué
*/
public abstract class ParkingSlot {
	
	/*ATTRIBUTES*/
	private static int counterToGenerateIDs = 0;
	private int ID;
	private Bicycle bicycleStored;
	private boolean isOutOfOrder;
	//
	
	
	/*CONSTRUCTORS*/
	public ParkingSlot(Bicycle bicycleStored) {
		super();
		this.bicycleStored = bicycleStored;
		isOutOfOrder = false;
		ID = this.generateID();
	}

	public ParkingSlot() {
		super();
		//or just leave it null?
		bicycleStored = new Bicycle();
		isOutOfOrder = false;
		ID = this.generateID();
	}
	
	
	/*METHODS*/
	//getters and setters
	public Bicycle getBicycleStored() {
		return bicycleStored;
	}

	public void setBicycleStored(Bicycle bicycleStored) throws RuntimeException {
		if (isOutOfOrder) {
			throw new RuntimeException("Cannot set stored bike of parking slot " + ID + ": parking bay is out of order");
		} else {
			this.bicycleStored = bicycleStored;
		}
	}

	public boolean isOutOfOrder() {
		return isOutOfOrder;
	}

	public void setOutOfOrder(boolean isOutOfOrder) throws RuntimeException {
		if (bicycleStored != null) {
			throw new RuntimeException("Cannot set parking slot "+ID+" to out of order: it is storing bicycle "+bicycleStored.getID());
		}
		this.isOutOfOrder = isOutOfOrder;
	}

	public int getID() {
		return ID;
	}	
	
	//toString
	@Override
	public String toString() {
		return "ParkingSlot " + ID + ":\n"
				+ "- bicycleStored: " + bicycleStored.toString() 
				+ "- " + (isOutOfOrder? "": "not ") + "out of order";
	}	
	
	//equals and hashCode
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ID;
		result = prime * result + ((bicycleStored == null) ? 0 : bicycleStored.hashCode());
		result = prime * result + (isOutOfOrder ? 1231 : 1237);
		return result;
	}
	//no need to override the equals method
	
	//custom methods
	/**
	 * Increments the static counter to generate a unique ID for each Station.
	 * @return the incremented static counter, i.e. a new ID
	 */
	public int generateID() {
		counterToGenerateIDs += 1;
		return counterToGenerateIDs;
	}
}
