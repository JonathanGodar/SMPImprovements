package com.ngodar.SMPImprovements.BaneOfGustaf;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import com.ngodar.SMPImprovements.GlobalKeys;
import com.ngodar.SMPImprovements.PickupStick.PickupStickRecipie;

import net.md_5.bungee.api.ChatColor;

public class BaneOfGustafRecipe {

	Plugin plugin;

	private static BaneOfGustafRecipe instance;

	public static String getName() {
		return ChatColor.DARK_RED + "Bane Of Gustaf";
	}

	private BaneOfGustafRecipe(Plugin plugin) {
		this.plugin = plugin;

		ShapedRecipe recipe = new ShapedRecipe(GlobalKeys.getBaneOfGustaf(), getBaneOfGustafStack());
		recipe.shape("I", "I", "I");

		recipe.setIngredient('I', Material.IRON_INGOT);

		plugin.getServer().addRecipe(recipe);
	}

	public static boolean initialize(Plugin plugin) {
		if (instance != null) {
			return false;
		}

		instance = new BaneOfGustafRecipe(plugin);
		return true;
	}

	public static boolean playerHasBaneOfGustaf(Player p) {
		ItemStack[] things = p.getInventory().getContents();
		for (ItemStack item : things) {
			if (isBaneOfGustaf(item))
				return true;

		}
		return false;
	}

	public static boolean playerIsHoldingBaneOfGustaf(Player p) {
		return isBaneOfGustaf(p.getInventory().getItemInMainHand());
	}

	public static boolean isBaneOfGustaf(ItemStack item) {
		if (item == null)
			return false;

		if (item.getType() != Material.GOLDEN_SWORD) {
			return false;
		}

		return item.getItemMeta().getPersistentDataContainer().has(GlobalKeys.getBaneOfGustaf(),
				PersistentDataType.BYTE);
	}

	public static ItemStack getBaneOfGustafStack() {
		ItemStack stack = new ItemStack(Material.GOLDEN_SWORD);
		stack.addUnsafeEnchantment(Enchantment.DURABILITY, 5);

		ItemMeta meta = stack.getItemMeta();

		meta.getPersistentDataContainer().set(GlobalKeys.getBaneOfGustaf(), PersistentDataType.BYTE, (byte) 1);
		meta.setDisplayName(getName());
		stack.setItemMeta(meta);
		return stack;
	}

}
