package core;

import java.util.Comparator;

public class UsageComparatorForStations implements Comparator<Station> {

	@Override
	public int compare(Station o1, Station o2) {
		return o2.getTotalNbOfRentOps() + o2.getTotalNbOfReturnOps() - (o1.getTotalNbOfRentOps() + o1.getTotalNbOfReturnOps());
	}

}
