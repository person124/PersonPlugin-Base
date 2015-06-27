package com.person124.plugin;

import org.bukkit.plugin.java.JavaPlugin;

public class PersonBase extends JavaPlugin {
	
	public void onEnable() {
		getLogger().info("has been enabled! Sub-plugins can now be used.");
	}
	
	public void onDisable() {
		getLogger().info("has been disabled.");
	}

}
