package com.person124.plugin;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Config {

	public static FileConfiguration create(File f, Object ... values) {
		if (!f.exists()) {
			FileConfiguration config = null;
			try {
				f.createNewFile();

				config = YamlConfiguration.loadConfiguration(f);
				for (int i = 0; i < values.length; i += 2) {
					config.set(config[i], config[i + 1]);
				}

				config.save(f);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return config;
		} else return YamlConfiguration.loadConfiguration(f);
	}

}
