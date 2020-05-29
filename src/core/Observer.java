package core;

/**
 * Custom Observer interface for a station to observe its parking slots' state.
 * @author Mathieu Sibu�
 */
public interface Observer {
	public void update(boolean isOutOfOrder);
}
