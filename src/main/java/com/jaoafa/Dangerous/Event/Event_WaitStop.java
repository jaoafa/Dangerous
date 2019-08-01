package com.jaoafa.Dangerous.Event;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.jaoafa.Dangerous.Main;

public class Event_WaitStop implements Listener {
	JavaPlugin plugin;
	public Event_WaitStop(JavaPlugin plugin) {
		this.plugin = plugin;
	}
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onQuit(PlayerQuitEvent event){
		if((Bukkit.getServer().getOnlinePlayers().size() - 1) == 0 && Main.WAITSTOPFLAG){
			Bukkit.broadcastMessage("[WAITSTOP] " + ChatColor.GREEN + "オンラインプレイヤーが0人になったため、サーバを停止します。");
			plugin.getServer().shutdown();
		}
	}
}
