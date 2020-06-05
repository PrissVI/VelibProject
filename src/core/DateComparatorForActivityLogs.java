package core;

import java.util.Comparator;


/**
 * Implementation of the interface Comparator (of ActivityLogs) to order the activity logs stored in each parking slot.
 * @author Mathieu Sibué
 *
 */
public class DateComparatorForActivityLogs implements Comparator<ActivityLog> {

	@Override
	public int compare(ActivityLog arg0, ActivityLog arg1) {
		return -ActivityLog.getDateDiff(arg0.getDate(), arg1.getDate());
	}
	
}
