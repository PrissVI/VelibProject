package core;

import java.util.Date;
import java.util.HashMap;

public class StationTest {
	
	public static void main(String[] args) {
		
		//méthodes à tester : 
	
		/*identifyUser (voir si ça print bien ce qu'on veut)*/
		Station st1 = new StdStation(2,1);
		User user1 = new User("Jean",0,1);
		
		st1.identifyUser(user1);
		
		user1.setRegistrationCard(new Vlibre());
		
		st1.identifyUser(user1);
		
		
		
		/*chargeUser (tester pr std station et plus station) (faire pr un user qui n'a pas de carte, pr un user qui en a une (comprendre la tarifi),...)*/
		//1
		Station st2 = new StdStation(3,1);
		User user2 = new User("Jean",1,1,20);
		user2.setRentedBicycle(new MechanicalBike());
		int duration1 = 30;
		
		//chargeUser UTILISABLE QUE DANS LE CADRE DE RETURN BIKE (sinon, pourra thrower un null pointer exception)
		st2.chargeUser(user2, duration1);
		System.out.println(user2.getCreditCardBalance()); //doit valoir 19,5
		System.out.println(user2.getMyVelibTotalCharges()+"\n"); //doit valoir 0,5
		
		int duration2 = 90;
		st2.chargeUser(user2, duration2);
		System.out.println(user2.getCreditCardBalance()); //doit valoir 18
		System.out.println(user2.getMyVelibTotalCharges()+"\n"); //doit valoir 2		
		
		//other cases (with card) have already been tested in the ConcreteVisitorTest file
		
		//2
		//faire un test avec un terminal qui ne marche pas
//		st2.setTerminalOutOfOrder(true);
//		st2.chargeUser(user2, duration2);	
		//erreur bien throwée
		
		
		//3
		User user3 = new User("Paul",3,2,20);
		user3.setRegistrationCard(new Vlibre());
		user3.setRentedBicycle(new MechanicalBike());
		int duration3 = 90;
		st2.chargeUser(user3, duration3);
		System.out.println(user3.getCreditCardBalance());
		System.out.println(user3.getMyVelibTotalCharges());	
		System.out.println(user3.getRegistrationCard().getTimeCredit()+"\n");
		
		
		//4 (avec une plus station et un user avec carte)
		Station st3 = new PlusStation(2,1);
		User user4 = new User("Greg",0,2,20);
		user4.setRegistrationCard(new Vlibre());
		user4.setRentedBicycle(new ElectricalBike());
		st3.chargeUser(user4, 120);
		System.out.println(user4.getCreditCardBalance()); //on a bien 17
		System.out.println(user4.getMyVelibTotalCharges());	//on a bien 3
		System.out.println(user4.getRegistrationCard().getTimeCredit()+"\n"); //on a bien 5 min de time credit
		
		//4 (avec une plus station et un user sans carte)
		Station st4 = new PlusStation(2,3);
		User user5 = new User("Tom",1,2,20);
		user5.setRentedBicycle(new ElectricalBike());
		st4.chargeUser(user5, 120);
		System.out.println(user5.getCreditCardBalance()); //on a bien 16
		System.out.println(user5.getMyVelibTotalCharges()+"\n");	//on a bien 4
		//pas de time credit (normal : pas de carte)
		
		//tous les cas limites seront testés avec les méthodes de User
		//(les cas offline n'ont PAS à etre traités ici : ils sont déjà checkés ds rentBike et returnBike où il faudra tester avec des stations pleines, vides, offline, avec des ps cassés,...)
		
		
		
		
		//getOccupationRate (faire pr une station qu'on popule initialement)
		//1
//		Station st5 = new StdStation(4,3);
//		HashMap<Integer,ParkingSlot> parkingSlots = new HashMap<Integer,ParkingSlot>();
//		
//		Date d1 = ActivityLog.getDate(2020, 4, 22, 2, 0, 0);
//		Date d2 = ActivityLog.getDate(2020, 4, 22, 2, 10, 0);
//		Date d3 = ActivityLog.getDate(2020, 4, 22, 2, 20, 0);
//		
//		
//		ParkingSlot ps1 = new ParkingSlot(new MechanicalBike());
//		ParkingSlot ps2 = new ParkingSlot();
//		parkingSlots.put(ps1.getID(),ps1);
//		parkingSlots.put(ps2.getID(),ps2);
//		st5.setParkingSlots(parkingSlots);
//		
//		ps1.setBicycleStored(null, d2);
//		ps2.setBicycleStored(new ElectricalBike(), d2);
//		
//		System.out.println(st5.getOccupationRate(d1, d3));	//0.5 comme attendu
		
		
		//2
//		Station st6 = new StdStation(7,4);
//		HashMap<Integer,ParkingSlot> parkingSlots2 = new HashMap<Integer,ParkingSlot>();
//		
//		Date d4 = ActivityLog.getDate(2020, 4, 23, 2, 0, 0);
//		Date d5 = ActivityLog.getDate(2020, 4, 23, 2, 10, 0);
//		
//		
//		ParkingSlot ps3 = new ParkingSlot();
//		ParkingSlot ps4 = new ParkingSlot();
//		ParkingSlot ps5 = new ParkingSlot();
//		parkingSlots2.put(ps3.getID(),ps3);
//		parkingSlots2.put(ps4.getID(),ps4);
//		parkingSlots2.put(ps5.getID(),ps5);
//		st6.setParkingSlots(parkingSlots2);
//		
//		ps3.setOutOfOrder(true, d4);
//		//ps2.setBicycleStored(new ElectricalBike(), d5);
//		
//		System.out.println(st6.getOccupationRate(d4, d5)); //0.3333333333333333 comme attendu
		
		
		//3
		Station st7 = new StdStation(5,5);
		HashMap<Integer,ParkingSlot> parkingSlots3 = new HashMap<Integer,ParkingSlot>();
		
		Date d1 = ActivityLog.getDate(2020, 4, 24, 2, 5, 0);
		Date d2 = ActivityLog.getDate(2020, 4, 24, 2, 10, 0);
		Date d3 = ActivityLog.getDate(2020, 4, 24, 2, 15, 0);
		Date d4 = ActivityLog.getDate(2020, 4, 24, 2, 25, 0);
		Date d5 = ActivityLog.getDate(2020, 4, 24, 2, 30, 0);
		
		
		ParkingSlot ps1 = new ParkingSlot();
		ParkingSlot ps2 = new ParkingSlot();
		ParkingSlot ps3 = new ParkingSlot();
		ParkingSlot ps4 = new ParkingSlot();
		parkingSlots3.put(ps1.getID(),ps1);
		parkingSlots3.put(ps2.getID(),ps2);
		parkingSlots3.put(ps3.getID(),ps3);
		parkingSlots3.put(ps4.getID(),ps4);
		st7.setParkingSlots(parkingSlots3);
		
		ps1.setBicycleStored(new MechanicalBike(), d1);
		ps2.setOutOfOrder(true, d2);
		ps1.setBicycleStored(null, d3);
		ps3.setBicycleStored(new MechanicalBike(), d3);
		ps2.setOutOfOrder(false, d4);
		ps3.setBicycleStored(null, d4);
		ps4.setBicycleStored(new ElectricalBike(), d4);
		ps1.setBicycleStored(new MechanicalBike(), d4);
		
		System.out.println(st7.getOccupationRate(d2, d5)); //0.5 comme attendu
	}
}
