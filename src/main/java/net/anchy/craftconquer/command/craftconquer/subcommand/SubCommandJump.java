package net.anchy.craftconquer.command.craftconquer.subcommand;

import net.anchy.craftconquer.command.ISubCommand;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SubCommandJump implements ISubCommand
{

    @Override
    public String getCommand()
    {
        return "jump";
    }

    @Override
    public void onCommand(CommandSender sender, Command command, String alias, String[] args)
    {
        if(!(sender instanceof Player))
        {
            sender.sendMessage("You can only run this command from the client.");
            return;
        }

        if(args.length < 2)
        {
            sender.sendMessage("Missing arguments.");
            return;
        }

        var world = args[1];

        if(Bukkit.getWorld(world) == null)
        {
            sender.sendMessage("World doesn't exist.");
            return;
        }

        var location = new Location(Bukkit.getWorld(world), 0, 32, 0);
        var player = (Player)sender;
        player.teleport(location);
        player.sendMessage("Teleporting to " + world);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args)
    {
        if(args.length == 2)
        {
            var arguments = new ArrayList<String>();
            arguments.add("<world>");

            return arguments;
        }

        return new ArrayList<>();
    }
}
