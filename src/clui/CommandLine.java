package clui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CommandLine {
	public static void main(String[] args) {
		System.out.println("Welcome to the myVelib App! Type 'help' for a list of commands.");

		Scanner input = new Scanner(System.in); // used to read the keyboard
		
		String entry; // stores the next line input
	    boolean exit = false;
	    
	    do {
	    	entry = input.nextLine();
	    	List<String> splitEntryList = new ArrayList<String>(Arrays.asList(entry.split(" ")));
	    	System.out.println(splitEntryList);
	    	String command = splitEntryList.get(0);

	    	if (command.equalsIgnoreCase("help")) {
	    		System.out.println("• setup <velibnetworkName>: to create a myVelib network with given name and consisting of 10 stations each of which has 10 parking slots and such that stations are arranged on a square grid whose of side 4km and initially populated with a 75% bikes randomly distributed over the 10 stations"
	    				+ "\n" + "• setup <name> <nstations> <nslots> <s> <nbikes>: to create a myVelib network with given name and consisting of nstations stations each of which has nslots parking slots and such that stations are arranged in as uniform as possible manner over a square area of side s account and how stations are distributed over it).Furthermore the network should be initially populated with a nbikes bikes randomly distributed over the nstations stations"
	    				+ "\n" + "• addUser <userName,cardType, velibnetworkName> : to add a user with name userName and card cardType (i.e. 'none' if the user has no card) to a myVelib network velibnetworkNam"
	    				+ "\n" + "• offline <velibnetworkName, stationID> : to put offline the station stationID of the myVelib network velibnetworkName"
	    				+ "\n" + "• online <velibnetworkName, stationID> : to put online the station stationID of the myVelib network velibnetworkName"
	    				+ "\n" + "• rentBike <userID, stationID> : to let the user userID renting a bike from station stationID (if no bikes are available will behave accordingly)"
	    				+ "\n" + "• returnBike <userID, stationID, time> : to let the user userID returning a bike to station stationID at a given instant of time time (if no parking bay is available will behave accordingly). This command should display the cost of the rent"
	    				+ "\n" + "• displayStation<velibnetworkName, stationID> : to display the statistics of station stationID of a myVelib network velibnetwork."
	    				+ "\n" + "• displayUser<velibnetworkName, userID> : to display the statistics of user userID of a myVelib network velibnetwork."
	    				+ "\n" + "• sortStation<velibnetworkName, sortpolicy> : to display the stations in increasing order w.r.t. to the sorting policy of user sortpolicy."
	    				+ "\n" + "• display <velibnetworkName>: to display the entire status (stations, parking bays, users) of an a myVelib network velibnetworkName."
	    				+ "\n" + "• runtest <testFile>: to reproduce a given test scenario."
	    				+ "\n" + "• exit: to close the command line.");
	    	} 
	    	//else if(command.equalsIgnoreCase(anotherString))
	    } while(!exit);
	}
}
