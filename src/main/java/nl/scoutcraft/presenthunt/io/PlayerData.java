package nl.scoutcraft.presenthunt.io;

import nl.scoutcraft.presenthunt.PresentHunt;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.UUID;

/**
 * It's a wrapper for the Bukkit YamlConfiguration class that automatically loads and saves the file
 */
public class PlayerData extends YamlConfiguration {

    private final File file;

    // It's creating a new file in the userdata folder with the name of the UUID.
    public PlayerData(UUID uuid) {
        this.file = new File(PresentHunt.getInstance().getDataFolder(), "userdata" + File.separator + uuid.toString() + ".yml");
        this.reload();
    }

    /**
     * It tries to load the file, and if it fails, it does nothing
     */
    private void reload() {
        try {
            load(this.file);
        } catch (Exception ignore) {
        }
    }

    /**
     * If the save function throws an exception, ignore it.
     */
    public void save() {
        try {
            save(this.file);
        } catch (Exception ignore) {
        }
    }
}
