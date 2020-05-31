package core;

import java.util.HashMap;

/** 
 * Represents a concrete standard station.
 * @author Mathieu Sibué
*/
public class StdStation extends Station {
	
	/*CONSTRUCTORS*/
	public StdStation(double x, double y) {
		super(x, y);
	}

	public StdStation(double x, double y, HashMap<Integer, ParkingSlot> parkingSlots) {
		super(x, y, parkingSlots);
	}

	/*
	@Override
	//should we keep throwing the exception?
	public void chargeUser(User user, int duration) {
		try {
			super.chargeUser(user, duration);
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	*/
	
	//toString
	@Override
	public String toString() {
		return super.toString() + "\n"
				+ "- station type: standard, no additional time credit";
	}
}
