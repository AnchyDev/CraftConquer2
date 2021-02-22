package net.anchy.craftconquer.command.craftconquer.subcommand;

import net.anchy.craftconquer.command.ISubCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class SubCommandList implements ISubCommand
{
    @Override
    public String getCommand()
    {
        return "list";
    }

    @Override
    public void onCommand(CommandSender sender, Command command, String s, String[] args)
    {
        var sj = new StringJoiner(", ");
        for(var player : Bukkit.getOnlinePlayers())
        {
            sj.add(player.getName());
        }
        sender.sendMessage("Online Player(s): " + sj.toString());
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args)
    {
        return new ArrayList<>();
    }
}
