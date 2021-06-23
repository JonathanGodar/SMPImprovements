package com.ngodar.SMPImprovements.BridgeEgg;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Vector;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.comphenix.protocol.wrappers.WrappedDataWatcher.Registry;
import com.comphenix.protocol.wrappers.WrappedDataWatcher.Serializer;
import com.destroystokyo.paper.event.entity.ProjectileCollideEvent;
import com.destroystokyo.paper.event.player.PlayerLaunchProjectileEvent;
import com.ngodar.SMPImprovements.Main;

public class BridgeEggListener implements Listener {
	
	final UUID specialPlayerUUID = UUID.fromString("02655666-063b-4663-910a-b8996ca6ff3e");
	final Material brigdeMaterial = Material.DIRT;
	
	Random rand;
	
	HashMap<Entity, Integer> stopCodeMap = new HashMap<Entity, Integer>();
	BukkitScheduler scheduler;
	
	public BridgeEggListener() {
		rand  = new Random();
		scheduler = Main.getPlugin().getServer().getScheduler();
	}
	
	@EventHandler
	public void onEggThrow(PlayerLaunchProjectileEvent evt){
		if(!BridgeEggRecipe.isBridgeEgg(evt.getItemStack())) {
			return;
		}
		
		Runnable runnable = new Runnable() {
		    private World world = evt.getProjectile().getWorld();
			
		    @Override
			public void run() {
		    	Location locationOfProjectile = evt.getProjectile().getLocation();
		    	
		    	Location locationToSetBlock = locationOfProjectile.subtract(evt.getProjectile().getVelocity().multiply(1.5)).add(new Vector(0, -2, 0));
		    	
		    	
				Block blockAtLocation = world.getBlockAt(locationToSetBlock);
				if(!blockAtLocation.getLocation().getNearbyEntities(.5, .5, .5).isEmpty()) {
					 return;
				}
				
				blockAtLocation.setType(Material.GLOWSTONE);
			}
		  };
		 
		int stopCode =  scheduler.scheduleSyncRepeatingTask(Main.getPlugin(), runnable, 2, 1);
		stopCodeMap.put(evt.getProjectile(), stopCode);
	}
	
	
	
	
	@EventHandler
	public void onEggCollideWithEntity(ProjectileCollideEvent evt) {
		if(evt.getEntity().getType() != EntityType.EGG) { 
			return;
		}
		
		
		Entity hit = evt.getCollidedWith();
		Egg egg = (Egg)evt.getEntity();
		
		
		
		// We do not want bridge eggs to produce bridge eggs
		if(BridgeEggRecipe.isBridgeEgg(egg.getItem())) {
			return;
		}
		
		if(hit.getUniqueId().equals(specialPlayerUUID)) {
			Player pHit = (Player)evt.getCollidedWith();
			
			ProjectileSource shooter = egg.getShooter();
			if(!(shooter instanceof Player)) {
				return;
			}
			
			Player pShooter = (Player)shooter;
			
			
			double pNewHealth = Math.max(0, pHit.getHealth() - 2);
			pHit.setHealth(pNewHealth);				
			
			
			
			double shooterNewHelath = Math.max(0, pShooter.getHealth() - 2);
			pShooter.setHealth(shooterNewHelath);
			
			if(rand.nextInt(20) == 0)
				egg.getWorld().dropItemNaturally(egg.getLocation(), BridgeEggRecipe.getBridgeEggStack());
			
			egg.remove();
			evt.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onEggCollide(ProjectileHitEvent evt) {
		Entity egg = evt.getEntity();
		
		if(stopCodeMap.containsKey(egg)) {
			handleBridgeEgg(egg);
			return;
		}	
	}
	
	
	public void handleBridgeEgg(Entity egg) {
		scheduler.cancelTask(stopCodeMap.get(egg));		

	}
	
	
	
	

}
