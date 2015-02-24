package com.person124.plugin;

import java.io.File;

import org.bukkit.plugin.configuration.file.FileConfiguration;
import org.bukkit.plugin.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class PersonPlugins extends JavaPlugin {
	
	private final boolean CONFIGURABLE;
	private final PPBase[] SUB_PLUGINS;

	private File cfgFile;
	private FileConfiguration config;
	
	public PersonPlugins(boolean config, PPBase... plugins) {
		CONFIGURABLE = config
		SUB_PLUGINS = plugins;
	}
	
	public void onEnable() {
		if (CONFIGURABLE) {
			createFolder();
			cfgFile = new File(getDataFolder(), getName() + ".prsn");
			String[] strs = new String[plugins.length];
			for (int i = 0; i < strs.length * 2; i++) {
				strs[i] = plugins[i].getName();
				strs[i + 1] = true;
			}
			config = Config.createConfig(cfgFile, strs);
		}
		for (PPBase ppb : SUB_PLUGINS) {
			if (config.getBoolean(ppb.getName()) {
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
