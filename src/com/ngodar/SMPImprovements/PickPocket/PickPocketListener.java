package com.ngodar.SMPImprovements.PickPocket;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import com.ngodar.SMPImprovements.common.DropItems;

public class PickPocketListener implements Listener{
	
	@EventHandler
	public static void onHit(EntityDamageByEntityEvent evt) {
		if(!(evt.getDamager() instanceof LivingEntity)) {
			return;
		}
		
		LivingEntity damager = (LivingEntity)evt.getDamager();
		
		if(!PickPocketRecipe.isPickPocketStack(damager.getEquipment().getItemInMainHand()))
			return;
		
		damager.setHealth(2);
		
		Entity damagee = evt.getEntity();
		
		if(damagee instanceof Player) {
			DropItems.ofInventory(((Player)damagee).getInventory());			
		}
	}
}
