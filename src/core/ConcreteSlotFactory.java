package core;

import java.util.ArrayList;

/** 
 * User to instantiate Slots (thus ParkingSlots), extending the AbstractFactory abstract class
 * @author Mathieu Sibué
*/
public class ConcreteSlotFactory extends AbstractFactory {

	@Override
	Person createPerson(ArrayList<Object> params) {
		return null;
	}
	
	/**
	 * Method used to instantiate different types of Slot objects (for now, only ParkingSlot exists)
	 * @param ArrayList<Object>: the parameters used to instantiate Slot objects of a specific type.
	 * <p>To instantiate a certain type of Slot, at least 1 parameter is expected  in the ArrayList:
	 * <ul>
	 * 		<li>String: the subclass of Slot we want to instantiate</li>
	 * </ul>
	 * Another optional parameter can be appended (to call an overloaded constructor of ParkingSlot) to create a ParkingSlot, which is a subclass of Slot:
	 * <ul>
	 * 		<li>Bicycle: a Bicycle initially stored in the ParkingSlot</li>
	 * </ul>
	 * </p>
	 * @return Slot: an object of a subclass of Slot
	*/
	@Override
	Slot createSlot(ArrayList<Object> params) {
		if (params.size()>0 && params.get(0) instanceof String) {
			String arg0 = (String) params.get(0);
			if (arg0.equalsIgnoreCase("PARKING")) {
				if (params.size()==1) {
					return new ParkingSlot();
				} else if (params.size()==2 && params.get(1) instanceof Bicycle) {
					Bicycle arg1 = (Bicycle) params.get(1);
					return new ParkingSlot(arg1);	
				}
			}
		}
		System.out.println("Incorrect params (type, number,...) in ArrayList argument.");
		return null;
	}

	@Override
	Card createCard(ArrayList<Object> params) {
		return null;
	}

	@Override
	Bicycle createBicycle(ArrayList<Object> params) {
		return null;
	}

	@Override
	Station createStation(ArrayList<Object> params) {
		return null;
	}

}
