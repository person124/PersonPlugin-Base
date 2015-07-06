package com.person124.plugin;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public abstract class PPTabCompleter implements TabCompleter {
	
	protected List<String> getList(String arg, String... str) {
		List<String> list = new ArrayList<String>();
		for (String s : str) {
			if (s.startsWith(arg)) list.add(s);
		}
		return list;
	}
	
	protected List<String> getOnlinePlayers(String arg) {
		List<String> list = new ArrayList<String>();
		for (Player p : PersonPlugins.instance.getServer().getOnlinePlayers()) {
			if (p.getDisplayName().startsWith(arg)) list.add(p.getDisplayName());
		}
		return list;
	}
	
	protected List<String> getAllPlayers(String arg) {
		List<String> list = new ArrayList<String>();
		for (OfflinePlayer p : PersonPlugins.instance.getServer().getOfflinePlayers()) {
			if (p.getName().startsWith(arg)) list.add(p.getName());
		}
		for (Player p : PersonPlugins.instance.getServer().getOnlinePlayers()) {
			if (p.getDisplayName().startsWith(arg)) list.add(p.getDisplayName());
		}
		return list;
	}

}
