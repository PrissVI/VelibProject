package test;

import core.*;
import static org.junit.Assert.*;

import org.junit.Test;

/**
 * JUnit test to make sure the Ride Planning Strategy Pattern returns the right stations.
 * 
 * @author Ali Raïki
 *
 */

public class ridePlanTest {

	/**
	 * Setting up the network before doing the tests, with 3 stations with 10 parking slots each, as well as 10 users, and a bike population of 70%.
	 * 
	 * Test the basic ride planning, which should return the station closest to the start coordinates and the station closest to the end coordinates.
	 *  
	 */
	@Test
	public void testBasicPlanning() {
		MyVelibNetwork network = new MyVelibNetwork(10);
		network.addStations(3, 10);
		network.addUsers(10);
		network.addBicyclePercentage(0.7);
		network.planning(5, 5, 8, 8, "electrical", new BasicPlanning());
	}

}
