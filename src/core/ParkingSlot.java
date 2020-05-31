package core;

import java.io.Serializable;

/** 
 * Represents a parking slot in a station.
 * @author Mathieu Sibué
*/
public class ParkingSlot extends Slot implements ParkingSlotObservable, Serializable {

	private static final long serialVersionUID = 36892121L;
	/*ATTRIBUTES*/
	private static int counterToGenerateIDs = 0;
	private int ID;
	private Bicycle bicycleStored;
	private boolean isOutOfOrder;
	private StationObserver uniqueStationObserver;
	//for statistics:
	//faire une liste de log d'activité
	
	
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
		bicycleStored = null;
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
			//ou sysout?
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
			//ou sysout?
		} else {
			if (isOutOfOrder != this.isOutOfOrder) {
				this.isOutOfOrder = isOutOfOrder;
				this.notifyObserver(isOutOfOrder);
			}	// si pas de chgt on ne fait RIEN
		}
	}

	public int getID() {
		return ID;
	}	
	
	//toString
	@Override
	public String toString() {
		return "ParkingSlot " + ID + ":\n"
				+ "- bicycleStored: " + (bicycleStored == null? "no bicycle stored": bicycleStored.toString())
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
	final public int generateID() {
		counterToGenerateIDs += 1;
		return counterToGenerateIDs;
	}

	//methods to override to implement the ParkingSlotObservable interface
	@Override
	public void registerObserver(StationObserver observer) {
		uniqueStationObserver = observer;
	}

	@Override
	public void removeObserver(StationObserver observer) {
		uniqueStationObserver = null;
	}

	@Override
	public void notifyObserver(boolean newIsOutOfOrder) {
		uniqueStationObserver.update(newIsOutOfOrder);
	}
}
