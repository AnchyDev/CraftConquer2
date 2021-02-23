package net.anchy.craftconquer.command.craftconquer.subcommand;

import net.anchy.craftconquer.Main;
import net.anchy.craftconquer.command.ISubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class SubCommandModules implements ISubCommand
{
    @Override
    public String getCommand()
    {
        return "modules";
    }

    @Override
    public void onCommand(CommandSender sender, Command command, String alias, String[] args)
    {
        var modules = Main.getInst().getModuleRegistry().getModules();

        var sj = new StringJoiner(", ");
        for(var module : modules)
        {
            sj.add(module.getName());
        }

        sender.sendMessage(sj.toString());
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args)
    {
        return new ArrayList<>();
    }
}
