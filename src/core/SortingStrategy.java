package core;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Strategy pattern (interface here) for sorting Stations.
 * @author Mathieu Sibué
 */
public interface SortingStrategy {
	
	/**
	 * Sorts the stations stored in the myVelibNetwork HashMap<Integer,Station> called stations wrt different policies.
	 * @param stations
	 * @return
	 */
	public ArrayList<Station> sort(HashMap<Integer,Station> stations);
}
