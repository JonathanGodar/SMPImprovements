package com.ngodar.SMPImprovements.PickupStick;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import com.ngodar.SMPImprovements.GlobalKeys;

import net.md_5.bungee.api.ChatColor;

public class PickupStickRecipie {
	
	private static PickupStickRecipie instance;
	
	public static final Material StickMaterial = Material.WOODEN_SHOVEL;
	
	Plugin plugin;
	
	private PickupStickRecipie(Plugin plugin) {
		this.plugin = plugin;
		
		ShapedRecipe recipe = new ShapedRecipe(GlobalKeys.getPickupStick(), getPickupStickItemStack());
		recipe.shape(
				"R", 
				"I",
				"I");
		recipe.setIngredient('R', Material.IRON_ORE);
		recipe.setIngredient('I', Material.IRON_INGOT);
		
		plugin.getServer().addRecipe(recipe);
	}
	
	public static boolean initialize(Plugin plugin) {
		if(instance != null) {
			return false;
		}
		
		instance = new PickupStickRecipie(plugin);
		return true;
		
	}
	
	@SuppressWarnings("deprecation")
	private ItemStack getPickupStickItemStack() {
		ItemStack item = new ItemStack(StickMaterial);
		
		ItemMeta meta = item.getItemMeta();
		
		// Set name and such
		meta.setDisplayName(ChatColor.LIGHT_PURPLE + "\"Plocka-upp\" pinnen");	
		
		// Set NBT Tag
		PersistentDataContainer data = meta.getPersistentDataContainer();
		data.set(GlobalKeys.getPickupStick(), PersistentDataType.BYTE, (byte)1);
		
		item.setItemMeta(meta);
		return item;
	}
	
	
	
	
	
	

}
