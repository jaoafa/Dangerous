package com.jaoafa.Dangerous.Lib;

import org.bukkit.ChatColor;

public enum Servers {
	JAO(1, "jao Minecraft Server", ChatColor.YELLOW, 189377932429492224L),
	FRUIT(2, "FruitServer", ChatColor.BLUE, 239487519488475136L),
	TORO(3, "TORO Server", ChatColor.DARK_GRAY, 337838758441517057L),
	SABAMISO(4, "さばみそサーバー", ChatColor.GOLD, 334770123220975628L),
	UNKNOWN(-1, "UNKNOWN", ChatColor.BLACK, -1L);

	private final int id;
	private final String name;
	private final ChatColor color;
	private final long discordServer;
	private Servers(int id, String name, ChatColor color, long discordServer){
		this.id = id;
		this.name = name;
		this.color = color;
		this.discordServer = discordServer;
	}

	public int getID(){
		return id;
	}
	public String getName(){
		return name;
	}
	public ChatColor getColor(){
		return color;
	}
	public long getDiscordServer(){
		return discordServer;
	}
	static Servers getServer(int id){
		for(Servers server : Servers.values()){
			if(server.id == id) return server;
		}
		return null;
	}
	public static Servers getServerFromDiscordServer(long l){
		for(Servers server : Servers.values()){
			if(server.discordServer == l) return server;
		}
		return null;
	}
}
