package nl.scoutcraft.presenthunt.data;

import nl.scoutcraft.presenthunt.PresentHunt;
import org.bukkit.NamespacedKey;

/**
 * It's a class that contains static variables that are used to store the names of the keys that we'll
 * use to store data in the player's inventory
 */
public class Keys {

    public static final NamespacedKey HUNT_WAND = new NamespacedKey(PresentHunt.getInstance(), "hunt_wand");
    public static final NamespacedKey COLLECTIBLE = new NamespacedKey(PresentHunt.getInstance(), "collectible");
}
