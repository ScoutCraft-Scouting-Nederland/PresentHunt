package nl.scoutcraft.presenthunt;

import nl.scoutcraft.eagle.libs.locale.Internationalization;
import nl.scoutcraft.eagle.libs.locale.ServerResourceGetter;
import nl.scoutcraft.presenthunt.command.MainCommand;
import nl.scoutcraft.presenthunt.data.Data;
import nl.scoutcraft.presenthunt.listener.BlockBreakListener;
import nl.scoutcraft.presenthunt.listener.CollectibleListener;
import nl.scoutcraft.presenthunt.listener.ConnectionListener;
import nl.scoutcraft.presenthunt.listener.PresentInteractListener;
import nl.scoutcraft.presenthunt.runable.PresentBar;
import nl.scoutcraft.presenthunt.runable.PresentParticles;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class PresentHunt extends JavaPlugin {

    private static PresentHunt plugin;
    private Internationalization lang;

    @Override
    public void onLoad() {
        plugin = this;
    }

    @Override
    public void onEnable() {
        this.saveDefaultConfig();

        this.lang = Internationalization.builder("messages", new ServerResourceGetter(this))
                .setLangDir(super.getDataFolder().toPath().resolve("lang"))
                .addDefaultLangFiles("nl", "en")
                .build();

        Data.loadPresents(this.getConfig());

        this.initialize();

        new PresentParticles(this).start();
        new PresentBar(this).start();
    }

    @Override
    public void onDisable() {

    }

    /**
     * It registers all of the event listeners
     */
    private void initialize() {
        PluginManager pluginManager = this.getServer().getPluginManager();
        pluginManager.registerEvents(new BlockBreakListener(), this);
        pluginManager.registerEvents(new CollectibleListener(), this);
        pluginManager.registerEvents(new ConnectionListener(), this);
        pluginManager.registerEvents(new PresentInteractListener(), this);
        this.getCommand("hunt").setExecutor(new MainCommand());
    }

    /**
     * This function returns the instance of the plugin.
     * 
     * @return The instance of the plugin.
     */
    public static PresentHunt getInstance() {
        return plugin;
    }

    /**
     * This function returns the language of the current user
     * 
     * @return The value of the lang variable.
     */
    public Internationalization getLang() {
        return this.lang;
    }
}
