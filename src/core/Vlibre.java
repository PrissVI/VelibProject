package core;

public class Vlibre extends Card {

	public Vlibre(int timeCredit) {
		super(timeCredit);
	}

	public static void main(String[] args) {
		Vlibre carte = new Vlibre(0);
		System.out.println(carte);
	}

}
