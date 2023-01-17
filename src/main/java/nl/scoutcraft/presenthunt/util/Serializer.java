package nl.scoutcraft.presenthunt.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class Serializer {

    public static Location deserializeLocation(String value) {
        String[] string = value.split(";");
        return new Location(Bukkit.getWorld(string[0]), Double.parseDouble(string[1]),
                Double.parseDouble(string[2]),
                Double.parseDouble(string[3]));
    }

    public static String serializeLocation(Location location) {
        return location.getWorld().getName() + ";" +
                location.getX() + ";" +
                location.getY() + ";" +
                location.getZ();
    }
}
