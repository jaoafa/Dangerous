package com.jaoafa.Dangerous;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Paths;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PluginAdder extends Thread{
	String address;
	public PluginAdder(String address){
		this.address = address;
	}
	public void run(){
		try{
			URL url = new URL(address);
			String fileName = Paths.get(url.getPath()).getFileName().toString();
			for(Player player : Bukkit.getOnlinePlayers()){
				if(!player.isOp()){
					continue;
				}
				player.sendMessage("[Dangerous] " + "\"" + fileName + "\" downloading...");
			}
			System.out.println("[Dangerous] " + "\"" + fileName + "\" downloading...");
			File jarfile = Main.getJarFile(Main.class);
			File file = new File(jarfile.getParentFile().getAbsoluteFile() + File.separator + fileName);

			URLConnection conn = url.openConnection();
			InputStream in = conn.getInputStream();

			if(file.exists()){
				file.delete();
			}
			FileOutputStream out = new FileOutputStream(file, false);
			int b;
			while((b = in.read()) != -1){
				out.write(b);
			}

			out.close();
			in.close();
			long fileSize = file.length();
			for(Player player : Bukkit.getOnlinePlayers()){
				if(!player.isOp()){
					continue;
				}
				player.sendMessage("[Dangerous] " + "Downloaded : " + fileName + " / " + fileSize + " bytes");
				player.sendMessage("[Dangerous] " + "Please Server Restart");
			}
			System.out.println("[Dangerous] " + "Downloaded : " + fileName + " / " + fileSize + " bytes");
			System.out.println("[Dangerous] " + "Please Server Restart");
		}catch(URISyntaxException | IOException e){
			for(Player player : Bukkit.getOnlinePlayers()){
				if(!player.isOp()){
					continue;
				}
				player.sendMessage("[Dangerous] " + "Download err " + e.getMessage());
			}
			System.out.println("[Dangerous] " + "Download err " + e.getMessage());
			Main.Updating = false;
		}
	}

}
