package core;

import java.util.Date;
import java.util.HashMap;

public class ObserverTest {

	public static void main(String[] args) {
		Station mySt = new StdStation(0, 0);
		ParkingSlot ps1 = new ParkingSlot();
		ParkingSlot ps2 = new ParkingSlot();
		
		
		//test1
		
		HashMap<Integer,ParkingSlot> myParkingSlots = new HashMap<Integer,ParkingSlot>();
		myParkingSlots.put(ps1.getID(),ps1);
		myParkingSlots.put(ps2.getID(),ps2);
		
		mySt.setParkingSlots(myParkingSlots);
		System.out.println(mySt.getNbOfOutOfOrderParkingSlots());	//vaut 0
		
		Date d1 = ActivityLog.getDate(2020, 4, 24, 2, 5, 0);

		ps1.setOutOfOrder(true, d1);
		System.out.println(mySt.getNbOfOutOfOrderParkingSlots());	//vaut 1
		
		
		//test2
		
		HashMap<Integer,ParkingSlot> myParkingSlots2 = new HashMap<Integer,ParkingSlot>();
		myParkingSlots2.put(ps1.getID(),ps1);
		myParkingSlots2.put(ps2.getID(),ps2);
		
		mySt.setParkingSlots(myParkingSlots);
		System.out.println(mySt.getNbOfOutOfOrderParkingSlots());	//vaut 1
		
		Date d2 = ActivityLog.getDate(2020, 4, 24, 2, 10, 0);
		ps2.setOutOfOrder(true, d2);
		System.out.println(mySt.getNbOfOutOfOrderParkingSlots());	//vaut 2
		
		mySt.setTerminalOutOfOrder(true);
		System.out.println(mySt.isOnline());		//vaut false => est bien offline
	}

}
