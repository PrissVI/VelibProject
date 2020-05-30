package core;

import java.util.ArrayList;

public class ConcretePersonFactory extends AbstractFactory {

	@Override
	Person createPerson(ArrayList<Object> params) {
		//multiple ifs to make the method open to modifications as well (if another type of Person is added
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
		return null;
	}
	
}
