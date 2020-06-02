package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

import core.ActivityLog;
import core.Bicycle;
import core.ElectricalBike;
import core.MechanicalBike;
import core.ParkingSlot;
import core.Station;
import core.StdStation;
import core.User;

class UserTest {

	/**
	 * Test for rentBicycle(...) method on User
	 * Here is a normal test case, where everything works as expected.
	 * A station st1 has 2 parking slots, one of them storing a bike from the beginning
	 * User u1 rents a bike at d1.
	 */
	@Test
	void test1() {
		Station st1 = new StdStation(1,2);
		Bicycle b1 = new MechanicalBike();
		ParkingSlot ps1 = new ParkingSlot(b1);
		ParkingSlot ps2 = new ParkingSlot();
		HashMap<Integer,ParkingSlot> parkingSlots = new HashMap<Integer,ParkingSlot>();
		parkingSlots.put(ps1.getID(),ps1);
		parkingSlots.put(ps2.getID(),ps2);
		st1.setParkingSlots(parkingSlots);
		
		User u1 = new User("Fred",1,2);
		
		Date d1 = ActivityLog.getDate(2020, 4, 5, 10, 0, 0);
		
		u1.rentBicycle(st1, d1);
		//"User ... registered with his credit card."
		
		assertEquals(u1.getRentedBicycle(),b1);
		assertEquals(b1.getRentDate(),d1);
		
		ActivityLog expectedAl = new ActivityLog(false,d1);
		assertEquals(ps1.getActivityLogs().get(1).toString(),expectedAl.toString());
		
		assertEquals(st1.getTotalNbOfRentOps(),1);
	}

	/**
	 * Test for rentBicycle(...) method on User
	 * Here the User is not at the same location as the station => rent operation should throw an exception
	 */
	@Test
	void test2() {
		Station st1 = new StdStation(1,2);
		Bicycle b1 = new MechanicalBike();
		ParkingSlot ps1 = new ParkingSlot(b1);
		ParkingSlot ps2 = new ParkingSlot();
		HashMap<Integer,ParkingSlot> parkingSlots = new HashMap<Integer,ParkingSlot>();
		parkingSlots.put(ps1.getID(),ps1);
		parkingSlots.put(ps2.getID(),ps2);
		st1.setParkingSlots(parkingSlots);
		
		User u1 = new User("Jamie",2,2);
		
		Date d1 = ActivityLog.getDate(2020, 4, 5, 10, 0, 0);
		
		Exception exception = assertThrows(RuntimeException.class, () -> {
			u1.rentBicycle(st1, d1);
		});
		
		String expectedMessage = "Cannot rent bicycle if station " + st1.getID() + " not reached.";
		String actualMessage = exception.getMessage();
		
		assertEquals(expectedMessage,actualMessage);
	}
	
	
	/**
	 * Test for rentBicycle(...) method on User
	 * Here the Station is not online => rent operation should throw an exception
	 */
	@Test
	void test3() {
		Station st1 = new StdStation(1,2);
		Bicycle b1 = new MechanicalBike();
		ParkingSlot ps1 = new ParkingSlot(b1);
		ParkingSlot ps2 = new ParkingSlot();
		HashMap<Integer,ParkingSlot> parkingSlots = new HashMap<Integer,ParkingSlot>();
		parkingSlots.put(ps1.getID(),ps1);
		parkingSlots.put(ps2.getID(),ps2);
		st1.setParkingSlots(parkingSlots);
		
		User u1 = new User("Rick",1,2);
		
		st1.setOnline(false);
		
		Date d1 = ActivityLog.getDate(2020, 4, 5, 10, 0, 0);
		
		Exception exception = assertThrows(RuntimeException.class, () -> {
			u1.rentBicycle(st1, d1);
		});
		
		String expectedMessage = "Cannot rent bicycle if station " + st1.getID() + " if offline.";
		String actualMessage = exception.getMessage();
		
		assertEquals(expectedMessage,actualMessage);
	}
	
	
	/**
	 * Test for rentBicycle(...) method on User
	 * Here User user1 rents a bicycle then tries to rent another one without returning his => rent operation should throw an exception
	 */
	@Test
	void test4() {
		Station st1 = new StdStation(1,2);
		Bicycle b1 = new MechanicalBike();
		ParkingSlot ps1 = new ParkingSlot(b1);
		ParkingSlot ps2 = new ParkingSlot();
		HashMap<Integer,ParkingSlot> parkingSlots = new HashMap<Integer,ParkingSlot>();
		parkingSlots.put(ps1.getID(),ps1);
		parkingSlots.put(ps2.getID(),ps2);
		st1.setParkingSlots(parkingSlots);
		
		User u1 = new User("Morty",1,2);
		
		Date d1 = ActivityLog.getDate(2020, 4, 5, 10, 0, 0);
		
		u1.rentBicycle(st1, d1);
		//user ... registered with his credit card.
		
		Date d2 = ActivityLog.getDate(2020, 4, 5, 10, 5, 0);
		
		Exception exception = assertThrows(RuntimeException.class, () -> {
			u1.rentBicycle(st1, d2);
		});
		
		String expectedMessage = "Cannot rent bicycle if user " + u1.getID() + " is already renting one.";
		String actualMessage = exception.getMessage();
		
		assertEquals(expectedMessage,actualMessage);
	}
	
	/**
	 * Test for rentBicycle(...) method on User
	 * Here User user1 tries to rent a bicycle in a station with no full or functioning parking slot => rent operation should throw an exception
	 */
	@Test
	void test5() {
		Station st1 = new StdStation(1,2);
		ParkingSlot ps1 = new ParkingSlot();
		ParkingSlot ps2 = new ParkingSlot();
		HashMap<Integer,ParkingSlot> parkingSlots = new HashMap<Integer,ParkingSlot>();
		parkingSlots.put(ps1.getID(),ps1);
		parkingSlots.put(ps2.getID(),ps2);
		st1.setParkingSlots(parkingSlots);
		
		User u1 = new User("Jake",1,2);
		
		Date d1 = ActivityLog.getDate(2020, 4, 5, 10, 0, 0);
		ps1.setOutOfOrder(true, d1);
		
		Date d2 = ActivityLog.getDate(2020, 4, 5, 10, 0, 0);
		
		Exception exception = assertThrows(RuntimeException.class, () -> {
			u1.rentBicycle(st1, d2);
		});
		
		String expectedMessage = "Cannot rent bicycle if station " + st1.getID() + " has no available bicycle.";
		String actualMessage = exception.getMessage();
		
		assertEquals(expectedMessage,actualMessage);
	}
	
	
	//5 exceptions à tester
	/**
	 * Test for returnBicycle(...) method on User
 	 * Here is a normal test case, where everything works as expected.
	 * A station st1 has 2 parking slots, one of them storing a bike from the beginning
	 * User u1 returns a bike (he rented at d1) at d2.
	 */
	@Test
	void test6() {
		Station st1 = new StdStation(1,2);
		ParkingSlot ps1 = new ParkingSlot();
		ParkingSlot ps2 = new ParkingSlot();
		HashMap<Integer,ParkingSlot> parkingSlots = new HashMap<Integer,ParkingSlot>();
		parkingSlots.put(ps1.getID(),ps1);
		parkingSlots.put(ps2.getID(),ps2);
		st1.setParkingSlots(parkingSlots);
		
		Date d1 = ActivityLog.getDate(2020, 4, 5, 10, 0, 0);
		User u1 = new User("Jeff",1,2);
		Bicycle b1 = new ElectricalBike();
		b1.setRentDate(d1);
		u1.setRentedBicycle(b1);
		
		Date d2 = ActivityLog.getDate(2020, 4, 5, 10, 10, 0);
		
		u1.returnBicycle(st1, d2);
		//The cost of the ride is: 0.33333...
		
		assertEquals(u1.getRentedBicycle(),null);
		assertEquals(b1.getRentDate(),null);
		
		assertTrue(ps1.getBicycleStored() == b1 || ps2.getBicycleStored() == b1);
		
		ActivityLog expectedAl = new ActivityLog(true,d2);
		if (ps1.getBicycleStored() == b1) {
			assertTrue(ps1.getActivityLogs().get(0).toString().equals(expectedAl.toString()));
		} else {
			assertTrue(ps2.getActivityLogs().get(0).toString().equals(expectedAl.toString()));
		}
		
		assertEquals(st1.getTotalNbOfReturnOps(),1);
		assertEquals(u1.getTotalNbOfRides(),1);
		assertEquals(u1.getTotalTimeSpentOnBike(),10);
	}
	
	
	/**
	 * Test for returnBicycle(...) method on User
	 * Here the User is not at the same location as the station => return operation should throw an exception
	 */
	@Test
	void test7() {
		Station st1 = new StdStation(1,2);
		ParkingSlot ps1 = new ParkingSlot();
		ParkingSlot ps2 = new ParkingSlot();
		HashMap<Integer,ParkingSlot> parkingSlots = new HashMap<Integer,ParkingSlot>();
		parkingSlots.put(ps1.getID(),ps1);
		parkingSlots.put(ps2.getID(),ps2);
		st1.setParkingSlots(parkingSlots);
		
		Date d1 = ActivityLog.getDate(2020, 4, 5, 10, 0, 0);
		User u1 = new User("Kurt",2,2);
		Bicycle b1 = new ElectricalBike();
		b1.setRentDate(d1);
		u1.setRentedBicycle(b1);
		
		Date d2 = ActivityLog.getDate(2020, 4, 5, 10, 10, 0);
		
		Exception exception = assertThrows(RuntimeException.class, () -> {
			u1.returnBicycle(st1, d2);
		});
		
		String expectedMessage = "Cannot return bicycle if station " + st1.getID() + " not reached.";
		String actualMessage = exception.getMessage();
		
		assertEquals(expectedMessage,actualMessage);
	}
	
	
	/**
	 * Test for returnBicycle(...) method on User
	 * Here the Station st1 is offline => return operation should throw an exception
	 */
	@Test
	void test8() {
		Station st1 = new StdStation(1,2);
		ParkingSlot ps1 = new ParkingSlot();
		ParkingSlot ps2 = new ParkingSlot();
		HashMap<Integer,ParkingSlot> parkingSlots = new HashMap<Integer,ParkingSlot>();
		parkingSlots.put(ps1.getID(),ps1);
		parkingSlots.put(ps2.getID(),ps2);
		st1.setParkingSlots(parkingSlots);
		
		Date d1 = ActivityLog.getDate(2020, 4, 5, 10, 0, 0);
		User u1 = new User("Jacob",1,2);
		Bicycle b1 = new ElectricalBike();
		b1.setRentDate(d1);
		u1.setRentedBicycle(b1);
		
		Date d2 = ActivityLog.getDate(2020, 4, 5, 10, 10, 0);
		
		st1.setOnline(false);
		
		Exception exception = assertThrows(RuntimeException.class, () -> {
			u1.returnBicycle(st1, d2);
		});
		
		String expectedMessage = "Cannot return bicycle if station " + st1.getID() + " if offline. Go to another one.";
		String actualMessage = exception.getMessage();
		
		assertEquals(expectedMessage,actualMessage);
	}
	
	
	/**
	 * Test for returnBicycle(...) method on User
	 * Here the User user1 has not rented a bike but still tries to return one => return operation should throw an exception
	 */
	@Test
	void test9() {
		Station st1 = new StdStation(1,2);
		ParkingSlot ps1 = new ParkingSlot();
		ParkingSlot ps2 = new ParkingSlot();
		HashMap<Integer,ParkingSlot> parkingSlots = new HashMap<Integer,ParkingSlot>();
		parkingSlots.put(ps1.getID(),ps1);
		parkingSlots.put(ps2.getID(),ps2);
		st1.setParkingSlots(parkingSlots);
		
		User u1 = new User("Lewis",1,2);
		
		Date d1 = ActivityLog.getDate(2020, 4, 5, 10, 0, 0);
		
		Exception exception = assertThrows(RuntimeException.class, () -> {
			u1.returnBicycle(st1, d1);
		});
		
		String expectedMessage = "Cannot return bicycle if user is not currently renting one.";
		String actualMessage = exception.getMessage();
		
		assertEquals(expectedMessage,actualMessage);
	}
	
	
	/**
	 * Test for returnBicycle(...) method on User
	 * Here the date anteriority is not respected: user1 tries to return a bike he has not rented yet in reality => return operation should throw an exception
	 */
	@Test
	void test10() {
		Station st1 = new StdStation(1,2);
		ParkingSlot ps1 = new ParkingSlot();
		ParkingSlot ps2 = new ParkingSlot();
		HashMap<Integer,ParkingSlot> parkingSlots = new HashMap<Integer,ParkingSlot>();
		parkingSlots.put(ps1.getID(),ps1);
		parkingSlots.put(ps2.getID(),ps2);
		st1.setParkingSlots(parkingSlots);
		
		Date d1 = ActivityLog.getDate(2020, 4, 5, 10, 10, 0);
		User u1 = new User("Sebastian",1,2);
		Bicycle b1 = new ElectricalBike();
		b1.setRentDate(d1);
		u1.setRentedBicycle(b1);
		
		Date d2 = ActivityLog.getDate(2020, 4, 5, 10, 0, 0);
		
		Exception exception = assertThrows(RuntimeException.class, () -> {
			u1.returnBicycle(st1, d2);
		});
		
		String expectedMessage = "Return date is anterior to rent date... check date anteriority";
		String actualMessage = exception.getMessage();
		
		assertEquals(expectedMessage,actualMessage);
	}
	
	
	/**
	 * Test for returnBicycle(...) method on User
	 * Here User user1 tries to return a bicycle in a station with only full or malfunctioning parking slot => return operation should throw an exception
	 */
	@Test
	void test11() {
		Station st1 = new StdStation(1,2);
		ParkingSlot ps1 = new ParkingSlot(new MechanicalBike());
		ParkingSlot ps2 = new ParkingSlot();
		Date d0 = ActivityLog.getDate(2020, 4, 5, 9, 0, 0);
		ps2.setOutOfOrder(true, d0);
		HashMap<Integer,ParkingSlot> parkingSlots = new HashMap<Integer,ParkingSlot>();
		parkingSlots.put(ps1.getID(),ps1);
		parkingSlots.put(ps2.getID(),ps2);
		st1.setParkingSlots(parkingSlots);
		
		Date d1 = ActivityLog.getDate(2020, 4, 5, 10, 0, 0);
		User u1 = new User("Mika",1,2);
		Bicycle b1 = new ElectricalBike();
		b1.setRentDate(d1);
		u1.setRentedBicycle(b1);
		
		Date d2 = ActivityLog.getDate(2020, 4, 5, 10, 10, 0);
		
		Exception exception = assertThrows(RuntimeException.class, () -> {
			u1.returnBicycle(st1, d2);
		});
		
		String expectedMessage = "Cannot return bicycle if station " + st1.getID() + " has no free parking slots. Go to another one.";
		String actualMessage = exception.getMessage();
		
		assertEquals(expectedMessage,actualMessage);
	}
}
