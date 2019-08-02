package com.jaoafa.Dangerous.Lib;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

// listにあればoff
public class JpManager {
	static JavaPlugin plugin;
	static File file;
	public static void start(JavaPlugin plugin) {
		JpManager.plugin = plugin;

		String path = "jp.yml";
		File file = new File(path);
		if(!file.exists()){
			Bukkit.getLogger().info("「" + path + "」が存在しません。作成します。");
			List<String> jps = new ArrayList<>();
			saveJp(file, jps);

			JpManager.file = file;
		}else if(file.exists() && file.canRead() && file.canWrite()){
			Bukkit.getLogger().info("「" + path + "」に読み込み・書き込み権限がありました。続行します。");
			JpManager.file = file;
		}else{
			Bukkit.getLogger().info("「" + path + "」に対する操作ができません。機能を無効化します。");
			JpManager.file = null;
		}
	}
	public static boolean OFF(String id) {
		List<String> jps = loadJp();
		if(jps == null) return false;
		if(jps.contains(id)){
			return false;
		}
		jps.add(id);
		return saveJp(jps);
	}
	public static boolean isOFF(String id) {
		List<String> jps = loadJp();
		if(jps == null) return false;
		return jps.contains(id);
	}

	public static boolean ON(String id) {
		List<String> jps = loadJp();
		if(jps == null) return false;
		if(!jps.contains(id)) {
			// not found
			return false;
		}
		jps.remove(id);
		return saveJp(jps);
	}


	public static boolean saveJp(List<String> jps) {
		return saveJp(file, jps);
	}

	public static boolean saveJp(File file, List<String> jps) {
		if(file == null) return false;
		FileConfiguration c = YamlConfiguration.loadConfiguration(file);
		c.set("jps", jps);
		try {
			c.save(file);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static List<String> loadJp() {
		if(file == null) return null;
		if(!file.exists()) return null;
		FileConfiguration c = YamlConfiguration.loadConfiguration(file);
		if(!(c.contains("jps"))){
			return null;
		}
		List<String> jps = c.getStringList("jps");
		return jps;
	}
}