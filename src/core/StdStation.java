package core;

import java.util.HashMap;

/** 
 * Represents a concrete standard station.
 * @author Mathieu Sibué
 * @version 1.0
*/
public class StdStation extends Station {
	
	/*CONSTRUCTORS*/
	public StdStation(double x, double y) {
		super(x, y);
	}

	public StdStation(double x, double y, HashMap<Integer, ParkingSlot> parkingSlots) {
		super(x, y, parkingSlots);
	}

	/*METHODS*/
	//explicit from superclass
	@Override
	void chargeUser(User user) {
		// TODO Auto-generated method stub
		
	}
	
	
	//toString
	@Override
	public String toString() {
		return super.toString() + "\n"
				+ "- station type: standard, no additional time credit";
	}
}
