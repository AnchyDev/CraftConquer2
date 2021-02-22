package net.anchy.craftconquer.command.craftconquer.subcommand;

import net.anchy.craftconquer.command.ISubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class SubCommandPing implements ISubCommand
{
    @Override
    public String getCommand()
    {
        return "ping";
    }

    @Override
    public void onCommand(CommandSender sender, Command command, String s, String[] args)
    {
        sender.sendMessage("Pong!");
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args)
    {
        return new ArrayList<>();
    }
}
