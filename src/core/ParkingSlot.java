package core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

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
	private ArrayList<ActivityLog> activityLogs;
	
	
	/*CONSTRUCTORS*/
	public ParkingSlot(Bicycle bicycleStored) {
		super();
		this.bicycleStored = bicycleStored;
		isOutOfOrder = false;
		ID = this.generateID();
		activityLogs = new ArrayList<ActivityLog>();
	}

	public ParkingSlot() {
		super();
		bicycleStored = null;
		isOutOfOrder = false;
		ID = this.generateID();
		activityLogs = new ArrayList<ActivityLog>();
	}
	
	
	/*METHODS*/
	//getters and setters
	public Bicycle getBicycleStored() {
		return bicycleStored;
	}

	//custom setter that only allows to set a bicycle in a free, on service slot
	public void setBicycleStored(Bicycle bicycleStored, Date date) throws RuntimeException {
		if (isOutOfOrder) {
			throw new RuntimeException("Cannot set stored bike of parking slot " + ID + ": parking bay is out of order");
		} else if (this.bicycleStored != null && bicycleStored != null) {
			throw new RuntimeException("Cannot store bike in parking slot " + ID + ": parking bay is already occupied");
		} else if (this.bicycleStored != bicycleStored) {
			if (bicycleStored == null) {
				ActivityLog al = new ActivityLog(false, date);
				activityLogs.add(al);
			} else {
				ActivityLog al = new ActivityLog(true, date);
				activityLogs.add(al);				
			}
			this.bicycleStored = bicycleStored;
		}
	}

	public boolean isOutOfOrder() {
		return isOutOfOrder;
	}

	//custom setter that only allows to set a slot out of order if it is free (so that the bicycle it stores is not "lost")
	public void setOutOfOrder(boolean isOutOfOrder, Date date) throws RuntimeException {
		if (bicycleStored != null) {
			throw new RuntimeException("Cannot set parking slot "+ID+" to out of order: it is storing bicycle "+bicycleStored.getID());
		} else {
			if (isOutOfOrder != this.isOutOfOrder) {
				this.isOutOfOrder = isOutOfOrder;
				this.notifyObserver(this.isOutOfOrder);
				if (isOutOfOrder) {
					activityLogs.add(new ActivityLog(true, date));
				} else {
					activityLogs.add(new ActivityLog(false, date));
				}
			}	// si pas de chgt on ne fait RIEN
		}
	}

	public ArrayList<ActivityLog> getActivityLogs() {
		return activityLogs;
	}

	public void setActivityLogs(ArrayList<ActivityLog> activityLogs) {
		this.activityLogs = activityLogs;
	}

	public int getID() {
		return ID;
	}	
	
	//toString
	@Override
	public String toString() {
		return "ParkingSlot " + ID + ":\n"
				+ "- bicycleStored: " + (bicycleStored == null? "no bicycle stored": bicycleStored.toString()) + "\n"
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
		if (uniqueStationObserver == null) {
			return;
		}
		uniqueStationObserver.update(newIsOutOfOrder);
	}
}
