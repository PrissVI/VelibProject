package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.Test;

import core.ActivityLog;
import core.DateComparatorForActivityLogs;

class DateComparatorForActivityLogsTest {

	/**
	 * Test of the compare(...) method from the class DateComparatorForActivityLogs to sort ActiviyLogs (in ascending order) with respect to their date using getDateDiff(...)
	 */
	@Test
	void test() {
		Date d1 = ActivityLog.getDate(2020, 4, 23, 2, 0, 0);
		Date d2 = ActivityLog.getDate(2020, 4, 23, 2, 10, 0);
		
		ActivityLog al1 = new ActivityLog(false, d2);
		ActivityLog al2 = new ActivityLog(false, d1);
		
		ArrayList<ActivityLog> als = new ArrayList<ActivityLog>();
		als.add(al1);
		als.add(al2);
		
		als.sort(new DateComparatorForActivityLogs());
		
		assertEquals(als.indexOf(al1),1);
		assertEquals(als.indexOf(al2),0);
	}

}
