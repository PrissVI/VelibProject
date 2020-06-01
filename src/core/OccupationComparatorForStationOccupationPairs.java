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
	
	/*
	public static void main(String[] args) {
		Station st1 = new StdStation(0, 2);
		HashMap<Integer,ParkingSlot> parkingSlots1 = new HashMap<Integer,ParkingSlot>();
		ParkingSlot ps1 = new ParkingSlot(new MechanicalBike());
		parkingSlots1.put(ps1.getID(),ps1);
		st1.setParkingSlots(parkingSlots1);
		
		Station st2 = new StdStation(1, 2);
		HashMap<Integer,ParkingSlot> parkingSlots2 = new HashMap<Integer,ParkingSlot>();
		ParkingSlot ps2 = new ParkingSlot();
		parkingSlots2.put(ps2.getID(),ps2);
		st2.setParkingSlots(parkingSlots2);
		
		Station st3 = new StdStation(2, 2);
		HashMap<Integer,ParkingSlot> parkingSlots3 = new HashMap<Integer,ParkingSlot>();
		ParkingSlot ps3 = new ParkingSlot();
		ParkingSlot ps4 = new ParkingSlot(new MechanicalBike());
		parkingSlots3.put(ps3.getID(),ps3);
		parkingSlots3.put(ps4.getID(),ps4);
		st3.setParkingSlots(parkingSlots3);
		
		
		Date d1 = ActivityLog.getDate(2020, 4, 22, 2, 0, 0);
		Date d2 = ActivityLog.getDate(2020, 4, 22, 2, 10, 0);
		
		SimpleEntry<Station, Double> pair1 = new SimpleEntry<Station, Double>(st1,st1.getOccupationRate(d1, d2));
		SimpleEntry<Station, Double> pair2 = new SimpleEntry<Station, Double>(st2,st2.getOccupationRate(d1, d2));
		SimpleEntry<Station, Double> pair3 = new SimpleEntry<Station, Double>(st3,st3.getOccupationRate(d1, d2));

		ArrayList<SimpleEntry<Station, Double>> myList = new ArrayList<SimpleEntry<Station, Double>>();
		myList.add(pair2);
		myList.add(pair1);
		myList.add(pair3);
		
		myList.sort(new OccupationComparatorForStationOccupationPairs());
		System.out.println(myList);
	}
	*/
}
