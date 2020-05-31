package test;

import core.*;
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

/**
 * JUnit test to make sure the bicycle Factory Pattern works properly.
 * 
 * @author Ali Raïki
 *
 */

public class BicycleTest {

	@Test
	public void testCreateBicycle() {
		ConcreteBicycleFactory bicycleFactory = new ConcreteBicycleFactory();
		
		ArrayList<Object> params = new ArrayList<Object>();
		params.add("ELECTRICAL");
		
		Bicycle bike = bicycleFactory.createBicycle(params);
	}

}
