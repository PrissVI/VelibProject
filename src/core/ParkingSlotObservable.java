package core;

/**
 * Custom Observable interface for a station to observe its parking slots' state.
 * @author Mathieu Sibué
 */
public interface ParkingSlotObservable {
	public void registerObserver(StationObserver observer);
	public void removeObserver(StationObserver observer);
	public void notifyObserver(boolean newIsOutOfOrder);
}
