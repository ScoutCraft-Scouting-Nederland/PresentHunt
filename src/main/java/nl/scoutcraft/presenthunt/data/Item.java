package nl.scoutcraft.presenthunt.data;

import nl.scoutcraft.eagle.server.utils.ItemBuilder;
import nl.scoutcraft.presenthunt.lang.Locale;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class Item {

    public static final ItemBuilder WAND;
    public static final ItemBuilder COLLECTIBLE;

    static {
        WAND = new ItemBuilder(Material.BLAZE_ROD).name(Locale.WAND_NAME).lore(Locale.WAND_LORE).data(Keys.HUNT_WAND, PersistentDataType.BYTE, (byte) 1);
        COLLECTIBLE = new ItemBuilder(Material.SNOWBALL).name(Locale.COLLECTIBLE_NAME).lore(Locale.COLLECTIBLE_LORE).data(Keys.COLLECTIBLE, PersistentDataType.BYTE, (byte) 1);
    }

    public static boolean isWand(ItemStack item) {
        return item.hasItemMeta() && item.getItemMeta().getPersistentDataContainer().has(Keys.HUNT_WAND, PersistentDataType.BYTE);
    }

    public static boolean isCollectible(ItemStack item) {
        return item.hasItemMeta() && item.getItemMeta().getPersistentDataContainer().has(Keys.COLLECTIBLE, PersistentDataType.BYTE);
    }
}
