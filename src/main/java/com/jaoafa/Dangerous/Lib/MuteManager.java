package com.jaoafa.Dangerous.Lib;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * ミュート機能の制御
 * @author Tomachi
 */
public class MuteManager {
	static JavaPlugin plugin;
	static File file;
	public static void start(JavaPlugin plugin) {
		MuteManager.plugin = plugin;

		String path = "mutes.yml";
		File file = new File(path);
		if(!file.exists()){
			Bukkit.getLogger().info("「" + path + "」が存在しません。作成します。");
			List<String> mutes = new ArrayList<>();
			saveMutes(file, mutes);

			MuteManager.file = file;
		}else if(file.exists() && file.canRead() && file.canWrite()){
			Bukkit.getLogger().info("「" + path + "」に読み込み・書き込み権限がありました。続行します。");
			MuteManager.file = file;
		}else{
			Bukkit.getLogger().info("「" + path + "」に対する操作ができません。機能を無効化します。");
			MuteManager.file = null;
		}
	}
	public static boolean Add(String id) {
		List<String> mutes = loadMutes();
		if(mutes == null) return false;
		if(mutes.contains(id)){
			return false;
		}
		mutes.add(id);
		return saveMutes(mutes);
	}
	public static boolean Exists(String id) {
		List<String> mutes = loadMutes();
		if(mutes == null) return false;
		return mutes.contains(id);
	}

	public static boolean Remove(String id) {
		List<String> mutes = loadMutes();
		if(mutes == null) return false;
		if(!mutes.contains(id)) {
			// not found
			return false;
		}
		mutes.remove(id);
		return saveMutes(mutes);
	}


	public static boolean saveMutes(List<String> mutes) {
		return saveMutes(file, mutes);
	}

	public static boolean saveMutes(File file, List<String> mutes) {
		if(file == null) return false;
		FileConfiguration c = YamlConfiguration.loadConfiguration(file);
		c.set("mutes", mutes);
		try {
			c.save(file);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static List<String> loadMutes() {
		if(file == null) return null;
		if(!file.exists()) return null;
		FileConfiguration c = YamlConfiguration.loadConfiguration(file);
		if(!(c.contains("mutes"))){
			return null;
		}
		List<String> mutes = c.getStringList("mutes");
		return mutes;
	}
}