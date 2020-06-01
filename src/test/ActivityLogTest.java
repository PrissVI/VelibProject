package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.Test;

import core.ActivityLog;

class ActivityLogTest {

	/**
	 * Test for the getDate(...) method
	*/
	@Test
	void test1() {
		Date date1 = ActivityLog.getDate(2020, 5, 1, 11, 0, 0);
		//warning: the month counter starts at 0.
		String correspondingDate = "Mon Jun 01 11:00:00 CEST 2020";
		assertEquals(date1.toString(), correspondingDate);
	}
	
	/**
	 * Test for the getDateDiff(...) method: checks whether the diff is exact when only hour changes
	*/
	@Test
	void test2() {
		Date date1 = ActivityLog.getDate(2020, 5, 1, 11, 0, 0);
		Date date2 = ActivityLog.getDate(2020, 5, 1, 13, 0, 0);
		
		int dateMinDiff = ActivityLog.getDateDiff(date1, date2);

		assertEquals(dateMinDiff, 120);
	}
	
	/**
	 * Test for the getDateDiff(...) method: checks whether the diff is exact when hour and day change
	*/
	@Test
	void test3() {
		Date date1 = ActivityLog.getDate(2020, 5, 1, 11, 0, 0);
		Date date2 = ActivityLog.getDate(2020, 5, 2, 10, 0, 0);
		
		int expectedDiff = 23 * 60;
		
		int dateMinDiff = ActivityLog.getDateDiff(date1, date2);

		assertEquals(dateMinDiff, expectedDiff);
	}

	/**
	 * Test for the getDateDiff(...) method: checks whether the diff is exact when hour, day, minutes and seconds change (you get the idea)
	*/
	@Test
	void test4() {
		Date date1 = ActivityLog.getDate(2020, 5, 1, 11, 5, 0);
		Date date2 = ActivityLog.getDate(2020, 5, 1, 11, 0, 4);
		
		int dateMinDiff = ActivityLog.getDateDiff(date1, date2);
		
		//date2 is before date1 => negative difference, lower-rounded to 4 because of the seconds
		assertEquals(dateMinDiff, -4);
	}
}
