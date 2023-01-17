package nl.scoutcraft.presenthunt.object;

import nl.scoutcraft.presenthunt.data.Data;
import nl.scoutcraft.presenthunt.data.Item;
import nl.scoutcraft.presenthunt.io.PlayerData;
import nl.scoutcraft.presenthunt.lang.Locale;
import nl.scoutcraft.presenthunt.util.Serializer;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

public class HuntPlayer {

    private final UUID uuid;
    private final List<Present> collected;
    private final PlayerData playerData;
    private boolean canFly;

    public HuntPlayer(UUID uuid, PlayerData playerData) {
        this.uuid = uuid;
        this.collected = playerData.getStringList("collected").stream().map(Serializer::deserializeLocation).map(Data.PRESENTS::get).filter(Objects::nonNull).collect(Collectors.toList());
        this.playerData = playerData;
        this.canFly = true;
    }

    public boolean hasCollected(Present present) {
        return this.collected.contains(present);
    }

    public boolean giveCollectible(Player player) {
        if (this.collectedCount() != Data.PRESENTS.size()) return false;

        if (player.getInventory().contains(Item.COLLECTIBLE.build(player))) return false;

        player.getInventory().setItem(4, Item.COLLECTIBLE.build(player));

        return true;
    }

    public boolean collect(Present present, Player player) {
        this.collected.add(present);
        if (this.giveCollectible(player)) {
            Locale.PRESENT_COMPLETED.send(player);
            return false;
        }
        return true;
    }

    public void removeCollected(Present present) {
        this.collected.remove(present);
    }

    public List<Present> getCollected() {
        return this.collected;
    }

    public int collectedCount() {
        return this.collected.size();
    }

    public PlayerData getPlayerData() {
        return this.playerData;
    }

    public void setCanFly(boolean canFly) {
        this.canFly = canFly;
    }

    public boolean getCanFly() {
        return this.canFly;
    }
}
