package com.animeiswrong;

import java.awt.AWTException;
import java.awt.Robot;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.PingEvent;
import org.pircbotx.hooks.types.GenericMessageEvent;

public class Listener extends ListenerAdapter {

	Robot inputbot;
	ExecutorService Queue = Executors.newSingleThreadExecutor();

	public Listener() {
		try {
			inputbot = new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onPing(PingEvent event) throws Exception {
		event.getBot().sendRaw().rawLineNow(String.format("PONG %s\r\n", event.getPingValue()));
	}
	
	@Override
	public void onGenericMessage(GenericMessageEvent event) {
		System.out.println(event.getUser().getLogin() + " : " + event.getMessage());
		if (event.getMessage().startsWith("!do")) {
			Command command = Config.getCommand(event.getMessage().replaceFirst("!do", "").trim().toLowerCase());
			if(command != null) {
				Queue.submit(command.run(inputbot));
			} else {
				System.out.println("Error: Incorrect command!");
			}
		}
	}
}
