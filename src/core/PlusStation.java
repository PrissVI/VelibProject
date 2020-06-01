package core;

import java.util.HashMap;

/** 
 * Represents a concrete plus station.
 * @author Mathieu Sibué
*/
public class PlusStation extends Station {

	private static final long serialVersionUID = 4875724235326482414L;
	
	/*ATTRIBUTES*/
	final static private int additionalTimeCredit = 5;	//in minutes

	/*CONSTRUCTORS*/
	public PlusStation(double x, double y) {
		super(x, y);
	}

	public PlusStation(double x, double y, HashMap<Integer, ParkingSlot> parkingSlots) {
		super(x, y, parkingSlots);
	}

	/*METHODS*/
	//explicit method from superclass
	@Override
	public void chargeUser(User user, int duration) throws RuntimeException {
		/*
		try {
			super.chargeUser(user, duration);
			Card userCard = user.getRegistrationCard();
			userCard.setTimeCredit(userCard.getTimeCredit() + additionalTimeCredit);
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		*/
		super.chargeUser(user, duration);
		Card userCard = user.getRegistrationCard();
		if (userCard != null) {
			userCard.setTimeCredit(userCard.getTimeCredit() + additionalTimeCredit);		
		}
	}
	
	//getters and setters
	public int getAdditionalTimeCredit() {
		return additionalTimeCredit;
	}

	//toString
	@Override
	public String toString() {
		return super.toString() + "\n"
				+ "- station type: plus, additional time credit of " + additionalTimeCredit + "\n";
	}
}
