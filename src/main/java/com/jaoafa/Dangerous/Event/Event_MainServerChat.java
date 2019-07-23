package com.jaoafa.Dangerous.Event;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.jaoafa.Dangerous.Lib.MainServerManager;
import com.jaoafa.Dangerous.Lib.Servers;

public class Event_MainServerChat implements Listener {
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onEvent_ChatMainServer(AsyncPlayerChatEvent event){
		Servers server = MainServerManager.getMainServer(event.getPlayer().getUniqueId());
		if(server == null) server = Servers.UNKNOWN;
		String format = server.getColor() + "■" + ChatColor.WHITE + "%s: %s";
		event.setFormat(format);
	}
}
