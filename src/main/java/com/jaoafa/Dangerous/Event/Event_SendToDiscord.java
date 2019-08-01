package com.jaoafa.Dangerous.Event;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import org.bukkit.plugin.java.JavaPlugin;

import com.jaoafa.Dangerous.Main;
import com.jaoafa.Dangerous.Lib.AdvancementJP;
import com.jaoafa.Dangerous.Lib.MainServerManager;
import com.jaoafa.Dangerous.Lib.MessageQueue;
import com.jaoafa.Dangerous.Lib.Servers;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IEmoji;
import sx.blah.discord.util.RequestBuffer;

public class Event_SendToDiscord implements Listener {
	JavaPlugin plugin;
	String emoji_regex = ":(.+?):";
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
		chat = chat.replaceAll("@here", "");
		chat = chat.replaceAll("@everyone", "");
		// emoji
		Pattern p = Pattern.compile(emoji_regex);
		Matcher m = p.matcher(chat);
		Servers server = MainServerManager.getMainServer(player.getUniqueId());
		while(m.find()){
			IEmoji emoji = Main.getEmoji(server, m.group(1));
			if(emoji == null){
				continue;
			}
			String animate = "";
			if(emoji.isAnimated()){
				animate = "a";
			}
			chat = chat.replace(m.group(), "<" + animate + ":" + emoji.getName() + ":" + emoji.getStringID() + ">");
		}
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
		reason = ChatColor.stripColor(reason);
		reason = reason.replaceAll("@here", "");
		reason = reason.replaceAll("@everyone", "");
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

		// https://github.com/DiscordSRV/DiscordSRV/blob/6b8de4afb3bfecf9c63275d381c75b103e5543f3/src/main/java/github/scarsz/discordsrv/listeners/PlayerAdvancementDoneListener.java
		if (event.getAdvancement() == null || event.getAdvancement().getKey().getKey().contains("recipe/") || event.getPlayer() == null) return;

        try {
            Object craftAdvancement = ((Object) event.getAdvancement()).getClass().getMethod("getHandle").invoke(event.getAdvancement());
            Object advancementDisplay = craftAdvancement.getClass().getMethod("c").invoke(craftAdvancement);
            boolean display = (boolean) advancementDisplay.getClass().getMethod("i").invoke(advancementDisplay);
            if (!display) return;
        } catch (NullPointerException e) {
            return;
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

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
	/*
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
	*/
}
