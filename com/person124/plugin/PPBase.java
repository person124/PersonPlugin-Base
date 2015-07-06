package com.person124.plugin;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.TileEntity;
import net.minecraft.server.v1_8_R3.TileEntityChest;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public abstract class PPBase implements Listener, CommandExecutor {
	protected PersonPlugins pp;

	private final String NAME;
	private boolean hasEvents = false, needsFolder = false;
	private String command;
	private TabCompleter completer;

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
	
	protected void setTabCompleter(TabCompleter tab) {
		completer = tab;
	}
	
	public boolean hasTabCompleter() {
		return completer != null;
	}
	
	public TabCompleter getTabCompleter() {
		return completer;
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

	public void setChestName(Location l, String name) {
		Block block = l.getBlock();
		if (block.getType() != Material.CHEST) {
			// Not a chest
			return;
		}

		// Get the NMS World
		net.minecraft.server.v1_8_R3.World nmsWorld = ((CraftWorld) block.getWorld()).getHandle();
		// Get the tile entity
		TileEntity te = nmsWorld.getTileEntity(new BlockPosition(block.getX(), block.getY(), block.getZ()));
		// Make sure it's a TileEntityChest before using it
		if (!(te instanceof TileEntityChest)) {
			// Not a chest :o!
			return;
		}
		((TileEntityChest) te).a(name);
	}
	
	public static String invName(String in) {
		if (in.length() <= 32) return in;
		return in.substring(0, 32);
	}

}
