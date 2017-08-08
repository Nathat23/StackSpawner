package uk.antiperson.stackspawner;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by nathat on 06/01/17.
 */
public class Utilities {

    private StackSpawner ss;
    private Random rand = new Random();
    public Utilities(StackSpawner ss){
        this.ss = ss;
    }

    public Random random(){
        return rand;
    }

    public List<Block> getBlocks(Block start, int radius){
        int iterations = (radius * 2) + 1;
        List<Block> blocks = new ArrayList<Block>(iterations * iterations * iterations);
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    blocks.add(start.getRelative(x, y, z));
                }
            }
        }
        blocks.remove(start);
        return blocks;
    }

    public LivingEntity getArmorStand(Block block){
        for(Entity e : block.getWorld().getNearbyEntities(block.getLocation().add(0.5, 0, 0.5), 0.2, 0.1, 0.2)){
            if(e.getType() == EntityType.ARMOR_STAND){
                return (LivingEntity) e;
            }
        }
        return null;
    }

    public void createNewArmorstand(Block e){
        ArmorStand am = (ArmorStand) e.getWorld().spawnEntity(e.getLocation().add(0.5, 0, 0.5), EntityType.ARMOR_STAND);
        am.setSmall(true);
        am.setVisible(false);
        am.setGravity(false);
        if(!Bukkit.getVersion().contains("1.8")){
            am.setInvulnerable(true);
        }
        am.setCustomNameVisible(ss.config.getFilecon().getBoolean("spawner.nametag.always-visible"));
        ss.spawnerAmount.put(e.getLocation(), 1);
        if(ss.config.getFilecon().getBoolean("spawner.nametag.display")) {
            ss.util.updateTag(am);
        }
    }

    public void updateTag(ArmorStand as){
        CreatureSpawner cs = (CreatureSpawner) as.getLocation().getBlock().getState();
        String a = ChatColor.translateAlternateColorCodes('&', ss.config.getFilecon().getString("spawner.nametag.format"));
        String b = a.replace("%amount%", ss.spawnerAmount.get(cs.getLocation()).toString()).replace("%type%", StringUtils.capitalize(cs.getCreatureTypeName().replace("_", " ")));
        as.setCustomName(b);
    }
}
