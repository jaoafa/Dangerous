package com.jaoafa.Dangerous.Event;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.jaoafa.Dangerous.Task_AFK;
import com.jaoafa.Dangerous.Command.Cmd_AFK;

public class Event_AFK implements Listener {
	@EventHandler
	public void onPlayerMoveEvent(PlayerMoveEvent event){
		Player player = event.getPlayer();

		Task_AFK.afktime.put(player.getName(), System.currentTimeMillis()); // 動いたら更新する

		if(!Cmd_AFK.getAFKing(player)){
			return;
	   	}

		Cmd_AFK.setAFK_False(player);
	}
	@EventHandler
	public void OnEvent_PlayerQuit(PlayerQuitEvent event){
		Player player = event.getPlayer();
		if(!Cmd_AFK.getAFKing(player)){
			return;
	   	}

		Cmd_AFK.setAFK_False(player);
	}
	@EventHandler
	public void onEntityTarget(EntityTargetEvent event) {
	    Entity target = event.getEntity();
	    if(target instanceof Player) {
	        Player player = (Player) target;
	        if(!Cmd_AFK.getAFKing(player)){
				return;
		   	}
	        event.setCancelled(true);
	    }
	}
}
