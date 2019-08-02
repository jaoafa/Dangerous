package com.jaoafa.Dangerous.Command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.jaoafa.Dangerous.Lib.JpManager;

public class Cmd_Jp implements CommandExecutor {
	JavaPlugin plugin;
	public Cmd_Jp(JavaPlugin plugin) {
		this.plugin = plugin;
	}
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender instanceof Player)){
			sender.sendMessage("[JP] " + ChatColor.GREEN + "このコマンドはサーバ内から実行してください。");
			return true;
		}
		Player player = (Player) sender;
		if(args.length == 1){
			if(args[0].equalsIgnoreCase("on")){
				if(!JpManager.isOFF(player.getUniqueId().toString())){
					sender.sendMessage("[JP] " + ChatColor.GREEN + "既にかな変換はオンになっています！");
					return true;
				}
				JpManager.ON(player.getUniqueId().toString());
				sender.sendMessage("[JP] " + ChatColor.GREEN + "かな変換をオンにしました。");
				return true;
			}else if(args[0].equalsIgnoreCase("off")){
				if(JpManager.isOFF(player.getUniqueId().toString())){
					sender.sendMessage("[JP] " + ChatColor.GREEN + "既にかな変換はオフになっています！");
					return true;
				}
				JpManager.OFF(player.getUniqueId().toString());
				sender.sendMessage("[JP] " + ChatColor.GREEN + "かな変換をオフにしました。");
				return true;
			}
		}
		sender.sendMessage("[JP] " + ChatColor.GREEN + "on/off");
		return true;
	}
}
