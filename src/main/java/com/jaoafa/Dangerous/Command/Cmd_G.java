package com.jaoafa.Dangerous.Command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Cmd_G implements CommandExecutor {
	JavaPlugin plugin;
	public Cmd_G(JavaPlugin plugin) {
		this.plugin = plugin;
	}
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender instanceof Player)){
			sender.sendMessage("[G] " + ChatColor.GREEN + "このコマンドはサーバ内から実行してください。");
			return true;
		}
		Player player = (Player) sender;
		if(!player.isOp()){
			// is not OP
			sender.sendMessage("[G] " + ChatColor.GREEN + "あなたはこのコマンドを実行するための権限を持っていません。");
			return true;
		}
		if(args.length == 0){
			if (!(sender instanceof Player)) {
				sender.sendMessage("[G] " + ChatColor.GREEN + "このコマンドはゲーム内から実行してください。");
				return true;
			}
			GameMode beforeGameMode = player.getGameMode();

			if(player.getGameMode() == GameMode.SPECTATOR){
				// スペクテイターならクリエイティブにする
				player.setGameMode(GameMode.CREATIVE);
				if(player.getGameMode() != GameMode.CREATIVE){
					sender.sendMessage("[G] " + ChatColor.GREEN + "ゲームモードの変更ができませんでした。");
					return true;
				}
				sender.sendMessage("[G] " + ChatColor.GREEN + beforeGameMode.name() + " -> " + GameMode.CREATIVE.name());
				return true;
			}else if(player.getGameMode() == GameMode.CREATIVE){
				// クリエイティブならスペクテイターにする
				player.setGameMode(GameMode.SPECTATOR);
				if(player.getGameMode() != GameMode.SPECTATOR){
					sender.sendMessage("[G] " + ChatColor.GREEN + "ゲームモードの変更ができませんでした。");
					return true;
				}
				sender.sendMessage("[G] " + ChatColor.GREEN + beforeGameMode.name() + " -> " + GameMode.SPECTATOR.name());
				return true;
			}else{
				// それ以外(サバイバル・アドベンチャー)ならクリエイティブにする
				player.setGameMode(GameMode.CREATIVE);
				if(player.getGameMode() != GameMode.CREATIVE){
					sender.sendMessage("[G] " + ChatColor.GREEN + "ゲームモードの変更ができませんでした。");
					return true;
				}
				sender.sendMessage("[G] " + ChatColor.GREEN + beforeGameMode.name() + " -> " + GameMode.CREATIVE.name());
				return true;
			}
		}else if(args.length == 1){
			if (!(sender instanceof Player)) {
				sender.sendMessage("[G] " + ChatColor.GREEN + "このコマンドはゲーム内から実行してください。");
				return true;
			}
			Player _player = (Player) sender;
			GameMode beforeGameMode = _player.getGameMode();

			int i;
			try{
				i = Integer.parseInt(args[0]);
			}catch(NumberFormatException e){
				sender.sendMessage("[G] " + ChatColor.GREEN + "引数には数値を指定してください。");
				return true;
			}

			GameMode gm = GameMode.getByValue(i);
			if(gm == null){
				sender.sendMessage("[G] " + ChatColor.GREEN + "指定された引数からゲームモードが取得できませんでした。");
				return true;
			}

			_player.setGameMode(gm);
			if(_player.getGameMode() != gm){
				sender.sendMessage("[G] " + ChatColor.GREEN + "ゲームモードの変更ができませんでした。");
				return true;
			}
			sender.sendMessage("[G] " + ChatColor.GREEN + beforeGameMode.name() + " -> " + gm.name());
			return true;
		}else if(args.length == 2){
			int i;
			try{
				i = Integer.parseInt(args[0]);
			}catch(NumberFormatException e){
				sender.sendMessage("[G] " + ChatColor.GREEN + "引数には数値を指定してください。");
				return true;
			}

			GameMode gm = GameMode.getByValue(i);
			if(gm == null){
				sender.sendMessage("[G] " + ChatColor.GREEN + "指定された引数からゲームモードが取得できませんでした。");
				return true;
			}

			String playername = args[1];
			Player _player = Bukkit.getPlayerExact(playername);
			if(_player == null){
				sender.sendMessage("[G] " + ChatColor.GREEN + "指定されたプレイヤー「" + playername + "」は見つかりませんでした。");

				Player any_chance_player = Bukkit.getPlayer(playername);
				if(any_chance_player != null){
					sender.sendMessage("[G] " + ChatColor.GREEN + "もしかして: " + any_chance_player.getName());
				}
				return true;
			}

			GameMode beforeGameMode = _player.getGameMode();

			_player.setGameMode(gm);
			if(_player.getGameMode() != gm){
				sender.sendMessage("[G] " + ChatColor.GREEN + "ゲームモードの変更ができませんでした。");
				return true;
			}
			sender.sendMessage("[G] " + ChatColor.GREEN + player.getName() + ": " + beforeGameMode.name() + " -> " + gm.name());
			return true;
		}
		sender.sendMessage("[G] " + ChatColor.GREEN + "/g: クリエイティブモードならスペクテイターモードに、スペクテイターモードならクリエイティブモードに、それ以外ならクリエイティブモードに変更します。");
		sender.sendMessage("[G] " + ChatColor.GREEN + "/g <0-3>: 指定された数値に合うゲームモードに変更します。");
		sender.sendMessage("[G] " + ChatColor.GREEN + "/g <0-3> <Player>: 指定したプレイヤーのゲームモードを指定された数値に合うゲームモードに変更します。");
		return true;
	}

}
