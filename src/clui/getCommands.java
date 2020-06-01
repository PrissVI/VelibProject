package clui;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class getCommands {
	
	public static void readTextFileLineByLine(String fileName) {
		FileReader file = null;
		BufferedReader reader = null;
		
		try {
			file = new FileReader(fileName); 
			reader = new BufferedReader(file); 
			String line = "";
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
				ProcessCommandLine.executeCommand(line);
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
	}
}
