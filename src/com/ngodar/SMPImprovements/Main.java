package com.ngodar.SMPImprovements;

import com.comphenix.protocol.ProtocolManager;
import com.ngodar.SMPImprovements.BaneOfGustaf.BaneOfGustafListener;
import com.ngodar.SMPImprovements.BaneOfGustaf.BaneOfGustafRecipe;
import com.ngodar.SMPImprovements.BridgeEgg.BridgeEggListener;
// import com.ngodar.SMPImprovements.EasyPickaxe.EasyPickaxe;
import com.ngodar.SMPImprovements.GlowingHelmet.GlowingHelmetListener;
import com.ngodar.SMPImprovements.GlowingHelmet.GlowingHelmetRecipe;
import com.ngodar.SMPImprovements.PickPocket.PickPocketListener;
import com.ngodar.SMPImprovements.PickPocket.PickPocketRecipe;
import com.ngodar.SMPImprovements.PickupStick.PickupStickListener;
import com.ngodar.SMPImprovements.PickupStick.PickupStickRecipie;
import com.ngodar.SMPImprovements.Strawberry.StrawberryListener;
import com.ngodar.SMPImprovements.Strawberry.StrawberryRecipe;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	static Main instance;

	static ProtocolManager manager;

	@Override
	public void onEnable() {
		instance = this;

		GlobalKeys.setSingletonIfEmpty(this);
		initializeRecipies();
		initializeListeners();
	}

	private void initializeRecipies() {
		PickupStickRecipie.initialize(this);
		BaneOfGustafRecipe.initialize(this);
		PickPocketRecipe.initialize(this);
		GlowingHelmetRecipe.initialize(this);
	}

	private void initializeListeners() {
		getServer().getPluginManager().registerEvents(new PickupStickListener(), this);
		getServer().getPluginManager().registerEvents(new BridgeEggListener(), this);
		getServer().getPluginManager().registerEvents(new BaneOfGustafListener(), this);
		getServer().getPluginManager().registerEvents(new PickPocketListener(), this);
		getServer().getPluginManager().registerEvents(new StrawberryListener(), this);
		getServer().getPluginManager().registerEvents(new GlowingHelmetListener(), this);
		// getServer().getPluginManager().registerEvents(new EasyPickaxe(), this);
	}

	public static Plugin getPlugin() {
		return (Plugin) instance;
	}

	public static ProtocolManager getProtocolManager() {
		return manager;
	}

}
