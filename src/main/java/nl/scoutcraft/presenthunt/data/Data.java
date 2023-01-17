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

public class Data {

    public static final Map<Location, Present> PRESENTS = new HashMap<>();
    public static final Map<UUID, HuntPlayer> HUNTPLAYERS = new HashMap<>();

    public static void loadPresents(Configuration config) {
        config.getStringList("presents").stream().map(Serializer::deserializeLocation).forEach(loc -> PRESENTS.put(loc, new Present(loc)));
    }

    public static boolean existsPresent(Block block) {
        return PRESENTS.containsKey(block.getLocation());
    }


    public static void addPresent(Block block) {
        Present present = new Present(block.getLocation());
        PRESENTS.put(block.getLocation(), present);

        List<String> presents = config().getStringList("presents");
        presents.add(Serializer.serializeLocation(present.getLocation()));
        config().set("presents", presents);
        save();
    }

    public static void removePresent(Present present) {
        List<String> presents = config().getStringList("presents");
        presents.remove(Serializer.serializeLocation(present.getLocation()));
        config().set("presents", presents);
        save();

        HUNTPLAYERS.values().forEach(hp -> hp.removeCollected(present));

        PRESENTS.remove(present.getLocation());
    }

    private static void save() {
        PresentHunt.getInstance().saveConfig();
    }

    private static Configuration config() {
        return PresentHunt.getInstance().getConfig();
    }

    public static void join(UUID uuid) {
        HUNTPLAYERS.computeIfAbsent(uuid, k -> new HuntPlayer(uuid, new PlayerData(uuid)));
        HUNTPLAYERS.get(uuid).giveCollectible(Bukkit.getPlayer(uuid));

    }

    public static void quit(UUID uuid) {
        HuntPlayer huntPlayer = HUNTPLAYERS.get(uuid);
        List<String> collected = huntPlayer.getCollected().stream().map(present -> Serializer.serializeLocation(present.getLocation())).toList();
        huntPlayer.getPlayerData().set("collected", collected);
        huntPlayer.getPlayerData().save();
        HUNTPLAYERS.remove(uuid);
    }

}
