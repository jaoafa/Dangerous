package com.jaoafa.Dangerous.Discord;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.jaoafa.Dangerous.Main;
import com.jaoafa.Dangerous.Lib.MessageQueue;

import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.obj.IChannel;

public class Event_DiscordReady {
	@EventSubscriber
	public void onReadyEvent(ReadyEvent event) {
		System.out.println("Ready: " + event.getClient().getOurUser().getName());

		IDiscordClient client = event.getClient();
		List<IChannel> channels = client.getChannels();
		System.out.println("[Dangerous] " + channels.size() + " channels were found from the Discord.");
		System.out.println("[Dangerous] " + Main.channelIds.size() + " channel IDs were found from the config.");
		for(Long channelid : Main.channelIds){
			List<IChannel> filtered = channels.stream().filter(
					channel -> channel != null && channel.getLongID() == channelid).collect(Collectors.toList());
			if(!filtered.isEmpty()){
				if(Main.channels == null){
					Main.channels = new ArrayList<>();
				}
				Main.channels.add(filtered.get(0));
			}else{
				System.out.println("[Dangerous] The channel ID " + channelid + " was not found.");
			}
		}

		System.out.println("[Dangerous] Send and receive messages on " + Main.channels.size() + " channels.");

		MessageQueue messagequeue = new MessageQueue();
		messagequeue.start();
	}
}
