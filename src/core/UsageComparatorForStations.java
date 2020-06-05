package core;

import java.util.Comparator;

/**
 * Implementation of the interface Comparator (of Stations) to order stations wrt their usage.
 * @author Mathieu Sibué
 *
 */
public class UsageComparatorForStations implements Comparator<Station> {

	@Override
	public int compare(Station o1, Station o2) {
		return o2.getTotalNbOfRentOps() + o2.getTotalNbOfReturnOps() - (o1.getTotalNbOfRentOps() + o1.getTotalNbOfReturnOps());
	}

}
