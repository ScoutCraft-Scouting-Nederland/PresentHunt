package nl.scoutcraft.presenthunt.listener;

import nl.scoutcraft.presenthunt.data.Data;
import nl.scoutcraft.presenthunt.data.Perms;
import nl.scoutcraft.presenthunt.lang.Locale;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener implements Listener {

    /**
     * If the player doesn't have permission to break presents, cancel the event
     * 
     * @param event The event that was called.
     */
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (!Data.existsPresent(event.getBlock()))
            return;

        if (event.getPlayer().hasPermission(Perms.ADMIN))
            Locale.PRESENT_CANTBREAK.send(event.getPlayer());

        event.setCancelled(true);
    }
}
