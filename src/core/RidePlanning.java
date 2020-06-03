package core;

import java.util.ArrayList;

/**
 * RidePlanning is an interface used for the strategy pattern that is used to find the "best" start and end stations, w.r.t the start and end coordinates, as well as the bicycle type that is wanted.
 *
 * @author Ali Raïki
 */
public interface RidePlanning {
	/**
	 * This method is used to find the stations that correspond most to what the user wants.
	 * @param x1
	 * 			The easting of the starting point.
	 * @param y1
	 * 			The northing of the starting point.
	 * @param x2
	 * 			The easting of the destination.
	 * @param y2
	 * 			The northing of the starting point.
	 * @param bicycleType
	 * 			The type of bicycle that the user wants.
	 * @param network
	 * 			The network in which we look for stations.
	 * @return an ArrayList of stations (the start and the end station).
	 */
	public ArrayList<Station> planRide(double x1, double y1, double x2, double y2, String bicycleType, MyVelibNetwork network);
}
