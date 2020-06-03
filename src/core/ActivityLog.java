package core;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/** 
 * Represents an activity log from the logs of parking slots
 * @author Mathieu Sibué
*/
public class ActivityLog implements Serializable {
	
	private static final long serialVersionUID = 3441618693126607150L;
	/*ATTRIBUTES*/
	private static int counter = 0;
	private int ID;
	private boolean isSetToOccupied;
	private Date date;
	
	/*CONSTRUCTOR*/
	public ActivityLog(boolean isSetToOccupied, Date date) {
		super();
		ID = counter;
		counter++;
		this.isSetToOccupied = isSetToOccupied;
		this.date = date;
	}

	/*METHODS*/
	//custom methods
	/**
	 * Computes a diff between two dates.
	 * @param Date: date1, the oldest date
	 * @param Date: date2, the newest date
	 * @return the diff value, in minutes
	 */
	public static int getDateDiff(Date date1, Date date2) {
	    long diffInMillies = date2.getTime() - date1.getTime();
	    return (int) TimeUnit.MINUTES.convert(diffInMillies,TimeUnit.MILLISECONDS);
	}
	
	/**
	 * Creates a Date object.
	 * @param int: the year of the date
	 * @param int: the month of the date
	 * @param int: the day of the date
	 * @param int: the hour of the date
	 * @param int: the minute of the date
	 * @param int: the second of the date
	 * @return Date: the date representation of the inputs
	 */
    public static Date getDate(int year, int month, int day, int hour, int minute, int second) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, second);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
	
	
	//getters and setters
	public boolean isSetToOccupied() {
		return isSetToOccupied;
	}

	public void setSetToOccupied(boolean isSetToOccupied) {
		this.isSetToOccupied = isSetToOccupied;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getID() {
		return ID;
	}
	

	//toString
	@Override
	public String toString() {
		return "ActivityLog at date " + date.toString() + " => slot set to " + (isSetToOccupied? "occupied": "free");
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ID;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + (isSetToOccupied ? 1231 : 1237);
		return result;
	}
	
}
