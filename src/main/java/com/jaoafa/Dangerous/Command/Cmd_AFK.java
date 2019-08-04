package com.jaoafa.Dangerous.Command;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import com.jaoafa.Dangerous.Main;
import com.jaoafa.Dangerous.Task_AFKING;
import com.jaoafa.Dangerous.Lib.MessageQueue;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.util.RequestBuffer;

public class Cmd_AFK implements CommandExecutor {
	private static Map<String, BukkitTask> afking = new HashMap<>();
	private static Map<String, Location> loc = new HashMap<>();
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		if (!(sender instanceof Player)) {
			sender.sendMessage("[AFK] " + ChatColor.GREEN + "このコマンドはゲーム内から実行してください。");
			return true;
		}
		Player player = (Player) sender;

		if(!getAFKing(player)){
			// NOT AFKなら NOT AFK→AFK
			setAFK_True(player);
		}else{
			// AFKなら AFK→NOT AFK
			setAFK_False(player);
		}
		return true;
	}

	/**
	 * プレイヤーをAFKにする
	 *
	 * @param player 設定するプレイヤー
	 * @author mine_book000
	 */
	public static void setAFK_True(Player player){
		Bukkit.broadcastMessage(ChatColor.DARK_GRAY + player.getName() + " is afk!");
		String message = player.getName() + " is afk!";
		if(Main.channels != null){
			for(IChannel channel : Main.channels){
				RequestBuffer.request(() -> {
					channel.sendMessage(message);
				});
			}
		}else{
			MessageQueue.Add(message);
		}

		String listname = player.getPlayerListName().replaceAll(player.getName(), ChatColor.DARK_GRAY + player.getName());
		player.setPlayerListName(listname);
		try{
			BukkitTask task = new Task_AFKING(player).runTaskTimer(Main.getJavaPlugin(), 0L, 5L);
			afking.put(player.getName(), task);
		}catch(java.lang.NoClassDefFoundError e){
			e.printStackTrace();
			afking.put(player.getName(), null);
		}
	}

	/**
	 * プレイヤーのAFKを解除する
	 *
	 * @param player 解除するプレイヤー
	 * @author mine_book000
	 */
	public static void setAFK_False(Player player){
		if(afking.get(player.getName()) != null){
			afking.get(player.getName()).cancel();
		}
		afking.remove(player.getName());

		String message = player.getName() + " is now online!";
		if(Main.channels != null){
			for(IChannel channel : Main.channels){
				RequestBuffer.request(() -> {
					channel.sendMessage(message);
				});
			}
		}else{
			MessageQueue.Add(message);
		}
		Bukkit.broadcastMessage(ChatColor.DARK_GRAY + player.getName() + " is now online!");

		String listname = player.getPlayerListName().replaceAll(player.getName(), ChatColor.WHITE + player.getName());
		player.setPlayerListName(listname);

		if(loc.containsKey(player.getName())){
			player.teleport(loc.get(player.getName()));
			loc.remove(player.getName());
		}
	}

	/**
	 * プレイヤーがAFKかどうか調べる
	 *
	 * @param player 調べるプレイヤー
	 * @return AFKかどうか
	 * @author mine_book000
	 */
	public static boolean getAFKing(Player player){
		if(afking.containsKey(player.getName())){
			return true;
		}
		return false;
	}
}
