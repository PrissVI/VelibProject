package core;

import java.util.ArrayList;

/** 
 * User to instantiate Cards (thus Vlibre and Vmax ones), extending the AbstractFactory abstract class
 * @author Mathieu Sibué
*/
public class ConcreteCardFactory extends AbstractFactory {

	@Override
	public Person createPerson(ArrayList<Object> params) {
		return null;
	}

	@Override
	public Slot createSlot(ArrayList<Object> params) {
		return null;
	}

	/**
	 * Method used to instantiate different types of Card objects (for now, only Vlibre and Vmax exist)
	 * @param ArrayList<Object>: the parameters used to instantiate Card objects of a specific type.
	 * <p>To instantiate a certain type of Card, at least 2 parameters are expected in the ArrayList:
	 * <ul>
	 * 		<li>String: the subclass of Card we want to instantiate</li>
	 * 		<li>Integer: the initial value of the time credit of the registration card</li>
	 * </ul>
	 * </p>
	 * @return Card: an object of a subclass of Card
	*/
	@Override
	public Card createCard(ArrayList<Object> params) {
		if (params == null) {
			return null;
		}
		if (params.size()>0 && params.get(0) instanceof String) {
			String arg0 = (String) params.get(0);
			if (params.size()==1) {
				if (arg0.equalsIgnoreCase("VLIBRE")) {
					return new Vlibre();
				} else if (arg0.equalsIgnoreCase("VMAX")) {	
					return new Vmax();
				}				
			} else if (params.size()==2) {
				if (arg0.equalsIgnoreCase("VLIBRE") && params.get(1) instanceof Number) {
					int arg1 = ((Number) params.get(1)).intValue();	
					return new Vlibre(arg1);
				} else if (arg0.equalsIgnoreCase("VMAX") && params.get(1) instanceof Number) {
					int arg1 = ((Number) params.get(1)).intValue();	
					return new Vmax(arg1);
				}				
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
	public Bicycle createBicycle(ArrayList<Object> params) {
		return null;
	}

	@Override
	public Station createStation(ArrayList<Object> params) {
		return null;
	}

}
