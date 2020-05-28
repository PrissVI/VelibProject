package core;

import java.util.HashMap;

/** 
 * Represents a concrete plus station.
 * @author Mathieu Sibué
 * @version 1.0
*/
public class PlusStation extends Station {
	
	/*ATTRIBUTES*/
	static private int additionalTimeCredit = 5;	//in minutes

	/*CONSTRUCTORS*/
	public PlusStation(double x, double y) {
		super(x, y);
		//additionalTimeCredit = 5;	
	}

	public PlusStation(double x, double y, HashMap<Integer, ParkingSlot> parkingSlots) {
		super(x, y, parkingSlots);
		//additionalTimeCredit = 5;
	}

	/*METHODS*/
	//explicit from superclass
	@Override
	void chargeUser(User user) {
		// TODO Auto-generated method stub
		
	}
	
	//getters and setters
	public int getAdditionalTimeCredit() {
		return additionalTimeCredit;
	}

	//toString
	@Override
	public String toString() {
		return super.toString() + "\n"
				+ "- station type: plus, additional time credit of " + additionalTimeCredit;
	}
}
