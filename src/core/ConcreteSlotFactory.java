package core;

import java.util.ArrayList;

public class ConcreteSlotFactory extends AbstractFactory {

	@Override
	Person createPerson(ArrayList<Object> params) {
		return null;
	}

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
