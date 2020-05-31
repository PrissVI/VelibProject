package core;

import java.util.ArrayList;

/** 
 * User to instantiate Persons (thus Users), extending the AbstractFactory abstract class
 * @author Mathieu Sibué
*/
public class ConcretePersonFactory extends AbstractFactory {

	/**
	 * Method used to instantiate different types of Person objects (for now, only Users exist)
	 * @param ArrayList<Object>: the parameters used to instantiate Person objects of a specific type (eg. Users).
	 * <p>To instantiate a user, at least 4 parameters are expected in the ArrayList (in this precise order):
	 * <ul>
	 * 		<li>String: "USER" (to specify we want to create a User person)</li>
	 * 		<li>String: the user's name</li>
	 * 		<li>Double: the user's x coordinate</li>
	 * 		<li>Double: the user's y coordinate</li>
	 * </ul>
	 * Other optional parameters that can be appended (to call overloaded constructors of User) in the following order to create a User (which is a specific subclass of Person):
	 * <ul>
	 * 		<li>Double: the user's initial credit card balance</li>
	 * 		<li>Card: the user's registration card</li>
	 * </ul>
	 * </p>
	 * @return Person: an object of a subclass of Person
	*/
	@Override
	public Person createPerson(ArrayList<Object> params) {
		//multiple ifs to make the method open to modifications as well (if another type of Person is added
		if (params == null) {
			return null;
		}
		if (params.size()>0 && params.get(0) instanceof String) {
			String arg0 = (String) params.get(0);
			if (arg0.equalsIgnoreCase("USER")) {
				if (params.size()>3 
					&& params.get(1) instanceof String 
					&& params.get(2) instanceof Number 
					&& params.get(3) instanceof Number
				) 
				{
					String arg1 = (String) params.get(1);
					Double arg2 = (double) params.get(2);
					Double arg3 = (double) params.get(3);
					
					if (params.size()==4) {
						return new User(arg1,arg2,arg3);
					} else if (params.size()>=5 && params.get(4) instanceof Number) {
						Double arg4 = (double) params.get(4);
						if (params.size()==5) {
							return new User(arg1,arg2,arg3,arg4);
						} else if (params.size()==6 && params.get(5) instanceof Card) {
							Card arg5 = (Card) params.get(5);
							return new User(arg1,arg2,arg3,arg4,arg5);
						}
					}
				}
			}
		}
		System.out.println("Incorrect params in ArrayList argument.");
		return null;
	}

	@Override
	public Slot createSlot(ArrayList<Object> params) {
		return null;
	}

	@Override
	public Card createCard(ArrayList<Object> params) {
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
