package com.jaoafa.Dangerous.Lib;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MainServerManager {
	private static Map<UUID, Integer> mainservers = new HashMap<>();
	public static Servers getMainServer(UUID uuid){
		if(mainservers.containsKey(uuid)){
			int id = mainservers.get(uuid);
			return Servers.getServer(id);
		}
		try {
			PreparedStatement statement = MySQL.getNewPreparedStatement("SELECT * FROM 201908event_mainserver WHERE uuid = ?");
			statement.setString(1, uuid.toString());
			ResultSet res = statement.executeQuery();
			if(!res.next()){
				return null;
			}
			int id = res.getInt("mainserver");
			mainservers.put(uuid, id);
			return Servers.getServer(id);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	public static void setMainServer(UUID uuid, Servers server){
		try {
			if(getMainServer(uuid) == null || getMainServer(uuid) == Servers.UNKNOWN){
				// insert
				PreparedStatement statement = MySQL.getNewPreparedStatement("INSERT INTO 201908event_mainserver (uuid, mainserver) VALUES (?, ?)");
				statement.setString(1, uuid.toString());
				statement.setInt(2, server.getID());
				statement.executeUpdate();
			}else{
				// update
				PreparedStatement statement = MySQL.getNewPreparedStatement("UPDATE 201908event_mainserver SET mainserver = ? WHERE uuid = ?");
				statement.setInt(1, server.getID());
				statement.setString(2, uuid.toString());
				statement.executeUpdate();
			}
			mainservers.put(uuid, server.getID());
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return;
		}
	}
}
