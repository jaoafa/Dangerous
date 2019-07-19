package com.jaoafa.Dangerous.Event;

import org.bukkit.ChatColor;
import org.bukkit.advancement.Advancement;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.jaoafa.Dangerous.Main;
import com.jaoafa.Dangerous.Lib.AdvancementJP;
import com.jaoafa.Dangerous.Lib.MessageQueue;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.util.RequestBuffer;

public class Event_SendToDiscord implements Listener {
	JavaPlugin plugin;
	public Event_SendToDiscord(JavaPlugin plugin) {
		this.plugin = plugin;
	}
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onLogin(PlayerJoinEvent event){
		Player player = event.getPlayer();
		String message = "**" + player.getName() + "** joined the game.";
		if(Main.channels != null){
			for(IChannel channel : Main.channels){
				RequestBuffer.request(() -> {
					channel.sendMessage(message);
				});
			}
		}else{
			MessageQueue.Add(message);
		}
	}
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onChat(AsyncPlayerChatEvent event){
		Player player = event.getPlayer();
		String chat = event.getMessage();
		chat = ChatColor.stripColor(chat);
		String message = "**" + player.getName() + "**: " + chat;
		if(Main.channels != null){
			for(IChannel channel : Main.channels){
				RequestBuffer.request(() -> {
					channel.sendMessage(message);
				});
			}
		}else{
			MessageQueue.Add(message);
		}
	}
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onQuit(PlayerQuitEvent event){
		Player player = event.getPlayer();
		String message = "**" + player.getName() + "** left the game.";
		if(Main.channels != null){
			for(IChannel channel : Main.channels){
				RequestBuffer.request(() -> {
					channel.sendMessage(message);
				});
			}
		}else{
			MessageQueue.Add(message);
		}
	}
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onDeath(PlayerDeathEvent event){
		String reason = event.getDeathMessage();
		String message = "**" + reason + "**";
		if(Main.channels != null){
			for(IChannel channel : Main.channels){
				RequestBuffer.request(() -> {
					channel.sendMessage(message);
				});
			}
		}else{
			MessageQueue.Add(message);
		}
	}
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onAdvancement(PlayerAdvancementDoneEvent event){
		Player player = event.getPlayer();
		Advancement advancement = event.getAdvancement();
		String rawAdvancementName = advancement.getKey().getKey();
		AdvancementJP advanceJp = AdvancementJP.getAdvancementJPFromKey(rawAdvancementName);
		String message = ":medal: **" + player.getName() + "が実績「" + advanceJp.getName() + "」を取得しました。**";
		if(Main.channels != null){
			for(IChannel channel : Main.channels){
				RequestBuffer.request(() -> {
					channel.sendMessage(message);
				});
			}
		}else{
			MessageQueue.Add(message);
		}
	}
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onEnable(PluginEnableEvent event){
		String message = ":white_check_mark: **Server Started!** :white_check_mark:";
		if(Main.channels != null){
			for(IChannel channel : Main.channels){
				RequestBuffer.request(() -> {
					channel.sendMessage(message);
				});
			}
		}else{
			MessageQueue.Add(message);
		}
	}
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onDisable(PluginDisableEvent event){
		String message = ":octagonal_sign: **Server Closed.** :octagonal_sign:";
		if(Main.channels != null){
			for(IChannel channel : Main.channels){
				RequestBuffer.request(() -> {
					channel.sendMessage(message);
				});
			}
		}else{
			MessageQueue.Add(message);
		}
	}
}
