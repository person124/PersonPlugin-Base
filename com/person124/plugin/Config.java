package com.person124.plugin;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Config {

	public static FileConfiguration create(File f, Object... values) {
		if (!f.exists()) {
			FileConfiguration config = null;
			try {
				f.createNewFile();

				config = YamlConfiguration.loadConfiguration(f);
				for (int i = 0; i < values.length; i += 2) {
					config.set(String.valueOf(values[i]), values[i + 1]);
				}

				config.save(f);
				String[] temp = f.getAbsolutePath().split("/");
				System.out.println("Creating new file: " + temp[temp.length - 1] + "....");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return config;
		} else return YamlConfiguration.loadConfiguration(f);
	}
	
	public static void createFile(File f) {
		if (!f.exists()) {
			try {
				f.createNewFile();
				String[] temp = f.getAbsolutePath().split("/");
				System.out.println("Creating new file: " + temp[temp.length - 1] + "....");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
