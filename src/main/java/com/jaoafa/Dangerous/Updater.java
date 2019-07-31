package com.jaoafa.Dangerous;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Updater extends Thread{
	String address;
	public Updater(){
		address = "https://raw.githubusercontent.com/jaoafa/Dangerous/master/Dangerous-jar-with-dependencies.jar";
	}
	public Updater(String address){
		this.address = address;
	}
	public void run(){
		Main.Updating = true;
		try{
			for(Player player : Bukkit.getOnlinePlayers()){
				if(!player.isOp()){
					continue;
				}
				player.sendMessage("[Dangerous] " + "Update file downloading...");
			}
			System.out.println("[Dangerous] " + "Update file downloading...");

			File jarfile = Main.getJarFile(Main.class);
			long jarfileSize = jarfile.length();
			URL url = new URL(address);

			URLConnection conn = url.openConnection();
			InputStream in = conn.getInputStream();

			File file = new File(jarfile.getParentFile().getAbsoluteFile() + File.separator + "Dangerous.update.jar");
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

			file.renameTo(jarfile);

			if(file.exists()){
				file.delete();
			}
			for(Player player : Bukkit.getOnlinePlayers()){
				if(!player.isOp()){
					continue;
				}
				player.sendMessage("[Dangerous] " + "Updated : " + jarfileSize + " -> " + fileSize);
				if(jarfileSize == fileSize){
					player.sendMessage("[Dangerous] " + "(No changed filesize)");
				}
				player.sendMessage("[Dangerous] " + "Please Server Restart");
			}
			System.out.println("[Dangerous] " + "Updated : " + jarfileSize + " -> " + fileSize);
			if(jarfileSize == fileSize){
				System.out.println("[Dangerous] " + "(No changed filesize)");
			}
			System.out.println("[Dangerous] " + "Please Server Restart");

		}catch(URISyntaxException | IOException e){
			for(Player player : Bukkit.getOnlinePlayers()){
				if(!player.isOp()){
					continue;
				}
				player.sendMessage("[Dangerous] " + "Update err " + e.getMessage());
			}
			System.out.println("[Dangerous] " + "Update err " + e.getMessage());
			Main.Updating = false;
		}
	}
}
