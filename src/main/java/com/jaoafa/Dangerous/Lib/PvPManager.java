package com.jaoafa.Dangerous.Lib;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PvPManager {
	private static Map<UUID, Boolean> pvpflag = new HashMap<>();
	public static boolean isPvP(UUID uuid){
		if(pvpflag.containsKey(uuid)){
			boolean flag = pvpflag.get(uuid);
			return flag;
		}
		try {
			PreparedStatement statement = MySQL.getNewPreparedStatement("SELECT * FROM 201908event_pvp WHERE uuid = ?");
			statement.setString(1, uuid.toString());
			ResultSet res = statement.executeQuery();
			if(!res.next()){
				return false;
			}
			boolean flag = res.getBoolean("flag");
			pvpflag.put(uuid, flag);
			return flag;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	public static boolean isExists(UUID uuid){
		try {
			PreparedStatement statement = MySQL.getNewPreparedStatement("SELECT * FROM 201908event_pvp WHERE uuid = ?");
			statement.setString(1, uuid.toString());
			ResultSet res = statement.executeQuery();
			return res.next();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	public static void setPvPFlag(UUID uuid, boolean flag){
		try {
			if(!isExists(uuid)){
				// insert
				PreparedStatement statement = MySQL.getNewPreparedStatement("INSERT INTO 201908event_pvp (uuid, flag) VALUES (?, ?)");
				statement.setString(1, uuid.toString());
				statement.setBoolean(2, flag);
				statement.executeUpdate();
			}else{
				// update
				PreparedStatement statement = MySQL.getNewPreparedStatement("UPDATE 201908event_pvp SET flag = ? WHERE uuid = ?");
				statement.setBoolean(1, flag);
				statement.setString(2, uuid.toString());
				statement.executeUpdate();
			}
			pvpflag.put(uuid, flag);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return;
		}
	}
}
