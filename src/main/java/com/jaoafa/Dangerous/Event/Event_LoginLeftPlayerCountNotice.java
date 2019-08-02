package com.jaoafa.Dangerous.Event;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class Event_LoginLeftPlayerCountNotice implements Listener {
	@EventHandler
	public void OnEvent_LoginPlayerCountNotice(PlayerJoinEvent event){
		Bukkit.broadcastMessage(ChatColor.RED + "■" + ChatColor.WHITE + "4Crime: 現在『" + Bukkit.getServer().getOnlinePlayers().size() + "人』がログインしています");
	}

	@EventHandler
	public void OnEvent_LeftPlayerCountNotice(PlayerQuitEvent event){
		Bukkit.broadcastMessage(ChatColor.RED + "■" + ChatColor.WHITE + "4Crime: 現在『" + (Bukkit.getServer().getOnlinePlayers().size() - 1) + "人』がログインしています");
	}
}
