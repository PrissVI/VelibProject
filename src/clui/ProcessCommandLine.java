package clui;

import java.util.ArrayList;

public class ProcessCommandLine {

	public static void executeCommands(ArrayList<String> lines) {

		for(String line : lines) {
			System.out.println(line);
			CommandLine.executeCommand(line);
		}
	}
}