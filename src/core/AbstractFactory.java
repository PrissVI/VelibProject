package core;

import java.util.ArrayList;

/**
 * Abstract class used in the Abstract Factory pattern to "group" concrete factories that extend it.
 * @author Mathieu Sibué
 */
public abstract class AbstractFactory {
	abstract Person createPerson(ArrayList<Object> params);
	abstract Slot createSlot(ArrayList<Object> params);
	abstract Card createCard(ArrayList<Object> params);
	abstract Bicycle createBicycle(ArrayList<Object> params);
	abstract Station createStation(ArrayList<Object> params);
}
