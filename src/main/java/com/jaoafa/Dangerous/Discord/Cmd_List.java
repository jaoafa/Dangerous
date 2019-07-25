package com.jaoafa.Dangerous.Discord;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.jaoafa.Dangerous.CommandPremise;
import com.jaoafa.Dangerous.Main;

import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.RequestBuffer;

public class Cmd_List implements CommandPremise {
	@Override
	public void onCommand(IDiscordClient client, IGuild guild, IChannel channel, IUser author, IMessage message, String[] args){
		if(Main.channels == null) return;
		List<IChannel> filtered = Main.channels.stream().filter(
				_channel -> _channel != null && _channel.getLongID() == channel.getLongID()).collect(Collectors.toList());
		if(filtered.isEmpty()){
			return; // 違うチャンネルから
		}
		if(Bukkit.getOnlinePlayers().size() == 0) {
			String content = "**No online players**";
			RequestBuffer.request(() -> {
				channel.sendMessage(content);
			});
		}
		Set<String> players = new HashSet<>();
		for(Player player : Bukkit.getOnlinePlayers()) {
			players.add(player.getName());
		}
		String content = "**Online players (" + Bukkit.getOnlinePlayers().size() + " / " + Bukkit.getMaxPlayers() + ")**:```" + String.join(", ", players) + "```";
		RequestBuffer.request(() -> {
			channel.sendMessage(content);
		});
	}

	@Override
	public String getDescription() {
		return "サーバ内のユーザー一覧を表示";
	}

	@Override
	public String getUsage() {
		return "/list";
	}
}
