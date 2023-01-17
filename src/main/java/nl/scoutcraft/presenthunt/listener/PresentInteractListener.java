package nl.scoutcraft.presenthunt.listener;

import nl.scoutcraft.eagle.server.locale.Placeholder;
import nl.scoutcraft.presenthunt.data.Data;
import nl.scoutcraft.presenthunt.data.Item;
import nl.scoutcraft.presenthunt.data.Perms;
import nl.scoutcraft.presenthunt.lang.Locale;
import nl.scoutcraft.presenthunt.object.HuntPlayer;
import nl.scoutcraft.presenthunt.object.Present;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.util.UUID;

public class PresentInteractListener implements Listener {

    /**
     * If the player is holding a present wand, they can remove or add presents. If they're not holding
     * a present wand, they can collect presents
     * 
     * @param event The event that is being listened to.
     */
    @EventHandler
    public void onPresentInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        HuntPlayer huntPlayer = Data.HUNTPLAYERS.get(uuid);

        Block block = event.getClickedBlock();

        if (event.getHand() == EquipmentSlot.OFF_HAND) return;

        if (block == null || (!block.getType().equals(Material.PLAYER_HEAD) && !block.getType().equals(Material.PLAYER_WALL_HEAD)))
            return;

        Location blockLocation = block.getLocation();

        if (player.hasPermission(Perms.ADMIN) && Item.isWand(player.getInventory().getItemInMainHand())) {
            if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
                if (!Data.existsPresent(block)) {
                    Locale.PRESENT_NOPRESENT.send(player);
                    event.setCancelled(true);
                    return;
                }

                Present present = Data.PRESENTS.get(blockLocation);
                Data.removePresent(present);
                Locale.PRESENT_REMOVE.send(player);
                event.setCancelled(true);
                return;
            }

            if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (Data.existsPresent(block)) {
                    Locale.PRESENT_ALREADYHERE.send(player);
                    return;
                }

                Data.addPresent(block);
                Locale.PRESENT_ADD.send(player);
                return;
            }

            return;
        }

        if (event.getAction() == Action.LEFT_CLICK_BLOCK) return;

        if (!Data.existsPresent(block)) return;

        Present present = Data.PRESENTS.get(blockLocation);

        if (huntPlayer.hasCollected(present)) {
            Locale.PRESENT_ALREADYFOUND.send(player);
            return;
        }

        if (!huntPlayer.collect(present, player)) return;

        Locale.PRESENT_FOUND.send(player, new Placeholder("%found%", String.valueOf(huntPlayer.collectedCount())), new Placeholder("%total%", String.valueOf(Data.PRESENTS.size())));
    }
}
