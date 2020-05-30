package core;

public interface CardVisitor {
	
	/**
	 * Computes the corresponding cost of the ride based on the ride duration (in minutes), 
	 * and can change the timeCredit the user has if it is used.
	 * @param vlibreCard
	 *				Registration card of type Vlibre
	 * @param rideDuration
	 *				Time spent on the bike
	 *@param bike
	 *				The bicycle that was rented (to get the type of bicycle)
	 * @return an double corresponding to the cost of the ride.
	 */
	double visit(Vlibre vlibreCard, int rideDuration, Bicycle bike);
	
	/**
	 * Computes the corresponding cost of the ride based on the ride duration (in minutes), 
	 * and can change the timeCredit the user has if it is used.
	 * @param vlibreCard
	 *				Registration card of type Vmax
	 * @param rideDuration
	 *				Time spent on the bike
	 * *@param bike
	 *				The bicycle that was rented (to get the type of bicycle)
	 * @return a double corresponding to the cost of the ride.
	 */
	double visit(Vmax vmaxCard, int rideDuration, Bicycle bike);
}
