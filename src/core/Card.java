package core;

/**
 * <b>Card is a class that represents the registration card of a user.</b>
 * <p>
 * An instance of Card is characterized by :
 * <ul>
 * <li>a time-credit balance (expressed in minutes) representing the credit gained by
returning bicycles to “plus” stations.</li>
 * </ul>
 * </p>
 * @author Ali Raïki
 * 
 */

public abstract class Card {
	
    /**
     * timeCreditBalance represents the credit gained (in minutes) by
	returning bicycles to “plus” stations
	<p>The time credit is used to compute the actual
	cost of a bike ride.</p>
     */
	private int timeCreditBalance; //in minutes
	
	/**
	 * Constructor of the object.
	 * @param timeCreditBalance
	 */
	public Card(int timeCreditBalance) {
		this.timeCreditBalance = timeCreditBalance;
	}

	/**
	 * Return the time credit balance (in minutes).
	 * @return an integer that represents the credit gained by returning bicycles to "plus" stations
	 */
	public int getTimeCredit() {
		return timeCreditBalance;
	}

	/**
	 * Updates the time credit balance (in minutes).
	 * @param timeCreditBalance
	 */
	public void setTimeCredit(int timeCreditBalance) {
		this.timeCreditBalance = timeCreditBalance;
	}
	
	@Override
	public int hashCode() {
		int result = 5;
		result = 41 * result + timeCreditBalance;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Card other = (Card) obj;
		if (timeCreditBalance != other.timeCreditBalance)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Card with a time credit balance of " + timeCreditBalance;
	}

	public abstract double accept(CardVisitor visitor, int rideDuration, Bicycle bike);	
}
