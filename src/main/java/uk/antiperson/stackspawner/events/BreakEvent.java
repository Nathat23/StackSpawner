package uk.antiperson.stackspawner.events;

import org.bukkit.Material;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import uk.antiperson.stackspawner.StackSpawner;

/**
 * Created by nathat on 07/01/17.
 */
public class BreakEvent implements Listener {

    private StackSpawner ss;
    public BreakEvent(StackSpawner ss){
        this.ss = ss;
    }

    @EventHandler
    public void onSpawnerBreak(final BlockBreakEvent e) {
        if(!e.isCancelled()){
            if(ss.spawnerAmount.containsKey(e.getBlock().getLocation())){
                if(ss.spawnerAmount.get(e.getBlock().getLocation()) > 1){
                    final EntityType et = ((CreatureSpawner) e.getBlock().getState()).getSpawnedType();
                    ss.getServer().getScheduler().runTaskLater(ss, new Runnable() {
                        @Override
                        public void run() {
                            e.getBlock().setType(Material.MOB_SPAWNER);
                            ((CreatureSpawner) e.getBlock().getState()).setSpawnedType(et);
                            ((CreatureSpawner) e.getBlock().getState()).setDelay(200);
                            ((CreatureSpawner) e.getBlock().getState()).update();
                            ss.spawnerAmount.put(e.getBlock().getLocation(), ss.spawnerAmount.get(e.getBlock().getLocation()) - 1);
                            ss.newSpawner.add(e.getBlock().getLocation());
                            ss.util.updateTag((ArmorStand) ss.util.getArmorStand(e.getBlock()));
                        }
                    }, 1);
                }else{
                    ss.util.getArmorStand(e.getBlock()).remove();
                    ss.spawnerAmount.remove(e.getBlock().getLocation());
                }
            }
        }
    }
}
