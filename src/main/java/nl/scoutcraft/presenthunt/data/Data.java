package nl.scoutcraft.presenthunt.data;

import nl.scoutcraft.presenthunt.PresentHunt;
import nl.scoutcraft.presenthunt.io.PlayerData;
import nl.scoutcraft.presenthunt.object.HuntPlayer;
import nl.scoutcraft.presenthunt.object.Present;
import nl.scoutcraft.presenthunt.util.Serializer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * It's a class that holds all the data for the plugin
 */
public class Data {

    public static final Map<Location, Present> PRESENTS = new HashMap<>();
    public static final Map<UUID, HuntPlayer> HUNTPLAYERS = new HashMap<>();

    /**
     * It takes a list of strings from the config, converts them to locations, and then adds them to a
     * map
     * 
     * @param config The config file that the presents are being loaded from.
     */
    public static void loadPresents(Configuration config) {
        config.getStringList("presents").stream().map(Serializer::deserializeLocation).forEach(loc -> PRESENTS.put(loc, new Present(loc)));
    }

    /**
     * If the PRESENTS HashMap contains the block's location, return true, otherwise return false.
     * 
     * @param block The block that you want to check if it's a present.
     * @return A boolean value.
     */
    public static boolean existsPresent(Block block) {
        return PRESENTS.containsKey(block.getLocation());
    }


    /**
     * It adds a present to the list of presents
     * 
     * @param block The block that the present is being placed on.
     */
    public static void addPresent(Block block) {
        Present present = new Present(block.getLocation());
        PRESENTS.put(block.getLocation(), present);

        List<String> presents = config().getStringList("presents");
        presents.add(Serializer.serializeLocation(present.getLocation()));
        config().set("presents", presents);
        save();
    }

    /**
     * Remove a present from the config, remove it from all players' collected presents, and remove it
     * from the PRESENTS HashMap
     * 
     * @param present The present to remove
     */
    public static void removePresent(Present present) {
        List<String> presents = config().getStringList("presents");
        presents.remove(Serializer.serializeLocation(present.getLocation()));
        config().set("presents", presents);
        save();

        HUNTPLAYERS.values().forEach(hp -> hp.removeCollected(present));

        PRESENTS.remove(present.getLocation());
    }

    /**
     * It saves the config.yml file
     */
    private static void save() {
        PresentHunt.getInstance().saveConfig();
    }

    /**
     * It returns the configuration file of the plugin
     * 
     * @return The config file for the plugin.
     */
    private static Configuration config() {
        return PresentHunt.getInstance().getConfig();
    }

    /**
     * If the player is not in the map, add them to the map. If they are in the map, do nothing
     * 
     * @param uuid The UUID of the player you want to join the game.
     */
    public static void join(UUID uuid) {
        HUNTPLAYERS.computeIfAbsent(uuid, k -> new HuntPlayer(uuid, new PlayerData(uuid)));
        HUNTPLAYERS.get(uuid).giveCollectible(Bukkit.getPlayer(uuid));

    }

    /**
     * It saves the player's collected presents to their player data file, then removes them from the
     * `HUNTPLAYERS` map
     * 
     * @param uuid The UUID of the player
     */
    public static void quit(UUID uuid) {
        HuntPlayer huntPlayer = HUNTPLAYERS.get(uuid);
        List<String> collected = huntPlayer.getCollected().stream().map(present -> Serializer.serializeLocation(present.getLocation())).toList();
        huntPlayer.getPlayerData().set("collected", collected);
        huntPlayer.getPlayerData().save();
        HUNTPLAYERS.remove(uuid);
    }

}
