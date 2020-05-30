package core;

import java.util.ArrayList;

public class ConcreteCardFactory extends AbstractFactory {

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
		if (params.size()>1 && params.get(0) instanceof String) {
			String arg0 = (String) params.get(0);
			if (arg0.equalsIgnoreCase("VLIBRE") && params.get(1) instanceof Number) {
				Integer arg1 = (int) params.get(1);	
				return new Vlibre(arg1);
			} else if (arg0.equalsIgnoreCase("VMAX") && params.get(1) instanceof Number) {
				Integer arg1 = (int) params.get(1);	
				return new Vmax(arg1);
			}
		}			
			/*try {
				String arg0 = params.get(0);
				String arg1 = params.get(1);
				if (arg0.equalsIgnoreCase("VLIBRE")) {
					return new Vlibre(Integer.parseInt(arg1));
				} else if (arg0.equalsIgnoreCase("VMAX")) {
					return new Vmax(Integer.parseInt(arg1));
				}					
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return null;
			}*/
		System.out.println("Incorrect params in ArrayList argument.");
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
