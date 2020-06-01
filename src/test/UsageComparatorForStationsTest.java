package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import core.Station;
import core.StdStation;

class UsageComparatorForStationsTest {
	
	/**
	 * Test of the compare(...) method from the class UsageComparatorForStations to sort in descending order Stations with respect to usage (ie nb of rents and returns).
	 * Here station st1 gets a total of 3 operations
	 * st2 gets a total of 1 operation
	 * st3 gets 0 operations
	 */
	@Test
	void test() {
		Station st1 = new StdStation(0, 2);
		st1.setTotalNbOfRentOps(2);
		st1.setTotalNbOfReturnOps(1);
		
		Station st2 = new StdStation(1, 2);
		st2.setTotalNbOfRentOps(0);
		st2.setTotalNbOfReturnOps(1);
		
		Station st3 = new StdStation(1, 1);
		st3.setTotalNbOfRentOps(0);
		st3.setTotalNbOfReturnOps(0);
		
		ArrayList<Station> mySts = new ArrayList<Station>();
		mySts.add(st2);
		mySts.add(st3);
		mySts.add(st1);
		
		mySts.sort(new core.UsageComparatorForStations());
		
		assertEquals(mySts.indexOf(st1),0);
		assertEquals(mySts.indexOf(st2),1);
		assertEquals(mySts.indexOf(st3),2);
	}

}
