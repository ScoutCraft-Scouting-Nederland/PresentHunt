package nl.scoutcraft.presenthunt.command.sub;

import nl.scoutcraft.presenthunt.command.SubCommand;
import nl.scoutcraft.presenthunt.data.Item;
import nl.scoutcraft.presenthunt.data.Perms;
import nl.scoutcraft.presenthunt.lang.Locale;
import org.bukkit.entity.Player;

/**
 * It adds a wand to the player's inventory
 */
public class Wand extends SubCommand {

    public Wand() {
        super(Locale.WAND_USAGE, Locale.WAND_USAGE, Perms.ADMIN, "wand");
    }

    /**
     * When the player types /hunt wand, give them a wand.
     * 
     * @param player The player who executed the command.
     * @param args The arguments passed to the command.
     */
    @Override
    public void onCommand(Player player, String[] args) {
        player.getInventory().addItem(Item.WAND.build(player));
    }
}
