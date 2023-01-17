package nl.scoutcraft.presenthunt.runable;

import nl.scoutcraft.eagle.server.locale.Placeholder;
import nl.scoutcraft.presenthunt.PresentHunt;
import nl.scoutcraft.presenthunt.data.Data;
import nl.scoutcraft.presenthunt.lang.Locale;
import org.bukkit.scheduler.BukkitRunnable;

public class PresentBar extends BukkitRunnable {

    private final PresentHunt plugin;

    public PresentBar(PresentHunt plugin) {
        this.plugin = plugin;
    }

    public void start() {
        runTaskTimerAsynchronously(this.plugin, 0L, 40L);
    }

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
