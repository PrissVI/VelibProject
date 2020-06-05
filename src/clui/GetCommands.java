package clui;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * getCommands is a class that is used to read a file of command lines, and execute these commands.
 * 
 * @author Ali Raïki
 */

public class GetCommands {
	
	/**
	 * Method that reads a file line by line, then calls another method to execute these commands.
	 * @param fileName
	 * 				Name of the file to be read.
	 */
	public static void readTextFileLineByLine(String fileName) {
		FileReader file = null;
		BufferedReader reader = null;
		ArrayList<String> lines = new ArrayList<String>();
		
		try {
			file = new FileReader(fileName); //A file reader for reading byte-by-byte
			reader = new BufferedReader(file); //Wrapping a file reader into a BufferedReader for reading line-by-line
			String line = "";
			while ((line = reader.readLine()) != null) { //Reading the file line by line
				lines.add(line);
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}  finally {
			if (reader != null) {
				try {reader.close(); }
				catch (IOException e) {
					System.err.println(e.getMessage());
				}
				if (file != null) {
					try {file.close(); }
					catch (IOException e) {
						System.err.println(e.getMessage());
					}
				}
			}
		}
		ProcessCommandLine.executeCommands(lines); //Execute the commands
	}
}
