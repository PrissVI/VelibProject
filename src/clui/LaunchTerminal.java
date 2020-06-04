package clui;

import java.util.Scanner;

/**
 * LaunchTerminal is a class that is used to launch the terminal so the user can input the different command that he want.
 * Run this class if you want to interact with this project.
 * 
 * @author Ali Raïki
 */

public class LaunchTerminal {
	
	public static void main(String[] args) {
		
		GetCommands.readTextFileLineByLine("my_velib.ini");
		System.out.println("Welcome to the myVelib App! Type 'help' for a list of commands.");
		Scanner input = new Scanner(System.in); // used to read the keyboard
		
		String entry; // stores the next line input
	    boolean exit = false;
	    
	    do {
	    	entry = input.nextLine(); //the input of the user
	    	if(entry.split(" ")[0].equalsIgnoreCase("exit")) {
	    		exit = true;
	    		input.close();
	    		System.out.println("Command line is closed.");
	    	}
	    	else {
	    		CommandLine.executeCommand(entry);
	    	}
	    } while(!exit);

	}
	
}
