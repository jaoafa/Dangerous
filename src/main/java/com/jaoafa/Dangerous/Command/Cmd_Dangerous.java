package com.jaoafa.Dangerous.Command;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.jaoafa.Dangerous.Main;
import com.jaoafa.Dangerous.Updater;
import com.jaoafa.Dangerous.Lib.MuteManager;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IUser;

public class Cmd_Dangerous implements CommandExecutor {
	JavaPlugin plugin;
	public Cmd_Dangerous(JavaPlugin plugin) {
		this.plugin = plugin;
	}
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender instanceof Player)){
			sender.sendMessage("[Dangerous] " + ChatColor.GREEN + "このコマンドはサーバ内から実行してください。");
			return true;
		}
		Player player = (Player) sender;
		if(!player.isOp()){
			sender.sendMessage("[Dangerous] " + ChatColor.GREEN + "あなたはこのコマンドを実行するための権限を持っていません。");
			return true;
		}
		// 送受信チャンネル制御・ユーザーミュート(送受信させない)他
		if(args.length == 3){
			if(args[0].equalsIgnoreCase("mute")){
				// ミュート連携関連
				if(args[1].equalsIgnoreCase("add")){
					String userid = args[2];
					long userlongid;
					try{
						userlongid = Long.valueOf(userid);
					}catch(NumberFormatException e){
						sender.sendMessage("[Dangerous] " + ChatColor.GREEN + "指定されたユーザーIDの形式が正しくありません。");
						return true;
					}
					IUser user = Main.getClient().fetchUser(userlongid);
					if(user == null){
						sender.sendMessage("[Dangerous] " + ChatColor.GREEN + "指定されたユーザーが見つかりません。強制的に追加する場合はforceaddを利用してください。");
						return true;
					}
					String name = user.getName();
					String discriminator = user.getDiscriminator();
					if(MuteManager.Exists(userid)){
						sender.sendMessage("[Dangerous] " + ChatColor.GREEN + "ユーザー「" + name + "#" + discriminator + "」(" + userid + ")は既にミュートリストに追加されています。");
						return true;
					}
					boolean bool = MuteManager.Add(userid);
					if(bool){
						sender.sendMessage("[Dangerous] " + ChatColor.GREEN + "ユーザー「" + name + "#" + discriminator + "」(" + userid + ")をミュートリストに追加しました。");
						return true;
					}else{
						sender.sendMessage("[Dangerous] " + ChatColor.GREEN + "ユーザー「" + name + "#" + discriminator + "」(" + userid + ")をミュートリストに追加できませんでした。");
						return true;
					}
				}else if(args[1].equalsIgnoreCase("forceadd")){
					String userid = args[2];
					long userlongid;
					try{
						userlongid = Long.valueOf(userid);
					}catch(NumberFormatException e){
						sender.sendMessage("[Dangerous] " + ChatColor.GREEN + "指定されたユーザーIDの形式が正しくありません。");
						return true;
					}
					if(MuteManager.Exists(userid)){
						sender.sendMessage("[Dangerous] " + ChatColor.GREEN + "ユーザー(" + userid + ")は既にミュートリストに追加されています。");
						return true;
					}
					boolean bool = MuteManager.Add(userid);
					if(bool){
						sender.sendMessage("[Dangerous] " + ChatColor.GREEN + "ユーザー(" + userid + ")をミュートリストに追加しました。");
						return true;
					}else{
						sender.sendMessage("[Dangerous] " + ChatColor.GREEN + "ユーザー(" + userid + ")をミュートリストに追加できませんでした。");
						return true;
					}
				}else if(args[1].equalsIgnoreCase("remove")){
					String userid = args[2];
					long userlongid;
					try{
						userlongid = Long.valueOf(userid);
					}catch(NumberFormatException e){
						sender.sendMessage("[Dangerous] " + ChatColor.GREEN + "指定されたユーザーIDの形式が正しくありません。");
						return true;
					}
					IUser user = Main.getClient().fetchUser(userlongid);
					if(user == null){
						sender.sendMessage("[Dangerous] " + ChatColor.GREEN + "指定されたユーザーが見つかりません。強制的に削除する場合はforceremoveを利用してください。");
						return true;
					}
					String name = user.getName();
					String discriminator = user.getDiscriminator();
					if(!MuteManager.Exists(userid)){
						sender.sendMessage("[Dangerous] " + ChatColor.GREEN + "ユーザー「" + name + "#" + discriminator + "」(" + userid + ")はミュートリストに追加されていません。");
						return true;
					}
					boolean bool = MuteManager.Remove(userid);
					if(bool){
						sender.sendMessage("[Dangerous] " + ChatColor.GREEN + "ユーザー「" + name + "#" + discriminator + "」(" + userid + ")をミュートリストから削除しました。");
						return true;
					}else{
						sender.sendMessage("[Dangerous] " + ChatColor.GREEN + "ユーザー「" + name + "#" + discriminator + "」(" + userid + ")をミュートリストから削除できませんでした。");
						return true;
					}
				}else if(args[1].equalsIgnoreCase("forceremove")){
					String userid = args[2];
					long userlongid;
					try{
						userlongid = Long.valueOf(userid);
					}catch(NumberFormatException e){
						sender.sendMessage("[Dangerous] " + ChatColor.GREEN + "指定されたユーザーIDの形式が正しくありません。");
						return true;
					}
					if(!MuteManager.Exists(userid)){
						sender.sendMessage("[Dangerous] " + ChatColor.GREEN + "ユーザー(" + userid + ")はミュートリストに追加されていません。");
						return true;
					}
					boolean bool = MuteManager.Remove(userid);
					if(bool){
						sender.sendMessage("[Dangerous] " + ChatColor.GREEN + "ユーザー(" + userid + ")をミュートリストから削除しました。");
						return true;
					}else{
						sender.sendMessage("[Dangerous] " + ChatColor.GREEN + "ユーザー(" + userid + ")をミュートリストから削除できませんでした。");
						return true;
					}
				}
			}else if(args[0].equalsIgnoreCase("channel")){
				if(args[1].equalsIgnoreCase("add")){
					long channelID;
					try{
						channelID = Long.valueOf(args[2]);
					}catch(NumberFormatException e){
						sender.sendMessage("[Dangerous] " + ChatColor.GREEN + "指定されたチャンネルIDの形式が正しくありません。");
						return true;
					}
					IChannel channel = Main.getClient().getChannelByID(channelID);
					if(channel == null){
						sender.sendMessage("[Dangerous] " + ChatColor.GREEN + "指定されたチャンネルが見つかりません。");
					}
					String name = channel.getName();
					String guildName = channel.getGuild().getName();
					FileConfiguration conf = plugin.getConfig();
					Main.channelIds = conf.getLongList("channels");
					Main.channelIds.add(channelID);
					Main.channels.add(channel);
					conf.set("channels", Main.channelIds);
					try {
						conf.save(plugin.getDataFolder() + File.separator + "config.yml");
						sender.sendMessage("[Dangerous] " + ChatColor.GREEN + "チャンネル「" + name + " (" + guildName + ")」を追加し、保存しました。");
						return true;
					} catch (IOException e) {
						sender.sendMessage("[Dangerous] " + ChatColor.GREEN + "チャンネル「" + name + " (" + guildName + ")」を追加しましたが、保存できませんでした。");
						return true;
					}
				}else if(args[1].equalsIgnoreCase("remove")){
					long channelID;
					try{
						channelID = Long.valueOf(args[2]);
					}catch(NumberFormatException e){
						sender.sendMessage("[Dangerous] " + ChatColor.GREEN + "指定されたチャンネルIDの形式が正しくありません。");
						return true;
					}
					IChannel channel = Main.getClient().getChannelByID(channelID);
					if(channel == null){
						sender.sendMessage("[Dangerous] " + ChatColor.GREEN + "指定されたチャンネルが見つかりません。");
					}
					String name = channel.getName();
					String guildName = channel.getGuild().getName();
					FileConfiguration conf = plugin.getConfig();
					Main.channelIds = conf.getLongList("channels");
					if(!Main.channelIds.contains(channelID)){
						sender.sendMessage("[Dangerous] " + ChatColor.GREEN + "指定されたチャンネルは送受信対象チャンネルではありません。");
						return true;
					}
					Main.channelIds.remove(channelID);
					if(Main.channels.contains(channel)) Main.channels.remove(channel);
					conf.set("channels", Main.channelIds);
					try {
						conf.save(plugin.getDataFolder() + File.separator + "config.yml");
						sender.sendMessage("[Dangerous] " + ChatColor.GREEN + "チャンネル「" + name + " (" + guildName + ")」を削除し、保存しました。");
						return true;
					} catch (IOException e) {
						sender.sendMessage("[Dangerous] " + ChatColor.GREEN + "チャンネル「" + name + " (" + guildName + ")」を削除しましたが、保存できませんでした。");
						return true;
					}
				}else if(args[1].equalsIgnoreCase("forceremove")){
					long channelID;
					try{
						channelID = Long.valueOf(args[2]);
					}catch(NumberFormatException e){
						sender.sendMessage("[Dangerous] " + ChatColor.GREEN + "指定されたチャンネルIDの形式が正しくありません。");
						return true;
					}
					FileConfiguration conf = plugin.getConfig();
					Main.channelIds = conf.getLongList("channels");
					if(!Main.channelIds.contains(channelID)){
						sender.sendMessage("[Dangerous] " + ChatColor.GREEN + "指定されたチャンネルは送受信対象チャンネルではありません。");
						return true;
					}
					Main.channelIds.remove(channelID);
					conf.set("channels", Main.channelIds);
					try {
						conf.save(plugin.getDataFolder() + File.separator + "config.yml");
						sender.sendMessage("[Dangerous] " + ChatColor.GREEN + "チャンネル(" + channelID + ")を削除し、保存しました。");
						return true;
					} catch (IOException e) {
						sender.sendMessage("[Dangerous] " + ChatColor.GREEN + "チャンネル(" + channelID + ")を削除しましたが、保存できませんでした。");
						return true;
					}
				}
			}
		}else if(args.length == 2){
			if(args[0].equalsIgnoreCase("mute")){
				// ミュート連携関連
				if(args[1].equalsIgnoreCase("list")){
					Set<String> mute_players = new HashSet<>();
					List<String> mutes = MuteManager.loadMutes();
					sender.sendMessage("[Dangerous] " + ChatColor.GREEN + "ミュートされているユーザーは以下の通りです。");
					for(String userid : mutes){
						long userlongid;
						try{
							userlongid = Long.valueOf(userid);
						}catch(NumberFormatException e){
							mute_players.add(userid);
							continue;
						}
						IUser user = Main.getClient().fetchUser(userlongid);
						if(user == null){
							mute_players.add(userid);
							continue;
						}
						String name = user.getName();
						String discriminator = user.getDiscriminator();
						mute_players.add(name + "#" + discriminator + " (" + userid + ")");
					}
					String users = String.join(", ", mute_players);
					sender.sendMessage("[Dangerous] " + ChatColor.GREEN + users);
					return true;
				}
			}else if(args[0].equalsIgnoreCase("channel")){
				// ミュート連携関連
				if(args[1].equalsIgnoreCase("list")){
					Set<String> strchannels = new HashSet<>();
					List<IChannel> channels = Main.channels;
					sender.sendMessage("[Dangerous] " + ChatColor.GREEN + "送受信対象のチャンネルは以下の通りです。");
					for(IChannel channel : channels){
						strchannels.add(channel.getName() + "(" + channel.getGuild().getName() + ")");
					}
					String allchannels = String.join(", ", strchannels);
					sender.sendMessage("[Dangerous] " + ChatColor.GREEN + allchannels);
					return true;
				}
			}
		}else if(args.length == 1){
			if(args[0].equalsIgnoreCase("update")){
				// Dangerousプラグインアップデート
				sender.sendMessage("[Dangerous] " + "test100000");
				new Updater().start();
				return true;
			}
		}
		sender.sendMessage("[Dangerous] " + "mute add, mute remove, mute forceadd, mute forceremove, mute list, channel add, channel add, channel remove, channel forceremove, channel list, update");
		return true;
	}

}
