package core;

/** 
 * Used to instantiate concrete Factories from the AbstractFactory abstract class
 * @author Mathieu Sibué
*/
public class FactoryProducer {
	
	/**
	 * Creates a concrete Factory of either Slots, Stations, Cards, Persons or Bicycle given an identifier
	 * @param choice
	 * 			String: type of factory we want to create
	 * @return AbstractFactory: the concrete factory we wanted to create
	*/
	public static AbstractFactory createFactory(String choice) {
		if (choice.equalsIgnoreCase("SLOT")) {
			return new ConcreteSlotFactory();
		} else if (choice.equalsIgnoreCase("STATION")) {
			return new ConcreteStationFactory();
		} else if (choice.equalsIgnoreCase("CARD")) {
			return new ConcreteCardFactory();
		} else if (choice.equalsIgnoreCase("PERSON")) {
			return new ConcretePersonFactory();
		} else if (choice.equalsIgnoreCase("BICYCLE")) {
			return new ConcreteBicycleFactory();
		}
		return null;
	}
}
