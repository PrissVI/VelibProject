package core;

import java.util.Date;

public class ParkingSlotTest {

	public static void main(String[] args) {
		
		//tester setBicyleStored et setOutOfOrder
		
		/*setBicyclStored tests*/
		//1
		ParkingSlot ps1 = new ParkingSlot();
		
		Date date1 = ActivityLog.getDate(2020, 10, 5, 0, 0, 0);
		
		Bicycle bike1 = new MechanicalBike();
		
		ps1.setBicycleStored(bike1, date1);
		
		System.out.println(ps1.getBicycleStored());	//doit donner l'objet bicycle créé : doit être bike1
		System.out.println(ps1.getActivityLogs()); //doit comporter un élément : le log lié à l'ajout de la bike
		//OK système d'ajout de log marche
		
		
		//2
		Date date2 = ActivityLog.getDate(2020, 10, 5, 1, 0, 0);
		
		//ps1.setOutOfOrder(true, date2); //doit balancer une exception
		//OK exception envoyée
		
		
		//3
		Bicycle bike2 = new MechanicalBike();
		Date date3 = ActivityLog.getDate(2020, 10, 5, 2, 0, 0);
		
		//ps1.setBicycleStored(bike2, date3); //doit balancer une exception
		//OK exception envoyée

		System.out.println(ps1.toString());
		
		//4
		//je le vide, je le set out of order, je le re set in order PUIS je le re-remplis puis j'affiche les logs
		Date date4 = ActivityLog.getDate(2020, 10, 5, 2, 30, 0);
		ps1.setBicycleStored(null, date4);
		
		Date date5 = ActivityLog.getDate(2020, 10, 5, 2, 35, 0);
		ps1.setOutOfOrder(true, date5);
		
		Date date6 = ActivityLog.getDate(2020, 10, 5, 3, 0, 0);
		ps1.setOutOfOrder(false, date6);
		
		Date date7 = ActivityLog.getDate(2020, 10, 5, 3, 5, 0);
		ps1.setBicycleStored(bike2, date7);
		
		System.out.println(ps1.getBicycleStored()); 
		System.out.println(ps1.getActivityLogs()); //5 logs : un set en occupied, un set en free à 2h30, un set en occupied à 2h35, un set en free à 3h, un set en occupied à 3h05
		
	}

}
