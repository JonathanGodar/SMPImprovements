package com.ngodar.SMPImprovements.Strawberry;

import java.util.Random;

import com.comphenix.net.bytebuddy.agent.builder.AgentBuilder.CircularityLock.Global;
import com.ngodar.SMPImprovements.GlobalKeys;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerHarvestBlockEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class StrawberryListener implements Listener {

    Random rand = new Random();

    @EventHandler
    public void handleHarvest(PlayerHarvestBlockEvent e) {

        if (e.getHarvestedBlock().getType() != Material.SWEET_BERRY_BUSH)
            return;

        if (rand.nextInt(25) != 0) {
            return;
        }

        e.getItemsHarvested().clear();
        e.getItemsHarvested().add(StrawberryRecipe.getStrawberryStack());
    }

    @EventHandler
    public void handleDamage(EntityDamageByEntityEvent e) {
        if (e.getEntity().getType() != EntityType.PLAYER)
            return;

        Player p = ((Player) e.getEntity());

        if (p.getHealth() - e.getFinalDamage() > 0)
            return;

        if (!(e.getDamager() instanceof LivingEntity))
            return;

        if (!StrawberryRecipe.useStrawberryIfExist(p)) {
            return;
        }

        LivingEntity entity = (LivingEntity) e.getDamager();

        entity.setHealth(0);
        entity.getPersistentDataContainer().set(GlobalKeys.getCustomDeathMessage(), PersistentDataType.STRING,
                entity.getName() + " attackerade " + p.getName() + " men råkade sätta en jordgubbe i halsen");
        e.setCancelled(true);
    }

    // Move to another file
    @EventHandler
    public void handleDeath(PlayerDeathEvent e) {
        PersistentDataContainer data = e.getEntity().getPersistentDataContainer();

        String deathMessage = data.get(GlobalKeys.getCustomDeathMessage(), PersistentDataType.STRING);
        if (deathMessage == null)
            return;

        e.setDeathMessage(deathMessage);
        data.remove(GlobalKeys.getCustomDeathMessage());
    }

    @EventHandler
    public void handleConsumeItem(PlayerItemConsumeEvent e) {
        if (!StrawberryRecipe.isStrawberry(e.getItem()))
            return;

        e.setCancelled(true);

        Player p = e.getPlayer();
        p.getPersistentDataContainer().set(GlobalKeys.getCustomDeathMessage(), PersistentDataType.STRING,
                p.getName() + " satte en " + "jordgubbe " + "i halsen...");

        int idx = e.getPlayer().getInventory().first(e.getItem());
        System.out.println(idx);
        ItemStack item = e.getItem();
        e.getPlayer().getInventory().setItem(idx, item.subtract());
        p.setHealth(0);
    }
}
