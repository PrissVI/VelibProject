package core;

import java.util.ArrayList;

public class ConcreteCardVisitor implements CardVisitor {

	@Override
	public ArrayList<Double> visit(Vlibre vlibreCard, int rideDuration) {
		//Initializing the variables
				int excessTimeBalance; //To compute the timeCredit left after the ride
				double mechanicalCost = 0;
				double electricalCost = 1;
				ArrayList<Double> cost = new ArrayList<Double>(); //To store the different costs
				
				if (rideDuration <= 60) { 
					cost.add(mechanicalCost);
					cost.add(electricalCost);
					return cost;
				}
				else {
					if(vlibreCard.getTimeCredit() == 0) {
						mechanicalCost = (rideDuration - 60)/60;
						electricalCost = (rideDuration - 60)/30;
						cost.add(mechanicalCost);
						cost.add(electricalCost);
						return cost;
					}
					else {
						excessTimeBalance = 60 - (rideDuration - vlibreCard.getTimeCredit());
						if(excessTimeBalance <= 0) {
							mechanicalCost = -excessTimeBalance/60;
							electricalCost = -excessTimeBalance/30;
							vlibreCard.setTimeCredit(0);
							cost.add(mechanicalCost);
							cost.add(electricalCost);
							return cost;
						}
						else{
							vlibreCard.setTimeCredit(excessTimeBalance);
							cost.add(mechanicalCost);
							cost.add(electricalCost);
							return cost;
						}
					}
				}
	}

	@Override
	public ArrayList<Double> visit(Vmax vmaxCard, int rideDuration) {
		//Initializing the variables
		int excessTimeBalance; //To compute the timeCredit left after the ride
		double rideCost = 0;
		ArrayList<Double> cost = new ArrayList<Double>(); //To store the different costs
		
		if (rideDuration <= 60) { 
			cost.add(rideCost);
			cost.add(rideCost);
			return cost;
		}
		else {
			if(vmaxCard.getTimeCredit() == 0) {
				rideCost = (rideDuration - 60)/60;
				cost.add(rideCost);
				cost.add(rideCost);
				return cost;
			}
			else {
				excessTimeBalance = 60 - (rideDuration - vmaxCard.getTimeCredit());
				if(excessTimeBalance <= 0) {
					rideCost = -excessTimeBalance/60;
					vmaxCard.setTimeCredit(0);
					cost.add(rideCost);
					cost.add(rideCost);
					return cost;
				}
				else{
					vmaxCard.setTimeCredit(excessTimeBalance);
					cost.add(rideCost);
					cost.add(rideCost);
					return cost;
				}
			}
		}
	}
}
