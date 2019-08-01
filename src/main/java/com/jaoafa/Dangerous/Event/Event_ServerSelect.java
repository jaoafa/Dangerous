package com.jaoafa.Dangerous.Event;

import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import com.jaoafa.Dangerous.Task_TabListSKKReloader;
import com.jaoafa.Dangerous.Lib.MainServerManager;
import com.jaoafa.Dangerous.Lib.Servers;

public class Event_ServerSelect implements Listener {
	JavaPlugin plugin;
	public Event_ServerSelect(JavaPlugin plugin) {
		this.plugin = plugin;
	}
	@EventHandler(ignoreCancelled = true)
	public void onSelect(InventoryClickEvent event) {
		if(event.getWhoClicked().getType() != EntityType.PLAYER) return;
		if(event.getClickedInventory() == null) return;
		if(!event.getView().getTitle().equals("メイン鯖選択画面")) return;
		Player player = (Player) event.getWhoClicked();
		UUID uuid = player.getUniqueId();

		event.setCancelled(true);

		ItemStack is = event.getCurrentItem();
		if(is == null){
			return;
		}
		if(is.getType() == Material.AIR){
			return;
		}
		ItemMeta itemmeta = is.getItemMeta();
		if(!itemmeta.hasDisplayName()){
			return;
		}
		/*
		  Server ID
		  1: jao Minecraft Server
		  2: FruitServer
		  3: TORO Server
		  4: さばみそサーバー
		*/
		if(itemmeta.getDisplayName().equals("jao Minecraft Server")){
			MainServerManager.setMainServer(uuid, Servers.JAO);
			player.sendMessage("[ServerMain] " + ChatColor.GREEN + "メインサーバを「" + ChatColor.YELLOW + "jao Minecraft Server" + ChatColor.GREEN + "」として設定しました。");
		}else if(itemmeta.getDisplayName().equals("FruitServer")){
			MainServerManager.setMainServer(uuid, Servers.FRUIT);
			player.sendMessage("[ServerMain] " + ChatColor.GREEN + "メインサーバを「" + ChatColor.BLUE + "FruitServer" + ChatColor.GREEN + "」として設定しました。");
		}else if(itemmeta.getDisplayName().equals("TORO Server")){
			MainServerManager.setMainServer(uuid, Servers.TORO);
			player.sendMessage("[ServerMain] " + ChatColor.GREEN + "メインサーバを「" + ChatColor.GRAY + "TORO Server" + ChatColor.GREEN + "」として設定しました。");
		}else if(itemmeta.getDisplayName().equals("さばみそサーバー")){
			MainServerManager.setMainServer(uuid, Servers.SABAMISO);
			player.sendMessage("[ServerMain] " + ChatColor.GREEN + "メインサーバを「" + ChatColor.GOLD + "さばみそサーバー" + ChatColor.GREEN + "」として設定しました。");
		}

		new Task_TabListSKKReloader(player).runTaskLater(plugin, 20L);

		player.closeInventory();
	}
}
