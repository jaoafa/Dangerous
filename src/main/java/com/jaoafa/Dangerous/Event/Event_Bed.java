package com.jaoafa.Dangerous.Event;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;

public class Event_Bed implements Listener {
	@EventHandler(priority = EventPriority.HIGHEST)
	public void OnPlayerBedEnterEvent(PlayerBedEnterEvent event){
		Player player = event.getPlayer();
		int playerCount = Bukkit.getWorld("fourcrime").getPlayers().size();

		for(Player p : Bukkit.getOnlinePlayers()){
			if(!p.getWorld().getName().equalsIgnoreCase("fourcrime")){
				continue;
			}
			if(p.getGameMode() == GameMode.CREATIVE || p.getGameMode() == GameMode.SPECTATOR){
				continue;
			}
		}
		int CREATIVEorSPECTATOR = 0;
		for(Player p : Bukkit.getOnlinePlayers()){
			if(!p.getWorld().getName().equalsIgnoreCase("fourcrime")){
				continue;
			}
			if(p.getGameMode() == GameMode.CREATIVE || p.getGameMode() == GameMode.SPECTATOR){
				CREATIVEorSPECTATOR++;
			}
		}
		int Need = playerCount - CREATIVEorSPECTATOR / 2;
		int NowSleeping = 1;
		for(Player p : Bukkit.getOnlinePlayers()){
			if(!p.getWorld().getName().equalsIgnoreCase("fourcrime")){
				continue;
			}
			if(p.getGameMode() == GameMode.CREATIVE || p.getGameMode() == GameMode.SPECTATOR){
				continue;
			}
			if(p.isSleeping()){
				NowSleeping++;
			}
		}
		int NowNeed = Need - NowSleeping;

		Bukkit.broadcastMessage(ChatColor.RED + "■" + ChatColor.RESET + "4Crime: " + player.getName() + "が就寝しました。夜が明けるにはあと" + NowNeed + "人が就寝しなければなりません。(必要人数: " + Need + "人)");
		if(NowNeed == 0){
			Bukkit.broadcastMessage(ChatColor.RED + "■" + ChatColor.RESET + "4Crime: " + "まもなく朝がやってきます…！");
			Bukkit.getWorld("Summer2018").setTime(0L);
		}else{
			Set<String> notsleeping = new HashSet<>();
			for(Player p : Bukkit.getOnlinePlayers()){
				if(!p.getWorld().getName().equalsIgnoreCase("fourcrime")){
					continue;
				}
				if(p.isSleeping()){
					continue;
				}
				if(p.getGameMode() == GameMode.CREATIVE || p.getGameMode() == GameMode.SPECTATOR){
					continue;
				}
				if(player.getName().equalsIgnoreCase(p.getName())){
					continue;
				}
				notsleeping.add(p.getName());
			}
			Bukkit.broadcastMessage(ChatColor.RED + "■" + ChatColor.RESET + "4Crime: " + "寝ていないプレイヤー: " + String.join(", ", notsleeping));
		}
	}
}
