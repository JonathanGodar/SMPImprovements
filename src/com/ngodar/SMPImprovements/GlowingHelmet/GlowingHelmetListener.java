package com.ngodar.SMPImprovements.GlowingHelmet;

import java.util.Collection;
import java.util.List;

import com.ngodar.SMPImprovements.Main;

import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;

public class GlowingHelmetListener implements Listener {

    static GlowingHelmetListener instance;

    public GlowingHelmetListener() {
        if (instance != null)
            return;

        instance = this;
        StartGlowingHelmetTask();
    }

    private static void StartGlowingHelmetTask() {
        BukkitScheduler scheduler = Main.getPlugin().getServer().getScheduler();

        final Server server = Main.getPlugin().getServer();

        scheduler.scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable() {
            @Override
            public void run() {
                Collection<? extends Player> players = server.getOnlinePlayers();
                for (Player player : players) {
                    if (GlowingHelmetRecipe.isWearingGlowingHelmet(player)) {
                        player.addPotionEffect(
                                new PotionEffect(PotionEffectType.NIGHT_VISION, 13 * 20, 1, true, false));
                    }
                }

            }
        }, 0, 2 * 20);

    }

}
