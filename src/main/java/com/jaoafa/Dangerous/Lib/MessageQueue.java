package com.jaoafa.Dangerous.Lib;

import com.jaoafa.Dangerous.Main;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.util.RequestBuffer;

public class MessageQueue extends Thread {
	public static boolean Add(String message){
		return Main.queue.add(message);
	}
	public static String Get(){
		return Main.queue.poll();
	}
	@Override
	public void run(){
		while(true){
			String message = Main.queue.poll();
			if(!Main.SERVICE_RUNNING_FLAG){
				return;
			}
			if(message == null) continue;
			for(IChannel channel : Main.channels){
				RequestBuffer.request(() -> {
					channel.sendMessage(message);
				});
			}
		}
	}
}
