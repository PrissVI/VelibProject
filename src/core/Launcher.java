package core;

import java.util.ArrayList;
import java.util.Random;

public class Launcher {
	public static void main(String[] args) {
//		Card vlibre = new Vlibre(20);
//		Card vmax = new Vmax(20);
//		System.out.println(vlibre.getTimeCredit()+"et"+vmax.getTimeCredit());
//		double cost1 = compute(vlibre);
//		double cost2 = compute(vmax);
//		System.out.println(cost1 + "and" + cost2);
//		System.out.println(vlibre.getTimeCredit()+"et"+vmax.getTimeCredit());
//		
//		Bicycle yas = new Bicycle();
//		System.out.println(yas instanceof MechanicalBike);
		Random random = new Random();
		double bla = random.nextDouble();
		double randomAngle = random.nextDouble();
		System.out.println(bla+"et"+randomAngle);

	}
	
	private static double compute(Card card) {
		CardVisitor visitor = new ConcreteCardVisitor();
		double sum = card.accept(visitor, 135, new MechanicalBike() );
		return sum;
		}
}
