package core;

import java.util.HashMap;

/** 
 * Represents a concrete plus station.
 * @author Mathieu Sibu�
*/
public class PlusStation extends Station {
	
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
	@Override
	void chargeUser(User user, int duration) {
		if (super.isTerminalOutOfOrder()) {
			throw new RuntimeException("Terminal of station "+super.getID()+" not working: go to closest station");
			//or just print something?
		} else {
			double cost;
			if (user.getRegistrationCard() == null) {
				if (user.getRentedBicycle() instanceof MechanicalBicycle) {
					cost = duration/60 * Station.getFeesForUserWithNoCard().get("mechanical");	
				} else if (user.getRentedBicycle() instanceof ElectricalBicycle) {
					cost = duration/60 * Station.getFeesForUserWithNoCard().get("electrical");
				}
			} else {
				CardVisitor cardVisitor = new ConcreteCardVisitor();
				//utiliser la fonction d'Ali
				Card userCard = user.getRegistrationCard();
				cost = userCard.accept(cardVisitor, duration);		//est cens�e �tre modifi�e
				userCard.setTimeCredit(userCard.getTimeCredit() + additionalTimeCredit);
			}
			user.setCreditCardBalance(user.getCreditCardBalance() - cost);
			user.setMyVelibTotalCharges(user.getMyVelibTotalCharges() + cost);
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
