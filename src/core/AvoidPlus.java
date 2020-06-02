package core;

import java.util.ArrayList;
import java.util.HashMap;

public class AvoidPlus implements RidePlanning {

	@Override
	public ArrayList<Station> planRide(double x1, double y1, double x2, double y2, String bicycleType, MyVelibNetwork network) {
		double finalDistanceFromStart = Double.POSITIVE_INFINITY;
		double finalDistanceFromEnd = Double.POSITIVE_INFINITY;
		boolean bikeIsAvailable;
		boolean parkingSlotIsAvailable;
		double distanceFromStart;
		double distanceFromEnd;
		Station startStation = null;
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
					if (bicycleType.toLowerCase()=="electrical" && parkingSlot.getBicycleStored() instanceof ElectricalBike) {
						bikeIsAvailable = true;
						finalDistanceFromStart = distanceFromStart;
						startStation = station;
						break;
					}
					if (bicycleType.toLowerCase()=="mechanical" && parkingSlot.getBicycleStored() instanceof MechanicalBike) {
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
		
		//Loop for the finish station
		for(Station station : stations.values()) {
			//Only consider standard stations
			if(station instanceof StdStation) {
				//Compute distance and compare to smallest distance yet (for a standard station)
				distanceFromEnd = Math.sqrt(Math.pow(station.getX()-x2, 2)+Math.pow(station.getY()-y2, 2));
				
				if(distanceFromEnd < finalDistanceFromEnd) {
					parkingSlotIsAvailable = false;
					//Make sure that a parking Slot is available
					for(ParkingSlot parkingSlot : station.getParkingSlots().values()) {
						if (parkingSlot.getBicycleStored() == null && !parkingSlot.isOutOfOrder()) {
							finalDistanceFromEnd = distanceFromEnd;
							endStation = station;
							parkingSlotIsAvailable = true;
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
		
		System.out.println("The nearest station to your starting point is:" + startStation);
		System.out.println("The nearest station to your destination is :" + endStation);
		
		stationList.add(startStation);
		stationList.add(endStation);
		return stationList;
	}		
}
