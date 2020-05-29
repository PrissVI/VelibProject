package core;

/**
 * <b>Bicycle is a class that represents the bicycles that will be available in the Velib Network.</b>
 * <p>
 * An instance of Bycicle is characterized by :
 * <ul>
 * <li>An ID</li>
 * </ul>
 * </p>
 * @author Ali Raïki
 *
 */
public class Bicycle {

	private int ID;
	private static int counter = 0; //for the ID
	
	/**
	 * Constructor of the object.
	 */
	public Bicycle() {
		counter++;
		this.ID = counter;
	}

	/**
	 * Return the ID of a bicycle.
	 * @return an integer that is the ID of a bicycle.
	 */
	public int getID() {
		return ID;
	}
	
}
