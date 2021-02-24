package net.anchy.craftconquer.module.regiontest;

import lombok.Getter;
import lombok.Setter;
import net.anchy.craftconquer.module.ModuleListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class ModuleRegionTest extends ModuleListener
{
    private boolean firstRegion;

    private Location loc1;
    private Location loc2;

    public ModuleRegionTest()
    {
        firstRegion = true;
    }

    @Override
    public String getName()
    {
        return "RegionTest";
    }

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent event)
    {
        if(event.getItem() == null)
        {
            return;
        }

        if(event.getItem().getType() != Material.NETHER_STAR)
        {
            return;
        }

        if(event.getAction() != Action.RIGHT_CLICK_BLOCK)
        {
            return;
        }

        if(firstRegion)
        {
            loc1 = event.getClickedBlock().getLocation();
            event.getPlayer().sendMessage("First location set at " + loc1.toString());
            firstRegion = false;
            return;
        }
        else
        {
            loc2 = event.getClickedBlock().getLocation();
            event.getPlayer().sendMessage("Second location set at " + loc1.toString());
            firstRegion = true;

            for(var block : blocksFromTwoPoints(loc1, loc2))
            {
                var loc = new Location(event.getPlayer().getWorld(), block.getX() + 0.5, block.getY(), block.getZ() + 0.5);
                var fallingBlock = (FallingBlock) event.getPlayer().getWorld().spawnFallingBlock(loc, Material.GLASS.createBlockData());
                fallingBlock.setGravity(false);
                fallingBlock.setDropItem(false);
                fallingBlock.setHurtEntities(false);
                if(block.corner)
                {
                    Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();
                    var team = board.getTeam("blueTeam");

                    if(team == null)
                    {
                        team = board.registerNewTeam("blueTeam");
                        team.setColor(ChatColor.BLUE);
                    }

                    team.addEntry(fallingBlock.getUniqueId().toString());
                }
                else
                {
                    Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();
                    var team = board.getTeam("lightBlueTeam");

                    if(team == null)
                    {
                        team = board.registerNewTeam("lightBlueTeam");
                        team.setColor(ChatColor.AQUA);
                    }

                    team.addEntry(fallingBlock.getUniqueId().toString());
                }
                fallingBlock.setGlowing(true);
                fallingBlock.setVelocity(new Vector(0, 0, 0));
            }

            return;
        }
    }

    //region code ripped from here, i wrote the edge code
    //https://bukkit.org/threads/block-listed-within-cuboid-x-y-coodinates.103681/
    public List<Vector3> blocksFromTwoPoints(Location loc1, Location loc2)
    {
        var blocks = new ArrayList<Vector3>();

        int topBlockX = (loc1.getBlockX() < loc2.getBlockX() ? loc2.getBlockX() : loc1.getBlockX());
        int bottomBlockX = (loc1.getBlockX() > loc2.getBlockX() ? loc2.getBlockX() : loc1.getBlockX());

        int topBlockY = (loc1.getBlockY() < loc2.getBlockY() ? loc2.getBlockY() : loc1.getBlockY());
        int bottomBlockY = (loc1.getBlockY() > loc2.getBlockY() ? loc2.getBlockY() : loc1.getBlockY());

        int topBlockZ = (loc1.getBlockZ() < loc2.getBlockZ() ? loc2.getBlockZ() : loc1.getBlockZ());
        int bottomBlockZ = (loc1.getBlockZ() > loc2.getBlockZ() ? loc2.getBlockZ() : loc1.getBlockZ());

        for(int x = bottomBlockX; x <= topBlockX; x++)
        {
            for(int z = bottomBlockZ; z <= topBlockZ; z++)
            {
                for(int y = bottomBlockY; y <= topBlockY; y++)
                {
                    if((x == bottomBlockX || x == topBlockX) && (y == bottomBlockY || y == topBlockY) ||
                            (x == bottomBlockX || x == topBlockX) && (z == bottomBlockZ || z== topBlockZ) ||
                            (z == bottomBlockZ || z == topBlockZ) && (y == bottomBlockY || y == topBlockY))
                    {
                        var block = loc1.getWorld().getBlockAt(x, y, z);
                        boolean corner = false;

                        if(x == bottomBlockX && y == bottomBlockY && z == bottomBlockZ ||
                                x == topBlockX && y == topBlockY && z == topBlockZ ||
                                x == topBlockX && y == bottomBlockY && z == topBlockZ ||
                                x == bottomBlockX && y == topBlockY && z == bottomBlockZ ||
                                x == topBlockX && y == topBlockY && z == bottomBlockZ ||
                                x == bottomBlockX && y == topBlockY && z == topBlockZ ||
                                x == bottomBlockX && y == bottomBlockY && z == topBlockZ ||
                                x == topBlockX && y == bottomBlockY && z == bottomBlockZ)
                        {
                            corner = true;
                        }

                        blocks.add(new Vector3(block.getX(), block.getY(), block.getZ(), corner));
                    }
                }
            }
        }

        return blocks;
    }
    public class Vector3
    {
        public Vector3(double x, double y, double z, boolean corner)
        {
            this.x = x;
            this.y = y;
            this.z = z;
            this.corner = corner;
        }

        @Getter @Setter
        private double x;

        @Getter @Setter
        private double y;

        @Getter @Setter
        private double z;

        private boolean corner;
    }
}