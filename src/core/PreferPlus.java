package core;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * PreferPlus is a class that implements RidePlanning. With this policy the return station should be a “plus” station (given a “plus” station no further away than 10% of the distance of the closest station to the destination location exists).
 * If no such a “plus” station exists, then this policy behaves normally (as a minimal walking distance).
 * 
 * @author Ali Raïki
 */

public class PreferPlus implements RidePlanning {

	/**
	 * This method is used to find the stations that correspond most to what the user wants, which is described in the comment above.
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
	@Override
	public ArrayList<Station> planRide(double x1, double y1, double x2, double y2, String bicycleType, MyVelibNetwork network) {
		double finalDistanceFromStart = Double.POSITIVE_INFINITY;
		double finalDistanceFromStdEnd = Double.POSITIVE_INFINITY;
		double finalDistanceFromPlusEnd = Double.POSITIVE_INFINITY;
		boolean bikeIsAvailable;
		boolean parkingSlotIsAvailable;
		double distanceFromStart;
		double distanceFromStdEnd;
		double distanceFromPlusEnd;
		Station startStation = null;
		Station endStdStation = null;
		Station endPlusStation = null;
		Station endStation = null;
		ArrayList<Station> stationList = new ArrayList<Station>();
		
		HashMap<Integer, Station> stations = network.getStations();
		
		//Loop for the start station
		for(Station station : stations.values()) {
			//Compute distance and compare to smallest distance yet
			distanceFromStart = Math.sqrt(Math.pow(station.getX()-x1, 2)+Math.pow(station.getY()-y1, 2));
			
			if(distanceFromStart < finalDistanceFromStart) {
				bikeIsAvailable = false;
				//Make sure that a bike of the desired type is available
				for(ParkingSlot parkingSlot : station.getParkingSlots().values()) {
					if (bicycleType.equalsIgnoreCase("electrical") && parkingSlot.getBicycleStored() instanceof ElectricalBike) {
						bikeIsAvailable = true;
						finalDistanceFromStart = distanceFromStart;
						startStation = station;
						break;
					}
					if (bicycleType.equalsIgnoreCase("mechanical") && parkingSlot.getBicycleStored() instanceof MechanicalBike) {
						bikeIsAvailable = true;
						finalDistanceFromStart = distanceFromStart;
						startStation = station;
						break;
					}
				}
				//If a bike of the desired type is not available, skip this station
				if(!bikeIsAvailable) {
					continue;
				}

			}
		}
		
		//Loop for the end station
		for(Station station : stations.values()) {
			if(station instanceof StdStation) {
				//Compute distance and compare to smallest distance yet (for a standard station)
				distanceFromStdEnd = Math.sqrt(Math.pow(station.getX()-x2, 2)+Math.pow(station.getY()-y2, 2));
				
				if(distanceFromStdEnd < finalDistanceFromStdEnd) {
					parkingSlotIsAvailable = false;
					//Make sure that a parking Slot is available
					for(ParkingSlot parkingSlot : station.getParkingSlots().values()) {
						if (parkingSlot.getBicycleStored() == null) {
							parkingSlotIsAvailable = true;
							finalDistanceFromStdEnd = distanceFromStdEnd;
							endStdStation = station;
							break;
						}
					}
					//If no parking slot is available, skip this station
					if(!parkingSlotIsAvailable) {
						continue;
					}
				}
			}
			else {
				//Compute distance and compare to smallest distance yet (for a plus station)
				distanceFromPlusEnd = Math.sqrt(Math.pow(station.getX()-x2, 2)+Math.pow(station.getY()-y2, 2));
				
				if(distanceFromPlusEnd < finalDistanceFromPlusEnd) {
					parkingSlotIsAvailable = false;
					//Make sure that a parking Slot is available
					for(ParkingSlot parkingSlot : station.getParkingSlots().values()) {
						if (parkingSlot.getBicycleStored() == null  && !parkingSlot.isOutOfOrder()) {
							parkingSlotIsAvailable = true;
							finalDistanceFromPlusEnd = distanceFromPlusEnd;
							endPlusStation = station;
							break;
						}
					}
					//If no parking slot is available, skip this station
					if(!parkingSlotIsAvailable) {
						continue;
					}
				}				
			}
		}
		
		if(finalDistanceFromPlusEnd>1.1*finalDistanceFromStdEnd) {
			endStation = endStdStation;
		} 
		else {
			endStation = endPlusStation;
		}
		
		System.out.println("The nearest station to your starting point is:" + startStation);
		System.out.println("The nearest station to your destination is :" + endStation);
		
		stationList.add(startStation);
		stationList.add(endStation);
		return stationList;
	}
}
