package com.jaoafa.Dangerous;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Updater extends Thread{
	public void run(){
		try{
			for(Player player : Bukkit.getOnlinePlayers()){
				if(!player.isOp()){
					continue;
				}
				player.sendMessage("[Dangerous] " + "Update file downloading...");
			}
			System.out.println("[Dangerous] " + "Update file downloading...");

			File jarfile = getJarFile(Main.class);
			long jarfileSize = jarfile.length();
			URL url = new URL("https://raw.githubusercontent.com/jaoafa/Dangerous/master/Dangerous-jar-with-dependencies.jar");

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
				player.sendMessage("[Dangerous] " + "Please Server Restart");
			}
			System.out.println("[Dangerous] " + "Updated : " + jarfileSize + " -> " + fileSize);
			System.out.println("[Dangerous] " + "Please Server Restart");

		}catch(URISyntaxException | IOException e){
			for(Player player : Bukkit.getOnlinePlayers()){
				if(!player.isOp()){
					continue;
				}
				player.sendMessage("[Dangerous] " + "Update err " + e.getMessage());
			}
			System.out.println("[Dangerous] " + "Update err " + e.getMessage());
		}
	}
	File getJarFile(Class<?> clazz) throws URISyntaxException, MalformedURLException {
        URL url = clazz.getProtectionDomain().getCodeSource().getLocation();
        return new File(new URL(url.toURI().toString().split("\\!")[0].replaceAll("jar:file", "file")).toURI().getPath());
    }
}