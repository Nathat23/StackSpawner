package uk.antiperson.stackspawner;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

/**
 * Created by nathat on 08/01/17.
 */
public class Configuration {

    private File file;
    private FileConfiguration filecon;
    private StackSpawner ss;
    public Configuration(StackSpawner ss){
        this.ss = ss;
        file = new File(ss.getDataFolder(), "config.yml");
        filecon = YamlConfiguration.loadConfiguration(file);
    }

    public void generateConfig(){
        filecon.set("spawner.nametag.display", true);
        filecon.set("spawner.nametag.format", "&a%amount%x &6%type%");
        filecon.set("spawner.nametag.always-visible", true);
        filecon.set("spawner.stacking.limit", 0);
        filecon.set("spawner.stacking.radius", 5);
        try{
            filecon.save(file);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public boolean exists(){
        return file.exists();
    }

    public FileConfiguration getFilecon(){
        return filecon;
    }
}
