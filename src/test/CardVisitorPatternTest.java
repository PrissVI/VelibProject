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

public class CardVisitorPatternTest {

	/**
	 * Test the visitor pattern put in place to get ride costs from a card, and update the time credit balance.
	 * 
	 * @result The ride costs will be correctly computed depending on the type of card, and the time credit balance will be updated accordingly.
	 * 
	 */
	@Test
	public void testCardVisitorPattern() {
		CardVisitor visitor = new ConcreteCardVisitor();
		
		//1st scenario : The time credit is not used entirely (with a mechanical bike).
		Card vlibre = new Vlibre(20);
		Card vmax = new Vmax(20);
		double vlibreCost = vlibre.accept(visitor, 65, new MechanicalBike()); //Ride cost if the ride lasted 65 minutes with a mechanical bike, with a Vlibre card.
		double vmaxCost = vmax.accept(visitor, 65, new MechanicalBike()); //Ride cost if the ride lasted 65 minutes with an mechanical bike, with a Vmax card.

		assertEquals(0, vlibreCost,0);
		assertEquals(0, vmaxCost,0);
		assertEquals(15, vlibre.getTimeCredit(),0);
		assertEquals(15, vmax.getTimeCredit(),0);
		
		//2nd scenario : The time credit is not used entirely (with an electrical bike)
		Card vlibre2 = new Vlibre(20);
		Card vmax2 = new Vmax(20);
		double vlibreCost2 = vlibre2.accept(visitor, 65, new ElectricalBike()); //Ride cost if the ride lasted 65 minutes with an electrical bike, with a Vlibre card.
		double vmaxCost2 = vmax2.accept(visitor, 65, new ElectricalBike()); //Ride cost if the ride lasted 65 minutes with an electrical bike, with a Vmax card.

		assertEquals(1, vlibreCost2,0);
		assertEquals(0, vmaxCost2,0);
		assertEquals(15, vlibre2.getTimeCredit(),0);
		assertEquals(15, vmax2.getTimeCredit(),0);
		
		//3rd scenario : The time credit is used entirely (with a mechanical bike).
		Card vlibre3 = new Vlibre(20);
		Card vmax3 = new Vmax(20);
		double vlibreCost3 = vlibre3.accept(visitor, 135, new MechanicalBike()); //Ride cost if the ride lasted 135 minutes with a mechanical bike, with a Vlibre card.
		double vmaxCost3 = vmax3.accept(visitor, 135, new MechanicalBike()); //Ride cost if the ride lasted 135 minutes with a mechanical bike, with a Vmax card.
		double expectedVlibreCost3 = (double) (135-20-60)/60;
		double expectedVmaxCost3 = (double) (135-20-60)/60;
		
		assertEquals(expectedVlibreCost3, vlibreCost3,0);
		assertEquals(expectedVmaxCost3, vmaxCost3,0);
		assertEquals(0, vlibre3.getTimeCredit(),0);
		assertEquals(0, vmax3.getTimeCredit(),0);
		
		//4th scenario : The time credit is used entirely (with an electrical bike).
		Card vlibre4 = new Vlibre(20);
		Card vmax4 = new Vmax(20);
		double vlibreCost4 = vlibre4.accept(visitor, 135, new ElectricalBike()); //Ride cost if the ride lasted 135 minutes with an electrical bike, with a Vlibre card.
		double vmaxCost4 = vmax4.accept(visitor, 135, new ElectricalBike()); //Ride cost if the ride lasted 135 minutes with an electrical bike, with a Vmax card.
		double expectedVlibreCost4 = (double) (135-20-60)/30 + 1;
		double expectedVmaxCost4 = (double) (135-20-60)/60;

		assertEquals(expectedVlibreCost4, vlibreCost4,0);
		assertEquals(expectedVmaxCost4, vmaxCost4,0);
		assertEquals(0, vlibre4.getTimeCredit(),0);
		assertEquals(0, vmax4.getTimeCredit(),0);
	}

}
