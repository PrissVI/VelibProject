package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

import core.ActivityLog;
import core.ElectricalBike;
import core.MechanicalBike;
import core.ParkingSlot;
import core.PlusStation;
import core.Station;
import core.StdStation;
import core.User;
import core.Vlibre;

class StationTest {
	
	/**
	 * Test for identifyUser(...) to check if it prints what it should
	*/
	@Test
	void test1() {
		Station st1 = new StdStation(2,1);
		User user1 = new User("Jean",0,1);
		st1.identifyUser(user1);
		//User 1 registered with his credit card.
		
		user1.setRegistrationCard(new Vlibre());
		st1.identifyUser(user1);
		//User 1 registered with class core.Vlibre card.
	}
	
	/*WARNING: all these tests are hypothetical and do not consider most limit cases that will be tested in the returnBicycle(...) method from User class
	Indeed, chargeUser(...) is bound to be used in the method returnBicycle(...) AND NOWHERE ELSE
	*/
	/**
	 * Test for chargeUser(...) for a user with no card, and a mechanical bike rented for 30 min, and then for 90 min more.
	 * User has initially 20 euros in his bank account, and goes to a StdStation.
	*/
	@Test
	void test2() {
		Station st2 = new StdStation(3,1);
		User user2 = new User("Jean",1,1,20);
		user2.setRentedBicycle(new MechanicalBike());
		int duration1 = 30;
		
		st2.chargeUser(user2, duration1);
		assertEquals(user2.getCreditCardBalance(), 19.5);
		assertEquals(user2.getMyVelibTotalCharges(), 0.5);
		
		int duration2 = 90;
		st2.chargeUser(user2, duration2);
		assertEquals(user2.getCreditCardBalance(), 18);
		assertEquals(user2.getMyVelibTotalCharges(), 2);
	}
	
	/**
	 * Test for chargeUser(...) to check if it throws an Exception when the station's terminal is out of order (additional functionality)
	*/
	@Test
	void test3() {
		Station st2 = new StdStation(3,1);
		User user2 = new User("Jean",1,1,20);
		user2.setRentedBicycle(new MechanicalBike());
		int duration2 = 90;
		st2.setTerminalOutOfOrder(true);

		Exception exception = assertThrows(RuntimeException.class, () -> {
			st2.chargeUser(user2, duration2);
		});
		
		String expectedMessage = "Terminal of station "+st2.getID()+" not working: go to closest station";
		String actualMessage = exception.getMessage();
		
		assertEquals(expectedMessage,actualMessage);
	}
	
	/**
	 * Test for chargeUser(...) for a user with Vlibre card and a mechanical bike rented for 90 min. 
	 * User has initially 20 euros in his bank account, and goes to a StdStation.
	*/
	@Test
	void test4() {
		Station st2 = new StdStation(3,1);
		User user3 = new User("Paul",3,2,20);
		user3.setRegistrationCard(new Vlibre());
		user3.setRentedBicycle(new MechanicalBike());
		int duration3 = 90;
		
		st2.chargeUser(user3, duration3);
		
		//first hour is free for mechanical bikes with Vlibre
		assertEquals(user3.getCreditCardBalance(), 19.5);
		assertEquals(user3.getMyVelibTotalCharges(), 0.5);
		assertEquals(user3.getRegistrationCard().getTimeCredit(), 0);
	}
	
	/**
	 * Test for chargeUser(...) for a user with Vlibre card and a electrical bike rented for 120 min. 
	 * User has initially 20 euros in his bank account, and goes to a PlusStation. (=> +5 time credit)
	*/
	@Test
	void test5() {
		Station st3 = new PlusStation(2,1);
		User user4 = new User("Greg",0,2,20);
		user4.setRegistrationCard(new Vlibre());
		user4.setRentedBicycle(new ElectricalBike());
		
		st3.chargeUser(user4, 120);
		
		//first hour is 1 euro for electrical bikes with Vlibre
		assertEquals(user4.getCreditCardBalance(), 17);		//PROBLEME ON A 18 !!!
		assertEquals(user4.getMyVelibTotalCharges(), 3);
		assertEquals(user4.getRegistrationCard().getTimeCredit(), 5);
	}
	
	/**
	 * Test for chargeUser(...) for a user with no card and a mechanical bike rented for 120 min. 
	 * User has initially 20 euros in his bank account, and goes to a PlusStation. (=> NO time credit since NO card)
	*/
	@Test
	void test6() {
		Station st4 = new PlusStation(2,3);
		User user5 = new User("Tom",1,2,20);
		user5.setRentedBicycle(new ElectricalBike());
		
		st4.chargeUser(user5, 120);
		
		assertEquals(user5.getCreditCardBalance(), 16);		
		assertEquals(user5.getMyVelibTotalCharges(), 4);
	}
	
	
	/**
	 * Test for getOccupationRate(...)
	 * A StdStation has 2 parking slots ps1 and ps2. 
	 * ps1 get populated with a bike from the beginning
	 * At d2, ps1 gets emptied and ps2 gets to store a bike
	 * The time window is [d1,d2]
	*/
	@Test
	void test7() {
		Station st5 = new StdStation(4,3);
		HashMap<Integer,ParkingSlot> parkingSlots = new HashMap<Integer,ParkingSlot>();
		
		Date d1 = ActivityLog.getDate(2020, 4, 22, 2, 0, 0);
		Date d2 = ActivityLog.getDate(2020, 4, 22, 2, 10, 0);
		Date d3 = ActivityLog.getDate(2020, 4, 22, 2, 20, 0);
		
		
		ParkingSlot ps1 = new ParkingSlot(new MechanicalBike());
		ParkingSlot ps2 = new ParkingSlot();
		parkingSlots.put(ps1.getID(),ps1);
		parkingSlots.put(ps2.getID(),ps2);
		st5.setParkingSlots(parkingSlots);
		
		ps1.setBicycleStored(null, d2);
		ps2.setBicycleStored(new ElectricalBike(), d2);
		
		double expectedOccupationRate = (double) (10 + 10) / (20 * 2);
		
		assertEquals(st5.getOccupationRate(d1, d3), expectedOccupationRate);	
	}
	
	/**
	 * Test for getOccupationRate(...)
	 * A StdStation has 3 parking slots ps1, ps2 and ps3.
	 * ps1 is set out of order from d4 to ...
	 * All other parking slots of the station remain empty btw d4 and d5
	 * The time window is [d4,d5]
	*/
	@Test
	void test8() {
		Station st6 = new StdStation(7,4);
		HashMap<Integer,ParkingSlot> parkingSlots2 = new HashMap<Integer,ParkingSlot>();
		
		Date d4 = ActivityLog.getDate(2020, 4, 23, 2, 0, 0);
		Date d5 = ActivityLog.getDate(2020, 4, 23, 2, 10, 0);
		
		ParkingSlot ps3 = new ParkingSlot();
		ParkingSlot ps4 = new ParkingSlot();
		ParkingSlot ps5 = new ParkingSlot();
		parkingSlots2.put(ps3.getID(),ps3);
		parkingSlots2.put(ps4.getID(),ps4);
		parkingSlots2.put(ps5.getID(),ps5);
		st6.setParkingSlots(parkingSlots2);
		
		ps3.setOutOfOrder(true, d4);
		
		double expectedOccupationRate = (double) (10 + 0 + 0) / (10 * 3);
		
		assertEquals(st6.getOccupationRate(d4, d5), expectedOccupationRate);	
	}
	
	/**
	 * Test for getOccupationRate(...)
	 * A StdStation has 4 parking slots ps1, ps2, ps3 and ps4.
	 * ps1 stores a bicycle from d1 to d3, then stores again a bicycle from d4 to ...
	 * ps2 is set out of order from d2 to d4
	 * ps3 stores a bicycle from d3 to d4
	 * ps4 stores a bicycle from d4 to ...
	 * All other parking slots of the station remain empty btw d4 and d5
	 * The time window is [d2,d5]
	*/
	@Test
	void test9() {
		Station st7 = new StdStation(5,5);
		HashMap<Integer,ParkingSlot> parkingSlots3 = new HashMap<Integer,ParkingSlot>();
		
		Date d1 = ActivityLog.getDate(2020, 4, 24, 2, 5, 0);
		Date d2 = ActivityLog.getDate(2020, 4, 24, 2, 10, 0);
		Date d3 = ActivityLog.getDate(2020, 4, 24, 2, 15, 0);
		Date d4 = ActivityLog.getDate(2020, 4, 24, 2, 25, 0);
		Date d5 = ActivityLog.getDate(2020, 4, 24, 2, 30, 0);
		
		ParkingSlot ps1 = new ParkingSlot();
		ParkingSlot ps2 = new ParkingSlot();
		ParkingSlot ps3 = new ParkingSlot();
		ParkingSlot ps4 = new ParkingSlot();
		parkingSlots3.put(ps1.getID(),ps1);
		parkingSlots3.put(ps2.getID(),ps2);
		parkingSlots3.put(ps3.getID(),ps3);
		parkingSlots3.put(ps4.getID(),ps4);
		st7.setParkingSlots(parkingSlots3);
		
		ps1.setBicycleStored(new MechanicalBike(), d1);
		ps2.setOutOfOrder(true, d2);
		ps1.setBicycleStored(null, d3);
		ps3.setBicycleStored(new MechanicalBike(), d3);
		ps2.setOutOfOrder(false, d4);
		ps3.setBicycleStored(null, d4);
		ps4.setBicycleStored(new ElectricalBike(), d4);
		ps1.setBicycleStored(new MechanicalBike(), d4);
		
		double expectedOccupationRate = (double) (10 + 15 + 10 + 5) / (20 * 4);
		
		assertEquals(st7.getOccupationRate(d2, d5), expectedOccupationRate);	
	}
}
