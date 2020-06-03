package test;

import core.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

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
		if(!(endStation==null)) {
			for (ParkingSlot slot : endStation.getParkingSlots().values()) {
				if (slot.getBicycleStored() == null) {
					availableSlot = true;
					break;
				}
			}
			assertTrue(availableSlot);	
		}
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
		
		if(!(endStation==null)) {
			for (ParkingSlot slot : endStation.getParkingSlots().values()) {
				if (slot.getBicycleStored() instanceof MechanicalBike) {
					availableBike = true;
					break;
				}
			}
			assertTrue(availableBike);
		}
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
		
		if(!(endStation==null)) {
			assertTrue(availableBike);	
		}
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
	
	/**
	 * Set up the network before doing the tests, with 4 stations at known locations, with 10 parking slots each and with known bicycles.
	 * 
	 * Test if the planning returns the right start station for each strategy.
	 *  
	 */
	@Test
	public void testReturnCorrectStartStation() {
		//Setup
		MyVelibNetwork networkTest = new MyVelibNetwork("network", 10);
		ConcreteStationFactory abstractFactory = (ConcreteStationFactory) FactoryProducer.createFactory("STATION");
		ConcreteBicycleFactory abstractFactory2 = (ConcreteBicycleFactory) FactoryProducer.createFactory("BICYCLE");
		
		//1st station :
		ArrayList<Object> params = new ArrayList<Object>();
		double x = 0;
		double y = 1;
		HashMap<Integer, ParkingSlot> parkingSlots = new HashMap<Integer, ParkingSlot>() ;
		ConcreteSlotFactory slotFactory = (ConcreteSlotFactory) FactoryProducer.createFactory("SLOT");

		for(int j = 0; j < 8; j++) { //Add parking slots with bicycles
			ArrayList<Object> slotParams = new ArrayList<Object>();
			slotParams.add("PARKING");
			ParkingSlot parkingSlot = (ParkingSlot) slotFactory.createSlot(slotParams);
			ArrayList<Object> paramsBike = new ArrayList<Object>();
			paramsBike.add("ELECTRICAL");
			Bicycle bicycle = (Bicycle) abstractFactory2.createBicycle(paramsBike);
			parkingSlot.setBicycleStored(bicycle, ActivityLog.getDate(0, 0, 0, 0, 0, 0));
			parkingSlots.put(parkingSlot.getID(), parkingSlot);
		}
		
		params.add("STANDARD");
		params.add(x);
		params.add(y);
		params.add(parkingSlots);
		Station station = (Station) abstractFactory.createStation(params);
		
		//2nd station :
		params.clear();
		double x2 = 4;
		double y2 = 5;
		HashMap<Integer, ParkingSlot> parkingSlots2 = new HashMap<Integer, ParkingSlot>() ;
		ConcreteSlotFactory slotFactory2 = (ConcreteSlotFactory) FactoryProducer.createFactory("SLOT");

		for(int j = 0; j < 8; j++) { //Adding stations with bicycles
			ArrayList<Object> slotParams = new ArrayList<Object>();
			slotParams.add("PARKING");
			ParkingSlot parkingSlot2 = (ParkingSlot) slotFactory2.createSlot(slotParams);
			ArrayList<Object> paramsBike = new ArrayList<Object>();
			paramsBike.add("MECHANICAL");
			Bicycle bicycle = (Bicycle) abstractFactory2.createBicycle(paramsBike);
			parkingSlot2.setBicycleStored(bicycle, ActivityLog.getDate(0, 0, 0, 0, 0, 0));
			parkingSlots2.put(parkingSlot2.getID(), parkingSlot2);
		}

		params.add("PLUS");
		params.add(x2);
		params.add(y2);
		params.add(parkingSlots2);
		Station station2 = (Station) abstractFactory.createStation(params);

		//3rd station :
		params.clear();
		double x3 = 10;
		double y3 = 8;
		HashMap<Integer, ParkingSlot> parkingSlots3 = new HashMap<Integer, ParkingSlot>() ;
		ConcreteSlotFactory slotFactory3 = (ConcreteSlotFactory) FactoryProducer.createFactory("SLOT");

		for(int j = 0; j < 8; j++) {
			ArrayList<Object> slotParams = new ArrayList<Object>();
			slotParams.add("PARKING");
			ParkingSlot parkingSlot3 = (ParkingSlot) slotFactory3.createSlot(slotParams);
			ArrayList<Object> paramsBike = new ArrayList<Object>();
			paramsBike.add("MECHANICAL");
			Bicycle bicycle = (Bicycle) abstractFactory2.createBicycle(paramsBike);
			parkingSlot3.setBicycleStored(bicycle, ActivityLog.getDate(0, 0, 0, 0, 0, 0));
			parkingSlots3.put(parkingSlot3.getID(), parkingSlot3);
		}

		params.add("PLUS");
		params.add(x3);
		params.add(y3);
		params.add(parkingSlots3);
		Station station3 = (Station) abstractFactory.createStation(params);
		
		//4th station :
		params.clear();
		double x4 = 6;
		double y4 = 5;
		HashMap<Integer, ParkingSlot> parkingSlots4 = new HashMap<Integer, ParkingSlot>() ;
		ConcreteSlotFactory slotFactory4 = (ConcreteSlotFactory) FactoryProducer.createFactory("SLOT");

		for(int j = 0; j < 8; j++) { //Adding stations with bicycles
			ArrayList<Object> slotParams = new ArrayList<Object>();
			slotParams.add("PARKING");
			ParkingSlot parkingSlot4 = (ParkingSlot) slotFactory4.createSlot(slotParams);
			ArrayList<Object> paramsBike = new ArrayList<Object>();
			paramsBike.add("ELECTRICAL");
			Bicycle bicycle = (Bicycle) abstractFactory2.createBicycle(paramsBike);
			parkingSlot4.setBicycleStored(bicycle, ActivityLog.getDate(0, 0, 0, 0, 0, 0));
			parkingSlots4.put(parkingSlot4.getID(), parkingSlot4);
		}

		params.add("STANDARD");
		params.add(x4);
		params.add(y4);
		params.add(parkingSlots4);
		Station station4 = (Station) abstractFactory.createStation(params);
		
		//Adding stations :
		networkTest.getStations().put(station.getID(), station);
		networkTest.getStations().put(station2.getID(), station2);
		networkTest.getStations().put(station3.getID(), station3);
		networkTest.getStations().put(station4.getID(), station4);
		
		
		ArrayList<Station> basicStationList = networkTest.planning(1, 0, 5, 6, "mechanical", new BasicPlanning());
		ArrayList<Station> avoidPlusStationList = networkTest.planning(1, 0, 5, 6, "mechanical", new AvoidPlus());
		ArrayList<Station> preferPlusStationList = networkTest.planning(1, 0, 5, 6, "electrical", new PreferPlus());
		
		assertEquals(station2, basicStationList.get(0)); //The closest station to the start, that has a mechanical bike, is station2
		
		assertEquals(station2, avoidPlusStationList.get(0)); //The closest station to the start, that has a mechanical bike, is station2
		
		assertEquals(station, preferPlusStationList.get(0)); //The closest station to the start, that has a mechanical bike, is station2
		
	}
	
	/**
	 * Set up the network before doing the tests, with 4 stations at known locations, with 10 parking slots each and with known bicycles.
	 * 
	 * Test if the planning returns the right end station for each strategy.
	 *  
	 */
	@Test
	public void testReturnCorrectEndStation() {
		//Setup
		MyVelibNetwork networkTest = new MyVelibNetwork("network", 10);
		ConcreteStationFactory abstractFactory = (ConcreteStationFactory) FactoryProducer.createFactory("STATION");
		
		//1st station :
		ArrayList<Object> params = new ArrayList<Object>();
		double x = 0;
		double y = 1;
		HashMap<Integer, ParkingSlot> parkingSlots = new HashMap<Integer, ParkingSlot>() ;
		ConcreteSlotFactory slotFactory = (ConcreteSlotFactory) FactoryProducer.createFactory("SLOT");

		for(int j = 0; j < 8; j++) { //Add parking slots with bicycles
			ArrayList<Object> slotParams = new ArrayList<Object>();
			slotParams.add("PARKING");
			ParkingSlot parkingSlot = (ParkingSlot) slotFactory.createSlot(slotParams);
			parkingSlots.put(parkingSlot.getID(), parkingSlot);
		}
		
		params.add("STANDARD");
		params.add(x);
		params.add(y);
		params.add(parkingSlots);
		Station station = (Station) abstractFactory.createStation(params);
		
		//2nd station :
		params.clear();
		double x2 = 4;
		double y2 = 5;
		HashMap<Integer, ParkingSlot> parkingSlots2 = new HashMap<Integer, ParkingSlot>() ;
		ConcreteSlotFactory slotFactory2 = (ConcreteSlotFactory) FactoryProducer.createFactory("SLOT");

		for(int j = 0; j < 8; j++) { //Adding stations with bicycles
			ArrayList<Object> slotParams = new ArrayList<Object>();
			slotParams.add("PARKING");
			ParkingSlot parkingSlot2 = (ParkingSlot) slotFactory2.createSlot(slotParams);
			parkingSlots2.put(parkingSlot2.getID(), parkingSlot2);
		}

		params.add("PLUS");
		params.add(x2);
		params.add(y2);
		params.add(parkingSlots2);
		Station station2 = (Station) abstractFactory.createStation(params);

		//3rd station :
		params.clear();
		double x3 = 10;
		double y3 = 8;
		HashMap<Integer, ParkingSlot> parkingSlots3 = new HashMap<Integer, ParkingSlot>() ;
		ConcreteSlotFactory slotFactory3 = (ConcreteSlotFactory) FactoryProducer.createFactory("SLOT");

		for(int j = 0; j < 8; j++) {
			ArrayList<Object> slotParams = new ArrayList<Object>();
			slotParams.add("PARKING");
			ParkingSlot parkingSlot3 = (ParkingSlot) slotFactory3.createSlot(slotParams);
			parkingSlots3.put(parkingSlot3.getID(), parkingSlot3);
		}

		params.add("PLUS");
		params.add(x3);
		params.add(y3);
		params.add(parkingSlots3);
		Station station3 = (Station) abstractFactory.createStation(params);
		
		//4th station :
		params.clear();
		double x4 = 6;
		double y4 = 5;
		HashMap<Integer, ParkingSlot> parkingSlots4 = new HashMap<Integer, ParkingSlot>() ;
		ConcreteSlotFactory slotFactory4 = (ConcreteSlotFactory) FactoryProducer.createFactory("SLOT");

		for(int j = 0; j < 8; j++) { //Adding stations with bicycles
			ArrayList<Object> slotParams = new ArrayList<Object>();
			slotParams.add("PARKING");
			ParkingSlot parkingSlot4 = (ParkingSlot) slotFactory4.createSlot(slotParams);
			parkingSlots4.put(parkingSlot4.getID(), parkingSlot4);
		}

		params.add("STANDARD");
		params.add(x4);
		params.add(y4);
		params.add(parkingSlots4);
		Station station4 = (Station) abstractFactory.createStation(params);
		
		//Adding stations :
		networkTest.getStations().put(station.getID(), station);
		networkTest.getStations().put(station2.getID(), station2);
		networkTest.getStations().put(station3.getID(), station3);
		networkTest.getStations().put(station4.getID(), station4);
		
		
		ArrayList<Station> basicStationList = networkTest.planning(1, 0, 5, 6, "mechanical", new BasicPlanning());
		ArrayList<Station> avoidPlusStationList = networkTest.planning(1, 0, 5, 6, "mechanical", new AvoidPlus());
		ArrayList<Station> preferPlusStationList = networkTest.planning(1, 0, 5, 6, "electrical", new PreferPlus());

		System.out.println("!!!"+basicStationList.get(1));
		System.out.println(networkTest);
		assertEquals(station4, basicStationList.get(1)); //The closest station to the end, that has an available slot, is station4

		assertEquals(station4, avoidPlusStationList.get(1)); //The closest station to the start, that is not Plus and has an available slot, is station4
		
		assertEquals(station2, preferPlusStationList.get(1));

		
	}
	

}
