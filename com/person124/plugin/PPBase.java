package com.person124.plugin;

import org.bukkit.Material;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public abstract class PPBase implements Listener, CommandExecutor {
	protected PersonPlugins pp;

	private final String NAME;
	private boolean hasEvents = false, needsFolder = false;
	private String command;

	public PPBase(String name) {
		this.NAME = name;
	}

	public void onActivated(PersonPlugins personPlugins) {
		pp = personPlugins;
		pp.getLogger().info("PersonPlugin: " + NAME + " is enabling....");
		onEnable();
		pp.getLogger().info("PersonPlugin: " + NAME + " has been enabled.");
	}

	public void onDeactivated() {
		pp.getLogger().info("PersonPlugin: " + NAME + " is disabling....");
		onDisable();
		pp.getLogger().info("PersonPlugin: " + NAME + " has been disabled.");
	}

	protected void onEnable() {}

	protected void onDisable() {}

	public String getName() {
		return NAME;
	}

	protected void setHasEvents() {
		hasEvents = true;
	}

	public boolean hasEvents() {
		return hasEvents;
	}

	protected void setNeedsFolder() {
		needsFolder = true;
	}

	public boolean needsFolder() {
		return needsFolder;
	}

	protected void setCommand(String name) {
		command = name;
	}

	public boolean hasCommand() {
		return command != null;
	}

	public String getCommand() {
		return command;
	}

	protected void addRecipe(ItemStack result, Object[] craft) {
		ShapedRecipe recipe = new ShapedRecipe(result);
		String[] matrix = new String[3];
		int m = 0;
		char last = 'n';
		for (int i = 0; i < craft.length; i++) {
			Object o = craft[i];
			if (o instanceof String) {
				matrix[m] = String.valueOf(o);
				if (m == 2) recipe.shape(matrix);
				m++;
			} else if (o instanceof Character) last = (char) o;
			else if (o instanceof Material) {
				recipe.setIngredient(last, (Material) o);
			}
		}
		pp.getServer().addRecipe(recipe);
	}

}
