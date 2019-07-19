package com.jaoafa.Dangerous.Event;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import com.jaoafa.Dangerous.Lib.MainServerManager;
import com.jaoafa.Dangerous.Lib.Servers;

public class Event_PlayerCommandSendOP implements Listener {
	@EventHandler
	public void onPlayerCommandPreprocessEvent(PlayerCommandPreprocessEvent e){
		Player player = e.getPlayer();
		if (!(player instanceof Player)) {
			return;
		}
		String command = e.getMessage();
		Servers mainserver = MainServerManager.getMainServer(player.getUniqueId());
		for(Player p: Bukkit.getServer().getOnlinePlayers()) {
			if(player.getUniqueId().equals(p.getUniqueId())){
				continue;
			}
			if(p.isOp()){
				p.sendMessage(ChatColor.GRAY + "(" + mainserver.getName() + ") " + player.getName() + ": " + ChatColor.YELLOW + command);
			}
		}
	}
}
