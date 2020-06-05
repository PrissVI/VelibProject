package core;

import java.util.HashMap;

/** 
 * Represents a concrete standard station.
 * @author Mathieu Sibué
*/
public class StdStation extends Station {
	
	private static final long serialVersionUID = 2787914000480818404L;

	/*CONSTRUCTORS*/
	public StdStation(double x, double y) {
		super(x, y);
	}

	public StdStation(double x, double y, HashMap<Integer, ParkingSlot> parkingSlots) {
		super(x, y, parkingSlots);
	}
	
	
	//toString
	@Override
	public String toString() {
		return super.toString() + "\n"
				+ "- station type: standard, no additional time credit";
	}
}
