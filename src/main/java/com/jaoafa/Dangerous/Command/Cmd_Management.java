package com.jaoafa.Dangerous.Command;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.UnknownDependencyException;
import org.bukkit.plugin.java.JavaPlugin;

import com.jaoafa.Dangerous.FTPUploader;
import com.jaoafa.Dangerous.Main;
import com.jaoafa.Dangerous.PluginAdder;

public class Cmd_Management implements CommandExecutor {
	JavaPlugin plugin;
	public Cmd_Management(JavaPlugin plugin) {
		this.plugin = plugin;
	}
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender instanceof Player)){
			sender.sendMessage("[Dangerous] " + ChatColor.GREEN + "このコマンドはサーバ内から実行してください。");
			return true;
		}
		Player player = (Player) sender;
		if(!player.getUniqueId().toString().equals("32ff7cdc-a1b4-450a-aa7e-6af75fe8c37c") &&
				!player.getUniqueId().toString().equals("5799296a-d1ec-4252-93bd-440bb9caa65c")){
			sender.sendMessage("[Dangerous] " + ChatColor.GREEN + "あなたはこのコマンドを実行するための権限を持っていません。");
			return true;
		}
		/*
		 * /management upload <HOST> <USER> <PASS> <PATH>
		 * /management plugin add https://.../Dangerous.jar
		 * /management plugin remove Dangerous.jar
		 * /management plugin enable Dangerous
		 * /management plugin disable Dangerous
		 * /management plugin load Dangerous
		 */
		if(args.length == 5){
			if(args[0].equals("upload")){
				String hostname = args[1];
				String user = args[2];
				String pass = args[3];
				String path = args[4];
				new FTPUploader(player, hostname, user, pass, path).start();
				return true;
			}
		}if(args.length == 3){
			if(args[0].equals("plugin")){
				if(args[1].equals("download")){
					String url = args[2];
					new PluginAdder(url).start();
					return true;
				}else if(args[1].equals("delete")){
					try{
						String fileName = args[2];
						File jarfile = Main.getJarFile(Main.class);
						File file = new File(jarfile.getParentFile().getAbsoluteFile() + File.separator + fileName);
						if(!file.exists()){
							sender.sendMessage("[Dangerous] " + ChatColor.GREEN + "指定されたファイルが見つかりません。");
							return true;
						}
						if(file.delete()){
							sender.sendMessage("[Dangerous] " + ChatColor.GREEN + "指定されたファイル「" + file.getAbsolutePath() + "」を削除しました。");
							return true;
						}else{
							sender.sendMessage("[Dangerous] " + ChatColor.GREEN + "指定されたファイル「" + file.getAbsolutePath() + "」を削除できませんでした。");
							return true;
						}
					}catch(IOException | URISyntaxException e){
						sender.sendMessage("[Dangerous] " + ChatColor.GREEN + "Delete Error : " + e.getMessage());
						return true;
					}
				}else if(args[1].equals("enable")){
					String name = args[2];
					if(plugin.getServer().getPluginManager().isPluginEnabled(name)){
						sender.sendMessage("[Dangerous] " + ChatColor.GREEN + "指定されたプラグイン「" + name + "」は既に有効化されています。");
						return true;
					}
					Plugin _plugin = plugin.getServer().getPluginManager().getPlugin(name);
					if(_plugin == null){
						sender.sendMessage("[Dangerous] " + ChatColor.GREEN + "指定されたプラグイン「" + name + "」を見つけられません。");
						return true;
					}
					plugin.getServer().getPluginManager().enablePlugin(_plugin);
					sender.sendMessage("[Dangerous] " + ChatColor.GREEN + "指定されたプラグイン「" + name + "」を有効化しました。");
					return true;
				}else if(args[1].equals("disable")){
					String name = args[2];
					if(!plugin.getServer().getPluginManager().isPluginEnabled(name)){
						sender.sendMessage("[Dangerous] " + ChatColor.GREEN + "指定されたプラグイン「" + name + "」は既に無効化されています。");
						return true;
					}
					Plugin _plugin = plugin.getServer().getPluginManager().getPlugin(name);
					if(_plugin == null){
						sender.sendMessage("[Dangerous] " + ChatColor.GREEN + "指定されたプラグイン「" + name + "」を見つけられません。");
						return true;
					}
					plugin.getServer().getPluginManager().disablePlugin(_plugin);
					sender.sendMessage("[Dangerous] " + ChatColor.GREEN + "指定されたプラグイン「" + name + "」を無効化しました。");
					return true;
				}else if(args[1].equals("load")){
					try{
						String name = args[2];
						File file = new File("plugins" + File.separator + name + (name.endsWith(".jar") ? "" : ".jar"));
						if(!file.exists()){
							sender.sendMessage("[Dangerous] " + ChatColor.GREEN + "指定されたファイル「" + file.getAbsolutePath() + "」が見つかりません。");
							return true;
						}
						Plugin _plugin = plugin.getServer().getPluginManager().loadPlugin(file);
						if(_plugin == null){
							sender.sendMessage("[Dangerous] " + ChatColor.GREEN + "指定されたファイル「" + file.getAbsolutePath() + "」をロードできません。");
							return true;
						}
						if(plugin.getServer().getPluginManager().isPluginEnabled(_plugin)){
							sender.sendMessage("[Dangerous] " + ChatColor.GREEN + "指定されたプラグイン「" + _plugin.getName() + "」は既に有効化されています。");
							return true;
						}
						_plugin.onLoad();
						plugin.getServer().getPluginManager().enablePlugin(_plugin);
						sender.sendMessage("[Dangerous] " + ChatColor.GREEN + "指定されたプラグイン「" + _plugin.getName() + "」をロードし、有効化しました。");
						return true;
					}catch(UnknownDependencyException | InvalidPluginException | InvalidDescriptionException e){
						sender.sendMessage("[Dangerous] " + ChatColor.GREEN + "Load Error : " + e.getMessage());
						return true;
					}
				}
			}
		}
		sender.sendMessage("[Dangerous] " + "plugin download, plugin delete, plugin enable, plugin disable, plugin load, upload");
		return true;
	}
}
