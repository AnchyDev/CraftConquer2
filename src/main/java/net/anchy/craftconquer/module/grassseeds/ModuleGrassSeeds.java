package net.anchy.craftconquer.module.grassseeds;

import net.anchy.craftconquer.module.ModuleListener;
import net.anchy.craftconquer.util.WeightBag;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class ModuleGrassSeeds extends ModuleListener
{
    private WeightBag<Material> seedBag;

    public ModuleGrassSeeds()
    {
        seedBag = new WeightBag<>();
        seedBag.addItem(Material.WHEAT_SEEDS, 0.1);
        seedBag.addItem(Material.PUMPKIN_SEEDS, 0.1);
        seedBag.addItem(Material.BEETROOT_SEEDS, 0.1);
        seedBag.addItem(Material.MELON_SEEDS, 0.1);
        seedBag.addItem(Material.AIR, 0.6);
    }

    @Override
    public String getName()
    {
        return "GrassSeeds";
    }

    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent event)
    {
        if(!isEnabled())
        {
            return;
        }

        Player player = event.getPlayer();

        if(player.getGameMode() == GameMode.CREATIVE)
        {
            return;
        }

        var block = event.getBlock();
        var blockType = block.getType();

        if (blockType == Material.GRASS || blockType == Material.TALL_GRASS)
        {
            block.getDrops().clear();
            event.setCancelled(true);
            block.setType(Material.AIR);

            Material randomSeed = seedBag.getItem();

            if (randomSeed != Material.AIR)
            {
                player.getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(randomSeed));
            }
        }
    }
}
