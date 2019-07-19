package com.jaoafa.Dangerous.Event;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import com.jaoafa.Dangerous.Lib.MainServerManager;
import com.jaoafa.Dangerous.Lib.Servers;

public class Event_JoinSelectServer implements Listener {
	JavaPlugin plugin;
	public Event_JoinSelectServer(JavaPlugin plugin) {
		this.plugin = plugin;
	}
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onLogin(PlayerJoinEvent event){
		Player player = event.getPlayer();
		UUID uuid = player.getUniqueId();
		if(MainServerManager.getMainServer(uuid) != null && MainServerManager.getMainServer(uuid) != Servers.UNKNOWN){
			return;
		}
		Inventory inv = Bukkit.getServer().createInventory(player, 3 * 9, "メイン鯖選択画面");

		ItemStack item = new ItemStack(Material.OAK_SIGN);
		ItemMeta itemmeta = item.getItemMeta();
		itemmeta.setDisplayName("あなたがよく利用するメインサーバを選択してください！");
		item.setItemMeta(itemmeta);
		inv.setItem(4, item);

		ItemStack item_yellow_bed = new ItemStack(Material.YELLOW_BED);
		ItemMeta itemmeta_yellow_bed = item_yellow_bed.getItemMeta();
		itemmeta_yellow_bed.setDisplayName("jao Minecraft Server");
		item_yellow_bed.setItemMeta(itemmeta_yellow_bed);
		inv.setItem(10, item_yellow_bed);

		ItemStack item_blue_bed = new ItemStack(Material.BLUE_BED);
		ItemMeta itemmeta_blue_bed = item_blue_bed.getItemMeta();
		itemmeta_blue_bed.setDisplayName("FruitServer");
		item_blue_bed.setItemMeta(itemmeta_blue_bed);
		inv.setItem(12, item_blue_bed);

		ItemStack item_black_bed = new ItemStack(Material.BLACK_BED);
		ItemMeta itemmeta_black_bed = item_black_bed.getItemMeta();
		itemmeta_black_bed.setDisplayName("TORO Server");
		item_black_bed.setItemMeta(itemmeta_black_bed);
		inv.setItem(14, item_black_bed);

		ItemStack item_orange_bed = new ItemStack(Material.ORANGE_BED);
		ItemMeta itemmeta_orange_bed = item_orange_bed.getItemMeta();
		itemmeta_orange_bed.setDisplayName("さばみそサーバー");
		item_orange_bed.setItemMeta(itemmeta_orange_bed);
		inv.setItem(16, item_orange_bed);

		player.openInventory(inv);
	}
}
