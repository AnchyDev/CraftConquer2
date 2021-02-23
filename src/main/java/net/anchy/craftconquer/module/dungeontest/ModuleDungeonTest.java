package net.anchy.craftconquer.module.dungeontest;

import net.anchy.craftconquer.Main;
import net.anchy.craftconquer.module.ModuleListener;
import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.world.PortalCreateEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class ModuleDungeonTest extends ModuleListener
{
    @Override
    public String getName()
    {
        return "DungeonTest";
    }

    private List<FallingBlock> portalBlockLocations;

    public ModuleDungeonTest()
    {
        portalBlockLocations = new ArrayList<>();

        /*new BukkitRunnable(){
            @Override
            public void run()
            {
                var portalBlockLocationsAdd = new ArrayList<FallingBlock>();
                var portalBlockLocationsRemoval = new ArrayList<FallingBlock>();
                for(var block : portalBlockLocations)
                {
                    var fallingBlock = (FallingBlock)block.getWorld().spawnFallingBlock(block.getLocation(), Material.GREEN_STAINED_GLASS.createBlockData());
                    fallingBlock.setGravity(false);
                    fallingBlock.setDropItem(false);
                    fallingBlock.setHurtEntities(false);
                    fallingBlock.setVelocity(new Vector(0, 0, 0));
                    block.remove();
                    portalBlockLocationsAdd.add(fallingBlock);
                    portalBlockLocationsRemoval.add(block);
                }

                for(var block : portalBlockLocationsAdd)
                {
                    portalBlockLocations.add(block);
                }
                for(var block : portalBlockLocationsRemoval)
                {
                    portalBlockLocations.remove(block);
                }
            }
        }.runTaskTimer(Main.getInst(), 0, 100);*/

        if(Bukkit.getWorld("dungeon-world") == null)
        {
            var creator = new WorldCreator("dungeon-world");
            creator.type(WorldType.FLAT);
            creator.generateStructures(false);
            creator.environment(World.Environment.NORMAL);
            creator.createWorld();
        }
    }

    @EventHandler
    public void onEntitySpawnEvent(EntitySpawnEvent event)
    {
        if(event.getEntity().getWorld().getName().equalsIgnoreCase("dungeon-world"))
        {
            event.setCancelled(true);
            return;
        }
    }

    @EventHandler
    public void onPortalCreateEvent(PortalCreateEvent event)
    {
        if(event.getReason() != PortalCreateEvent.CreateReason.FIRE)
        {
            return;
        }

        event.setCancelled(true);

        for(var block : event.getBlocks())
        {
            if(block.getType() == Material.NETHER_PORTAL)
            {
                block.getWorld().getBlockAt(block.getLocation()).setType(Material.AIR);
                var fallingBlock = (FallingBlock)block.getWorld().spawnFallingBlock(block.getLocation(), Material.GREEN_STAINED_GLASS.createBlockData());
                fallingBlock.setGravity(false);
                fallingBlock.setDropItem(false);
                fallingBlock.setHurtEntities(false);
                fallingBlock.setVelocity(new Vector(0, 0, 0));
                portalBlockLocations.add(fallingBlock);
            }
            else
            {
                block.getWorld().getBlockAt(block.getLocation()).setType(block.getBlock().getType());
            }
        }
    }
}
