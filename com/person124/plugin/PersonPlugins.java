package com.person124.plugin;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class PersonPlugins extends JavaPlugin {
	
	private final boolean CONFIGURABLE;
	private final PPBase[] SUB_PLUGINS;

	private File cfgFile;
	private FileConfiguration config;
	
	public PersonPlugins(boolean config, PPBase... plugins) {
		CONFIGURABLE = config;
		SUB_PLUGINS = plugins;
	}
	
	public void onEnable() {
		if (CONFIGURABLE) {
			createFolder();
			cfgFile = new File(getDataFolder(), getName() + ".prsn");
			Object[] strs = new Object[SUB_PLUGINS.length * 2];
			for (int i = 0; i < SUB_PLUGINS.length; i++) {
				strs[i * 2] = SUB_PLUGINS[i].getName();
				strs[(i * 2) + 1] = true;
			}
			config = Config.create(cfgFile, strs);
		}
		for (PPBase ppb : SUB_PLUGINS) {
			if (config.getBoolean(ppb.getName())) {
				if (ppb.needsFolder()) createFolder();
				ppb.onActivated(this);
				if (ppb.hasEvents()) getServer().getPluginManager().registerEvents(ppb, this);
				if (ppb.hasCommand()) getCommand(ppb.getCommand()).setExecutor(ppb);
			}
		}
	}
	
	public void onDisable() {
		for (PPBase ppb : SUB_PLUGINS) {
			if (config.getBoolean(ppb.getName()))ppb.onDeactivated();
		}
	}

	private void createFolder() {
		if (!getDataFolder().exists()) {
			getDataFolder().mkdir();
			getLogger().info("Creating plugin folder....");
		}
	}
	
}
