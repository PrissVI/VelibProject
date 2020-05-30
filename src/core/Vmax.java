package core;


/**
 * <b>Vmax is a type of Card, and represents a type of registration card of a user.</b>
 * <p>This class extends the class Card.</p>
 * 
 * @see Card
 * @author Ali Raïki
 */
public class Vmax extends Card {

	/**
	 * Constructors of the Vmax object.
	 * @param timeCreditBalance
	 */
	public Vmax() {
		super();
	}
	
	public Vmax(int timeCreditBalance) {
		super(timeCreditBalance);
	}
	
	@Override
	public double accept(CardVisitor visitor, int rideDuration, Bicycle bike) {
		return visitor.visit(this, rideDuration, bike);
	}
}
