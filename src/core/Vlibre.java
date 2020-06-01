package core;


/**
 * <b>Vlibre is a type of Card, and represents a type of registration card of a user.</b>
 * <p>This class extends the class Card.</p>
 * 
 * @see Card
 * @author Ali Raïki
 */
public class Vlibre extends Card {
	
	private static final long serialVersionUID = 6106881719246599397L;

	/**
	 * Constructors of the Vlibre object.
	 * @param timeCreditBalance
	 */
	public Vlibre() {
		super();
	}
	
	public Vlibre(int timeCreditBalance) {
		super(timeCreditBalance);
	}
	
	@Override
	public String toString() {
		return "Vlibre Card with a time credit balance of " + this.getTimeCredit();
	}
	
	@Override
	public double accept(CardVisitor visitor, int rideDuration, Bicycle bike) {
		return visitor.visit(this, rideDuration, bike);
	}
}
