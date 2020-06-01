package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.AbstractMap.SimpleEntry;

import org.junit.jupiter.api.Test;

import core.ActivityLog;
import core.MechanicalBike;
import core.OccupationComparatorForStationOccupationPairs;
import core.ParkingSlot;
import core.Station;
import core.StdStation;

class OccupationComparatorForStationOccupationPairsTest {

	/**
	 * Test of the compare(...) method from the class OccupationComparatorForStationOccupationPairs to sort in ascending order SimpleEntries<Station,Double> with respect to their value which represents the occupation rate of the key station.
	 * Here station st1 gets a parking slot ps1 storing a bicycle for the whole time (occup rate = 1)
	 * st2 gets an empty parking slot the whole time (occup rate = 0)
	 * st3 gets one empty parking slot ps3 and another one storing a bike the whole time (occup rate = 0.5)
	 */
	@Test
	void test() {
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
		
		
		Date d1 = ActivityLog.getDate(2020, 4, 22, 2, 0, 0);
		Date d2 = ActivityLog.getDate(2020, 4, 22, 2, 10, 0);
		
		SimpleEntry<Station, Double> pair1 = new SimpleEntry<Station, Double>(st1,st1.getOccupationRate(d1, d2));
		SimpleEntry<Station, Double> pair2 = new SimpleEntry<Station, Double>(st2,st2.getOccupationRate(d1, d2));
		SimpleEntry<Station, Double> pair3 = new SimpleEntry<Station, Double>(st3,st3.getOccupationRate(d1, d2));

		ArrayList<SimpleEntry<Station, Double>> myList = new ArrayList<SimpleEntry<Station, Double>>();
		myList.add(pair2);
		myList.add(pair1);
		myList.add(pair3);
		
		myList.sort(new OccupationComparatorForStationOccupationPairs());
		
		assertEquals(myList.indexOf(pair1),2);
		assertEquals(myList.indexOf(pair2),0);
		assertEquals(myList.indexOf(pair3),1);
	}

}
