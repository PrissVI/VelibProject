package core;

import java.util.ArrayList;

public abstract class AbstractFactory {
	abstract Person createPerson(ArrayList<Object> params);
	abstract Slot createSlot(ArrayList<Object> params);
	abstract Card createCard(ArrayList<Object> params);
	abstract Bicycle createBicycle(ArrayList<Object> params);
	abstract Station createStation(ArrayList<Object> params);
}
