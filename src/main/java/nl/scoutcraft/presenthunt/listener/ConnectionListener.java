package nl.scoutcraft.presenthunt.listener;

import nl.scoutcraft.presenthunt.data.Data;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * When a player joins, we call the `Data.join()` method, and when a player quits, we call the
 * `Data.quit()` method
 */
public class ConnectionListener implements Listener {

    /**
     * When a player joins the server, the plugin will get the player's UUID and send it to the Data
     * class.
     * 
     * @param event The event that is being called.
     */
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Data.join(event.getPlayer().getUniqueId());
    }

    /**
     * When a player quits, remove them from the Data class
     * 
     * @param event The event that is being listened for.
     */
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Data.quit(event.getPlayer().getUniqueId());
    }
}
