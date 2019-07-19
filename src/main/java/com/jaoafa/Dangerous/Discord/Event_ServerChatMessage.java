package com.jaoafa.Dangerous.Discord;

import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import com.jaoafa.Dangerous.Main;

import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.RequestBuffer;

public class Event_ServerChatMessage {
	@EventSubscriber
	public void onMessageReceivedEvent(MessageReceivedEvent event) {
		IGuild guild = event.getGuild();
		IChannel channel = event.getChannel();
		final IUser author = event.getAuthor();
		IMessage message = event.getMessage();
		String content = message.getContent();
		String formatcontent = message.getFormattedContent();

		if(Main.channels == null) return;
		List<IChannel> filtered = Main.channels.stream().filter(
				_channel -> _channel != null && _channel.getLongID() == channel.getLongID()).collect(Collectors.toList());
		if(filtered.isEmpty()){
			return; // 違うチャンネルから
		}
		String name = author.getNicknameForGuild(guild);
		if(name == null){
			name = author.getName();
		}
		Bukkit.broadcastMessage(ChatColor.AQUA + "[" + guild.getName() + "#" + channel.getName() + "] " + ChatColor.RESET + name + ": " + formatcontent);

		content = content.replaceAll("@here", "");
		content = content.replaceAll("@everyone", "");

		String _content = content;
		for(IChannel _channel : Main.channels){
			if(channel.getLongID() == _channel.getLongID()) continue;
			RequestBuffer.request(() -> {
				String _name = author.getNicknameForGuild(guild);
				if(_name == null){
					_name = author.getName();
				}
				_channel.sendMessage("**[" + guild.getName() + "#" + channel.getName() + "] " + _name + "**: " + _content);
			});
		}
	}
}
