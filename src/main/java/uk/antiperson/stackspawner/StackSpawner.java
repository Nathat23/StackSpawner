package uk.antiperson.stackspawner;

import org.bstats.bukkit.Metrics;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import uk.antiperson.stackspawner.events.BreakEvent;
import uk.antiperson.stackspawner.events.PlaceEvent;
import uk.antiperson.stackspawner.events.SpawnerEvent;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by nathat on 06/01/17.
 */
public class StackSpawner extends JavaPlugin {

    public Utilities util = new Utilities(this);
    public HashMap<Location, Integer> spawnerAmount = new HashMap<Location, Integer>();
    public HashSet<Location> newSpawner = new HashSet<Location>();
    public Configuration config = new Configuration(this);
    private SaveAmounts sa = new SaveAmounts(this);

    @Override
    public void onEnable(){
        getLogger().info("StackSpawner v" + getDescription().getVersion() + " by antiPerson");
        getLogger().info("Find more information at " + getDescription().getWebsite());
        sa.loadStacks();
        if(!config.exists()){
            getLogger().info("Generating new configuration file...");
            config.generateConfig();
            getLogger().info("Generated configuration file!");
        }
        getLogger().info("Registering event listeners and commands...");
        getServer().getPluginManager().registerEvents(new SpawnerEvent(this), this);
        getServer().getPluginManager().registerEvents(new PlaceEvent(this), this);
        getServer().getPluginManager().registerEvents(new BreakEvent(this), this);
        getCommand("sts").setExecutor(new Commands(this));
        getLogger().info("Regestered event listeners and commands!");
        new Metrics(this);
    }

    @Override
    public void onDisable(){
        sa.saveStacks();
    }


}
