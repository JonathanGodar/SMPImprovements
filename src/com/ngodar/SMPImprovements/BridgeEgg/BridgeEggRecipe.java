package com.ngodar.SMPImprovements.BridgeEgg;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import com.ngodar.SMPImprovements.GlobalKeys;

import net.md_5.bungee.api.ChatColor;

public class BridgeEggRecipe {

	
	
	@SuppressWarnings("deprecation")
	static ItemStack getBridgeEggStack() {
		
		ItemStack item = new ItemStack(Material.EGG);
		item.addUnsafeEnchantment(Enchantment.ARROW_FIRE, 1);
		
		ItemMeta meta = item.getItemMeta();
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		
		meta.setDisplayName(ChatColor.DARK_PURPLE + "BROÄGGET");
		
		
		meta.getPersistentDataContainer().set(GlobalKeys.getBridgeEgg(), PersistentDataType.BYTE, (byte)1);
		
		
		item.setItemMeta(meta);
		return item;
	}
	
	static boolean isBridgeEgg(ItemStack item) {
		if(item.getType() != Material.EGG)
			return false ;
		
		PersistentDataContainer data = item.getItemMeta().getPersistentDataContainer();
		return data.has(GlobalKeys.getBridgeEgg(), PersistentDataType.BYTE);
	}
	
}
