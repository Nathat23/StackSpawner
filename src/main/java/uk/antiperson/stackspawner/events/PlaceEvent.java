package uk.antiperson.stackspawner.events;

import de.dustplanet.silkspawners.events.SilkSpawnersSpawnerPlaceEvent;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import uk.antiperson.stackspawner.StackSpawner;

/**
 * Created by nathat on 06/01/17.
 */
public class PlaceEvent implements Listener {

    private StackSpawner ss;
    public PlaceEvent(StackSpawner ss){
        this.ss = ss;
    }

    @EventHandler
    public void onBlockPlace(final BlockPlaceEvent e){
        if(e.getBlock().getType() == Material.MOB_SPAWNER && e.getPlayer().hasPermission("stackspawner.stack")){
            ss.getServer().getScheduler().runTaskLater(ss, new Runnable() {
                @Override
                public void run() {
                    CreatureSpawner first = (CreatureSpawner) e.getBlock().getState();
                    for(Block block : ss.util.getBlocks(e.getBlock(), ss.config.getFilecon().getInt("spawner.stacking.radius"))){
                        if(block.getType() == Material.MOB_SPAWNER){
                            CreatureSpawner cs = (CreatureSpawner) block.getState();
                            if(cs.getSpawnedType() == first.getSpawnedType()){
                                ((CreatureSpawner) e.getBlock().getState()).setDelay(200);
                                e.getBlock().getState().update();
                                if(ss.spawnerAmount.containsKey(block.getLocation())){
                                    if((ss.spawnerAmount.get(block.getLocation()) < ss.config.getFilecon().getInt("spawner.stacking.limit")) || ss.config.getFilecon().getInt("spawner.stacking.limit") == 0){
                                        ss.spawnerAmount.put(block.getLocation(), ss.spawnerAmount.get(block.getLocation()) + 1);
                                        if(ss.config.getFilecon().getBoolean("spawner.nametag.display")){
                                            LivingEntity le = ss.util.getArmorStand(block);
                                            ss.util.updateTag((ArmorStand) le);
                                        }
                                        e.getBlock().setType(Material.AIR);
                                        return;
                                    }
                                }

                            }
                        }
                    }
                    ss.util.createNewArmorstand(e.getBlock());
                }
            }, 1);
        }
    }

}
