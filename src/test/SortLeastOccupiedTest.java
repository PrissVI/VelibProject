package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

import core.ActivityLog;
import core.MechanicalBike;
import core.ParkingSlot;
import core.SortLeastOccupied;
import core.SortingStrategy;
import core.Station;
import core.StdStation;

class SortLeastOccupiedTest {
	
	/**
	 * Test of the convertToDate(...) method
	 * Normal use case here.
	 */
	@Test
	void test1() {
		String s = "2020/05/06-15:02:00";
		Date d = ActivityLog.getDate(2020, 5-1, 6, 15, 2, 0);
		
		assertEquals(SortLeastOccupied.convertToDate(s),d);
	}
	
	
	/**
	 * Test of the convertToDate(...) method
	 * Date in wrong format because of delimiters or time granularity
	 */
	@Test
	void test2() {
		String s = "2020/05/06-15:02";
		
		Exception exception = assertThrows(RuntimeException.class, () -> {
			SortLeastOccupied.convertToDate(s);
		});
		
		String expectedMessage = "Date in wrong format: check delimiters and/or time granularity";
		String actualMessage = exception.getMessage();
		
		assertEquals(expectedMessage,actualMessage);
		
		//part 2
		String s1 = "2020_05_06-15:02:00";
		
		Exception exception2 = assertThrows(RuntimeException.class, () -> {
			SortLeastOccupied.convertToDate(s1);
		});
		
		String expectedMessage2 = "Date in wrong format: check delimiters and/or time granularity";
		String actualMessage2 = exception2.getMessage();
		
		assertEquals(expectedMessage2,actualMessage2);
	}
	
	
	/**
	 * Test of the convertToDate(...) method
	 * Invalid value for time
	 */
	@Test
	void test3() {
		String s = "2020/05/06-15:02:truc";
		
		Exception exception = assertThrows(RuntimeException.class, () -> {
			SortLeastOccupied.convertToDate(s);
		});
		
		String expectedMessage = "Date in wrong format: check value of time units";
		String actualMessage = exception.getMessage();
		
		assertEquals(expectedMessage,actualMessage);
	}
	

	/**
	 * Test of the sort(...) method when the client wants to type in a custom time window for occupation rate computation
	 * Station st1 has 1 full parking slot from the beginning that does not get rented => occup rate of 1
	 * Station st2 has 1 empty parking slot from the beginning that does not store a returned bike at any moment => occup rate of 0
	 * Station st3 has 1 full parking slot and 1 empty parking slot from the beginning => occup rate of 0.5
	 * Expected order: st2, st3, st1
	 */
	@Test
	void test4() {
		Station st1 = new StdStation(0, 2);
		HashMap<Integer,ParkingSlot> parkingSlots1 = new HashMap<Integer,ParkingSlot>();
		ParkingSlot ps1 = new ParkingSlot(new MechanicalBike());
		parkingSlots1.put(ps1.getID(),ps1);
		st1.setParkingSlots(parkingSlots1);
		
		Station st2 = new StdStation(1, 2);
		HashMap<Integer,ParkingSlot> parkingSlots2 = new HashMap<Integer,ParkingSlot>();
		ParkingSlot ps2 = new ParkingSlot();
		parkingSlots2.put(ps2.getID(),ps2);
		st2.setParkingSlots(parkingSlots2);
		
		Station st3 = new StdStation(2, 2);
		HashMap<Integer,ParkingSlot> parkingSlots3 = new HashMap<Integer,ParkingSlot>();
		ParkingSlot ps3 = new ParkingSlot();
		ParkingSlot ps4 = new ParkingSlot(new MechanicalBike());
		parkingSlots3.put(ps3.getID(),ps3);
		parkingSlots3.put(ps4.getID(),ps4);
		st3.setParkingSlots(parkingSlots3);
		
		//SortLeastOccupied strategy instantiated with no arguments
		SortingStrategy ss = new SortLeastOccupied();
		HashMap<Integer,Station> myList2 = new HashMap<Integer,Station>();
		myList2.put(st1.getID(),st1);
		myList2.put(st2.getID(),st2);
		myList2.put(st3.getID(),st3);
		
		ArrayList<Station> myList3 = ss.sort(myList2);
		//TYPE IN: 2020/4/22-2:0:0 as initial date and 2020/4/22-2:10:0 as final date
		
		assertEquals(myList3.get(0),st2);
		assertEquals(myList3.get(1),st3);
		assertEquals(myList3.get(2),st1);
	}

	
	/**
	 * Test of the sort(...) method with a fixed time window
	 * Station st1 has 1 full parking slot from the beginning that does not get rented => occup rate of 1
	 * Station st2 has 1 empty parking slot from the beginning that does not store a returned bike at any moment => occup rate of 0
	 * Station st3 has 1 full parking slot and 1 empty parking slot from the beginning => occup rate of 0.5
	 * Expected order: st2, st3, st1
	 */
	@Test
	void test5() {
		Station st1 = new StdStation(0, 2);
		HashMap<Integer,ParkingSlot> parkingSlots1 = new HashMap<Integer,ParkingSlot>();
		ParkingSlot ps1 = new ParkingSlot(new MechanicalBike());
		parkingSlots1.put(ps1.getID(),ps1);
		st1.setParkingSlots(parkingSlots1);
		
		Station st2 = new StdStation(1, 2);
		HashMap<Integer,ParkingSlot> parkingSlots2 = new HashMap<Integer,ParkingSlot>();
		ParkingSlot ps2 = new ParkingSlot();
		parkingSlots2.put(ps2.getID(),ps2);
		st2.setParkingSlots(parkingSlots2);
		
		Station st3 = new StdStation(2, 2);
		HashMap<Integer,ParkingSlot> parkingSlots3 = new HashMap<Integer,ParkingSlot>();
		ParkingSlot ps3 = new ParkingSlot();
		ParkingSlot ps4 = new ParkingSlot(new MechanicalBike());
		parkingSlots3.put(ps3.getID(),ps3);
		parkingSlots3.put(ps4.getID(),ps4);
		st3.setParkingSlots(parkingSlots3);
		
		//those dates will be supplied by a method from myVelibNetwork
		Date infDate = ActivityLog.getDate(2020, 3, 22, 2, 0, 0);
		Date supDate = ActivityLog.getDate(2020, 3, 22, 2, 10, 0);
		
		//SortLeastOccupied strategy instantiated with two arguments
		SortingStrategy ss = new SortLeastOccupied(infDate,supDate);
		HashMap<Integer,Station> myList2 = new HashMap<Integer,Station>();
		myList2.put(st1.getID(),st1);
		myList2.put(st2.getID(),st2);
		myList2.put(st3.getID(),st3);
		
		ArrayList<Station> myList3 = ss.sort(myList2);
		
		assertEquals(myList3.get(0),st2);
		assertEquals(myList3.get(1),st3);
		assertEquals(myList3.get(2),st1);
	}
}
