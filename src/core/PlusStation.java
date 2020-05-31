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
	//POURQUOI NE PAS LA METTRE DANS LA CLASSE ABSTRAITE PARENTE PUIS JUSTE METTRE UN TRY CATCH QD ON OVERRIDE LA METHODE?
	/*
	@Override
	void chargeUser(User user, int duration) throws RuntimeException {
		if (super.isTerminalOutOfOrder()) {
			throw new RuntimeException("Terminal of station "+super.getID()+" not working: go to closest station");
			//or just print something?
		} else {
			double cost = 0;	//is there a pb with it?
			if (user.getRegistrationCard() == null) {
				if (user.getRentedBicycle() instanceof MechanicalBike) {
					cost = duration/60 * Station.getFeesForUserWithNoCard().get("mechanical");	
				} else if (user.getRentedBicycle() instanceof ElectricalBike) {
					cost = duration/60 * Station.getFeesForUserWithNoCard().get("electrical");
				}
			} else {
				CardVisitor cardVisitor = new ConcreteCardVisitor();
				//utiliser la fonction d'Ali
				Card userCard = user.getRegistrationCard();
				cost = userCard.accept(cardVisitor, duration, user.getRentedBicycle());		//est censée être modifiée
				userCard.setTimeCredit(userCard.getTimeCredit() + additionalTimeCredit);
			}
			user.setCreditCardBalance(user.getCreditCardBalance() - cost);
			user.setMyVelibTotalCharges(user.getMyVelibTotalCharges() + cost);
		}
	}
	*/
	@Override
	public void chargeUser(User user, int duration) {
		try {
			super.chargeUser(user, duration);
			Card userCard = user.getRegistrationCard();
			userCard.setTimeCredit(userCard.getTimeCredit() + additionalTimeCredit);
		} catch(Exception e) {
			System.out.println(e.getMessage());
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
				+ "- station type: plus, additional time credit of " + additionalTimeCredit;
	}
}
