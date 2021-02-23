package net.anchy.craftconquer.command.craftconquer;

import net.anchy.craftconquer.command.BaseCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.StringJoiner;

public class CommandCraftConquer extends BaseCommand
{
    @Override
    public String getCommand()
    {
        return "craftconquer";
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args)
    {
        if(args.length == 0)
        {
            var sj = new StringJoiner(", ");

            for(var subcommand : getSubCommands().entrySet())
            {
                sj.add(subcommand.getKey());
            }

            sender.sendMessage("Command(s): " + sj.toString());
        }

        return super.onCommand(sender, command, alias, args);
    }
}
