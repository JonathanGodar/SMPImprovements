package com.ngodar.SMPImprovements.PickupStick;

import java.util.List;

import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.FireworkExplodeEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import com.ngodar.SMPImprovements.GlobalKeys;
import com.ngodar.SMPImprovements.Main;
import com.ngodar.SMPImprovements.common.ItemUtils;

public class PickupStickListener implements Listener {
	public PickupStickListener() {
	}

	@EventHandler
	public void handleRightClickEntity(PlayerInteractEntityEvent evt) {

		Player player = evt.getPlayer();

		if (player.getPassengers().size() > 0) {
			if (player.getPassengers().get(0).equals(evt.getRightClicked()))
				return;
			player.getPassengers().get(0).leaveVehicle();
		}

		if (!isHoldingPickupStick(player))
			return;
		player.addPassenger(evt.getRightClicked());
	}

	@EventHandler
	public void handleSneak(PlayerToggleSneakEvent evt) {
		if (!evt.isSneaking())
			return;

		if (!isHoldingPickupStick(evt.getPlayer())) {
			return;
		}

		List<Entity> passengers = evt.getPlayer().getPassengers();

		if (passengers.size() > 0) {
			passengers.get(0).leaveVehicle();
		}

		ItemUtils.DamageItemInMainHand(evt.getPlayer(), 1, Sound.BLOCK_GLASS_BREAK);
	}

	@EventHandler
	public void handleLeftClickBlock(PlayerInteractEvent evt) {
		@NotNull
		Action action = evt.getAction();
		if (!(action.equals(Action.LEFT_CLICK_BLOCK) || action.equals(Action.LEFT_CLICK_AIR))) {
			return;
		}
		handleLeftClick(evt.getPlayer());
	}

	@EventHandler
	public void handleLeftClickEntity(EntityDamageByEntityEvent evt) {
		if (!(evt.getDamager() instanceof Player))
			return;

		if (handleLeftClick((Player) evt.getDamager())) {
			evt.setCancelled(true);
		}
	}
	
	@EventHandler
	public void handleFireWork(FireworkExplodeEvent evt) {
		Player p = ((Player)evt.getEntity().getShooter());
		p.teleport(evt.getEntity());
	}
	

	// Returns true if the player intended to throw off passengers
	@SuppressWarnings("deprecation")
	private boolean handleLeftClick(Player player) {

		if (!isHoldingPickupStick(player))
			return false;

		List<Entity> passengers = player.getPassengers();

		if (passengers.size() <= 0)
			return false;

		System.out.println("Someone is left clicking");

		Entity targetPassenger = passengers.get(0);
		targetPassenger.leaveVehicle();
		

		float coefficient = 1;

		if (!player.isOnGround())
			coefficient = 10;

		final float runnableCoefficient = coefficient;
		
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				targetPassenger.setVelocity(targetPassenger.getVelocity()
						.add(player.getEyeLocation().getDirection().normalize().multiply(runnableCoefficient)));
			}
		  };
		  
		  
		Main.getPlugin().getServer().getScheduler().runTaskLater(Main.getPlugin(), runnable, 1);
		
		  
//		targetPassenger.setVelocity(targetPassenger.getVelocity()
//				.add(player.getEyeLocation().getDirection().normalize().multiply(coefficient)));
		System.out.println("Setting velocity to a lot");
 
		ItemUtils.DamageItemInMainHand(player, 1, Sound.BLOCK_GLASS_BREAK);
		return true;
	}

	private boolean isHoldingPickupStick(Player p) {
		ItemStack stack = p.getInventory().getItemInMainHand();
		return isPickupStick(stack);
	}

	private boolean isPickupStick(ItemStack itemStack) {

		ItemMeta meta = itemStack.getItemMeta();

		boolean isOfCorrectType = itemStack.getType().equals(PickupStickRecipie.StickMaterial);
		if (!isOfCorrectType) {
			return false;
		}

		PersistentDataContainer storage = meta.getPersistentDataContainer();
		boolean hasCorrectNBT = storage.has(GlobalKeys.getPickupStick(), PersistentDataType.BYTE);

		if (!hasCorrectNBT) {
			return false;
		}

		return true;
	}

}
