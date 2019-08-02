package com.jaoafa.Dangerous.Event;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import com.jaoafa.Dangerous.Lib.PvPManager;

public class Event_PvP implements Listener {
	@EventHandler(priority = EventPriority.HIGHEST)
	public void OnEntityDamageByEntityEvent(EntityDamageByEntityEvent event){
		if (!(event.getDamager() instanceof Player)) {
			return;
		}
		Player from = (Player) event.getDamager(); //殴った人
		Entity entity = event.getEntity(); //殴られたエンティティ
		if(entity == null){
			return;
		}
		if(entity.getType() != EntityType.PLAYER){
			return;
		}
		Player to = (Player) entity;
		boolean fromBool = PvPManager.isPvP(from.getUniqueId());
		boolean toBool = PvPManager.isPvP(to.getUniqueId());
		if(!fromBool){
			// not pvp
			from.sendMessage("[PvP] " + ChatColor.GREEN + "あなたはPvPを無効化しています。PvPを行う場合はコマンド「/pvp」でPvPを有効化してください。");
			event.setCancelled(true);
			return;
		}
		if(!toBool){
			from.sendMessage("[PvP] " + ChatColor.GREEN + "相手がPvPを無効化しているため、PvPはできません。");
			event.setCancelled(true);
			return;
		}
	}
}
