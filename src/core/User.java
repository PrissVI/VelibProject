package core;

import java.util.ArrayList;

public class User {

	private int id;
	private static int counter = 0; //for the ID
	private String name;
	private ArrayList<Double> coordinates = new ArrayList<Double>();
	private int creditCard;
	private Card registrationCard;

	//Constructor with name and credit card
	public User(String name, int creditCard) {
		counter++;
		this.id = counter;
		this.name = name;
		//this.coordinates;
		this.creditCard = creditCard;
		this.registrationCard = null;
	}
	
	//Constructor with name, credit card and registration card
	public User(String name, int creditCard, Card registrationCard) {
		counter++;
		this.id = counter;
		this.name = name;
		//this.coordinates;
		this.creditCard = creditCard;
		this.registrationCard = registrationCard;
	}
	
	//Getters and Setters
	public ArrayList<Double> getCoordinates() {
		return coordinates;
	}
	
	public void setCoordinates(ArrayList<Double> coordinates) {
		this.coordinates = coordinates;
	}
	
	public int getCreditCard() {
		return creditCard;
	}
	
	public void setCreditCard(int creditCard) {
		this.creditCard = creditCard;
	}
	
	public Card getRegistrationCard() {
		return registrationCard;
	}
	
	public void setRegistrationCard(Card registrationCard) {
		this.registrationCard = registrationCard;
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}

}
