package nl.scoutcraft.presenthunt.io;

import nl.scoutcraft.presenthunt.PresentHunt;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.UUID;

public class PlayerData extends YamlConfiguration {

    private final File file;

    public PlayerData(UUID uuid) {
        this.file = new File(PresentHunt.getInstance().getDataFolder(), "userdata" + File.separator + uuid.toString() + ".yml");
        this.reload();
    }

    private void reload() {
        try {
            load(this.file);
        } catch (Exception ignore) {
        }
    }

    public void save() {
        try {
            save(this.file);
        } catch (Exception ignore) {
        }
    }
}
