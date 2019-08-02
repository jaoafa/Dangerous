package com.jaoafa.Dangerous.Event;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.jaoafa.Dangerous.Lib.JpManager;
import com.jaoafa.Dangerous.Lib.KatakanaToKanaConverter;


public class Event_Katakana implements Listener {
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onEvent_ChatMainServer(AsyncPlayerChatEvent event){
		Player player = event.getPlayer();
		String message = event.getMessage();
		String kanaTemp = ChatColor.stripColor(message);
		if(JpManager.isOFF(player.getUniqueId().toString())){
			return;
		}
        if (kanaTemp.getBytes().length > kanaTemp.length() ||
                        kanaTemp.matches("[ \\uFF61-\\uFF9F]+") ) {
            return;
        }
		String parsed = KatakanaToKanaConverter.conv(message);
		if(message.equals(".") || message.equals(parsed)){
			return;
		}
		event.setMessage(message + ChatColor.YELLOW + " (" + parsed + ")");
	}
}