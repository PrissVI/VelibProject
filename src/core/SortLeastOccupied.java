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
	
	/*ATTRIBUTES*/
	//just to store the earliest and latest activities in order to compute the occupation rate with no client input
	private Date myInfDate;
	private Date mySupDate;
	
	
	/*CONSTRUCTORS*/
	//constructor used when the client DOES NOT want to input the time window for the occupation rate computation
	public SortLeastOccupied(Date myInfDate, Date mySupDate) {
		super();
		this.myInfDate = myInfDate;
		this.mySupDate = mySupDate;
	}

	//constructor used when the client wants to input the time window for the occupation rate computation
	public SortLeastOccupied() {
		super();
	}
	
	/*METHODS*/
	//custom methods
	//implementation of the sort method from the interface.
	@Override
	public ArrayList<Station> sort(HashMap<Integer,Station> stations) throws RuntimeException {
		ArrayList<Station> myStations = new ArrayList<Station>();
		for (Station st: stations.values()) {
			myStations.add(st);
		}
		Date infDate;
		Date supDate;
		if (myInfDate == null || mySupDate == null) {
			//can throw exceptions
			Scanner sc = new Scanner(System.in);
			
			System.out.println("Enter an initial date (YYYY/MM/DD-HH:MM:SS) for occupation window: ");
			String infDateIn = sc.nextLine();
			infDate = SortLeastOccupied.convertToDate(infDateIn);
			
			System.out.println("Enter an final date (YYYY/MM/DD-HH:MM:SS) for occupation window: ");
			String supDateIn = sc.nextLine();
			supDate = SortLeastOccupied.convertToDate(supDateIn);
			
			sc.close();			
		} else {
			//if the SortLeastOccupied object has been instantiated with no time window for the occupation rate computation, we use the one computed right before and normally passed into the first constructor
			infDate = myInfDate;
			supDate = mySupDate;
		}
		
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
	
	/**
	 * Parses a string in specific format (YYYY/MM/DD-HH:MM:SS) to create a date object from it.
	 * @param s
	 * @return Date parsed from string s
	 * @throws RuntimeException if date in wrong format
	 */
	public static Date convertToDate(String s) throws RuntimeException {
		String[] dateInfo = s.split("/|\\:|\\-");
		
		if (dateInfo.length != 6) {
			throw new RuntimeException("Date in wrong format: check delimiters and/or time granularity");
		}
		
		try {
			Date date = ActivityLog.getDate(Integer.parseInt(dateInfo[0]), Integer.parseInt(dateInfo[1])-1, Integer.parseInt(dateInfo[2]), Integer.parseInt(dateInfo[3]), Integer.parseInt(dateInfo[4]), Integer.parseInt(dateInfo[5]));
			return date;
		} catch (Exception e) {
			//we add our custom exception
			throw new RuntimeException("Date in wrong format: check value of time units");
		}
	}

}
