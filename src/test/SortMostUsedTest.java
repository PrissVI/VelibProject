package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

import core.SortMostUsed;
import core.SortingStrategy;
import core.Station;
import core.StdStation;

class SortMostUsedTest {

	/**
	 * Test of the sort(...) method using the UsageComparatorForStations comparator (just to check if the data type conversion is OK)
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
		
		HashMap<Integer,Station> mySts = new HashMap<Integer,Station>();
		mySts.put(st2.getID(),st2);
		mySts.put(st3.getID(),st3);
		mySts.put(st1.getID(),st1);
		
		SortingStrategy mostUsed = new SortMostUsed();
		ArrayList<Station> stList = mostUsed.sort(mySts);
		
		assertEquals(stList.indexOf(st1),0);
		assertEquals(stList.indexOf(st2),1);
		assertEquals(stList.indexOf(st3),2);
	}

}
