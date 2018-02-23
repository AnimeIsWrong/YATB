package com.animeiswrong;

import java.awt.Robot;
import java.util.Arrays;

public class Command {
	
	String[] Aliases;
	int Key;
	long Time;
	
	public Runnable run(Robot inputBot) {
		return() ->{
			inputBot.keyPress(Key);
			try {
				Thread.sleep(Time);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			inputBot.keyRelease(Key);
		};
	}
	
	@Override
	public String toString() {
		return "Command [Aliases=" + Arrays.toString(Aliases) + ", Key=" + Key + ", Time=" + Time + "]";
	}

	public Command(String[] aliases, int key, long time) {
		super();
		Aliases = aliases;
		Key = key;
		Time = time;
	}
	
	public static class builder {
		private String[] Aliases;
		private int Key;
		private long Time;
		
		public builder setAliases(String[] Aliases) {
			this.Aliases = Aliases;
			return this;
		}
		public builder setKey(int Key) {
			this.Key = Key;
			return this;
		}
		public builder setTime(long Time) {
			this.Time = Time;
			return this;
		}
		
		public Command build() {
			return new Command(Aliases, Key, Time);
		}
	}
	
}
