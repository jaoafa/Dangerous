package com.jaoafa.Dangerous.Command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.jaoafa.Dangerous.Task_Bubu;

public class Cmd_H implements CommandExecutor {
	JavaPlugin plugin;
	public Cmd_H(JavaPlugin plugin) {
		this.plugin = plugin;
	}
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender instanceof Player)){
			sender.sendMessage("[H] " + ChatColor.GREEN + "このコマンドはサーバ内から実行してください。");
			return true;
		}
		Player player = (Player) sender;
		if(args.length == 2){
			if(args[0].equalsIgnoreCase("bubu")){
				if(!isInt(args[1])){
					sender.sendMessage("[H] " + ChatColor.GREEN + "0～2147483647の範囲内で指定してね！");
					return true;
				}
				int remain = Integer.valueOf(args[1]);
				if(remain < 0){
					sender.sendMessage("[H] " + ChatColor.GREEN + "0～2147483647の範囲内で指定してね！");
					return true;
				}
				new Task_Bubu(player, remain).runTaskTimer(plugin, 0L, 1L);
				return true;
			}
		}else if(args.length == 1){
			if(args[0].equalsIgnoreCase("bubu")){
				new Task_Bubu(player, 50).runTaskTimer(plugin, 0L, 1L);
				return true;
			}
		}
		sender.sendMessage("[H] " + ChatColor.GREEN + "bubu");
		return true;
	}
	public static boolean isInt(String s){
		try{
			Integer.valueOf(s);
			return true;
		}catch(NumberFormatException e){
			return false;
		}
	}
}
