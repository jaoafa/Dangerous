package com.jaoafa.Dangerous.Command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class Cmd_PvP implements CommandExecutor {
		JavaPlugin plugin;
		public Cmd_PvP(JavaPlugin plugin) {
			this.plugin = plugin;
		}
		@Override
		public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
			sender.sendMessage("disabled");return true;
			/*
			if(!(sender instanceof Player)){
				sender.sendMessage("[PvP] " + ChatColor.GREEN + "このコマンドはサーバ内から実行してください。");
				return true;
			}
			Player player = (Player) sender;
			UUID uuid = player.getUniqueId();
			if(PvPManager.isPvP(uuid)){
				// true -> false
				PvPManager.setPvPFlag(uuid, false);
				sender.sendMessage("[PvP] " + ChatColor.GREEN + "PvPを無効化しました。");
				return true;
			}else{
				// false -> true
				PvPManager.setPvPFlag(uuid, true);
				sender.sendMessage("[PvP] " + ChatColor.GREEN + "PvPを有効化しました。");
				return true;
			}*/
		}
	}
