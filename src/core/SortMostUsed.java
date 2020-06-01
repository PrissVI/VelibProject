package core;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

/**
 * Strategy implementation to sort stations, which allows us to embed the code to sort (desc) wrt usage ONLY in this class and not in the client code MyVelibNetwork
 * @author Mathieu Sibué
 */
public class SortMostUsed implements SortingStrategy {

	@Override
	public ArrayList<Station> sort(HashMap<Integer,Station> stations) {
		ArrayList<Station> myStations = new ArrayList<Station>();
		for (Station st: stations.values()) {
			myStations.add(st);
		}
		Comparator<Station> usageComp = new UsageComparatorForStations();
		myStations.sort(usageComp);
		return myStations;
	}

}
