package net.anchy.craftconquer.command;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseCommand implements TabExecutor
{
    @Getter
    private HashMap<String, ISubCommand> subCommands;

    @Getter @Setter
    private String command;

    public BaseCommand()
    {
        subCommands = new HashMap<>();
    }

    public void registerSubCommand(ISubCommand executor)
    {
        subCommands.put(executor.getCommand(), executor);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args)
    {
        HashMap<String, ISubCommand> commands = getSubCommands();

        if (args.length < 1)
        {
            return true;
        }

        if (getSubCommands().size() < 1)
        {
            return true;
        }

        for (Map.Entry<String, ISubCommand> entry : commands.entrySet())
        {
            String subCommand = entry.getKey();
            ISubCommand executor = entry.getValue();

            if (args[0].equalsIgnoreCase(subCommand))
            {
                if(!sender.hasPermission(getCommand().toLowerCase() + "." + subCommand.toLowerCase()))
                {
                    sender.sendMessage("You do not have permission to execute this command.");
                    continue;
                }

                executor.onCommand(sender, command, alias, args);
            }
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args)
    {
        if (args.length == 1)
        {
            if (getSubCommands().size() < 1)
            {
                return null;
            }

            var commands = new ArrayList<String>();

            for (Map.Entry<String, ISubCommand> entry : getSubCommands().entrySet())
            {
                String subCommand = entry.getKey();

                if(!sender.hasPermission(getCommand().toLowerCase() + "." + subCommand.toLowerCase()))
                {
                    continue;
                }

                commands.add(subCommand);
            }

            return commands;
        } else if (args.length > 1)
        {
            for (Map.Entry<String, ISubCommand> entry : getSubCommands().entrySet())
            {
                String subCommand = entry.getKey();

                if (args[0].equalsIgnoreCase(subCommand))
                {
                    return entry.getValue().onTabComplete(sender, cmd, alias, args);
                }
            }
        }

        return new ArrayList<>();
    }
}
