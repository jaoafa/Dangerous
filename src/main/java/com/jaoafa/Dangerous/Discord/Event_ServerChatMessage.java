package com.jaoafa.Dangerous.Discord;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import com.jaoafa.Dangerous.Main;
import com.jaoafa.Dangerous.Lib.MuteManager;
import com.jaoafa.Dangerous.Lib.Servers;
import com.vdurmont.emoji.EmojiParser;

import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IMessage.Attachment;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.RequestBuffer;

public class Event_ServerChatMessage {
	String regex = "<a?:(.+?):([0-9]+)>";
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
		if(MuteManager.Exists(author.getStringID())){
			message.delete();
			return;
		}
		String name = author.getNicknameForGuild(guild);
		if(name == null){
			name = author.getName();
		}
		formatcontent = EmojiParser.parseToAliases(formatcontent);
		formatcontent = ChatColor.translateAlternateColorCodes('&', formatcontent);

		// 鯖絵文字
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(formatcontent);
		while(m.find()){
			formatcontent = formatcontent.replace(m.group(), ":" + m.group(1) + ":");
		}

		// ServerColor
		Servers server = Servers.getServerFromDiscordServer(guild.getLongID());
		ChatColor color = server.getColor();

		Bukkit.broadcastMessage(color + "[" + guild.getName() + "] " + ChatColor.RESET + name + ": " + formatcontent);

		if(!message.getAttachments().isEmpty()){
			List<String> urls = new ArrayList<>();
			for(Attachment attachment : message.getAttachments()){
				urls.add(attachment.getUrl());
			}
			Bukkit.broadcastMessage(color + "[" + guild.getName() + "] " + ChatColor.RESET + name + ": " + String.join(" ", urls));
		}

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
				_name = _name.replace("_", "\\_");
				_name = _name.replace("*", "\\*");
				_name = _name.replace("`", "\\`");
				_name = _name.replace("~", "\\~");
				_channel.sendMessage("**[" + guild.getName() + "] " + _name + "**: " + _content + "\n");
			});
			if(!message.getAttachments().isEmpty()){
				RequestBuffer.request(() -> {
					String _name = author.getNicknameForGuild(guild);
					if(_name == null){
						_name = author.getName();
					}
					_name = _name.replace("_", "\\_");
					_name = _name.replace("*", "\\*");
					_name = _name.replace("`", "\\`");
					_name = _name.replace("~", "\\~");
					List<String> urls = new ArrayList<>();
					for(Attachment attachment : message.getAttachments()){
						urls.add(attachment.getUrl());
					}
					_channel.sendMessage("**[" + guild.getName() + "] " + _name + "**: " + String.join(" ", urls));
				});
			}
		}
	}
}
