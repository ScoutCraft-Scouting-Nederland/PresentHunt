package nl.scoutcraft.presenthunt.data;

import nl.scoutcraft.eagle.server.utils.ItemBuilder;
import nl.scoutcraft.presenthunt.lang.Locale;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

/**
 * It's a static class that contains static fields that are used to create items
 */
public class Item {

    public static final ItemBuilder WAND;
    public static final ItemBuilder COLLECTIBLE;

    // It's creating the items that are used in the game.
    static {
        WAND = new ItemBuilder(Material.BLAZE_ROD).name(Locale.WAND_NAME).lore(Locale.WAND_LORE).data(Keys.HUNT_WAND, PersistentDataType.BYTE, (byte) 1);
        COLLECTIBLE = new ItemBuilder(Material.SNOWBALL).name(Locale.COLLECTIBLE_NAME).lore(Locale.COLLECTIBLE_LORE).data(Keys.COLLECTIBLE, PersistentDataType.BYTE, (byte) 1);
    }

    /**
     * If the item has meta and the meta has a persistent data container that has a byte with the key
     * HUNT_WAND, then return true
     * 
     * @param item The item to check
     * @return A boolean value.
     */
    public static boolean isWand(ItemStack item) {
        return item.hasItemMeta() && item.getItemMeta().getPersistentDataContainer().has(Keys.HUNT_WAND, PersistentDataType.BYTE);
    }

    /**
     * If the item has meta and the meta has a byte tag with the key "collectible", then return true
     * 
     * @param item The item to check
     * @return A boolean value.
     */
    public static boolean isCollectible(ItemStack item) {
        return item.hasItemMeta() && item.getItemMeta().getPersistentDataContainer().has(Keys.COLLECTIBLE, PersistentDataType.BYTE);
    }
}
