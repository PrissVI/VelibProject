package core;

/**
 * ConcreteCardVisitor class that implements the CardVisitor interface, for the Visitor Pattern
 * @see CardVisitor
 * @author Ali Raïki
 */

public class ConcreteCardVisitor implements CardVisitor {

	/**
	 * Computes the corresponding cost of the ride based on the ride duration (in minutes), 
	 * and can change the timeCredit the user has if it is used.
	 * @param vlibreCard
	 *				Registration card of type Vlibre
	 * @param rideDuration
	 *				Time spent on the bike
	 * @param bike
	 *				The bicycle that was rented (to get the type of bicycle)
	 * @return an double corresponding to the cost of the ride.
	 */
	@Override
	public double visit(Vlibre vlibreCard, int rideDuration, Bicycle bike) {
		//Initializing the variables
		double excessTimeBalance; // To compute the timeCredit left after the ride
		double mechanicalCost = 0;
		double electricalCost = 1;

		if (rideDuration <= 60) {
			if(bike instanceof MechanicalBike) {return mechanicalCost;} 
			else {return electricalCost;}
		} else {
			if (vlibreCard.getTimeCredit() == 0) {
				if(bike instanceof MechanicalBike) {
					mechanicalCost = (double) (rideDuration - 60) / 60;
					return mechanicalCost;}
				else {
					electricalCost = (double) (rideDuration - 60) / 30 + 1;
					return electricalCost;}
			} else {
				excessTimeBalance = 60 - (rideDuration - vlibreCard.getTimeCredit());
				if (excessTimeBalance <= 0) {
					vlibreCard.setTimeCredit(0);
					if(bike instanceof MechanicalBike) {
						mechanicalCost = (double) -excessTimeBalance / 60;
						return mechanicalCost;}
					else {
						electricalCost = (double) -excessTimeBalance / 30 + 1;
						return electricalCost;}
				} else {
					vlibreCard.setTimeCredit((int) excessTimeBalance);
					if(bike instanceof MechanicalBike) {
						return mechanicalCost;}
					else {
						return electricalCost;}
				}
			}
		}
	}

	/**
	 * Computes the corresponding cost of the ride based on the ride duration (in minutes), 
	 * and can change the timeCredit the user has if it is used.
	 * @param vmaxCard
	 *				Registration card of type Vmax
	 * @param rideDuration
	 *				Time spent on the bike
	 * @param bike
	 *				The bicycle that was rented (to get the type of bicycle)
	 * @return a double corresponding to the cost of the ride.
	 */
	@Override
	public double visit(Vmax vmaxCard, int rideDuration, Bicycle bike) {
		//Initializing the variables
		double excessTimeBalance; //To compute the timeCredit left after the ride
		double rideCost = 0;
		
		if (rideDuration <= 60) { 
			return rideCost;
		}
		else {
			if(vmaxCard.getTimeCredit() == 0) {
				rideCost = (double) (rideDuration - 60)/60;
				return rideCost;
			}
			else {
				excessTimeBalance = 60 - (rideDuration - vmaxCard.getTimeCredit());
				if(excessTimeBalance <= 0) {
					rideCost = (double) -excessTimeBalance/60;
					vmaxCard.setTimeCredit(0);
					return rideCost;
				}
				else{
					vmaxCard.setTimeCredit((int) excessTimeBalance);
					return rideCost;
				}
			}
		}
	}
}
