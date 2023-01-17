package nl.scoutcraft.presenthunt.listener;

import nl.scoutcraft.presenthunt.PresentHunt;
import nl.scoutcraft.presenthunt.data.Data;
import nl.scoutcraft.presenthunt.data.Item;
import nl.scoutcraft.presenthunt.object.HuntPlayer;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;

public class
// A listener that listens to the `ProjectileLaunchEvent` and `ProjectileHitEvent` events.
CollectibleListener implements Listener {

    private final Map<Integer, BukkitTask> runnable = new HashMap<>();

    /**
     * If a player throws a snowball, and the snowball is a collectible, and the player can fly, then
     * the player will fly with the snowball
     * 
     * @param event The event that is being listened for.
     */
    @EventHandler
    public void onCollectibleInteract(ProjectileLaunchEvent event) {
        if (event.getEntity().getType() != EntityType.SNOWBALL
                || !Item.isCollectible(((Snowball) event.getEntity()).getItem())
                || !(event.getEntity().getShooter() instanceof Player player))
            return;

        HuntPlayer huntPlayer = Data.HUNTPLAYERS.get(player.getUniqueId());

        if (!huntPlayer.getCanFly()) {
            event.setCancelled(true);
            return;
        }

        event.getEntity().addPassenger(player);
        huntPlayer.setCanFly(false);

        this.runnable.put(event.getEntity().getEntityId(),
                new BukkitRunnable() {
                    public void run() {
                        final Location loc = player.getLocation();
                        player.getWorld().spawnParticle(Particle.SNOWBALL, loc, 5, 0.25, 0.25, 0.25, 1);
                        player.getWorld().spawnParticle(Particle.SNOWFLAKE, loc, 5, 0.25, 0.25, 0.25, 1);
                    }
                }.runTaskTimer(PresentHunt.getInstance(), 0, 1)
        );
    }

    /**
     * If a snowball hits something, cancel the runnable and give the player the ability to fly again
     * 
     * @param event The event that is being listened to.
     */
    @EventHandler
    public void onCollectibleHit(ProjectileHitEvent event) {
        if (event.getEntity().getType() != EntityType.SNOWBALL) return;

        int id = event.getEntity().getEntityId();

        if (!this.runnable.containsKey(id)) return;

        this.runnable.get(id).cancel();

        if (!(event.getEntity().getShooter() instanceof Player player)) return;

        Data.HUNTPLAYERS.get(player.getUniqueId()).setCanFly(true);
        player.getInventory().setItem(4, Item.COLLECTIBLE.build(player));
        player.setCooldown(Material.SNOWBALL, 20);
    }
}
