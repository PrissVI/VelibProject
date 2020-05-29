package core;

import java.util.ArrayList;

/**
 * <b>Vmax is a type of Card, and represents a type of registration card of a user.</b>
 * <p>This class extends the class Card.</p>
 * 
 * @see Card
 * @author Ali Ra�ki
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
	public double accept(CardVisitor visitor, int rideDuration, Bicycle bike) {
		return visitor.visit(this, rideDuration, bike);
	}
}
