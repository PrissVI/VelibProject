package test;

import core.*;
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

/**
 * JUnit test to make sure the Ride Planning Strategy Pattern returns the right stations.
 * 
 * @author Ali Raïki
 *
 */

public class RidePlanningStrategyPatternTest {

	/**
	 * Set up the network before doing the tests, with 3 stations with 10 parking slots each, as well as 10 users.
	 * 
	 * Test the basic ride planning, which should return the station closest to the start coordinates and the station closest to the end coordinates.
	 *  
	 */
	@Test //If all the parking slots are full (i.e. there is a bike percentage of 100%), no end station is returned.
	public void testAllSlotsFullBasicPlanning() {
		//Setup for 1st scenario
		MyVelibNetwork network = new MyVelibNetwork("network", 10);
		network.addStations(3, 10);
		network.addUsers(10);
		network.addBicyclePercentage(1);
		
		ArrayList<Station> stationList = network.planning(5, 5, 8, 8, "electrical", new BasicPlanning());
		assertNull(stationList.get(1));
	}
	
	@Test //If all the parking slots are empty (i.e. there is a bike percentage of 0%), no start station is returned.
	public void testAllSlotsEmptyBasicPlanning() {
		//Setup for 2st scenario
		MyVelibNetwork network = new MyVelibNetwork("network", 10);
		network.addStations(3, 10);
		network.addUsers(10);
		network.addBicyclePercentage(0);
		
		ArrayList<Station> stationList = network.planning(5, 5, 8, 8, "mechanical", new BasicPlanning());
		assertNull(stationList.get(0));		
	}
	
	@Test //The end station must have at least one empty parking slot (test for BasicPlanning).
	public void testEndStationHasEmptySlotBasicPlanning() {
		//Setup
		MyVelibNetwork network = new MyVelibNetwork("network", 10);
		network.addStations(3, 10);
		network.addUsers(10);
		network.addBicyclePercentage(0.7);

		ArrayList<Station> stationList = network.planning(5, 5, 8, 8, "electrical", new BasicPlanning());
		Station endStation = stationList.get(1);
		boolean availableSlot = false;
		
		for (ParkingSlot slot : endStation.getParkingSlots().values()) {
			if (slot.getBicycleStored() == null) {
				availableSlot = true;
				break;
			}
		}
		assertTrue(availableSlot);	
	}
	
	@Test //The start station must have at least one available bicycle of the desired type (test for BasicPlanning).
	public void testStartStationHasBikeBasicPlanning() {
		//Setup
		MyVelibNetwork network = new MyVelibNetwork("network", 10);
		network.addStations(3, 10);
		network.addUsers(10);
		network.addBicyclePercentage(0.7);

		ArrayList<Station> stationList = network.planning(5, 5, 8, 8, "mechanical", new BasicPlanning());
		Station endStation = stationList.get(1);
		boolean availableBike = false;
		
		for (ParkingSlot slot : endStation.getParkingSlots().values()) {
			if (slot.getBicycleStored() instanceof MechanicalBike) {
				availableBike = true;
				break;
			}
		}
		assertTrue(availableBike);	
	}
	
	/**
	 * Set up the network before doing the tests, with 3 stations with 10 parking slots each, as well as 10 users, and a bicycle population of 70%.
	 * 
	 * Test the avoid plus ride planning, which should not return a plus station for the end station.
	 *  
	 */
	@Test
	public void testAvoidPlus() {
		//Setup
		MyVelibNetwork network = new MyVelibNetwork("network", 10);
		network.addStations(3, 10);
		network.addUsers(10);
		network.addBicyclePercentage(0.7);
		
		ArrayList<Station> stationList = network.planning(5, 5, 8, 8, "electrical", new AvoidPlus());
		assertFalse(stationList.get(1) instanceof PlusStation);
	}
	
	@Test //The end station must have at least one empty parking slot (test for AvoidPlus ride plan).
	public void testEndStationHasEmptySlotAvoidPlus() {
		//Setup
		MyVelibNetwork network = new MyVelibNetwork("network", 10);
		network.addStations(3, 10);
		network.addUsers(10);
		network.addBicyclePercentage(0.7);

		ArrayList<Station> stationList = network.planning(5, 5, 8, 8, "electrical", new AvoidPlus());
		Station endStation = stationList.get(1);
		boolean availableSlot = false;
		
		for (ParkingSlot slot : endStation.getParkingSlots().values()) {
			if (slot.getBicycleStored() == null) {
				availableSlot = true;
				break;
			}
		}
		assertTrue(availableSlot);	
	}
	
	@Test //The start station must have at least one available bicycle of the desired type (test for AvoidPlus ride plan).
	public void testStartStationHasBikeAvoidPlus() {
		//Setup
		MyVelibNetwork network = new MyVelibNetwork("network", 10);
		network.addStations(3, 10);
		network.addUsers(10);
		network.addBicyclePercentage(0.7);

		ArrayList<Station> stationList = network.planning(5, 5, 8, 8, "mechanical", new AvoidPlus());
		Station endStation = stationList.get(1);
		boolean availableBike = false;
		
		for (ParkingSlot slot : endStation.getParkingSlots().values()) {
			if (slot.getBicycleStored() instanceof MechanicalBike) {
				availableBike = true;
				break;
			}
		}
		assertTrue(availableBike);	
	}
	
	
	/**
	 * Set up the network before doing the tests, with 3 stations with 10 parking slots each, as well as 10 users, and a bicycle population of 70%.
	 * 
	 * Test the prefer plus ride planning.
	 *  
	 */
	@Test //The end station must have at least one empty parking slot (test for PreferPlus ride plan).
	public void testEndStationHasEmptySlotPreferPlus() {
		//Setup
		MyVelibNetwork network = new MyVelibNetwork("network", 10);
		network.addStations(3, 10);
		network.addUsers(10);
		network.addBicyclePercentage(0.7);

		ArrayList<Station> stationList = network.planning(5, 5, 8, 8, "electrical", new PreferPlus());
		Station endStation = stationList.get(1);
		boolean availableSlot = false;
		
		for (ParkingSlot slot : endStation.getParkingSlots().values()) {
			if (slot.getBicycleStored() == null) {
				availableSlot = true;
				break;
			}
		}
		assertTrue(availableSlot);	
	}
	
	@Test //The start station must have at least one available bicycle of the desired type (test for PreferPlus ride plan).
	public void testStartStationHasBikePreferPlus() {
		//Setup
		MyVelibNetwork network = new MyVelibNetwork("network", 10);
		network.addStations(3, 10);
		network.addUsers(10);
		network.addBicyclePercentage(0.7);

		ArrayList<Station> stationList = network.planning(5, 5, 8, 8, "mechanical", new PreferPlus());
		Station endStation = stationList.get(1);
		boolean availableBike = false;
		
		for (ParkingSlot slot : endStation.getParkingSlots().values()) {
			if (slot.getBicycleStored() instanceof MechanicalBike) {
				availableBike = true;
				break;
			}
		}
		assertTrue(availableBike);	
	}
	
	
	/**
	 * Set up the network before doing the tests, with 3 stations with 10 parking slots each, as well as 10 users, and a bicycle population of 70%.
	 * 
	 * Test if all the the start stations are the same, regardless of the ride plan type, for the same criteria.
	 *  
	 */
	@Test
	public void testSameStartStation() {
		//Setup
		MyVelibNetwork network = new MyVelibNetwork("network", 10);
		network.addStations(3, 10);
		network.addUsers(10);
		network.addBicyclePercentage(0.7);
		
		ArrayList<Station> basicStationList = network.planning(5, 5, 8, 8, "electrical", new BasicPlanning());
		ArrayList<Station> avoidPlusStationList = network.planning(5, 5, 8, 8, "electrical", new AvoidPlus());
		ArrayList<Station> preferPlusStationList = network.planning(5, 5, 8, 8, "electrical", new PreferPlus());
		
		assertEquals(basicStationList.get(0), avoidPlusStationList.get(0));
		assertEquals(basicStationList.get(0), preferPlusStationList.get(0));
	}
	

}
