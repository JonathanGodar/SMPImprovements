package com.ngodar.SMPImprovements.Strawberry;

import java.util.Random;

import com.ngodar.SMPImprovements.GlobalKeys;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.junit.Ignore;

import net.md_5.bungee.api.ChatColor;

public class StrawberryRecipe {

    @SuppressWarnings("deprication")
    static public ItemStack getStrawberryStack() {
        ItemStack stack = new ItemStack(Material.APPLE);

        ItemMeta meta = stack.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();

        meta.setDisplayName(ChatColor.RED + "Jordgubbe");
        container.set(GlobalKeys.getStrawberry(), PersistentDataType.BYTE, (byte) 1);

        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        stack.setItemMeta(meta);
        stack.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 1);
        stack.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        return stack;
    }

    static public int getFirstStrawberryStackLocation(Player p) {
        for (int i = 0; i < p.getInventory().getSize(); i++) {
            if (isStrawberry(p.getInventory().getItem(i))) {
                return i;
            }
        }
        return -1;
    }

    public static boolean isStrawberry(ItemStack item) {
        if (item == null)
            return false;

        if (item.getType() != Material.APPLE)
            return false;

        if (!item.getItemMeta().getPersistentDataContainer().has(GlobalKeys.getStrawberry(), PersistentDataType.BYTE))
            return false;
        return true;
    }

    static Random rand = new Random();

    public static boolean useStrawberryIfExist(Player p) {

        int idx = getFirstStrawberryStackLocation(p);
        if (idx == -1)
            return false;

        ItemStack item = p.getInventory().getItem(idx);

        item.setAmount(item.getAmount() - 1);
        p.getInventory().setItem(idx, item);

        if (rand.nextInt(2) == 0) {
            return true;
        }

        return false;
    }
}
