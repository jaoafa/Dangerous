package com.jaoafa.Dangerous.Command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class Cmd_SelectMain implements CommandExecutor {
	JavaPlugin plugin;
	public Cmd_SelectMain(JavaPlugin plugin) {
		this.plugin = plugin;
	}
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender instanceof Player)){
			sender.sendMessage("[SelectMain] " + ChatColor.GREEN + "このコマンドはサーバ内から実行してください。");
			return true;
		}
		Player player = (Player) sender;
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
		return true;
	}

}
