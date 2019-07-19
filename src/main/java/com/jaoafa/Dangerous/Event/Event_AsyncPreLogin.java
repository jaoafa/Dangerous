package com.jaoafa.Dangerous.Event;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result;
import org.bukkit.plugin.java.JavaPlugin;

import com.jaoafa.Dangerous.Lib.MySQL;

public class Event_AsyncPreLogin implements Listener {
	JavaPlugin plugin;
	public Event_AsyncPreLogin(JavaPlugin plugin) {
		this.plugin = plugin;
	}
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onAsyncPreLogin(AsyncPlayerPreLoginEvent event){
		String name = event.getName();
		UUID uuid = event.getUniqueId();

		try {
			PreparedStatement statement = MySQL.getNewPreparedStatement("SELECT * FROM 201908event_ban WHERE uuid = ?");
			statement.setString(1, uuid.toString());
			ResultSet res = statement.executeQuery();
			if(res.next()){
				// found
				String banned_by_user = res.getString("banned_by_user");
				String banned_by_server = res.getString("banned_by_server");
				String reason = res.getString("reason");
				String display_reason = res.getString("reason");
				int mcbans_banid = res.getInt("mcbans_banid"); // null eq 0
				if(mcbans_banid != 0){
					display_reason += "\n" + ChatColor.RESET + "https://www.mcbans.com/ban/" + mcbans_banid;
				}
				event.disallow(Result.KICK_BANNED,
						ChatColor.RED + "[Login Denied]\n"
								+ ChatColor.RESET + display_reason);
				for(Player p : Bukkit.getOnlinePlayers()){
					if(!p.isOp()){
						continue;
					}
					p.sendMessage("[Dangerous] " + name + " | REASON: " + reason);
					p.sendMessage("[Dangerous] " + name + " | BY: " + banned_by_user + " (" + banned_by_server + ")");
				}
				return;
			}
		} catch (ClassNotFoundException | SQLException e) {
			for(Player p : Bukkit.getOnlinePlayers()){
				if(!p.isOp()){
					continue;
				}
				p.sendMessage("[Dangerous] " + name + " | LoginCheckError: " + e.getMessage());
			}
		}
	}
}