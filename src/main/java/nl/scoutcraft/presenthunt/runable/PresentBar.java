package nl.scoutcraft.presenthunt.runable;

import nl.scoutcraft.eagle.server.locale.Placeholder;
import nl.scoutcraft.presenthunt.PresentHunt;
import nl.scoutcraft.presenthunt.data.Data;
import nl.scoutcraft.presenthunt.lang.Locale;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * It sends an action bar to all online players every 40 ticks (2 seconds) with the amount of presents
 * they've collected and the total amount of presents
 */
public class PresentBar extends BukkitRunnable {

    private final PresentHunt plugin;

    public PresentBar(PresentHunt plugin) {
        this.plugin = plugin;
    }

    /**
     * Start a repeating task that runs every 40 ticks (2 seconds)
     */
    public void start() {
        runTaskTimerAsynchronously(this.plugin, 0L, 40L);
    }

    /**
     * It sends an action bar to all online players with the amount of presents they've collected and
     * the total amount of presents
     */
    @Override
    public void run() {
        if (this.plugin.getServer().getOnlinePlayers().size() <= 0 || Data.PRESENTS.isEmpty())
            return;

        Placeholder total = new Placeholder("%total%", String.valueOf(Data.PRESENTS.size()));

        this.plugin.getServer().getOnlinePlayers().forEach(player -> {
            Locale.ACTION_BAR.sendActionBar(player, new Placeholder("%found%", String.valueOf(Data.HUNTPLAYERS.get(player.getUniqueId()).collectedCount())), total);
        });
    }
}
