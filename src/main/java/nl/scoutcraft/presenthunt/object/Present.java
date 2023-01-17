package nl.scoutcraft.presenthunt.object;

import org.bukkit.Location;

public record Present(Location location) {

    // It's a getter for the location variable.
    public Location getLocation() {
        return this.location;
    }
}
