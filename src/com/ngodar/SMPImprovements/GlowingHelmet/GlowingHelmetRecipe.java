package com.ngodar.SMPImprovements.GlowingHelmet;

import java.util.Arrays;

import com.ngodar.SMPImprovements.GlobalKeys;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import net.md_5.bungee.api.ChatColor;

public class GlowingHelmetRecipe {

    static GlowingHelmetRecipe instance;

    private Plugin plugin;

    private GlowingHelmetRecipe(Plugin plugin) {
        instance = this;
        this.plugin = plugin;

        addRecipies(plugin);
    }

    private static void addRecipies(Plugin plugin) {
        addRecipeFor(plugin, Material.CHAINMAIL_HELMET);
        addRecipeFor(plugin, Material.LEATHER_HELMET);
        addRecipeFor(plugin, Material.GOLDEN_HELMET);
        addRecipeFor(plugin, Material.IRON_HELMET);
        addRecipeFor(plugin, Material.DIAMOND_HELMET);
        addRecipeFor(plugin, Material.NETHERITE_HELMET);
        addRecipeFor(plugin, Material.TURTLE_HELMET);
    }

    private static void addRecipeFor(Plugin plugin, Material material) {
        plugin.getServer().addRecipe(createRecipeFor(material));
    }

    private static Recipe createRecipeFor(Material material) {
        ShapedRecipe recipe = new ShapedRecipe(createKeyFor(material), getGlowingHelmet(material));

        recipe.shape("GGG", "GIG", "GGG");
        recipe.setIngredient('G', Material.GLOWSTONE);
        recipe.setIngredient('I', new ItemStack(material));

        return recipe;
    }

    private static NamespacedKey createKeyFor(Material material) {
        return new NamespacedKey(instance.plugin, GlobalKeys.getGlowingHelmet().getKey() + "_" + material.toString());
    }

    public static ItemStack getGlowingHelmet(Material material) {
        ItemStack stack = new ItemStack(material);
        ItemMeta meta = stack.getItemMeta();

        meta.getPersistentDataContainer().set(GlobalKeys.getGlowingHelmet(), PersistentDataType.BYTE, (byte) 1);
        meta.setLore(Arrays.asList(ChatColor.GOLD + "LYSANDE"));
        stack.setItemMeta(meta);
        return stack;
    }

    public static boolean isWearingGlowingHelmet(Player p) {
        return isGlowingHelmet(p.getInventory().getHelmet());
    }

    public static boolean isGlowingHelmet(ItemStack stack) {
        if (stack == null)
            return false;

        return stack.getItemMeta().getPersistentDataContainer().has(GlobalKeys.getGlowingHelmet(),
                PersistentDataType.BYTE);
    }

    public static boolean initialize(Plugin plugin) {
        if (instance != null)
            return false;

        new GlowingHelmetRecipe(plugin);
        return true;
    }
}
