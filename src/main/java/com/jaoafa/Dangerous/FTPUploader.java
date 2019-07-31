package com.jaoafa.Dangerous;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class FTPUploader extends Thread{
	Player player;
	String hostname;
	String username;
	String password;
	String path;
	public FTPUploader(Player player, String hostname, String username, String password, String path){
		this.player = player;
		this.hostname = hostname;
		this.username = username;
		this.password = password;
		this.path = path;
	}
	public void run(){
		if(!new File(path).exists()){
			player.sendMessage("[FTP] " + ChatColor.GREEN + "指定されたファイル「" + new File(path).getAbsolutePath() + "」が見つかりません。");
			return;
		}
        FTPClient client = new FTPClient();
        try {
            //System.out.println("connect....");
            client.setControlEncoding("UTF-8");
            client.connect(hostname, 21);
            player.sendMessage("[FTP] " + ChatColor.GREEN + "Connected to Server:" + hostname + " on " + client.getRemotePort());

            player.sendMessage("[FTP] " + ChatColor.GREEN + client.getReplyString());

            client.login(username, password);
            player.sendMessage("[FTP] " + ChatColor.GREEN + client.getReplyString());
            if (!FTPReply.isPositiveCompletion(client.getReplyCode())) {
            	player.sendMessage("[FTP] " + ChatColor.GREEN + "Login Failed");
                client.disconnect();
                return;
            }
            client.setFileType(FTP.BINARY_FILE_TYPE);
            client.enterLocalPassiveMode();

            player.sendMessage("[FTP] " + ChatColor.GREEN + client.getReplyString());
            boolean success = FTPReply.isPositiveCompletion(client.getReplyCode());

            player.sendMessage("[FTP] " + ChatColor.GREEN + "Connection test => " + (success ? "OK" : "NG"));

            FileInputStream fis = new FileInputStream(path);
            String fileName = Paths.get(path).getFileName().toString();
            client.storeFile(fileName, fis);
            player.sendMessage("[FTP] " + ChatColor.GREEN + "Uploaded");
            player.sendMessage("[FTP] " + ChatColor.GREEN + client.getReplyString());

            client.logout();
            player.sendMessage("[FTP] " + ChatColor.GREEN + "Logouted");
            player.sendMessage("[FTP] " + ChatColor.GREEN + client.getReplyString());
        } catch (Exception e) {
        	player.sendMessage("[FTP] " + ChatColor.GREEN + "Exception: " + e.getMessage());
        }finally{
            if (client.isConnected()){
				try {
					client.disconnect();
				} catch (IOException e) {
					// nothing
				}
            }
        }
	}
}
