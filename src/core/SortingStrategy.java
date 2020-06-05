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
	 * 			an HashMap of <Integer,Station> for which stations have to be sorted
	 * @return an ArrayList of the sorted stations
	 */
	public ArrayList<Station> sort(HashMap<Integer,Station> stations);
}
