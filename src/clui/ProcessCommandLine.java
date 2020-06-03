package clui;

import java.util.ArrayList;

/**
 * ProcessCommandLine is a class that is used to execute an array list of commands.
 * 
 * @author Ali Raïki
 */

public class ProcessCommandLine {

	/**
	 * This method takes an arraylist of commands and run them one by one thanks to the method executeCommand.
	 * @param lines
	 * 				ArrayList of commands.
	 */
	public static void executeCommands(ArrayList<String> lines) {

		for(String line : lines) {
			System.out.println(line);
			CommandLine.executeCommand(line);
		}
	}
}