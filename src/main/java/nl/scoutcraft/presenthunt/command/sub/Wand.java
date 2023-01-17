package nl.scoutcraft.presenthunt.command.sub;

import nl.scoutcraft.presenthunt.command.SubCommand;
import nl.scoutcraft.presenthunt.data.Item;
import nl.scoutcraft.presenthunt.data.Perms;
import nl.scoutcraft.presenthunt.lang.Locale;
import org.bukkit.entity.Player;

public class Wand extends SubCommand {

    public Wand() {
        super(Locale.WAND_USAGE, Locale.WAND_USAGE, Perms.ADMIN, "wand");
    }

    @Override
    public void onCommand(Player player, String[] args) {
        player.getInventory().addItem(Item.WAND.build(player));
    }
}
