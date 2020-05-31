package core;

import java.util.Date;

public class ActivityLogTest {

	public static void main(String[] args) {
		//test1 pr getDate
		Date date1 = ActivityLog.getDate(2020, 5, 1, 11, 0, 0);
		Date date2 = ActivityLog.getDate(2020, 5, 1, 13, 0, 0);
		
		System.out.println(date1);
		// faire 1 test où on regarde le jour obtenu, l'h, les mins, les seconds, l'année (avec un print ?)
		//ATTENTION MOIS COMMENCENT A 0
		
		//test2 pr getDateDiff
		int dateMinDiff = ActivityLog.getDateDiff(date1, date2);
		System.out.println(dateMinDiff); //120
		
		
		//test3 pr getDateDiff
		Date date3 = ActivityLog.getDate(2020, 5, 1, 11, 0, 0);
		Date date4 = ActivityLog.getDate(2020, 5, 2, 10, 0, 0);
	
		int dateMinDiff2 = ActivityLog.getDateDiff(date3, date4);
		System.out.println(dateMinDiff2); //1380 = 23x60
		
		
		//test4 pr getDateDiff
		Date date5 = ActivityLog.getDate(2020, 5, 1, 11, 5, 0);
		Date date6 = ActivityLog.getDate(2020, 5, 1, 11, 0, 4);
		
		int dateMinDiff3 = ActivityLog.getDateDiff(date5, date6);
		System.out.println(dateMinDiff3); //- 4 expected since it rounds result to lower minute unit
		
		
	}
	
}
