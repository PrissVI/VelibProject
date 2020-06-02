package clui;

import java.util.Scanner;

public class LaunchTerminal {
	
	public static void main(String[] args) {
		
		System.out.println("Welcome to the myVelib App! Type 'help' for a list of commands.");
		Scanner input = new Scanner(System.in); // used to read the keyboard
		
		String entry; // stores the next line input
	    boolean exit = false;
	    
	    do {
	    	entry = input.nextLine();
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
