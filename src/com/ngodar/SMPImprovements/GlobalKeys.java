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
		strawberry = new NamespacedKey(plugin, "Strawberry");
		customDeathMessage = new NamespacedKey(plugin, "CustomDeathMessage");
		glowingHelmet = new NamespacedKey(plugin, "GlowingHelmet");
	}

	public static boolean setSingletonIfEmpty(Plugin plugin) {
		if (instance != null) {
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

	public static NamespacedKey getStrawberry() {
		return instance.strawberry;
	}

	public static NamespacedKey getCustomDeathMessage() {
		return instance.customDeathMessage;
	}

	public static NamespacedKey getGlowingHelmet() {
		return instance.glowingHelmet;
	}

	private NamespacedKey pickupStick;
	private NamespacedKey bridgeEgg;
	private NamespacedKey baneOfGustaf;
	private NamespacedKey pickPocket;
	private NamespacedKey strawberry;
	private NamespacedKey customDeathMessage;
	private NamespacedKey glowingHelmet;
}
