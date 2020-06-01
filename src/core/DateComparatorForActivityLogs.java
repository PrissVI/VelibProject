package core;

//import java.util.ArrayList;
import java.util.Comparator;
//import java.util.Date;

public class DateComparatorForActivityLogs implements Comparator<ActivityLog> {

	@Override
	public int compare(ActivityLog arg0, ActivityLog arg1) {
		return -ActivityLog.getDateDiff(arg0.getDate(), arg1.getDate());
	}
	
	/*
	public static void main(String[] args) {
		Date d1 = ActivityLog.getDate(2020, 4, 23, 2, 0, 0);
		Date d2 = ActivityLog.getDate(2020, 4, 23, 2, 10, 0);
		
		ActivityLog al1 = new ActivityLog(false, d2);
		ActivityLog al2 = new ActivityLog(false, d1);
		
		ArrayList<ActivityLog> als = new ArrayList<ActivityLog>();
		als.add(al1);
		als.add(al2);
		
		als.sort(new DateComparatorForActivityLogs());
		
		System.out.println(als);
	}
	*/
	
}
