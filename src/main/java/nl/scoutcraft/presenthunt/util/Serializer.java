package nl.scoutcraft.presenthunt.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;

/**
 * It converts a location to a string and vice versa
 */
public class Serializer {

    /**
     * It takes a string, splits it into an array, and then returns a new location with the world, x,
     * y, and z values from the array
     * 
     * @param value The string to deserialize
     * @return A location
     */
    public static Location deserializeLocation(String value) {
        String[] string = value.split(";");
        return new Location(Bukkit.getWorld(string[0]), Double.parseDouble(string[1]),
                Double.parseDouble(string[2]),
                Double.parseDouble(string[3]));
    }

    /**
     * It takes a location and returns a string that can be used to recreate the location
     * 
     * @param location The location you want to serialize.
     * @return A string
     */
    public static String serializeLocation(Location location) {
        return location.getWorld().getName() + ";" +
                location.getX() + ";" +
                location.getY() + ";" +
                location.getZ();
    }
}
