package clui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import core.AvoidPlus;
import core.BasicPlanning;
import core.Bicycle;
import core.DeserializeMyVelibNetwork;
import core.ElectricalBike;
import core.MechanicalBike;
import core.MyVelibNetwork;
import core.PreferPlus;
import core.RidePlanning;
import core.SerializeMyVelibNetwork;
import core.SortLeastOccupied;
import core.Station;
import core.User;

/**
 * CommandLine is a class that is used to read and run the command that a user types thanks to the Scanner, and stores the useful information in the HashMap networks.
 * 
 * @author Ali Raïki
 *
 */

public class CommandLine {

	static HashMap<Integer,MyVelibNetwork> networks = new HashMap<Integer, MyVelibNetwork>();
	boolean exit = false;

	/**
	 * This method reads the command and searches for the right methods to run.
	 * @param commandLine
	 * 				a String that represents the command.
	 */
	public static void executeCommand(String commandLine) {

		String entry = commandLine;
		List<String> splitEntryList = new ArrayList<String>(Arrays.asList(entry.split(" ")));
		String command = splitEntryList.get(0);

		if (command.equalsIgnoreCase("help")) {
			System.out.println("• setup <velibnetworkName>: to create a myVelib network with given name and consisting of 10 stations each of which has 10 parking slots and such that stations are arranged on a square grid whose of side 4km and initially populated with a 75% bikes randomly distributed over the 10 stations"
					+ "\n" + "• setup <name> <nstations> <nslots> <s> <nbikes>: to create a myVelib network with given name and consisting of nstations stations each of which has nslots parking slots and such that stations are arranged in as uniform as possible manner over a square area of side s account and how stations are distributed over it).Furthermore the network should be initially populated with a nbikes bikes randomly distributed over the nstations stations"
					+ "\n" + "• addUser <userName,cardType, velibnetworkName> : to add a user with name userName and card cardType (i.e. 'none' if the user has no card) to a myVelib network velibnetworkNam"
					+ "\n" + "• offline <velibnetworkName, stationID> : to put offline the station stationID of the myVelib network velibnetworkName"
					+ "\n" + "• online <velibnetworkName, stationID> : to put online the station stationID of the myVelib network velibnetworkName"
					+ "\n" + "• planRide <velibnetworkName, startX, startY, endX, endY, bicycleType, ridePolicy> : to find the best start and end stations in the myVelib network velibnetworkName, with a start in (startX, startY) and an end in (endX, endY), with a specific bicycle type ('ELECTRICAL' or 'MECHANICAL'), w.r.t the policies : 'BASIC', 'AVOID-PLUS' or 'PREFER-PLUS'."
					+ "\n" + "• move <userID, stationID> : to move the user userID to the station stationID"
					+ "\n" + "• rentBike <userID, stationID, date> : to let the user userID renting a bike from station stationID at a certain date (if no bikes are available will behave accordingly)"
					+ "\n" + "• returnBike <userID, stationID, date> : to let the user userID returning a bike to station stationID at a given instant of date date (if no parking bay is available will behave accordingly). This command should display the cost of the rent."
					+ "\n" + "• displayStation <velibnetworkName, stationID, (optional) infDate, (optional) supDate> : to display the statistics of station stationID of a myVelib network velibnetwork, between the dates infDate and supDate."
					+ "\n" + "• displayUser <velibnetworkName, userID> : to display the statistics of user userID of a myVelib network velibnetwork."
					+ "\n" + "• sortStation <velibnetworkName, sortpolicy> : to display the stations in increasing order w.r.t. to the sorting policy of user sortpolicy (MOST-USED, LEAST-OCCUPIED or LEAST-OCCUPIED-WITH-DATES)."
					+ "\n" + "• display <velibnetworkName>: to display the entire status (stations, parking bays, users) of the myVelib network velibnetworkName."
					+ "\n" + "• getUsers <velibnetworkName>: to display the users of the myVelib network velibnetworkName."
					+ "\n" + "• getStations <velibnetworkName>: to display the stations of the myVelib network velibnetworkName."
					+ "\n" + "• getBicycles <velibnetworkName>: to display the bicycles of the myVelib network velibnetworkName."
					+ "\n" + "• runtest <testFile>: to reproduce a given test scenario."
					+ "\n" + "• save <velibnetworkName, fileName>: to serialize the myVelib network velibnetworkName into the file fileName."
					+ "\n" + "• open <fileName>: to deserialize the file fileName."
					+ "\n" + "• exit: to close the command line.");
		} 

		else if(command.equalsIgnoreCase("setup")) {
			System.out.println("---------------Result of command------------------");
			splitEntryList.remove(0);
			if(splitEntryList.size()==1) {
				String name = splitEntryList.get(0);
				System.out.println(name);
				MyVelibNetwork network = new MyVelibNetwork(name,4);
				network.addStations(10, 10);
				network.addBicyclePercentage(0.75);
				networks.put(network.getID(), network);
				System.out.println(network);
			} else if(splitEntryList.size()==5) {
				try {
					String name = splitEntryList.get(0);
					int nstations = Integer.parseInt(splitEntryList.get(1));
					int nslots = Integer.parseInt(splitEntryList.get(2));
					double s = Double.parseDouble(splitEntryList.get(3));
					int nbikes = Integer.parseInt(splitEntryList.get(4));
					MyVelibNetwork network = new MyVelibNetwork(name,s);
					network.addStations(nstations, nslots);
					network.addBicycleNumber(nbikes);
					networks.put(network.getID(), network);
					System.out.println(network);
				} catch (Exception e) {
					System.err.println("Incorrect parameters");
					System.err.println(e.getMessage());
				}
			} else {
				System.err.println("Too many or not enough parameters");
			}
			System.out.println("---------------End of command------------------");
		} 

		else if(command.equalsIgnoreCase("addUser")) {
			System.out.println("---------------Result of command------------------");
			splitEntryList.remove(0);
			if(splitEntryList.size()==3) {
				try {
					String name = splitEntryList.get(0);
					String cardType = splitEntryList.get(1);
					String networkName = splitEntryList.get(2);
					MyVelibNetwork velibNetwork = null;
					for (MyVelibNetwork network : networks.values()) {
						if (networkName.equalsIgnoreCase(network.getName())) {
							velibNetwork = network;
							break;
						}
					}
					if (velibNetwork == null) {
						System.err.println("The myVelib network entered does not exist");
					} else {
						velibNetwork.addUser(name, cardType);
						System.out.println("The user has been added to " + networkName);
						for(User user : velibNetwork.getUsers().values()) {
							if(name.equalsIgnoreCase(user.getName())){
								System.out.println(user.getRegistrationCard());
								break;
							}
						}
					} 
				}  catch (Exception e) {
					System.err.println("Incorrect parameters");
					System.err.println(e.getMessage());
				}
			} else {
				System.err.println("Too many or not enough parameters");
			}
			System.out.println("---------------End of command------------------");
		}

		else if(command.equalsIgnoreCase("offline")) {
			System.out.println("---------------Result of command------------------");
			splitEntryList.remove(0);
			if(splitEntryList.size()==2) {
				try {
					String networkName = splitEntryList.get(0);
					int stationID = Integer.parseInt(splitEntryList.get(1));
					MyVelibNetwork velibNetwork = null;
					Station stationInput = null;
					for (MyVelibNetwork network : networks.values()) {
						if (networkName.equalsIgnoreCase(network.getName())) {
							velibNetwork = network;
							break;
						}
					}
					if (velibNetwork == null) {
						System.err.println("The myVelib network entered does not exist");
					} else {
						if (velibNetwork.getStations().keySet().contains(stationID)) {
							stationInput = velibNetwork.getStations().get(stationID);
							stationInput.setOnline(false);
							System.out.println("The station n°" + stationID + " of the network '" + networkName + "' has been set to offline");
						}
						else {
							System.err.println("This station does not exist in this velib network");
						}
					}
				} catch (Exception e) {
					System.err.println("Incorrect parameters");
					System.err.println(e.getMessage());
				}

			} else {
				System.err.println("Too many or not enough parameters");
			}
			System.out.println("---------------End of command------------------");
		}

		else if(command.equalsIgnoreCase("online")) {
			System.out.println("---------------Result of command------------------");
			splitEntryList.remove(0);
			if(splitEntryList.size()==2) {
				try {
					String networkName = splitEntryList.get(0);
					int stationID = Integer.parseInt(splitEntryList.get(1));
					MyVelibNetwork velibNetwork = null;
					Station stationInput = null;
					for (MyVelibNetwork network : networks.values()) {
						if (networkName.equalsIgnoreCase(network.getName())) {
							velibNetwork = network;
							break;
						}
					}
					if (velibNetwork == null) {
						System.err.println("The myVelib network entered does not exist");
					} else {
						if (velibNetwork.getStations().keySet().contains(stationID)) {
							stationInput = velibNetwork.getStations().get(stationID);
							stationInput.setOnline(true);
							System.out.println("The station n°" + stationID + " of the network '" + networkName + "' has been set to online");
						}
						else {
							System.err.println("This station does not exist in this velib network");
						}
					}
				} catch (Exception e) {
					System.err.println("Incorrect parameters");
					System.err.println(e.getMessage());
				}

			} else {
				System.err.println("Too many or not enough parameters");
			}
			System.out.println("---------------End of command------------------");
		}

		else if(command.equalsIgnoreCase("planRide")) {
			System.out.println("---------------Result of command------------------");
			splitEntryList.remove(0);
			if(splitEntryList.size()==7) {
				try {
					String networkName = splitEntryList.get(0);
					double x1 = Double.parseDouble(splitEntryList.get(1));
					double y1 = Double.parseDouble(splitEntryList.get(2));
					double x2 = Double.parseDouble(splitEntryList.get(3));
					double y2 = Double.parseDouble(splitEntryList.get(4));
					String bicycleType = splitEntryList.get(5);
					String policy = splitEntryList.get(6);
					MyVelibNetwork velibNetwork = null;
					Bicycle bike = null;
					RidePlanning planning = null;
					for (MyVelibNetwork network : networks.values()) {
						if (networkName.equalsIgnoreCase(network.getName())) {
							velibNetwork = network;
							break;
						}
					}

					if(bicycleType.equalsIgnoreCase("MECHANICAL")) {
						bike = new MechanicalBike();
					} else if(bicycleType.equalsIgnoreCase("ELECTRICAL")) {
						bike = new ElectricalBike();
					}
					if(policy.equalsIgnoreCase("BASIC")) {
						planning = new BasicPlanning();
					} else if(policy.equalsIgnoreCase("AVOID-PLUS")) {
						System.out.println("here");
						planning = new AvoidPlus();
					} else if(policy.equalsIgnoreCase("PREFER-PLUS")) {
						planning = new PreferPlus();
					}

					if (velibNetwork == null) {
						System.err.println("The myVelib network entered does not exist");
					} else {
						if(bike==null) {
							System.err.println("This bike type does not exist.");
						} else {
							if(planning==null) {
								System.err.println("This ride planning policy does not exist.");
							}
							else {
								planning.planRide(x1, y1, x2, y2, bicycleType, velibNetwork);
							}
						}
					}
				} catch (Exception e) {
					System.err.println("Incorrect parameters");
					System.err.println(e.getMessage());
				}

			} else {
				System.err.println("Too many or not enough parameters");
			}
			System.out.println("---------------End of command------------------");
		}

		else if(command.equalsIgnoreCase("rentBike")) {
			System.out.println("---------------Result of command------------------");
			splitEntryList.remove(0);
			if(splitEntryList.size()==3) {
				try {
					int userID = Integer.parseInt(splitEntryList.get(0));
					int stationID = Integer.parseInt(splitEntryList.get(1));
					Date date = SortLeastOccupied.convertToDate(splitEntryList.get(2));
					MyVelibNetwork velibNetwork = null;
					User userInput = null;
					Station stationInput = null;
					for(MyVelibNetwork network : networks.values()) {
						if (network.getStations().keySet().contains(stationID)) {
							velibNetwork = network;
							stationInput = velibNetwork.getStations().get(stationID);
							break;
						}
					}
					if (velibNetwork == null) {
						System.err.println("This station does not exist.");
					} else {
						if(velibNetwork.getUsers().keySet().contains(userID)) {
							userInput = velibNetwork.getUsers().get(userID);
							userInput.rentBicycle(stationInput, date);
						} else {
							System.err.println("This user does not exist.");
						}
					}
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}

			} else {
				System.err.println("Too many or not enough parameters");
			}
			System.out.println("---------------End of command------------------");
		}

		else if(command.equalsIgnoreCase("returnBike")) {
			System.out.println("---------------Result of command------------------");
			splitEntryList.remove(0);
			if(splitEntryList.size()==3) {
				try {
					int userID = Integer.parseInt(splitEntryList.get(0));
					int stationID = Integer.parseInt(splitEntryList.get(1));
					Date date = SortLeastOccupied.convertToDate(splitEntryList.get(2));
					MyVelibNetwork velibNetwork = null;
					User userInput = null;
					Station stationInput = null;
					for(MyVelibNetwork network : networks.values()) {
						if (network.getStations().keySet().contains(stationID)) {
							velibNetwork = network;
							stationInput = velibNetwork.getStations().get(stationID);
							break;
						}
					}
					if (velibNetwork == null) {
						System.err.println("This station does not exist.");
					} else {
						if(velibNetwork.getUsers().keySet().contains(userID)) {
							userInput = velibNetwork.getUsers().get(userID);
							userInput.returnBicycle(stationInput, date);
						} else {
							System.err.println("This user does not exist.");
						}
					}
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}

			} else {
				System.err.println("Too many or not enough parameters");
			}
			System.out.println("---------------End of command------------------");
		}

		else if(command.equalsIgnoreCase("move")) {
			System.out.println("---------------Result of command------------------");
			splitEntryList.remove(0);
			if(splitEntryList.size()==2) {
				try {
					int userID = Integer.parseInt(splitEntryList.get(0));
					int stationID = Integer.parseInt(splitEntryList.get(1));
					MyVelibNetwork velibNetwork = null;
					User userInput = null;
					Station stationInput = null;
					for(MyVelibNetwork network : networks.values()) {
						if (network.getStations().keySet().contains(stationID)) {
							velibNetwork = network;
							stationInput = velibNetwork.getStations().get(stationID);
							break;
						}
					}
					if (velibNetwork == null) {
						System.err.println("This station does not exist.");
					} else {
						if(velibNetwork.getUsers().keySet().contains(userID)) {
							userInput = velibNetwork.getUsers().get(userID);
							userInput.setX(stationInput.getX());
							userInput.setY(stationInput.getY());
							System.out.println("User n°" + userID + " is now at the station n°" + stationID);
						} else {
							System.err.println("This user does not exist.");
						}
					}
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}

			} else {
				System.err.println("Too many or not enough parameters");
			}
			System.out.println("---------------End of command------------------");
		}

		else if(command.equalsIgnoreCase("displayStation")) {
			System.out.println("---------------Result of command------------------");
			splitEntryList.remove(0);
			if(splitEntryList.size()==2) {
				try {
					String networkName = splitEntryList.get(0);	
					int stationID = Integer.parseInt(splitEntryList.get(1));
					MyVelibNetwork velibNetwork = null;

					for (MyVelibNetwork network : networks.values()) {
						if (networkName.equalsIgnoreCase(network.getName())) {
							velibNetwork = network;
							break;
						}
					}
					if (velibNetwork == null) {
						System.err.println("The myVelib network entered does not exist");
					} else {
						if (velibNetwork.getStations().keySet().contains(stationID)) {
							String result = velibNetwork.getStationBalance(stationID);
							System.out.println(result);
						}
						else {
							System.err.println("This station does not exist in this velib network");
						}
					}
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}

			} else if(splitEntryList.size()==4) {
				try {
					String networkName = splitEntryList.get(0);	
					int stationID = Integer.parseInt(splitEntryList.get(1));
					Date infDate = SortLeastOccupied.convertToDate(splitEntryList.get(2));
					Date supDate = SortLeastOccupied.convertToDate(splitEntryList.get(3));
					MyVelibNetwork velibNetwork = null;
					Station stationInput = null;

					for (MyVelibNetwork network : networks.values()) {
						if (networkName.equalsIgnoreCase(network.getName())) {
							velibNetwork = network;
							break;
						}
					}
					if (velibNetwork == null) {
						System.err.println("The myVelib network entered does not exist");
					} else {
						if (velibNetwork.getStations().keySet().contains(stationID)) {
							stationInput = velibNetwork.getStations().get(stationID);
							System.out.println(stationInput.getStatistics(infDate, supDate));
						}
						else {
							System.err.println("This station does not exist in this velib network");
						}
					}
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}

			} else {
				System.err.println("Too many or not enough parameters");
			}
			System.out.println("---------------End of command------------------");
		}

		else if(command.equalsIgnoreCase("displayUser")) {
			System.out.println("---------------Result of command------------------");
			splitEntryList.remove(0);
			if(splitEntryList.size()==2) {
				try {
					String networkName = splitEntryList.get(0);	
					int userID = Integer.parseInt(splitEntryList.get(1));
					MyVelibNetwork velibNetwork = null;
					User userInput = null;

					for (MyVelibNetwork network : networks.values()) {
						if (networkName.equalsIgnoreCase(network.getName())) {
							velibNetwork = network;
							break;
						}
					}
					if (velibNetwork == null) {
						System.err.println("The myVelib network entered does not exist");
					} else {
						if (velibNetwork.getUsers().keySet().contains(userID)) {
							userInput = velibNetwork.getUsers().get(userID);
							System.out.println(userInput.getStatistics());
						}
						else {
							System.err.println("This user does not exist in this velib network");
						}
					}
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}
			} else {
				System.err.println("Too many or not enough parameters");
			}
			System.out.println("---------------End of command------------------");
		}

		else if(command.equalsIgnoreCase("sortStation")) {
			System.out.println("---------------Result of command------------------");
			splitEntryList.remove(0);
			if(splitEntryList.size()==2) {
				try {
					String networkName = splitEntryList.get(0);	
					String sortPolicy = splitEntryList.get(1);
					MyVelibNetwork velibNetwork = null;

					for (MyVelibNetwork network : networks.values()) {
						if (networkName.equalsIgnoreCase(network.getName())) {
							velibNetwork = network;
							break;
						}
					}
					if (velibNetwork == null) {
						System.err.println("The myVelib network entered does not exist");
					} else {
						if (sortPolicy.equalsIgnoreCase("MOST-USED")) {
							System.out.println(velibNetwork.sortStations("MOST USED"));
						}
						else if (sortPolicy.equalsIgnoreCase("LEAST-OCCUPIED")){
							System.out.println(velibNetwork.sortStations("LEAST OCCUPIED"));
						} else if (sortPolicy.equalsIgnoreCase("LEAST-OCCUPIED-WITH-DATES")){
							System.out.println(velibNetwork.sortStations("LEAST OCCUPIED WITH DATES"));
						} else {
							System.err.println("This sorting policy does not exist");
						}
					}
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}
			} else {
				System.err.println("Too many or not enough parameters");
			}
			System.out.println("---------------End of command------------------");
		}

		else if(command.equalsIgnoreCase("display")) {
			System.out.println("---------------Result of command------------------");
			splitEntryList.remove(0);
			if(splitEntryList.size()==2) {
				try {
					String networkName = splitEntryList.get(0);
					MyVelibNetwork velibNetwork = null;

					for (MyVelibNetwork network : networks.values()) {
						if (networkName.equalsIgnoreCase(network.getName())) {
							velibNetwork = network;
							break;
						}
					}
					if (velibNetwork == null) {
						System.err.println("The myVelib network entered does not exist");
					} else {
						System.out.println(velibNetwork);
					}
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}
			} else {
				System.err.println("Too many or not enough parameters");
			}
			System.out.println("---------------End of command------------------");
		}	    	

		else if(command.equalsIgnoreCase("getUsers")) {
			System.out.println("---------------Result of command------------------");
			splitEntryList.remove(0);
			if(splitEntryList.size()==1) {
				try {
					String networkName = splitEntryList.get(0);
					MyVelibNetwork velibNetwork = null;

					for (MyVelibNetwork network : networks.values()) {
						if (networkName.equalsIgnoreCase(network.getName())) {
							velibNetwork = network;
							break;
						}
					}
					if (velibNetwork == null) {
						System.err.println("The myVelib network entered does not exist");
					} else {
						System.out.println(velibNetwork.getUsers());
					}
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}
			} else {
				System.err.println("Too many or not enough parameters");
			}
			System.out.println("---------------End of command------------------");
		}

		else if(command.equalsIgnoreCase("getStations")) {
			System.out.println("---------------Result of command------------------");
			splitEntryList.remove(0);
			if(splitEntryList.size()==1) {
				try {
					String networkName = splitEntryList.get(0);
					MyVelibNetwork velibNetwork = null;

					for (MyVelibNetwork network : networks.values()) {
						if (networkName.equalsIgnoreCase(network.getName())) {
							velibNetwork = network;
							break;
						}
					}
					if (velibNetwork == null) {
						System.err.println("The myVelib network entered does not exist");
					} else {
						System.out.println(velibNetwork.getStations());
					}
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}
			} else {
				System.err.println("Too many or not enough parameters");
			}
			System.out.println("---------------End of command------------------");
		}

		else if(command.equalsIgnoreCase("getBicycles")) {
			System.out.println("---------------Result of command------------------");
			splitEntryList.remove(0);
			if(splitEntryList.size()==1) {
				try {
					String networkName = splitEntryList.get(0);
					MyVelibNetwork velibNetwork = null;

					for (MyVelibNetwork network : networks.values()) {
						if (networkName.equalsIgnoreCase(network.getName())) {
							velibNetwork = network;
							break;
						}
					}
					if (velibNetwork == null) {
						System.err.println("The myVelib network entered does not exist");
					} else {
						System.out.println(velibNetwork.getBicycles());
					}
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}
			} else {
				System.err.println("Too many or not enough parameters");
			}
			System.out.println("---------------End of command------------------");
		}

		else if(command.equalsIgnoreCase("runtest")) {
			splitEntryList.remove(0);
			if(splitEntryList.size()==1) {
				try {
					String filename = splitEntryList.get(0);
					getCommands.readTextFileLineByLine(filename);
				} catch (Exception e) {
					System.err.println("Incorrect parameters");
					System.err.println(e.getMessage());
				}

			} else {
				System.err.println("Too many or not enough parameters");
			}
		}

		else if(command.equalsIgnoreCase("save")) {
			splitEntryList.remove(0);
			if(splitEntryList.size()==2) {
				String networkName = splitEntryList.get(0);
				String fileName = splitEntryList.get(1);
				MyVelibNetwork velibNetwork = null;

				for (MyVelibNetwork network : networks.values()) {
					if (networkName.equalsIgnoreCase(network.getName())) {
						velibNetwork = network;
						break;
					}
				}
				if(velibNetwork != null) {
					SerializeMyVelibNetwork.serializeNetwork(velibNetwork, fileName);
				}
				else {
					System.err.println("The myVelib network entered does not exist");
				}

			} else {
				System.err.println("Too many or not enough parameters");
			}
		}

		else if(command.equalsIgnoreCase("open")) {
			splitEntryList.remove(0);
			if(splitEntryList.size()==1) {
				String fileName = splitEntryList.get(0);
				DeserializeMyVelibNetwork.deserializeNetwork(fileName);

			} else {
				System.err.println("Too many or not enough parameters");
			}
		}
		
		else {
			System.err.println("This command does not exist. Type 'help' for a list of commands.");
		}
	}

}
