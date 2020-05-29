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

	/*METHODS*/
	//explicit method from superclass
	//POURQUOI NE PAS LA METTRE DANS LA CLASSE ABSTRAITE PARENTE PUIS JUSTE METTRE UN TRY CATCH QD ON OVERRIDE LA METHODE?
	@Override
	void chargeUser(User user, int duration) throws RuntimeException {
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
				//utiliser la fonction d'Ali
			}
			user.setCreditCardBalance(user.getCreditCardBalance() - cost);
			user.setMyVelibTotalCharges(user.getMyVelibTotalCharges() + cost);
		}
	}
	
	//toString
	@Override
	public String toString() {
		return super.toString() + "\n"
				+ "- station type: standard, no additional time credit";
	}
}
