package com.animeiswrong;

import java.io.IOException;
import java.util.Scanner;

import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import org.pircbotx.exception.IrcException;

public class Launch {
	
	static PircBotX bot = null;
	public static void main(String[] args) {
		Config.loadConfig();
		boolean isRunning = true;
		Configuration configuration = new Configuration.Builder()
				.setName("animeisabot")
				.addServer("irc.chat.twitch.tv", 6667)
				.setServerPassword("oauth:9d1hzmym17ex8t6yksrqtr5nbhzd9f")
				.addAutoJoinChannel("#animeiswrong")
				.setAutoReconnect(true)
				.addListener(new Listener())
				.buildConfiguration();
		
		bot = new PircBotX(configuration);
		Thread BotThread = new Thread() {
			@Override
			public void run() {
				try {
					bot.startBot();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IrcException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		BotThread.start();
		
		Scanner scanner = new Scanner(System.in);
		while(isRunning){
			String input = scanner.nextLine();
			switch(input){
			case "stop":
				bot.close();
				System.exit(2);
				break;
			case "reload":
				Config.loadConfig();
				break;
			case "test":
				Config.test();
				break;
			}
		}
		scanner.close();
	}

}
