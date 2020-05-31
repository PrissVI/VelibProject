package core;

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
		System.out.println(mySt.getNbOfOutOfOrderParkingSlots());
		
		ps1.setOutOfOrder(true);
		System.out.println(mySt.getNbOfOutOfOrderParkingSlots());
		
		
		//test2
		
		HashMap<Integer,ParkingSlot> myParkingSlots2 = new HashMap<Integer,ParkingSlot>();
		myParkingSlots2.put(ps1.getID(),ps1);
		myParkingSlots2.put(ps2.getID(),ps2);
		
		mySt.setParkingSlots(myParkingSlots);
		System.out.println(mySt.getNbOfOutOfOrderParkingSlots());
		
		ps2.setOutOfOrder(true);
		System.out.println(mySt.getNbOfOutOfOrderParkingSlots());
		
		mySt.setTerminalOutOfOrder(true);
		System.out.println(mySt.isOnline());
	}

}
