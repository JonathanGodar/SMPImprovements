package com.ngodar.SMPImprovements.common;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class DropItems {
	public static void ofInventory(Inventory inventory){
		
		
		for(ItemStack stack : inventory.getStorageContents()) {
			if(stack == null)
				continue;
			inventory.getLocation().getWorld().dropItemNaturally(inventory.getLocation(), stack);
		}
		inventory.setStorageContents(null);	
	}
}
