package core;

public class ConcreteCardVisitor implements CardVisitor {

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
					electricalCost = (double) (rideDuration - 60) / 30;
					return electricalCost;}
			} else {
				excessTimeBalance = 60 - (rideDuration - vlibreCard.getTimeCredit());
				if (excessTimeBalance <= 0) {
					vlibreCard.setTimeCredit(0);
					if(bike instanceof MechanicalBike) {
						mechanicalCost = (double) -excessTimeBalance / 60;
						return mechanicalCost;}
					else {
						electricalCost = (double) -excessTimeBalance / 30;
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
