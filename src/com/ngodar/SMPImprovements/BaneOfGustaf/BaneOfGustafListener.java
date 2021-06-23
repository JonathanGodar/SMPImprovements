package com.ngodar.SMPImprovements.BaneOfGustaf;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.ngodar.SMPImprovements.Main;


class Aggression { // Improve
	
	public Aggression(Entity damager, Entity damagee) {
		this.Damager = damager;
		this.Damagee = damagee;
	}
	
	public Entity Damager;
	public Entity Damagee;
}



public class BaneOfGustafListener implements Listener {

	
	private HashMap<UUID, Aggression> aggressions = new HashMap<UUID, Aggression>();
	
	int baneOfGustafAggroDuration = 20 * 30;
	
	BukkitScheduler scheduler;
	
	public BaneOfGustafListener() {
		this.scheduler = Main.getPlugin().getServer().getScheduler();
	}
	
	@EventHandler
	public void entityDamageEntityHandler(EntityDamageByEntityEvent evt) {
		handleAggression(evt);
		handleBaneOfGustafHit(evt);	
		System.out.println(aggressions.values().size());
	}
	
	public void handleBaneOfGustafHit(EntityDamageByEntityEvent evt) {
		
		System.out.println("Bane of gustaf hit");
		if(!(evt.getDamager() instanceof Player)) {
			return;
		}
		

		System.out.println("Damager is entity");
		Player damager = (Player)evt.getDamager();
		
		if(!BaneOfGustafRecipe.playerIsHoldingBaneOfGustaf(damager)) {
			return;
		}
		System.out.println("Player is holding bane of gustaf");
		
		if(aggressionExists(evt.getEntity(), evt.getDamager())) {
			Damageable toDamage = (Damageable)evt.getEntity();
			toDamage.setHealth(0);
		}
	}
	
	
	
	public boolean aggressionExists(Entity damager, Entity damagee) {
		for(Aggression agg : aggressions.values()) {
			if(agg.Damagee != damagee) {
				System.out.println("Continuing!");
				continue;
			}
			
			if(agg.Damager != damager) {
				continue;
			}
			return true;
		} 
		return false;
	}
	
	// https://www.spigotmc.org/threads/colored-glowing-on-client-side-entities.236881/
	public void handleAggression(EntityDamageByEntityEvent evt) {

		if(!(evt.getDamager() instanceof LivingEntity))
			return;
		
		if(!(evt.getEntity() instanceof LivingEntity)) {
			return;
		}

		LivingEntity damager = (LivingEntity)evt.getDamager();
		LivingEntity damagee = (LivingEntity)evt.getEntity();
		if(aggressionExists(damagee, evt.getDamager())) // This is self defense!
			return;
		
		
		
		final UUID aggId = UUID.randomUUID(); 
		aggressions.put(aggId, new Aggression(evt.getDamager(), damagee));
		
		// Copied
		if(damagee instanceof Player) {
			if(BaneOfGustafRecipe.playerHasBaneOfGustaf(((Player)damagee))){				
				Player pDamagee = (Player) damagee;
				pDamagee.sendActionBar(damager.getName() + " slog dig, SLÅ TILLBAKA med " + BaneOfGustafRecipe.getName());
			}
		}
		
		scheduler.runTaskLater(Main.getPlugin(), new Runnable() {
			@Override
			public void run() {
				System.out.println("Removing aggression!");
				aggressions.remove(aggId);
			}
		}, baneOfGustafAggroDuration);	
	
	}
	
}
