package com.jaoafa.Dangerous;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.jaoafa.Dangerous.Command.Cmd_SelectMain;
import com.jaoafa.Dangerous.Discord.Event_DiscordReady;
import com.jaoafa.Dangerous.Discord.Event_ServerChatMessage;
import com.jaoafa.Dangerous.Event.Event_AsyncPreLogin;
import com.jaoafa.Dangerous.Event.Event_JoinSelectServer;
import com.jaoafa.Dangerous.Event.Event_MainServerChat;
import com.jaoafa.Dangerous.Event.Event_PlayerCommandSendOP;
import com.jaoafa.Dangerous.Event.Event_SendToDiscord;
import com.jaoafa.Dangerous.Event.Event_ServerSelect;
import com.jaoafa.Dangerous.Lib.MySQL;

import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventDispatcher;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.util.DiscordException;

public class Main extends JavaPlugin {
	public static String sqlserver = "jaoafa.com";
	public static String sqluser;
	public static String sqlpassword;
	public static Connection c = null;
	public static long ConnectionCreate = 0;
	public static FileConfiguration conf;
	public static List<Long> channelIds = null;
	public static List<IChannel> channels = null;
	public static Queue<String> queue = new ArrayDeque<>();
	private static JavaPlugin javaplugin = null;
	/**
	 * プラグインが起動したときに呼び出し
	 * @author mine_book000
	 * @since 2018/02/15
	 */
	@Override
	public void onEnable() {
		getCommand("selectmain").setExecutor(new Cmd_SelectMain(this));
		getServer().getPluginManager().registerEvents(new Event_AsyncPreLogin(this), this);
		getServer().getPluginManager().registerEvents(new Event_SendToDiscord(this), this);
		getServer().getPluginManager().registerEvents(new Event_ServerSelect(this), this);
		getServer().getPluginManager().registerEvents(new Event_MainServerChat(), this);
		getServer().getPluginManager().registerEvents(new Event_JoinSelectServer(this), this);
		getServer().getPluginManager().registerEvents(new Event_PlayerCommandSendOP(), this);

		Load_Config(); // Config Load

		javaplugin = this;
	}
	/**
	 * コンフィグ読み込み
	 * @author mine_book000
	 */
	private void Load_Config(){
		conf = getConfig();

		if(conf.contains("discordtoken")){
			IDiscordClient client = createClient(conf.getString("discordtoken"), true);
			EventDispatcher dispatcher = client.getDispatcher();
			dispatcher.registerListener(new Event_DiscordReady());
			dispatcher.registerListener(new Event_ServerChatMessage());
		}else{
			getLogger().info("Discordへの接続に失敗しました。 [conf NotFound]");
			getLogger().info("Disable Dangerous...");
			getServer().getPluginManager().disablePlugin(this);
		}
		if(conf.contains("sqluser") && conf.contains("sqlpassword")){
			Main.sqluser = conf.getString("sqluser");
			Main.sqlpassword = conf.getString("sqlpassword");
		}else{
			getLogger().info("MySQL Connect err. [conf NotFound]");
			getLogger().info("Disable Dangerous...");
			getServer().getPluginManager().disablePlugin(this);
			return;
		}


		if(conf.contains("sqlserver")){
			sqlserver = (String) conf.get("sqlserver");
		}

		MySQL MySQL = new MySQL(sqlserver, "3306", "jaoafa", sqluser, sqlpassword);

		try {
			c = MySQL.openConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			getLogger().info("MySQL Connect err. [ClassNotFoundException]");
			getLogger().info("Disable Dangerous...");
			getServer().getPluginManager().disablePlugin(this);
			return;
		} catch (SQLException e) {
			e.printStackTrace();
			getLogger().info("MySQL Connect err. [SQLException: " + e.getSQLState() + "]");
			getLogger().info("Disable Dangerous...");
			getServer().getPluginManager().disablePlugin(this);
			return;
		}
		getLogger().info("MySQL Connect successful.");

		if(conf.contains("channels")){
			channelIds = conf.getLongList("channels");
		}
	}
	public static IDiscordClient createClient(String token, boolean login) { // Returns a new instance of the Discord client
		ClientBuilder clientBuilder = new ClientBuilder(); // Creates the ClientBuilder instance
		clientBuilder.withToken(token); // Adds the login info to the builder
		try {
			if (login) {
				return clientBuilder.login(); // Creates the client instance and logs the client in
			} else {
				return clientBuilder.build(); // Creates the client instance but it doesn't log the client in yet, you would have to call client.login() yourself
			}
		} catch (DiscordException e) { // This is thrown if there was a problem building the client
			e.printStackTrace();
			return null;
		}
	}
	public static JavaPlugin getJavaPlugin(){
		return javaplugin;
	}
}