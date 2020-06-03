package core;

//import java.util.ArrayList;
import java.util.Comparator;
//import java.util.Date;

public class DateComparatorForActivityLogs implements Comparator<ActivityLog> {

	@Override
	public int compare(ActivityLog arg0, ActivityLog arg1) {
		return -ActivityLog.getDateDiff(arg0.getDate(), arg1.getDate());
	}
	
}
