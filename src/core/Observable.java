package core;

/**
 * Custom Observable interface for a station to observe its parking slots' state.
 * @author Mathieu Sibué
 */
public interface Observable {
	public void registerObserver(Observer observer);
	public void removeObserver(Observer observer);
	public void notifyObserver(boolean newIsOutOfOrder);
}
