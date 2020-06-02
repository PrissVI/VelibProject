package test;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.junit.Test;

import core.*;

/**
 * JUnit test to make sure the MyVelibNetwork methods work properly (adding users, stations and bicycles). 
 * 
 * @author Ali Raïki and Mathieu Sibué
 *
 */

public class MyVelibNetworkTest {

	/**
	 * Set up a network of side 10.
	 * 
	 * Test if the right number of stations and parking slots is added, and correctly initialized.
	 *  
	 */
	@Test
	public void testAddStations() {
		MyVelibNetwork network = new MyVelibNetwork("Test",10);
		network.addStations(6, 11); //Adding 6 stations, with 11 parking slots in each one.
		
		assertEquals(network.getStations().size(), 6);
		
		HashMap.Entry<Integer,Station> stationEntry = network.getStations().entrySet().iterator().next();
		int nbOfParkingSlots = stationEntry.getValue().getParkingSlots().size();
		int totalParkingSlots = nbOfParkingSlots*network.getStations().size();
		assertEquals(totalParkingSlots, 66);
		
		for(Station station : network.getStations().values()) {
			assertTrue(station.isOnline());
			assertNotNull(station.getX());
			assertNotNull(station.getY());
		}
		
	}

	/**
	 * Set up a network of side 10.
	 * 
	 * Test if the right number of users is added and that all the required fields are correctly filled.
	 *  
	 */
	@Test
	public void testAddUsers() {
		MyVelibNetwork network = new MyVelibNetwork("Test",10);
		network.addUsers(10); //Adding 10 users.
		
		assertEquals(network.getUsers().size(), 10);
		
		for(User user : network.getUsers().values()) {
			assertNotNull(user.getName());
			assertNotNull(user.getCreditCardBalance());
			assertNotNull(user.getX());
			assertNotNull(user.getY());
		}
		
	}

	/**
	 * Set up a network of side 10.
	 * 
	 * Test adding a single user.
	 *  
	 */
	@Test
	public void testAddUser() {
		MyVelibNetwork network = new MyVelibNetwork("Test",10);
		network.addUser("Georges", "vmax"); //Add a user Georges with a Vmax Card;
		network.addUser("Suzie", "vlibre"); //Add a user Suzie with a Vlibre Card;
		network.addUser("John", "none"); //Add a user John with no registration Card;
		
		assertEquals(network.getUsers().size(), 3);		
	}
	
	/**
	 * Set up a network of side 10, and 3 stations with 10 parking slots each.
	 * 
	 * Test adding a certain amount of bicycles.
	 *  
	 */
	@Test
	//1st scenario : There are more parking slots available than bicycles (normal scenario).
	public void testAddBicyclesNumber() {
		MyVelibNetwork network = new MyVelibNetwork("Test",10);
		network.addStations(3, 10);
		
		network.addBicycleNumber(25);
		assertEquals(network.getBicycles().size(), 25);		
		
	}
	
	//2nd scenario : There are not enough parking slots available.
	@Test(expected = RuntimeException.class)
	public void testAddBicyclesNumberTooBig() {
		MyVelibNetwork network = new MyVelibNetwork("Test",10);
		network.addStations(3, 10);
		
		network.addBicycleNumber(15); //Adding 15 bicycles
		network.addBicycleNumber(20); //Then trying to add 20 more bicycles
		
	}
	
	/**
	 * Set up a network of side 10, and 3 stations with 10 parking slots each.
	 * 
	 * Test populating a percentage of parking slots.
	 *  
	 */
	@Test
	//1st scenario : There are more parking slots available than bicycles (normal scenario).
	public void testAddBicyclesPercentage() {
		MyVelibNetwork network = new MyVelibNetwork("Test",10);
		network.addStations(3, 10);
		
		network.addBicyclePercentage(0.5);
		assertEquals(network.getBicycles().size(), 15);		
		
	}
	
	//2nd scenario : There are not enough parking slots available.
	@Test(expected = RuntimeException.class)
	public void testAddBicyclesPercentageTooBig() {
		MyVelibNetwork network = new MyVelibNetwork("Test",10);
		network.addStations(3, 10);
		
		network.addBicyclePercentage(1.2);

	}
	
	
	//stats tests
	/**
	 * Test for the getEarliestAndLatestActivities(...) method
	 * We check here if an exception is thrown when there hasn't been any activity in the network
	 */
	@Test
	public void testGetEarliestAndLatestActivities1() {
		MyVelibNetwork network = new MyVelibNetwork("Test",10);
		network.addStations(3, 10);
		
		Exception exception = assertThrows(RuntimeException.class, () -> {
			network.getEarliestAndLatestActivity();
		});
		
		String expectedMessage = "Not enough user activity logs to define earliest non-null and latest activity.";
		String actualMessage = exception.getMessage();
		
		assertEquals(expectedMessage,actualMessage);
		
		//even if we initialize our stations by populating our parking slots right after
		//there has not been any rent or return activity => throws exception
		network.addBicyclePercentage(0.5);
		
		Exception exception2 = assertThrows(RuntimeException.class, () -> {
			network.getEarliestAndLatestActivity();
		});
		
		String expectedMessage2 = "Not enough user activity logs to define earliest non-null and latest activity.";
		String actualMessage2 = exception2.getMessage();
		
		assertEquals(expectedMessage2,actualMessage2);
	}
	
	/**
	 * Test for the getEarliestAndLatestActivities(...) method
	 * We check here if an exception is thrown when stations have not been initialized with parking slots
	 */
	@Test
	public void testGetEarliestAndLatestActivities2() {
		MyVelibNetwork network = new MyVelibNetwork("Test",10);
		
		Exception exception = assertThrows(RuntimeException.class, () -> {
			network.getEarliestAndLatestActivity();
		});
		
		String expectedMessage = "No available station yet.";
		String actualMessage = exception.getMessage();
		
		assertEquals(expectedMessage,actualMessage);
	}
	
	/**
	 * Test for the getEarliestAndLatestActivities(...) method
	 * We check here if everything works in normal conditions
	 * Here a User u1 rents a bike in a Station st1 at d1, returns it in Station st2 at date d2
	 * He then proceeds to rent a bike at date d3 in st2, then returns it at date d4 in st1
	 */
	@Test
	public void testGetEarliestAndLatestActivities3() {
		MyVelibNetwork network = new MyVelibNetwork("Test",10);
		network.addStations(3, 10);
		
		network.addBicycleNumber(15); //Adding 15 bicycles
		
		ArrayList<Object> params = new ArrayList<Object>();
		params.add("USER");
		params.add("Rick");
		params.add(1);
		params.add(0);
		User u1 = (User) MyVelibNetwork.getPersonFactory().createPerson(params);
		HashMap<Integer,User> users = network.getUsers();
		users.put(u1.getID(),u1);
		network.setUsers(users);
		
		Object[] stations = network.getStations().values().toArray();
		Station st1 = (Station) stations[0];
		Station st2 = (Station) stations[1];
				
		Date d1 = ActivityLog.getDate(2020, 7, 10, 8, 0, 0);
		Date d2 = ActivityLog.getDate(2020, 7, 10, 8, 10, 0);
		Date d3 = ActivityLog.getDate(2020, 7, 10, 8, 20, 0);
		Date d4 = ActivityLog.getDate(2020, 7, 10, 8, 30, 0);
		
		u1.setX(st1.getX());
		u1.setY(st1.getY());
		
		u1.rentBicycle(st1, d1);
		
		u1.setX(st2.getX());
		u1.setY(st2.getY());
		
		u1.returnBicycle(st2, d2);
		
		u1.rentBicycle(st2, d3);
		
		u1.setX(st1.getX());
		u1.setY(st1.getY());
		
		u1.returnBicycle(st1, d4);
		
		ArrayList<Date> expectedRes = new ArrayList<Date>();
		expectedRes.add(d1);
		expectedRes.add(d4);
		assertEquals(network.getEarliestAndLatestActivity(),expectedRes);
	}
	
	
	/**
	 * Test of the getUserBalance(...) method
	 * We check here if an exception is thrown when trying to get information about a user not associated with the network
	 */
	@Test
	public void testGetUserBalance1() {
		MyVelibNetwork network = new MyVelibNetwork("Test",10);
		
		Exception exception = assertThrows(RuntimeException.class, () -> {
			network.getUserBalance(300);
		});
		
		String expectedMessage = "User " + 300 + " not in network " + network.getID() + ".";
		String actualMessage = exception.getMessage();
		
		assertEquals(expectedMessage,actualMessage);
	}
	
	/**
	 * Test of the getUserBalance(...) method
	 * Here a User u1 rents a bike in a Station st1 at d1, returns it in Station st2 at date d2
	 * He then proceeds to rent a bike at date d3 in st2, then returns it at date d4 in st1
	 * Then we just display his balance
	 */
	@Test
	public void testGetUserBalance2() {
		MyVelibNetwork network = new MyVelibNetwork("Test",10);
		network.addStations(3, 10);
		
		network.addBicycleNumber(15); //Adding 15 bicycles
		
		ArrayList<Object> params = new ArrayList<Object>();
		params.add("USER");
		params.add("Rick");
		params.add(1);
		params.add(0);
		User u1 = (User) MyVelibNetwork.getPersonFactory().createPerson(params);
		HashMap<Integer,User> users = network.getUsers();
		users.put(u1.getID(),u1);
		network.setUsers(users);
		
		Object[] stations = network.getStations().values().toArray();
		Station st1 = (Station) stations[0];
		Station st2 = (Station) stations[1];
				
		Date d1 = ActivityLog.getDate(2020, 7, 10, 8, 0, 0);
		Date d2 = ActivityLog.getDate(2020, 7, 10, 8, 10, 0);
		Date d3 = ActivityLog.getDate(2020, 7, 10, 8, 20, 0);
		Date d4 = ActivityLog.getDate(2020, 7, 10, 8, 30, 0);
		
		u1.setX(st1.getX());
		u1.setY(st1.getY());
		
		u1.rentBicycle(st1, d1);
		
		u1.setX(st2.getX());
		u1.setY(st2.getY());
		
		u1.returnBicycle(st2, d2);
		
		u1.rentBicycle(st2, d3);
		
		u1.setX(st1.getX());
		u1.setY(st1.getY());
		
		u1.returnBicycle(st1, d4);
		
		String expectedRes = u1.getStatistics();
		System.out.println(expectedRes);
		assertEquals(network.getUserBalance(u1.getID()),expectedRes);
	}
	
	
	/**
	 * Test of the getStationBalance(...) method
	 * We check here if an exception is thrown when trying to get information about a station not associated with the network
	 */
	@Test
	public void testGetStationBalance1() {
		MyVelibNetwork network = new MyVelibNetwork("Test",10);
		
		Exception exception = assertThrows(RuntimeException.class, () -> {
			network.getStationBalance(300);
		});
		
		String expectedMessage = "Station 300 not in network " + network.getID() + ".";
		String actualMessage = exception.getMessage();
		
		assertEquals(expectedMessage,actualMessage);
	}
	
	/**
	 * Test of the getStationBalance(...) method (both overloaded versions)
	 * Here a User u1 rents a bike in a Station st1 at d1, returns it in Station st2 at date d2
	 * He then proceeds to rent a bike at date d3 in st2, then returns it at date d4 in st1
	 * Then we just display st1's balance as well as st2's
	 */
	@Test
	public void testGetStationBalance2() {
		MyVelibNetwork network = new MyVelibNetwork("Test",10);
		network.addStations(3, 10);
		
		network.addBicycleNumber(15); //Adding 15 bicycles
		
		ArrayList<Object> params = new ArrayList<Object>();
		params.add("USER");
		params.add("Rick");
		params.add(1);
		params.add(0);
		User u1 = (User) MyVelibNetwork.getPersonFactory().createPerson(params);
		HashMap<Integer,User> users = network.getUsers();
		users.put(u1.getID(),u1);
		network.setUsers(users);
		
		Object[] stations = network.getStations().values().toArray();
		Station st1 = (Station) stations[0];
		Station st2 = (Station) stations[1];
		
		System.out.println(st1.getParkingSlots());
		System.out.println(st2.getParkingSlots());
				
		Date d1 = ActivityLog.getDate(2020, 7, 10, 8, 0, 0);
		Date d2 = ActivityLog.getDate(2020, 7, 10, 8, 10, 0);
		Date d3 = ActivityLog.getDate(2020, 7, 10, 8, 20, 0);
		Date d4 = ActivityLog.getDate(2020, 7, 10, 8, 30, 0);
		
		u1.setX(st1.getX());
		u1.setY(st1.getY());
		
		u1.rentBicycle(st1, d1);
		
		u1.setX(st2.getX());
		u1.setY(st2.getY());
		
		u1.returnBicycle(st2, d2);
		
		u1.rentBicycle(st2, d3);
		
		u1.setX(st1.getX());
		u1.setY(st1.getY());
		
		u1.returnBicycle(st1, d4);
		
		String expectedRes = st1.getStatistics(d1,d4);
		System.out.println(expectedRes);
		assertEquals(network.getStationBalance(st1.getID()),expectedRes);
		
		String expectedRes2 = st2.getStatistics(d1,d4);
		System.out.println(expectedRes2);
		assertEquals(network.getStationBalance(st2.getID()),expectedRes2);
		
		//we check that our 2 overloaded methods (one using getEarliestAndLatestActivities()) return the same string
		assertEquals(network.getStationBalance(st1.getID(),d1,d4),network.getStationBalance(st1.getID()));
		assertEquals(network.getStationBalance(st2.getID(),d1,d4),network.getStationBalance(st2.getID()));
	}
	
	
	/**
	 * Test of the sortStations(...) method
	 * We check if an exception is thrown when an invalid sort policy is typed
	 */
	@Test
	public void testSortStations1() {
		MyVelibNetwork network = new MyVelibNetwork("Test",10);
		network.addStations(3, 10);
		
		Exception exception = assertThrows(RuntimeException.class, () -> {
			network.sortStations("test");
		});
		
		String expectedMessage = "Incorrect sorting policy.";
		String actualMessage = exception.getMessage();
		
		assertEquals(expectedMessage,actualMessage);
	}
	
	/**
	 * Test of the sortStations(...) method
	 * The different ordering policies have already been tested in SortLeastOccupiedTest and SortMostUsedTest
	 * We are only going to check if policies "LEAST OCCUPIED" and "LEAST OCCUPIED WITH DATES" return the same thing when infDate and supDate are indeed set to the earliest and latest activities in the network 
	 * Here a User u1 rents a bike in a Station st1 at d1, returns it in Station st2 at date d2
	 * He then proceeds to rent a bike at date d3 in st2, then returns it at date d4 in st1
	 * Then we just display st1's balance as well as st2's
	 */
	@Test
	public void testSortStations2() {
		MyVelibNetwork network = new MyVelibNetwork("Test",10);
		network.addStations(3, 10);
		
		network.addBicycleNumber(15); //Adding 15 bicycles
		
		ArrayList<Object> params = new ArrayList<Object>();
		params.add("USER");
		params.add("Rick");
		params.add(1);
		params.add(0);
		User u1 = (User) MyVelibNetwork.getPersonFactory().createPerson(params);
		HashMap<Integer,User> users = network.getUsers();
		users.put(u1.getID(),u1);
		network.setUsers(users);
		
		Object[] stations = network.getStations().values().toArray();
		Station st1 = (Station) stations[0];
		Station st2 = (Station) stations[1];
		
		System.out.println(st1.getParkingSlots());
		System.out.println(st2.getParkingSlots());
				
		Date d1 = ActivityLog.getDate(2020, 7, 10, 8, 0, 0);
		Date d2 = ActivityLog.getDate(2020, 7, 10, 8, 10, 0);
		Date d3 = ActivityLog.getDate(2020, 7, 10, 8, 20, 0);
		Date d4 = ActivityLog.getDate(2020, 7, 10, 8, 30, 0);
		
		u1.setX(st1.getX());
		u1.setY(st1.getY());
		
		u1.rentBicycle(st1, d1);
		
		u1.setX(st2.getX());
		u1.setY(st2.getY());
		
		u1.returnBicycle(st2, d2);
		
		u1.rentBicycle(st2, d3);
		
		u1.setX(st1.getX());
		u1.setY(st1.getY());
		
		u1.returnBicycle(st1, d4);
		
		assertEquals(network.sortStations("LEAST OCCUPIED WITH DATES"),network.sortStations("LEAST OCCUPIED"));
		//when asked, type 2020/8/10-8:0:0 for initial date and 2020/8/10-8:30:0 for final date
	}
	
}
