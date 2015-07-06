package com.person124.plugin;

import java.io.File;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class PersonPlugins extends JavaPlugin {

	public static PersonPlugins instance;
	
	private final boolean CONFIGURABLE;
	private final PPBase[] SUB_PLUGINS;

	private File cfgFile;
	private FileConfiguration config;

	private boolean command = false;
	private String permission;

	public PersonPlugins(boolean config, PPBase... plugins) {
		CONFIGURABLE = config;
		SUB_PLUGINS = plugins;
		instance = this;
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
			if (isEnabled(ppb.getName())) {
				if (ppb.needsFolder()) createFolder();
				ppb.onActivated(this);
				if (ppb.hasEvents()) getServer().getPluginManager().registerEvents(ppb, this);
				if (ppb.hasCommand()) getCommand(ppb.getCommand()).setExecutor(ppb);
				if (ppb.hasTabCompleter()) getCommand(ppb.getCommand()).setTabCompleter(ppb.getTabCompleter());
			}
		}
	}

	public void onDisable() {
		for (PPBase ppb : SUB_PLUGINS) {
			if (isEnabled(ppb.getName())) ppb.onDeactivated();
		}
	}

	private void createFolder() {
		if (!getDataFolder().exists()) {
			getDataFolder().mkdir();
			getLogger().info("Creating plugin folder....");
		}
	}

	protected void hasCommand(String perm) {
		command = true;
		permission = perm;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (command) {
			if (label.equalsIgnoreCase(getName())) {
				if (sender instanceof Player) {
					Player p = (Player) sender;
					if (permission != null) {
						if (!p.hasPermission(permission)) {
							p.sendMessage(ChatColor.RED + "You don't have permission to use this command!");
							return true;
						}
					}

					if (args.length == 0) getHelp(p);
					else if (args.length == 1) {
						if (args[0].equalsIgnoreCase("list")) listSubPlugins(p);
						else if (args[0].equalsIgnoreCase("reload")) {
							onDisable();
							onEnable();
						} else getHelp(p);
					} else if (args.length == 2) {
						if (args[0].equalsIgnoreCase("reload")) reloadSub(p, args[1]);
						else getHelp(p);
					}
				} else {
					if (args.length == 0) getHelp(null);
					else if (args.length == 1) {
						if (args[0].equalsIgnoreCase("list")) listSubPlugins(null);
						else if (args[0].equalsIgnoreCase("reload")) {
							onDisable();
							onEnable();
						} else getHelp(null);
					} else if (args.length == 2) {
						if (args[0].equalsIgnoreCase("reload")) reloadSub(null, args[1]);
						else getHelp(null);
					}
				}
			}
		}
		return true;
	}

	private void listSubPlugins(Player p) {
		String s = "";
		for (PPBase plugin : SUB_PLUGINS) {
			s += plugin.getName() + ", ";
		}
		if (p != null) p.sendMessage(ChatColor.GOLD + "Sub-Plugins: " + s);
		else getLogger().info("Sub-Plugins: " + s);
	}

	private void reloadSub(Player p, String name) {
		for (PPBase plugin : SUB_PLUGINS) {
			if (plugin.getName().equalsIgnoreCase(name)) {
				if (isEnabled(plugin.getName())) {
					plugin.onDeactivated();
					plugin.onActivated(this);
					if (p != null) p.sendMessage(ChatColor.GOLD + "Sub-plugin: " + name + " has been reloaded.");
					else getLogger().info("Sub-plugin: " + name + " has been reloaded.");
				}
				return;
			}
		}
	}

	private void getHelp(Player p) {
		if (p != null) {
			p.sendMessage(ChatColor.GOLD + getHelp(0));
			p.sendMessage(ChatColor.GOLD + getHelp(1));
			p.sendMessage(ChatColor.GOLD + getHelp(2));
			p.sendMessage(ChatColor.GOLD + getHelp(3));
		} else {
			getLogger().info(getHelp(0));
			getLogger().info(getHelp(1));
			getLogger().info(getHelp(2));
			getLogger().info(getHelp(3));
		}
	}

	private String getHelp(int i) {
		String s = "NOPE.AVI!";
		switch (i) {
			case 0:
				s = "/" + getName().toLowerCase() + " | Shows this menu.";
				break;
			case 1:
				s = "/" + getName().toLowerCase() + " list | Lists all sub-plugins.";
				break;
			case 2:
				s = "/" + getName().toLowerCase() + " reload | Reloads all sub-plugins.";
				break;
			case 3:
				s = "/" + getName().toLowerCase() + " reload <plugin name> | Reloads specified sub-plugins.";
				break;
		}
		return s;
	}
	
	private boolean isEnabled(String name) {
		if (CONFIGURABLE) {
			return config.getBoolean(name);
		}
		return true;
	}
	
	public OfflinePlayer getOfflinePlayer(String name) {
		for (OfflinePlayer p : getServer().getOfflinePlayers()) {
			if (p.getName().equalsIgnoreCase(name)) return p;
		}
		return null;
	}

}
