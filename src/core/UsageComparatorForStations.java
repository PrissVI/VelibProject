package core;

import java.util.ArrayList;
import java.util.Comparator;

public class UsageComparatorForStations implements Comparator<Station> {

	@Override
	public int compare(Station o1, Station o2) {
		return o2.getTotalNbOfRentOps() + o2.getTotalNbOfReturnOps() - (o1.getTotalNbOfRentOps() + o1.getTotalNbOfReturnOps());
	}

	public static void main(String[] args) {
		Station st1 = new StdStation(0, 2);
		st1.setTotalNbOfRentOps(2);
		st1.setTotalNbOfReturnOps(1);
		
		Station st2 = new StdStation(1, 2);
		st2.setTotalNbOfRentOps(0);
		st2.setTotalNbOfReturnOps(1);
		
		Station st3 = new StdStation(1, 1);
		st3.setTotalNbOfRentOps(0);
		st3.setTotalNbOfReturnOps(0);
		
		ArrayList<Station> mySts = new ArrayList<Station>();
		mySts.add(st2);
		mySts.add(st3);
		mySts.add(st1);
		
		mySts.sort(new UsageComparatorForStations());
		
		System.out.println(mySts); //st1 puis st2 puis st3 : bien ds le bon ordre descendant
	}
}
