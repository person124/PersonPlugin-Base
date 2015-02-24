package com.person124.plugin;

import java.lang.reflect.Field;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;

public class EnchantmentUtil {
	
	public static Enchantment createEnchantment(int id, final String NAME) {
		Enchantment ench = new Enchantment(id) {
			
			@Override
			public int getStartLevel() {
				return 1;
			}
			
			@Override
			public String getName() {
				return NAME;
			}
			
			@Override
			public int getMaxLevel() {
				return 2;
			}
			
			@Override
			public EnchantmentTarget getItemTarget() {
				return null;
			}
			
			@Override
			public boolean conflictsWith(Enchantment arg0) {
				return false;
			}
			
			@Override
			public boolean canEnchantItem(ItemStack arg0) {
				return true;
			}
		};
		return ench;
	}
	
	public static void registerEnchants(Enchantment... enchs) {
		try {
			try {
				Field f = Enchantment.class.getDeclaredField("acceptingNew");
				f.setAccessible(true);
				f.set(null, true);
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				for (Enchantment e : enchs) {
					Enchantment.registerEnchantment(e);
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
