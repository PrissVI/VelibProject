package clui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import core.ActivityLog;
import core.MyVelibNetwork;
import core.SortLeastOccupied;
import core.SortMostUsed;
import core.SortingStrategy;
import core.Station;
import core.User;

public class ProcessCommandLine {

	public static void executeCommand(ArrayList<String> lines) {

		HashMap<Integer,MyVelibNetwork> networks = new HashMap<Integer, MyVelibNetwork>();

		String entry; // stores the next line input

		for(String line : lines) {
			entry = line;
			System.out.println(entry);
			List<String> splitEntryList = new ArrayList<String>(Arrays.asList(entry.split(" ")));
			String command = splitEntryList.get(0);

			if (command.equalsIgnoreCase("help")) {
				System.out.println("• setup <velibnetworkName>: to create a myVelib network with given name and consisting of 10 stations each of which has 10 parking slots and such that stations are arranged on a square grid whose of side 4km and initially populated with a 75% bikes randomly distributed over the 10 stations"
						+ "\n" + "• setup <name> <nstations> <nslots> <s> <nbikes>: to create a myVelib network with given name and consisting of nstations stations each of which has nslots parking slots and such that stations are arranged in as uniform as possible manner over a square area of side s account and how stations are distributed over it).Furthermore the network should be initially populated with a nbikes bikes randomly distributed over the nstations stations"
						+ "\n" + "• addUser <userName,cardType, velibnetworkName> : to add a user with name userName and card cardType (i.e. 'none' if the user has no card) to a myVelib network velibnetworkNam"
						+ "\n" + "• offline <velibnetworkName, stationID> : to put offline the station stationID of the myVelib network velibnetworkName"
						+ "\n" + "• online <velibnetworkName, stationID> : to put online the station stationID of the myVelib network velibnetworkName"
						+ "\n" + "• move <userID, stationID> : to move the user userID to the station stationID"
						+ "\n" + "• rentBike <userID, stationID, date> : to let the user userID renting a bike from station stationID at a certain date (if no bikes are available will behave accordingly)"
						+ "\n" + "• returnBike <userID, stationID, date> : to let the user userID returning a bike to station stationID at a given instant of date date (if no parking bay is available will behave accordingly). This command should display the cost of the rent."
						+ "\n" + "• displayStation<velibnetworkName, stationID, (optional) infDate, (optional) supDate> : to display the statistics of station stationID of a myVelib network velibnetwork, between the dates infDate and supDate."
						+ "\n" + "• displayUser<velibnetworkName, userID> : to display the statistics of user userID of a myVelib network velibnetwork."
						+ "\n" + "• sortStation<velibnetworkName, sortpolicy> : to display the stations in increasing order w.r.t. to the sorting policy of user sortpolicy."
						+ "\n" + "• display <velibnetworkName>: to display the entire status (stations, parking bays, users) of an a myVelib network velibnetworkName."
						+ "\n" + "• runtest <testFile>: to reproduce a given test scenario."
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


			else if(command.equalsIgnoreCase("rentBike")) {
				System.out.println("---------------Result of command------------------");
				splitEntryList.remove(0);
				if(splitEntryList.size()==3) {
					try {
						int userID = Integer.parseInt(splitEntryList.get(0));
						int stationID = Integer.parseInt(splitEntryList.get(1));
						SortLeastOccupied convertion = new SortLeastOccupied();
						Date date = convertion.convertToDate(splitEntryList.get(2));
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
						SortLeastOccupied convertion = new SortLeastOccupied();
						Date date = convertion.convertToDate(splitEntryList.get(2));
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
						Station stationInput = null;
						Date infDate = ActivityLog.getDate(2019, 12, 12, 22, 22, 22);
						Date supDate = ActivityLog.getDate(2019, 12, 12, 23, 22, 22);

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

				} else if(splitEntryList.size()==4) {
					try {
						String networkName = splitEntryList.get(0);	
						int stationID = Integer.parseInt(splitEntryList.get(1));
						SortLeastOccupied convertion = new SortLeastOccupied();
						Date infDate = convertion.convertToDate(splitEntryList.get(2));
						Date supDate = convertion.convertToDate(splitEntryList.get(3));
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
						SortingStrategy policy = null;

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
								policy = new SortMostUsed();
								System.out.println(policy.sort(velibNetwork.getStations()));
							}
							else if (sortPolicy.equalsIgnoreCase("LEAST-OCCUPIED")){
								policy = new SortLeastOccupied();
								System.out.println(policy.sort(velibNetwork.getStations()));
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

			else {
				System.err.println("This command does not exist. Type 'help' for a list of commands.");
			}
		}
	}
}
