package test;

import core.*;
import static org.junit.Assert.*;

import org.junit.Test;

/**
 * JUnit test to make sure the Card Visitor Pattern works the right way.
 * 
 * @author Ali Raïki
 *
 */

public class CardTest {

	/**
	 * Test the visitor pattern put in place to get ride costs from a card, and update the time credit balance.
	 * 
	 * @result The ride costs will be correctly computed depending on the type of card, and the time credit balance will be updated accordingly.
	 * 
	 */
	@Test
	public void testCardVisitorPattern() {
		
		//1st scenario : The time credit is not used entirely
		Card vlibre = new Vlibre(20);
		Card vmax = new Vmax(20);
		CardVisitor visitor = new ConcreteCardVisitor();
		double vlibreCost = vlibre.accept(visitor, 65, new ElectricalBike()); //Ride cost if the ride lasted 65 minutes with an electrical bike, with a Vlibre card.
		double vmaxCost = vmax.accept(visitor, 65, new ElectricalBike()); //Ride cost if the ride lasted 65 minutes with an electrical bike, with a Vmax card.

		assertEquals(1, vlibreCost,0);
		assertEquals(0, vmaxCost,0);
		assertEquals(15, vlibre.getTimeCredit(),0);
		assertEquals(15, vmax.getTimeCredit(),0);
		
		//2st scenario : The time credit is used entirely
		Card vlibre2 = new Vlibre(20);
		Card vmax2 = new Vmax(20);
		double vlibreCost2 = vlibre2.accept(visitor, 135, new MechanicalBike()); //Ride cost if the ride lasted 135 minutes with a mechanical bike, with a Vlibre card.
		double vmaxCost2 = vmax2.accept(visitor, 135, new MechanicalBike()); //Ride cost if the ride lasted 135 minutes with a mechanical bike, with a Vmax card.
		double expectedVlibreCost = (double) (135-20-60)/60;
		double expectedVmaxCost = (double) (135-20-60)/60;
		
		assertEquals(expectedVlibreCost, vlibreCost2,0);
		assertEquals(expectedVmaxCost, vmaxCost2,0);
		assertEquals(0, vlibre2.getTimeCredit(),0);
		assertEquals(0, vmax2.getTimeCredit(),0);
	}

}
