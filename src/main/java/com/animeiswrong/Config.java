package com.animeiswrong;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Scanner;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Config {
	
	/*
	 * {"Alias":["W","Forward"],"Key":87,"Time":1000}
	 * {"Alias":["A","Left"],"Key":65,"Time":1000}
	 * {"Alias":["S","Backward"],"Key":83,"Time":1000}
	 * {"Alias":["D","Right"],"Key":68,"Time":1000}
	 */
	
	private static ArrayList<Command> Commands = new ArrayList<>();

	public static Command getCommand(String word) {
		for(Command command:Commands)
			for(String alias:command.Aliases)
				if(alias.equals(word))
					return command;
		return null;
	}
	
	public static void test() {
		for(Command command:Commands)
			for(String alias:command.Aliases)
				System.out.println(alias);
	}
	
	public static void loadConfig() {
		OutputStream output = null;
		File ConfigFile = new File("Config.config");
		boolean isNew = true;
		Scanner scanner = null;
		try {
			scanner = new Scanner(ConfigFile);
			while(scanner.hasNextLine()) {
				isNew = false;
				JsonObject object = (JsonObject) new JsonParser().parse(scanner.nextLine());
				ArrayList<String> aliases = new ArrayList<>();
				for(JsonElement str:object.get("Alias").getAsJsonArray())
					aliases.add(str.getAsString().toLowerCase());
				Command newCommand = new Command
						.builder()
						.setAliases(aliases.toArray(new String[aliases.size()]))
						.setKey(object.get("Key").getAsInt())
						.setTime(object.get("Time").getAsLong())
						.build();
				Commands.add(newCommand);
				System.out.println("added: "+newCommand.toString());
			}
		} catch (FileNotFoundException e1) {
			System.out.println("Couldn't find config generating example config...");
		} finally {
			if(scanner != null)
				scanner.close();
		}
		if (isNew) {
			try {
				output = new FileOutputStream(ConfigFile);
				String Example = "{\"Alias\":[\"W\",\"Forward\"],\"Key\":87,\"Time\":1000}\r\n" + 
						"{\"Alias\":[\"A\",\"Left\"],\"Key\":65,\"Time\":1000}\r\n" + 
						"{\"Alias\":[\"S\",\"Backward\"],\"Key\":83,\"Time\":1000}\r\n" + 
						"{\"Alias\":[\"D\",\"Right\"],\"Key\":68,\"Time\":1000}";
				output.write(Example.toString().getBytes());
				loadConfig();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					if (output != null)
						output.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
