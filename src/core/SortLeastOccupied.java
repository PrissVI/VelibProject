package core;

import java.util.AbstractMap.SimpleEntry;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Strategy implementation to sort stations, which allows us to embed the code to sort (asc) wrt occupation ONLY in this class and not in the client code MyVelibNetwork
 * @author Mathieu Sibué
 */
public class SortLeastOccupied implements SortingStrategy {

	@Override
	public ArrayList<Station> sort(HashMap<Integer,Station> stations) {
		ArrayList<Station> myStations = new ArrayList<Station>();
		for (Station st: stations.values()) {
			myStations.add(st);
		}
		
		//can throw exceptions
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Enter an initial date (YYYY/MM/DD-HH:MM:SS) for occupation window: ");
		String infDateIn = sc.nextLine();
		Date infDate = this.convertToDate(infDateIn);
		
		System.out.println("Enter an final date (YYYY/MM/DD-HH:MM:SS) for occupation window: ");
		String supDateIn = sc.nextLine();
		Date supDate = this.convertToDate(supDateIn);
		
		sc.close();
		
		ArrayList<SimpleEntry<Station,Double>> inter = new ArrayList<SimpleEntry<Station,Double>>();
		for (Station st: myStations) {
			inter.add(new SimpleEntry<Station,Double>(st,st.getOccupationRate(infDate, supDate)));
		}
		
		Comparator<SimpleEntry<Station,Double>> occupationComp = new OccupationComparatorForStationOccupationPairs();
		inter.sort(occupationComp);
		
		ArrayList<Station> res = new ArrayList<Station>();
		for (SimpleEntry<Station,Double> se: inter) {
			res.add(se.getKey());
		}
		
		return res;
	}
	
	public Date convertToDate(String s) throws RuntimeException {
		String[] dateInfo = s.split("/|\\:|\\-");
		/*
		System.out.println(Integer.parseInt(dateInfo[0]));
		System.out.println(Integer.parseInt(dateInfo[1]));
		System.out.println(Integer.parseInt(dateInfo[2]));
		System.out.println(Integer.parseInt(dateInfo[3]));
		System.out.println(Integer.parseInt(dateInfo[4]));
		System.out.println(Integer.parseInt(dateInfo[5]));
		*/
		if (dateInfo.length != 6) {
			throw new RuntimeException("Date in wrong format: check delimiters of time granularity");
		}
		
		try {
			Date date = ActivityLog.getDate(Integer.parseInt(dateInfo[0]), Integer.parseInt(dateInfo[1])-1, Integer.parseInt(dateInfo[2]), Integer.parseInt(dateInfo[3]), Integer.parseInt(dateInfo[4]), Integer.parseInt(dateInfo[5]));
			return date;
		} catch (Exception e) {
			//we add our custom exception
			throw new RuntimeException("Date in wrong format: check value of time units");
		}
	}
	
	//test
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
		
		//with only comparator
		SimpleEntry<Station, Double> pair1 = new SimpleEntry<Station, Double>(st1,st1.getOccupationRate(d1, d2));
		SimpleEntry<Station, Double> pair2 = new SimpleEntry<Station, Double>(st2,st2.getOccupationRate(d1, d2));
		SimpleEntry<Station, Double> pair3 = new SimpleEntry<Station, Double>(st3,st3.getOccupationRate(d1, d2));

		ArrayList<SimpleEntry<Station, Double>> myList = new ArrayList<SimpleEntry<Station, Double>>();
		myList.add(pair2);
		myList.add(pair1);
		myList.add(pair3);
		
		myList.sort(new OccupationComparatorForStationOccupationPairs());
		System.out.println(myList);
		
		System.out.println("\n\n");
		
		//with this class
		SortingStrategy ss = new SortLeastOccupied();
		HashMap<Integer,Station> myList2 = new HashMap<Integer,Station>();
		myList2.put(st1.getID(),st1);
		myList2.put(st2.getID(),st2);
		myList2.put(st3.getID(),st3);
		
		ArrayList<Station> myList3 = ss.sort(myList2);
		System.out.println(myList3);
		
	}

}
