package core;

import java.util.ArrayList;

/**
 * <b>Vmax is a type of Card, and represents a type of registration card of a user.</b>
 * <p>This class extends the class Card.</p>
 * 
 * @see core.Card
 * 
 */
public class Vmax extends Card {

	/**
	 * Constructor of the Vmax object.
	 * @param timeCreditBalance
	 */
	public Vmax(int timeCreditBalance) {
		super(timeCreditBalance);
	}
	
	@Override
	public ArrayList<Double> accept(CardVisitor visitor, int rideDuration) {
		return visitor.visit(this, rideDuration);
	}
}
