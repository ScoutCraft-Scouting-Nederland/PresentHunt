package nl.scoutcraft.presenthunt.runable;

import nl.scoutcraft.presenthunt.PresentHunt;
import nl.scoutcraft.presenthunt.data.Data;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * It spawns particles around presents every 5 ticks.
 */
public class PresentParticles extends BukkitRunnable {

    private final PresentHunt plugin;

    public PresentParticles(PresentHunt plugin) {
        this.plugin = plugin;
    }

    /**
     * Start a repeating task that runs every 5 ticks (1/4 second)
     */
    public void start() {
        runTaskTimerAsynchronously(this.plugin, 0L, 5L);
    }

    /**
     * For each present, spawn a particle for each online player, with a different color depending on
     * whether or not they've collected the present
     */
    @Override
    public void run() {
        if (this.plugin.getServer().getOnlinePlayers().size() <= 0 || Data.PRESENTS.isEmpty())
            return;

        Data.PRESENTS.values().forEach(present -> {
            Location presentLocation = present.getLocation().clone().add(0.5, 0.5, 0.5);
            this.plugin.getServer().getOnlinePlayers().forEach(online -> {
                boolean collected = Data.HUNTPLAYERS.get(online.getUniqueId()).hasCollected(present);
                online.spawnParticle(Particle.REDSTONE, presentLocation, 25, 0.25, 0.30, 0.25, 1, new Particle.DustOptions(collected ? Color.GREEN : Color.RED, 1));
            });
        });
    }
}
