package core;

import java.util.ArrayList;

/**
 * <b>Vlibre is a type of Card, and represents a type of registration card of a user.</b>
 * <p>This class extends the class Card.</p>
 * 
 * @see Card
 * @author Ali Ra�ki
 */
public class Vlibre extends Card {
	
	/**
	 * Constructor of the Vlibre object.
	 * @param timeCreditBalance
	 */
	public Vlibre(int timeCreditBalance) {
		super(timeCreditBalance);
	}

	
	@Override
	public double accept(CardVisitor visitor, int rideDuration, Bicycle bike) {
		return visitor.visit(this, rideDuration, bike);
	}
}