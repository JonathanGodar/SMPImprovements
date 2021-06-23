package com.ngodar.SMPImprovements.common;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;


public class ItemUtils {

	public static void DamageItemInMainHand(Player player, int toDamage, Sound breakSound) {
		ItemStack inMainHand = player.getInventory().getItemInMainHand();
		if(inMainHand.getType() == Material.AIR) {
			return;
		}
		
		ItemStack damaged = Damage(inMainHand, toDamage);
		
		
		if(damaged.getType() == Material.AIR) { // Will only fire if the thing broke
			player.playSound(player.getLocation(), breakSound, 1, 1);
		}
		
		player.getInventory().setItemInMainHand(damaged);
	}
	
	public static ItemStack Damage(ItemStack itemStack, int toDamage) {
		ItemMeta meta = itemStack.getItemMeta();
		
		if(meta instanceof Damageable) {
			Damageable metaDamageable = (Damageable)meta;
			metaDamageable.setDamage(metaDamageable.getDamage() + toDamage);
			
			itemStack.setItemMeta((ItemMeta)metaDamageable);
			
			if(itemStack.getType().getMaxDurability() < metaDamageable.getDamage()) {
				itemStack.setType(Material.AIR);
			}
		}
		
		return itemStack;
		
		
	}
	
}
