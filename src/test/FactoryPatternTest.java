package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

import core.*;

/**
 * JUnit test to make sure the Factory Pattern works the right way.
 * 
 * @author Ali Raïki
 *
 */

public class FactoryPatternTest {
	
	/**
	 * Test the Factory Producer
	 * 
	 * @see FactoryProducer
	 */
	@Test
	public void testFactoryProducer() {
		
		//1st scenario: Choice is entered correctly.
		ConcretePersonFactory personFactory = (ConcretePersonFactory) FactoryProducer.createFactory("PERSON");
		ConcreteSlotFactory slotFactory = (ConcreteSlotFactory) FactoryProducer.createFactory("SLOT");
		ConcreteCardFactory cardFactory = (ConcreteCardFactory) FactoryProducer.createFactory("CARD");
		ConcreteStationFactory stationFactory = (ConcreteStationFactory) FactoryProducer.createFactory("STATION");
		ConcreteBicycleFactory bicycleFactory = (ConcreteBicycleFactory) FactoryProducer.createFactory("BICYCLE");
		
		assertNotNull(personFactory);
		assertNotNull(slotFactory);
		assertNotNull(cardFactory);
		assertNotNull(stationFactory);
		assertNotNull(bicycleFactory);

		//2nd scenario: Choice is not case-sensitive.
		ConcretePersonFactory personFactory2 = (ConcretePersonFactory) FactoryProducer.createFactory("pErSoN");
		ConcreteSlotFactory slotFactory2 = (ConcreteSlotFactory) FactoryProducer.createFactory("sLoT");
		ConcreteCardFactory cardFactory2 = (ConcreteCardFactory) FactoryProducer.createFactory("cArD");
		ConcreteStationFactory stationFactory2 = (ConcreteStationFactory) FactoryProducer.createFactory("sTaTiOn");
		ConcreteBicycleFactory bicycleFactory2 = (ConcreteBicycleFactory) FactoryProducer.createFactory("bIcYcLe");
		
		assertNotNull(personFactory2);
		assertNotNull(slotFactory2);
		assertNotNull(cardFactory2);
		assertNotNull(stationFactory2);
		assertNotNull(bicycleFactory2);
		
		//3rd scenario: If choice doesn't exist, return null.
		ConcretePersonFactory personFactory3 = (ConcretePersonFactory) FactoryProducer.createFactory("FVKMV");
		ConcreteSlotFactory slotFactory3 = (ConcreteSlotFactory) FactoryProducer.createFactory("sLovfvT");
		ConcreteCardFactory cardFactory3 = (ConcreteCardFactory) FactoryProducer.createFactory("cArffD");
		ConcreteStationFactory stationFactory3 = (ConcreteStationFactory) FactoryProducer.createFactory("");
		ConcreteBicycleFactory bicycleFactory3 = (ConcreteBicycleFactory) FactoryProducer.createFactory("brffLe");

		assertNull(personFactory3);
		assertNull(slotFactory3);
		assertNull(cardFactory3);
		assertNull(stationFactory3);
		assertNull(bicycleFactory3);
		
	}

	/**
	 * Test the Person Factory
	 * 
	 * @see ConcretePersonFactory
	 */
	@Test
	public void testCreatePerson() {
		AbstractFactory abstractFactory = new ConcretePersonFactory();
		
		//1st scenario: Mandatory parameters are correctly entered.
		ArrayList<Object> params = new ArrayList<Object>();
		String name = "John";
		double x = 5; //coordinates of user
		double y = 6; //coordinates of user
		params.add("USER");
		params.add(name);
		params.add(x);
		params.add(y);
		User user = (User) abstractFactory.createPerson(params);
		assertNotNull(user);
		assertEquals("John", user.getName());
		assertEquals(x, user.getX(), 0);
		assertEquals(y, user.getY(), 0);
		
		//2nd scenario: Mandatory and optional parameters are correctly entered
		ArrayList<Object> params2 = new ArrayList<Object>();
		String name2 = "John";
		double x2 = 5; //coordinates of user
		double y2 = 6; //coordinates of user
		double creditCardBalance = 50;
		params2.add("USER");
		params2.add(name2);
		params2.add(x2);
		params2.add(y2);
		params2.add(creditCardBalance);
		params2.add(new Vlibre());
		User user2 = (User) abstractFactory.createPerson(params2);
		assertNotNull(user2);
		assertTrue(user2.getRegistrationCard() instanceof Vlibre);
		assertEquals(creditCardBalance, user2.getCreditCardBalance(), 0);
		
		//3rd scenario: If at least one parameter is wrong, return null.
		ArrayList<Object> params3 = new ArrayList<Object>();
		String name3 = "John";
		String x3 = "5"; //coordinates of user
		double y3 = 6; //coordinates of user
		params3.add("USER");
		params3.add(name3);
		params3.add(x3);
		params3.add(y3);
		User user3 = (User) abstractFactory.createPerson(params3);
		assertNull(user3);
		
		//4th scenario: If parameters entered in the wrong order, return null.
		ArrayList<Object> params4 = new ArrayList<Object>();
		String name4 = "John";
		double x4 = 5; //coordinates of user
		double y4 = 6; //coordinates of user
		params4.add("USER");
		params4.add(x4);
		params4.add(name4);
		params4.add(y4);
		User user4 = (User) abstractFactory.createPerson(params4);
		assertNull(user4);
		
		//5th scenario: Test that the method is not case-sensitive.
		ArrayList<Object> params5 = new ArrayList<Object>();
		String name5 = "John";
		double x5 = 5; //coordinates of user
		double y5 = 6; //coordinates of user
		params5.add("UsEr");
		params5.add(name5);
		params5.add(x5);
		params5.add(y5);
		User user5 = (User) abstractFactory.createPerson(params5);
		assertNotNull(user5);
	}
	
	/**
	 * Test the Slot Factory
	 * 
	 * @see ConcreteSlotFactory
	 */
	@Test
	public void testCreateSlot() {
		AbstractFactory abstractFactory = new ConcreteSlotFactory();
		
		//1st scenario: Mandatory parameters are correctly entered.
		ArrayList<Object> params = new ArrayList<Object>();
		params.add("PARKING");
		ParkingSlot slot = (ParkingSlot) abstractFactory.createSlot(params);
		assertNotNull(slot);
		
		//2nd scenario: Mandatory and optional parameters are correctly entered
		ArrayList<Object> params2 = new ArrayList<Object>();
		params2.add("PARKING");
		params2.add(new MechanicalBike());
		ParkingSlot slot2 = (ParkingSlot) abstractFactory.createSlot(params2);
		assertNotNull(slot2);
		
		//3rd scenario: If at least one parameter is wrong, return null.
		ArrayList<Object> params3 = new ArrayList<Object>();
		params3.add("PARK");
		params3.add(new MechanicalBike());
		ParkingSlot slot3 = (ParkingSlot) abstractFactory.createSlot(params3);
		assertNull(slot3);
		
		//4th scenario: If parameters entered in the wrong order, return null.
		ArrayList<Object> params4 = new ArrayList<Object>();
		params4.add(new MechanicalBike());
		params4.add("PARKING");
		ParkingSlot slot4 = (ParkingSlot) abstractFactory.createSlot(params4);
		assertNull(slot4);
		
		//5th scenario: Test that the method is not case-sensitive.
		ArrayList<Object> params5 = new ArrayList<Object>();
		params5.add("PaRKing");
		params5.add(new MechanicalBike());
		ParkingSlot slot5 = (ParkingSlot) abstractFactory.createSlot(params5);
		assertNotNull(slot5);
	}

	/**
	 * Test the Card Factory
	 * 
	 * @see ConcreteCardFactory
	 */
	@Test
	public void testCreateCard() {
		AbstractFactory abstractFactory = new ConcreteCardFactory();
		
		//1st scenario: Mandatory parameters are correctly entered.
		ArrayList<Object> params = new ArrayList<Object>();
		int timeCreditBalance = 10;
		params.add("VLIBRE");
		params.add(timeCreditBalance);
		Card card = (Card) abstractFactory.createCard(params);
		assertNotNull(card);
		
		//2rd scenario: If at least one parameter is wrong, return null.
		ArrayList<Object> params2 = new ArrayList<Object>();
		String timeCreditBalance2 = "10";
		params2.add("VLIBRE");
		params2.add(timeCreditBalance2);
		Card card2 = (Card) abstractFactory.createCard(params2);
		assertNull(card2);
		
		//3rd scenario: If parameters entered in the wrong order, return null.
		ArrayList<Object> params3 = new ArrayList<Object>();
		int timeCreditBalance3 = 10;
		params3.add(timeCreditBalance3);
		params3.add("VMAX");
		Card card3 = (Card) abstractFactory.createCard(params3);
		assertNull(card3);
		
		//4th scenario: Test that the method is not case-sensitive.
		ArrayList<Object> params4 = new ArrayList<Object>();
		int timeCreditBalance4 = 10;
		params4.add("VmaX");
		params4.add(timeCreditBalance4);
		Card card4 = (Card) abstractFactory.createCard(params4);
		assertNotNull(card4);
		
	}
	
	/**
	 * Test the Station Factory
	 * 
	 * @see ConcreteStationFactory
	 */
	@Test
	public void testCreateStation() {
		AbstractFactory abstractFactory = new ConcreteStationFactory();
		
		//1st scenario: Mandatory parameters are correctly entered.
		ArrayList<Object> params = new ArrayList<Object>();
		double x = 4;
		double y = 5;
		params.add("PLUS");
		params.add(x);
		params.add(y);
		Station station = (Station) abstractFactory.createStation(params);
		assertNotNull(station);
		
		//2nd scenario: Mandatory and optional parameters are correctly entered
		ArrayList<Object> params2 = new ArrayList<Object>();
		double x2 = 4;
		double y2 = 5;
		HashMap<Integer, ParkingSlot> parkingSlots = new HashMap<Integer, ParkingSlot>() ;
		ConcreteSlotFactory slotFactory = (ConcreteSlotFactory) FactoryProducer.createFactory("SLOT");

		for(int j = 0; j < 10; j++) {
			ArrayList<Object> slotParams = new ArrayList<Object>();
			slotParams.add("PARKING");
			ParkingSlot parkingSlot = (ParkingSlot) slotFactory.createSlot(slotParams);
			parkingSlots.put(parkingSlot.getID(), parkingSlot);
		}
		
		params2.add("STANDARD");
		params2.add(x2);
		params2.add(y2);
		params2.add(parkingSlots);
		Station station2 = (Station) abstractFactory.createStation(params2);
		assertNotNull(station2);
		
		//3rd scenario: If at least one parameter is wrong, return null.
		ArrayList<Object> params3 = new ArrayList<Object>();
		double x3 = 4;
		String y3 = "5";
		params3.add("PLUS");
		params3.add(x3);
		params3.add(y3);
		Station station3 = (Station) abstractFactory.createStation(params3);
		assertNull(station3);
		
		//4th scenario: If parameters entered in the wrong order, return null.
		ArrayList<Object> params4 = new ArrayList<Object>();
		double x4 = 4;
		double y4 = 5;
		params4.add(x4);
		params4.add("STANDARD");
		params4.add(y4);
		Station station4 = (Station) abstractFactory.createStation(params4);
		assertNull(station4);
		
		//5th scenario: Test that the method is not case-sensitive.
		ArrayList<Object> params5 = new ArrayList<Object>();
		double x5 = 4;
		double y5 = 5;
		params5.add("pluS");
		params5.add(x5);
		params5.add(y5);
		Station station5 = (Station) abstractFactory.createStation(params5);
		assertNotNull(station5);
		
	}
	
	/**
	 * Test the Bicycle Factory
	 * 
	 * @see ConcreteBicycleFactory
	 */
	@Test
	public void testCreateBicycle() {
		AbstractFactory abstractFactory = new ConcreteBicycleFactory();
		
		//1st scenario: Mandatory parameters are correctly entered.
		ArrayList<Object> params = new ArrayList<Object>();
		params.add("MECHANICAL");
		Bicycle bicycle = (Bicycle) abstractFactory.createBicycle(params);
		assertNotNull(bicycle);
		
		//2rd scenario: If at least one parameter is wrong, return null.
		ArrayList<Object> params2 = new ArrayList<Object>();
		params2.add("mech");
		Bicycle bicycle2 = (Bicycle) abstractFactory.createBicycle(params2);
		assertNull(bicycle2);
		
		//3rd scenario: Test that the method is not case-sensitive.
		ArrayList<Object> params3 = new ArrayList<Object>();
		params3.add("ElecTRICal");
		Bicycle bicycle3 = (Bicycle) abstractFactory.createBicycle(params);
		assertNotNull(bicycle3);
		
	}

}
