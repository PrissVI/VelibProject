package core;

import java.util.ArrayList;
import java.util.Random;

public class Launcher {
	public static void main(String[] args) {
		Card vlibre = new Vlibre();
		Card vmax = new Vmax();
		System.out.println(vlibre.getTimeCredit()+"et"+vmax.getTimeCredit());
		double cost1 = compute(vlibre);
		double cost2 = compute(vmax);
		System.out.println(cost1 + "and" + cost2);
		System.out.println(vlibre.getTimeCredit()+"et"+vmax.getTimeCredit());
	}
	
	private static double compute(Card card) {
		CardVisitor visitor = new ConcreteCardVisitor();
		double sum = card.accept(visitor,90, new MechanicalBike() );
		return sum;
		}
}
