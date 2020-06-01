package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.Test;

import core.ActivityLog;
import core.Bicycle;
import core.MechanicalBike;
import core.ParkingSlot;

class ParkingSlotTest {
	
	/**
	 * Test of the setBicycleStored(...) (custom setter) method: see if a log is created when we store a bicycle
	*/
	@Test
	void test1() {
		ParkingSlot ps1 = new ParkingSlot();
		Date date1 = ActivityLog.getDate(2020, 10, 5, 0, 0, 0);
		Bicycle bike1 = new MechanicalBike();
		
		ps1.setBicycleStored(bike1, date1);
		
		assertEquals(ps1.getBicycleStored(), bike1);
		
		ArrayList<ActivityLog> expectedLog = new ArrayList<ActivityLog>();
		expectedLog.add(new ActivityLog(true, date1));
		
		//of course not equal because these logs don't have the same IDs (impossible bc of the static counter) AND have different memory locations
		assertEquals(ps1.getActivityLogs().toString(), expectedLog.toString());
	}

	/**
	 * Test of the setOutOfOrder(...) (custom setter) method: see if an Exception is thrown when trying to set out of order a parking slot storing a Bicycle (for it not to disappear...)
	*/
	@Test
	void test2() {
		ParkingSlot ps1 = new ParkingSlot();
		Date date1 = ActivityLog.getDate(2020, 10, 5, 0, 0, 0);
		Bicycle bike1 = new MechanicalBike();
		ps1.setBicycleStored(bike1, date1);
		
		Date date2 = ActivityLog.getDate(2020, 10, 5, 1, 0, 0);

		Exception exception = assertThrows(RuntimeException.class, () -> {
			ps1.setOutOfOrder(true, date2); 
		});
		
		String expectedMessage = "Cannot set parking slot "+ps1.getID()+" to out of order: it is storing bicycle "+bike1.getID();
		String actualMessage = exception.getMessage();
		
		assertEquals(expectedMessage,actualMessage);
	}
	
	/**
	 * Test of the setBicycleStored(...) (custom setter) method: see if an Exception is thrown when trying to put a bike in a parking slot already storing one
	*/
	@Test
	void test3() {
		ParkingSlot ps1 = new ParkingSlot();
		Date date1 = ActivityLog.getDate(2020, 10, 5, 0, 0, 0);
		Bicycle bike1 = new MechanicalBike();
		ps1.setBicycleStored(bike1, date1);
		
		Bicycle bike2 = new MechanicalBike();
		Date date2 = ActivityLog.getDate(2020, 10, 5, 1, 0, 0);
		
		Exception exception = assertThrows(RuntimeException.class, () -> {
			ps1.setBicycleStored(bike2, date2); 
		});
		
		String expectedMessage = "Cannot store bike in parking slot " + ps1.getID() + ": parking bay is already occupied";
		String actualMessage = exception.getMessage();
		
		assertEquals(expectedMessage,actualMessage);
	}
	
	/**
	 * Test of the setBicycleStored(...) (custom setter) method: see if an Exception is thrown when trying to put a bike in a parking slot out of order
	*/
	@Test
	void test4() {
		ParkingSlot ps1 = new ParkingSlot();
		Date date1 = ActivityLog.getDate(2020, 10, 5, 0, 0, 0);
		ps1.setOutOfOrder(true, date1);
		
		Bicycle bike1 = new MechanicalBike();
		Date date2 = ActivityLog.getDate(2020, 10, 5, 1, 0, 0);
		
		Exception exception = assertThrows(RuntimeException.class, () -> {
			ps1.setBicycleStored(bike1, date2); 
		});
		
		String expectedMessage = "Cannot set stored bike of parking slot " + ps1.getID() + ": parking bay is out of order";
		String actualMessage = exception.getMessage();
		
		assertEquals(expectedMessage,actualMessage);
	}
	
	
	/**
	 * Test of the setBicycleStored(...) (custom setter) method: see if multiple logs are created in correct order
	*/
	@Test
	void test5() {
		//we first create a parking slot and populate it with a bike at date1 
		ParkingSlot ps1 = new ParkingSlot();
		Date date1 = ActivityLog.getDate(2020, 10, 5, 0, 0, 0);
		Bicycle bike1 = new MechanicalBike();
		ps1.setBicycleStored(bike1, date1);
		
		//then we empty out our parking slot at date2
		Date date2 = ActivityLog.getDate(2020, 10, 5, 2, 30, 0);
		ps1.setBicycleStored(null, date2);
		
		//then we set our parking slot out of order at date3
		Date date3 = ActivityLog.getDate(2020, 10, 5, 2, 35, 0);
		ps1.setOutOfOrder(true, date3);
		
		//then we repair our parking slot at date4
		Date date4 = ActivityLog.getDate(2020, 10, 5, 3, 0, 0);
		ps1.setOutOfOrder(false, date4);
		
		//then we re-populate our parking slot at date5 with another bike
		Bicycle bike2 = new MechanicalBike();
		Date date5 = ActivityLog.getDate(2020, 10, 5, 3, 5, 0);
		ps1.setBicycleStored(bike2, date5);
		
		//we create the expected log list
		ArrayList<ActivityLog> expectedLog = new ArrayList<ActivityLog>();
		expectedLog.add(new ActivityLog(true, date1));
		expectedLog.add(new ActivityLog(false, date2));
		expectedLog.add(new ActivityLog(true, date3));
		expectedLog.add(new ActivityLog(false, date4));
		expectedLog.add(new ActivityLog(true, date5));
		
		//of course not equal because these logs don't have the same IDs (impossible bc of the static counter) AND have different memory locations
		assertEquals(ps1.getActivityLogs().toString(), expectedLog.toString());
		//and if the logs are not ordered chronologically, it won't be a problem in the occupation rate computation
	}
}
