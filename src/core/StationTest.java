package core;

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
		System.out.println(user2.getMyVelibTotalCharges()); //doit valoir 0,5
		
		int duration2 = 90;
		st2.chargeUser(user2, duration2);
		System.out.println(user2.getCreditCardBalance()); //doit valoir 18
		System.out.println(user2.getMyVelibTotalCharges()); //doit valoir 2		
		
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
		System.out.println(user3.getRegistrationCard().getTimeCredit());
		
		
		//4 (avec une plus station)
		User user4 = new User("Greg",0,2,20);
		user4.setRentedBicycle(new ElectricalBike());
		
		
		//tous les cas limites seront testés avec les méthodes de User
		//(les cas offline n'ont PAS à etre traités ici : ils sont déjà checkés ds rentBike et returnBike où il faudra tester avec des stations pleines, vides, offline, avec des ps cassés,...)
		
		
		
		
		//getOccupationRate (faire pr une station qu'on popule initialement)
		
		
	}
}
