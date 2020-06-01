package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

import core.ActivityLog;
import core.ParkingSlot;
import core.Station;
import core.StdStation;

class ObserverPatternTest {

	/**
	 * Test to check if the additional functionality (observer pattern) to inform a station that one of its parking slots has been set out of order works.
	 * With this functionality, if all parking slots are out of order and the terminal is out of order as well, the station turns itself to out of order until reparations (and the user can still turn it offline whenever he wants).
	 * In this test, we create two parking slots, link them to a station, check the nb of out of order parking slots, turn one out of order, then re-check the nb of out of order parking slots.
	 */
	@Test
	void test1() {
		Station mySt = new StdStation(0, 0);
		ParkingSlot ps1 = new ParkingSlot();
		ParkingSlot ps2 = new ParkingSlot();
		
		HashMap<Integer,ParkingSlot> myParkingSlots = new HashMap<Integer,ParkingSlot>();
		myParkingSlots.put(ps1.getID(),ps1);
		myParkingSlots.put(ps2.getID(),ps2);
		
		mySt.setParkingSlots(myParkingSlots);
		assertEquals(mySt.getNbOfOutOfOrderParkingSlots(),0);
		
		Date d1 = ActivityLog.getDate(2020, 4, 24, 2, 5, 0);
		ps1.setOutOfOrder(true, d1);
		
		assertEquals(mySt.getNbOfOutOfOrderParkingSlots(),1);
	}

	/**
	 * Test to check if the additional functionality (observer pattern) to inform a station that one of its parking slots has been set out of order works.
	 * With this functionality, if all parking slots are out of order and the terminal is out of order as well, the station turns itself to out of order until reparations (and the client can still turn it online/offline whenever he wants).
	 * In this test, we create two parking slots, turn one offline, link them to a station, check the nb of out of order parking slots, turn one out of order, then re-check the nb of out of order parking slots.
	 */
	@Test
	void test2() {
		//test2
		Station mySt = new StdStation(0, 0);
		ParkingSlot ps1 = new ParkingSlot();
		ParkingSlot ps2 = new ParkingSlot();
		
		Date d1 = ActivityLog.getDate(2020, 4, 24, 2, 5, 0);
		ps1.setOutOfOrder(true, d1);
		
		HashMap<Integer,ParkingSlot> myParkingSlots2 = new HashMap<Integer,ParkingSlot>();
		myParkingSlots2.put(ps1.getID(),ps1);
		myParkingSlots2.put(ps2.getID(),ps2);
		
		mySt.setParkingSlots(myParkingSlots2);
		
		assertEquals(mySt.getNbOfOutOfOrderParkingSlots(),1);
		
		Date d2 = ActivityLog.getDate(2020, 4, 24, 2, 10, 0);
		ps2.setOutOfOrder(true, d2);
		
		assertEquals(mySt.getNbOfOutOfOrderParkingSlots(),2);
		
		mySt.setTerminalOutOfOrder(true);
		
		assertFalse(mySt.isOnline());
	}
}
