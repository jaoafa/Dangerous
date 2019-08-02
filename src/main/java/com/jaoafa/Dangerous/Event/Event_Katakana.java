package com.jaoafa.Dangerous.Event;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.jaoafa.Dangerous.Lib.KatakanaToKanaConverter;


public class Event_Katakana implements Listener {
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onEvent_ChatMainServer(AsyncPlayerChatEvent event){
		String message = event.getMessage();
		String kanaTemp = ChatColor.stripColor(message);
		boolean skipJapanize = false; // 今後実装検討
        if ( !skipJapanize &&
                ( kanaTemp.getBytes().length > kanaTemp.length() ||
                        kanaTemp.matches("[ \\uFF61-\\uFF9F]+") ) ) {
            return;
        }
		String parsed = KatakanaToKanaConverter.conv(message);
		event.setMessage(message + ChatColor.YELLOW + " (" + parsed + ")");
	}
}