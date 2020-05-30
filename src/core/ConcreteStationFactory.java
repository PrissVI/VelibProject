package core;

import java.util.ArrayList;
import java.util.HashMap;

public class ConcreteStationFactory extends AbstractFactory {

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
		return null;
	}

	@Override
	Station createStation(ArrayList<Object> params) {
		if (params.size()>0 && params.get(0) instanceof String) {
			String arg0 = (String) params.get(0);
			if (arg0.equalsIgnoreCase("PLUS")
				&& params.size()>2
				&& params.get(1) instanceof Number
				&& params.get(2) instanceof Number
			)
			{
				Double arg1 = (double) params.get(1);
				Double arg2 = (double) params.get(2);
				
				if (params.size()==3) {
					return new PlusStation(arg1,arg2);
				} else if (params.size()==4 && params.get(3) instanceof HashMap) {
					for (Object key: ((HashMap<?,?>) params.get(3)).keySet()) {
						if (!(key instanceof Integer) || !(((HashMap<?,?>) params.get(3)).get(key) instanceof ParkingSlot)) {
							System.out.println("The fourth argument is of unexpected type.");
							return null;
						}
					}
					HashMap<Integer,ParkingSlot> arg3 = (HashMap<Integer,ParkingSlot>) params.get(3);
					return new PlusStation(arg1,arg2,arg3);
				}
			}
			if (arg0.equalsIgnoreCase("STANDARD")
				&& params.size()>2
				&& params.get(1) instanceof Number
				&& params.get(2) instanceof Number
			)
			{
				Double arg1 = (double) params.get(1);
				Double arg2 = (double) params.get(2);

				if (params.size()>3 && params.get(3) instanceof HashMap) {
					for (Object key: ((HashMap<?,?>) params.get(3)).keySet()) {
						if (!(key instanceof Integer) || !(((HashMap<?,?>) params.get(3)).get(key) instanceof ParkingSlot)) {
							System.out.println("The fourth argument is of unexpected type.");
							return new StdStation(arg1,arg2);
						}
					}
					HashMap<Integer,ParkingSlot> arg3 = (HashMap<Integer,ParkingSlot>) params.get(3);
					return new StdStation(arg1,arg2,arg3);
				} else {
					System.out.println("The third argument is of unexpected type.");
					return new StdStation(arg1,arg2);
				}
			}
		}
		System.out.println("Incorrect params in ArrayList argument.");
		return null;
	}

}
