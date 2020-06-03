package core;

import java.io.Serializable;
import java.util.Date;


/**
 * <b>Bicycle is a class that represents the bicycles that will be available in the Velib Network.</b>
 * <p>
 * An instance of Bicycle is characterized by :
 * <ul>
 * <li>An ID</li>
 * </ul>
 * </p>
 * @author Ali Raïki
 *
 */
public abstract class Bicycle implements Serializable {

	private static final long serialVersionUID = 545691321L;
	private int ID;
	private static int counter = 0; //for the ID
	private Date rentDate;
	
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
	
	public Date getRentDate() {
		return rentDate;
	}

	public void setRentDate(Date rentDate) {
		this.rentDate = rentDate;
	}

	@Override
	public String toString() {
		return "Bicycle " + ID + (rentDate == null? " not rented currently": " currently rented since " + rentDate.toString());
	}
}
