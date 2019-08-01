package com.jaoafa.Dangerous;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Task_Bubu extends BukkitRunnable {
	Player player;
	int remain;
	public Task_Bubu(Player player, int remain){
		this.player = player;
		this.remain = remain;
	}
	@Override
	public void run() {
		if(!player.isOnline()){
			cancel();
			return;
		}
		if(player.isDead()){
			cancel();
			return;
		}
		if(remain < 0){
			player.setHealth(0D);
			cancel();
			return;
		}
		player.chat(ChatColor.RED + "BUBUBUBUBUBUBUBU!!!!!");
		player.getWorld().createExplosion(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), 2F, false, false);
		remain--;
	}
}
