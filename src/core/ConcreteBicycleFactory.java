package core;

import java.util.ArrayList;

public class ConcreteBicycleFactory extends AbstractFactory {

	@Override
	Person createPerson(ArrayList<Object> params) {
		return null;
	}

	@Override
	Slot createSlot(ArrayList<Object> params) {
		return null;
	}

	@Override
	Card createCard(ArrayList<Object> params) {
		return null;
	}

	@Override
	Bicycle createBicycle(ArrayList<Object> params) {
		if (params.size()>0 && params.get(0) instanceof String) {
			String arg0 = (String) params.get(0);
			if (arg0.equalsIgnoreCase("MECHANICAL")) {
				return new MechanicalBike();
			} else if (arg0.equalsIgnoreCase("ELECTRICAL")) {
				return new ElectricalBike();
			}
		}
		System.out.println("Incorrect params in ArrayList argument.");
		return null;
	}

	@Override
	Station createStation(ArrayList<Object> params) {
		return null;
	}
	
}
