package net.anchy.craftconquer.command.craftconquer.subcommand;

import net.anchy.craftconquer.command.ISubCommand;
import net.anchy.craftconquer.util.Locale;
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
    public void onCommand(CommandSender sender, Command command, String alias, String[] args)
    {
        var sj = new StringJoiner(", ");
        for(var player : Bukkit.getOnlinePlayers())
        {
            sj.add(player.getName());
        }
        sender.sendMessage(Locale.getLocale().getMessage("LIST_PLAYERS") + sj.toString());
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args)
    {
        return new ArrayList<>();
    }
}
