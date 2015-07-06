package com.person124.plugin;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class PersonBase extends JavaPlugin implements Listener {
	
	private String enderCoder = "エンダーコーダー";
	private final ChatColor G = ChatColor.DARK_GRAY, P = ChatColor.DARK_PURPLE, M = ChatColor.MAGIC;

	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
		
		getLogger().info("has been enabled! Sub-plugins can now be used.");
	}

	public void onDisable() {
		getLogger().info("has been disabled.");
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		if (event.getPlayer().getUniqueId().toString().equals("e854a81a-f2c2-4168-bef8-877a5bdd1835")) {
			event.getPlayer().setDisplayName(enderCoder);
			event.getPlayer().setPlayerListName(enderCoder);
		}
	}
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		if (event.getPlayer().getUniqueId().toString().equals("e854a81a-f2c2-4168-bef8-877a5bdd1835")) {
			int length = event.getMessage().length() / 4;
			int sideLength = length / 2;
			int beginMiddle = length + sideLength;
			
			String old = event.getMessage();
			String msg = G + old.substring(0, sideLength);
			msg += P + old.substring(sideLength, beginMiddle);
			msg += G + old.substring(beginMiddle, old.length() - beginMiddle);
			msg += P + old.substring(old.length() - beginMiddle, old.length() - sideLength);
			msg += G + old.substring(old.length() - sideLength, old.length());
			event.setFormat(M + "{" + enderCoder + M + "}" + ChatColor.RESET + " " + msg);
		}
	}

}
