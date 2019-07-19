package com.jaoafa.Dangerous.Lib;

import org.bukkit.ChatColor;

public enum Servers {
	JAO(1, "jao Minecraft Server", ChatColor.YELLOW),
	FRUIT(2, "FruitServer", ChatColor.BLUE),
	TORO(3, "TORO Server", ChatColor.BLACK),
	SABAMISO(4, "さばみそサーバー", ChatColor.GOLD),
	UNKNOWN(-1, "UNKNOWN", ChatColor.GRAY);

	private final int id;
	private final String name;
	private final ChatColor color;
	private Servers(int id, String name, ChatColor color){
		this.id = id;
		this.name = name;
		this.color = color;
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
	static Servers getServer(int id){
		for(Servers server : Servers.values()){
			if(server.id == id) return server;
		}
		return null;
	}
}
