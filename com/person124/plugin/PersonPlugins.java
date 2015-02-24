package com.person124.plugin;

import org.bukkit.plugin.java.JavaPlugin;

public class PersonPlugins extends JavaPlugin {
	
	private final PPBase[] SUB_PLUGINS;
	
	public PersonPlugins(PPBase... plugins) {
		SUB_PLUGINS = plugins;
	}
	
	public void onEnable() {
		for (PPBase ppb : SUB_PLUGINS) {
			if (ppb.needsFolder() && !getDataFolder().exists()) {
				getDataFolder().mkdir();
				getLogger().info("Creating plugin folder....");
			}
			ppb.onActivated(this);
			if (ppb.hasEvents()) getServer().getPluginManager().registerEvents(ppb, this);
			if (ppb.hasCommand()) getCommand(ppb.getCommand()).setExecutor(ppb);
		}
	}
	
	public void onDisable() {
		for (PPBase ppb : SUB_PLUGINS) {
			ppb.onDeactivated();
		}
	}
	
}
