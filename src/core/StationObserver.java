package core;

/**
 * Custom Observer interface for a station to observe its parking slots' state.
 * @author Mathieu Sibu�
 */
public interface StationObserver {
	public void update(boolean newIsOutOfOrder);
}
