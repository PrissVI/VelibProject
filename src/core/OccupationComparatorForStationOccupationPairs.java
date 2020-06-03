package core;

import java.util.AbstractMap.SimpleEntry;
//import java.util.ArrayList;
import java.util.Comparator;
//import java.util.Date;
//import java.util.HashMap;

public class OccupationComparatorForStationOccupationPairs implements Comparator<SimpleEntry<Station,Double>> {

	@Override
	public int compare(SimpleEntry<Station, Double> o1, SimpleEntry<Station, Double> o2) {
		double res = o1.getValue() - o2.getValue();
		if (res<0) {
			return -1;
		} else if (res>0) {
			return 1;
		} else {
			return 0;
		}
	}

}
