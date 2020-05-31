package core;

import java.util.ArrayList;

public interface RidePlanning {
	public ArrayList<Station> planRide(double x1, double y1, double x2, double y2, String bicycleType, MyVelibNetwork network);
}
