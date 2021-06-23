package com.ngodar.SMPImprovements;

import org.bukkit.NamespacedKey;
import org.bukkit.plugin.Plugin;

public class GlobalKeys {

	private static GlobalKeys instance;

	
	
	
	private GlobalKeys(Plugin plugin) {	
		pickupStick = new NamespacedKey(plugin, "PickupStick");
		bridgeEgg = new NamespacedKey(plugin, "BridgeEgg");
		baneOfGustaf = new NamespacedKey(plugin, "BaneOfGustaf");
		pickPocket = new NamespacedKey(plugin, "PickPocket");
	}
	
	public static boolean setSingletonIfEmpty(Plugin plugin) {
		if(instance != null) {
			return false;
		}
		
		instance = new GlobalKeys(plugin);	
		return true;
	}
	
	public static NamespacedKey getPickupStick() {
		return instance.pickupStick;
	}
	
	public static NamespacedKey getBridgeEgg() {
		return instance.bridgeEgg;
	}
	
	public static NamespacedKey getBaneOfGustaf() {
		return instance.baneOfGustaf;
	}
	
	public static NamespacedKey getPickPocket() {
		return instance.pickPocket;
	}

	private NamespacedKey pickupStick;
	private NamespacedKey bridgeEgg;
	private NamespacedKey baneOfGustaf;
	private NamespacedKey pickPocket;
}
