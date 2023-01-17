package nl.scoutcraft.presenthunt.object;

import org.bukkit.Location;

public record Present(Location location) {

    public Location getLocation() {
        return this.location;
    }
}
