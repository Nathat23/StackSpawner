package uk.antiperson.stackspawner.events;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.SpawnerSpawnEvent;
import uk.antiperson.stackmob.StackMob;
import uk.antiperson.stackmob.api.EntityManager;
import uk.antiperson.stackspawner.StackSpawner;

/**
 * Created by nathat on 07/01/17.
 */
public class SpawnerEvent implements Listener {

    private StackSpawner ss;
    public SpawnerEvent(StackSpawner ss){
        this.ss = ss;
    }

    @EventHandler
    public void onSpawnerSpawn(SpawnerSpawnEvent e){
        if(ss.spawnerAmount.containsKey(e.getSpawner().getLocation())){
            if(ss.newSpawner.contains(e.getSpawner().getLocation())){
                ss.newSpawner.remove(e.getSpawner().getLocation());
                e.getSpawner().setDelay(-1);
            }
            int amount = (int) Math.round((0.5 + ss.util.random().nextDouble()) * ss.spawnerAmount.get(e.getSpawner().getLocation()));
            if(Bukkit.getPluginManager().getPlugin("StackMob") != null && Bukkit.getPluginManager().getPlugin("StackMob").isEnabled()){
                StackMob api = ((StackMob) Bukkit.getPluginManager().getPlugin("StackMob"));
                EntityManager em = new EntityManager(api);
                em.addNewStack(e.getEntity(), amount);
            }else{
                for(int i = 0; i < amount; i++){
                    e.getEntity().getWorld().spawnEntity(e.getLocation(), e.getEntityType());
                }
            }
        }
    }
}
