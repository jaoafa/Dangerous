package com.jaoafa.Dangerous.Command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.jaoafa.Dangerous.Main;

public class Cmd_WaitStop implements CommandExecutor {
	JavaPlugin plugin;
	public Cmd_WaitStop(JavaPlugin plugin) {
		this.plugin = plugin;
	}
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender instanceof Player)){
			sender.sendMessage("[WAITSTOP] " + ChatColor.GREEN + "このコマンドはサーバ内から実行してください。");
			return true;
		}
		Player player = (Player) sender;
		if(!player.isOp()){
			sender.sendMessage("[WAITSTOP] " + ChatColor.GREEN + "あなたはこのコマンドを実行するための権限を持っていません。");
			return true;
		}
		Main.WAITSTOPFLAG = !Main.WAITSTOPFLAG;
		if(Main.WAITSTOPFLAG){
			sender.sendMessage("[WAITSTOP] " + ChatColor.GREEN + "プレイヤー数が0人になったときにサーバを停止します。");
		}else{
			sender.sendMessage("[WAITSTOP] " + ChatColor.GREEN + "プレイヤー数が0人になったときにサーバを停止しません。");
		}
		return true;
	}
}
