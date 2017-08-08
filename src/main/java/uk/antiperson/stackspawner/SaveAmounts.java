package uk.antiperson.stackspawner;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by nathat on 08/01/17.
 */
public class SaveAmounts {

    private StackSpawner ss;
    private File file;
    private FileConfiguration filecon;
    public SaveAmounts(StackSpawner ss){
        this.ss = ss;
        file = new File(ss.getDataFolder(), "spawnerinfo.yml");
        filecon = YamlConfiguration.loadConfiguration(file);
    }


    public void loadStacks(){
        ss.getLogger().info("Loading spawner stack amounts...");
        for(String s : filecon.getKeys(false)){
            Double x = filecon.getDouble(s + ".x");
            Double y = filecon.getDouble(s + ".y");
            Double z = filecon.getDouble(s + ".z");
            World world = Bukkit.getWorld(UUID.fromString(filecon.getString(s + ".world")));
            Location loc = new Location(world, x, y, z);
            ss.spawnerAmount.put(loc, filecon.getInt(s + ".amount"));
        }
        ss.getLogger().info("Loaded spawner stack amounts!");
        file.delete();
    }


    public void saveStacks(){
        ss.getLogger().info("Saving spawner stack amounts...");
        filecon.options().header("This file is for the storing of StackSpawner spawner amounts. \n There is nothing that should be changed here.");
        int i = 0;
        for(Location loc : ss.spawnerAmount.keySet()){
            i++;
            filecon.set(i + ".x", loc.getX());
            filecon.set(i + ".y", loc.getY());
            filecon.set(i + ".z", loc.getZ());
            filecon.set(i + ".world", loc.getWorld().getUID().toString());
            filecon.set(i + ".amount", ss.spawnerAmount.get(loc));
        }
        try{
            filecon.save(file);
            ss.getLogger().info("Saved all spawner stack amounts!");
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
