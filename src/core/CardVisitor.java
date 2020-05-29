package core;

import java.util.ArrayList;

public interface CardVisitor {
	
	/**
	 * Computes the corresponding cost of the ride based on the ride duration (in minutes), 
	 * and can change the timeCredit the user has if it is used.
	 * @param vlibreCard
	 *				Registration card of type Vlibre
	 * @param rideDuration
	 *				Time spent on the bike
	 * @return an ArrayList corresponding to the cost of the ride if the bike is mechanical (index 0) or electrical (index 1).
	 */
	double visit(Vlibre vlibreCard, int rideDuration, Bicycle bike);
	
	/**
	 * Computes the corresponding cost of the ride based on the ride duration (in minutes), 
	 * and can change the timeCredit the user has if it is used.
	 * @param vlibreCard
	 *				Registration card of type Vmax
	 * @param rideDuration
	 *				Time spent on the bike
	 * @return an ArrayList corresponding to the cost of the ride if the bike is mechanical (index 0) or electrical (index 1).
	 */
	double visit(Vmax vmaxCard, int rideDuration, Bicycle bike);
}
