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

/**
 * It's a class that represents a player in the hunt minigame
 */
public class HuntPlayer {

    private final UUID uuid;
    private final List<Present> collected;
    private final PlayerData playerData;
    private boolean canFly;

    // It's a constructor that sets the UUID, the collected presents, the player data and the canFly
    // boolean.
    public HuntPlayer(UUID uuid, PlayerData playerData) {
        this.uuid = uuid;
        this.collected = playerData.getStringList("collected").stream().map(Serializer::deserializeLocation).map(Data.PRESENTS::get).filter(Objects::nonNull).collect(Collectors.toList());
        this.playerData = playerData;
        this.canFly = true;
    }

    /**
     * Returns true if the present is in the list of collected presents.
     * 
     * @param present The present to check if it has been collected.
     * @return A boolean value.
     */
    public boolean hasCollected(Present present) {
        return this.collected.contains(present);
    }

    /**
     * If the player has collected all the presents, give them the collectible
     * 
     * @param player The player who is receiving the collectible.
     * @return A boolean value.
     */
    public boolean giveCollectible(Player player) {
        if (this.collectedCount() != Data.PRESENTS.size()) return false;

        if (player.getInventory().contains(Item.COLLECTIBLE.build(player))) return false;

        player.getInventory().setItem(4, Item.COLLECTIBLE.build(player));

        return true;
    }

    /**
     * If the player has collected all the presents, give them the collectible and return false.
     * Otherwise, return true
     * 
     * @param present The present that was collected
     * @param player The player who collected the present
     * @return A boolean value.
     */
    public boolean collect(Present present, Player player) {
        this.collected.add(present);
        if (this.giveCollectible(player)) {
            Locale.PRESENT_COMPLETED.send(player);
            return false;
        }
        return true;
    }

    /**
     * Remove the present from the list of collected presents.
     * 
     * @param present The present to remove from the list of collected presents.
     */
    public void removeCollected(Present present) {
        this.collected.remove(present);
    }

    /**
     * This function returns the list of presents that the player has collected.
     * 
     * @return A list of presents that have been collected.
     */
    public List<Present> getCollected() {
        return this.collected;
    }

    /**
     * This function returns the number of elements in the collected array.
     * 
     * @return The number of elements in the collected array.
     */
    public int collectedCount() {
        return this.collected.size();
    }

    /**
     * It returns the playerData variable
     * 
     * @return The playerData variable is being returned.
     */
    public PlayerData getPlayerData() {
        return this.playerData;
    }

    /**
     * This function sets the value of the canFly variable to the value of the canFly parameter.
     * 
     * @param canFly Whether or not the entity can fly.
     */
    public void setCanFly(boolean canFly) {
        this.canFly = canFly;
    }

    /**
     * This function returns a boolean value that indicates whether or not the player can fly.
     * 
     * @return The boolean value of the variable canFly.
     */
    public boolean getCanFly() {
        return this.canFly;
    }
}
