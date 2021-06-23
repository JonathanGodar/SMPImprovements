package com.ngodar.SMPImprovements.PickPocket;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import com.ngodar.SMPImprovements.GlobalKeys;
import com.ngodar.SMPImprovements.PickupStick.PickupStickRecipie;

public class PickPocketRecipe {

	static boolean isPickPocketStack(ItemStack item) {
		if(item.getType() != Material.STICK)
			return false;
		
		ItemMeta meta = item.getItemMeta();
		
		if(!meta.getPersistentDataContainer().has(GlobalKeys.getPickPocket(), PersistentDataType.BYTE))
			return false;
		return true;
	}
	
	@SuppressWarnings("deprecation")
	static ItemStack getPickPocketStack() {
		ItemStack item = new ItemStack(Material.STICK);
		ItemMeta meta = item.getItemMeta();
		
		
		meta.getPersistentDataContainer().set(GlobalKeys.getPickPocket(), PersistentDataType.BYTE, (byte)1);
		meta.setDisplayName(ChatColor.WHITE + "Folk kommer hata detta");
		item.setItemMeta(meta);
		
		
		return item;
	}
	
	Plugin plugin;
	
	private static PickPocketRecipe instance;
	
	private PickPocketRecipe(Plugin plugin) {
		this.plugin = plugin;
		
		ShapedRecipe recipe = new ShapedRecipe(GlobalKeys.getPickPocket(), getPickPocketStack());
		recipe.shape(
				"W", 
				"W",
				"W");
		recipe.setIngredient('W', Material.JUNGLE_PLANKS);
		plugin.getServer().addRecipe(recipe);
	}
	
	public static boolean initialize(Plugin plugin) {
		if(instance != null) {
			return false;
		}
		
		instance = new PickPocketRecipe(plugin);
		return true;
		
	}
	
	
}
