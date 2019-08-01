package com.jaoafa.Dangerous.Command;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;

public class Cmd_Hat implements CommandExecutor {
	JavaPlugin plugin;
	public Cmd_Hat(JavaPlugin plugin) {
		this.plugin = plugin;
	}
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		if (!(sender instanceof Player)) {
			sender.sendMessage("[HAT] " + ChatColor.GREEN + "このコマンドはゲーム内から実行してください。");
			return true;
		}
		Player player = (Player) sender;
		PlayerInventory inv = player.getInventory();
		ItemStack hand = player.getInventory().getItemInMainHand();
		if(hand.getType() == Material.AIR){
			sender.sendMessage("[HAT] " + ChatColor.GREEN + "手にブロックを持ってください。");
			return true;
		}
		ItemStack head = inv.getHelmet();
		if(head != null){
			if(head.getType() != Material.AIR){
				inv.removeItem(head);
			}
		}
		inv.setHelmet(hand);
		player.getInventory().setItemInMainHand(head);
		sender.sendMessage("[HAT] " + ChatColor.GREEN + "持っていたブロックを頭にのせました。");
		return true;
	}
}
