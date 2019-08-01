package com.jaoafa.Dangerous;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.jaoafa.Dangerous.Lib.MainServerManager;
import com.jaoafa.Dangerous.Lib.Servers;

public class Task_TabListSKKReloader extends BukkitRunnable {
	private Player player;
	public Task_TabListSKKReloader(Player player) {
		this.player = player;
	}
	@Override
	public void run() {
		if(!player.isOnline()){
			return;
		}
		Servers server = MainServerManager.getMainServer(player.getUniqueId());
		if(server == null) server = Servers.UNKNOWN;
		player.setPlayerListName(server.getColor() + "■" + ChatColor.RESET + player.getName());
		player.setDisplayName(server.getColor() + "■" + ChatColor.RESET + player.getName());
	}
}
