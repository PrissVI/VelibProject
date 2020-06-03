package test;

import org.junit.jupiter.api.Test;

import clui.CommandLine;

/**
 * Test errors relatives to the command that is given.
 * 
 * @author Ali Raïki
 */


class CommandLineTest {
	
	/**
	 * Inexistent command.
	 */
	@Test
	void test() {
		String commandError = "randomCommand network 1";
		CommandLine.executeCommand(commandError); //Should print that this command does not exist.
	}
	
	/**
	 * Too many parameters.
	 */
	@Test
	void test2() {
		String commandError = "setup network 1 djdj jedjdj djdjd fjfj djdjfj fjfjf jfjf";
		CommandLine.executeCommand(commandError); //Should print that there are too many or not enough parameters.
	}
	
	/**
	 * Not enough parameters.
	 */
	@Test
	void test3() {
		String commandError = "setup";
		CommandLine.executeCommand(commandError); //Should print that there are too many or not enough parameters.
	}
	
	
	/**
	 * Incorrect parameters (String instead of Integer for example).
	 */
	@Test
	void test4() {
		String commandError = "setup network2 i 10 5 25";
		CommandLine.executeCommand(commandError); //Should print that the parameters are incorrect (with a specific error).
	}
	

	/**
	 * Test of some of the commands (setup, adduser, online station etc.)
	 */
	
	@Test
	void test5() {
		String command = "setup network";
		CommandLine.executeCommand(command); //Should print the MyVelibNetwork "network" that was created, consisting of 10 stations each of which has 10 parking slots and such that stations are arranged on a square grid whose of side 4km and initially populated with a 75% bikes randomly distributed over the 10 stations
		String commandError = "setup";
		CommandLine.executeCommand(commandError); //Should print too many or not enough parameters
	}

	@Test
	void test6() {
		String command2 = "setup network2 3 10 5 25";
		CommandLine.executeCommand(command2); //Should print the MyVelibNetwork "network" that was created, consisting of 3 stations each of which has 10 parking slots and such that stations are arranged on a square grid whose of side 5km and initially populated with a 25 bikes randomly distributed over the 10 stations.
		String commandError2 = "setup network2 i 10 5 25";
		CommandLine.executeCommand(commandError2); //Incorrect parameters for input string: "i"
	}
	
	@Test
	void test7() {
		//Setup
		String command3 = "adduser john vlibre network";
		CommandLine.executeCommand(command3); //Should print the ID of the user (1 in this case), the name of the network it has been added to, his card and his time credit balance.
		String commandError3 = "adduser john vlibre randomNetwork";
		CommandLine.executeCommand(commandError3); //Should print that this network does not exist.
	}
	
	@Test
	void test8() {
		String command4 = "offline network 1";
		CommandLine.executeCommand(command4); //Should print that the station n°1 has been set to offline.
		String commandError4 = "offline network 20";
		CommandLine.executeCommand(commandError4); //Should print that the station does not exist.
	}
	
}
