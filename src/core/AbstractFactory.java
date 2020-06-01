package core;

import java.util.ArrayList;

/**
 * Abstract class used in the Abstract Factory pattern to "group" concrete factories that extend it.
 * @author Mathieu Sibué
 */
public abstract class AbstractFactory {
	public abstract Person createPerson(ArrayList<Object> params);
	public abstract Slot createSlot(ArrayList<Object> params);
	public abstract Card createCard(ArrayList<Object> params);
	public abstract Bicycle createBicycle(ArrayList<Object> params);
	public abstract Station createStation(ArrayList<Object> params);
}
