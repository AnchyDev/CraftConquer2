package net.anchy.craftconquer.command;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public interface ISubCommand
{
    String getCommand();
    void onCommand(CommandSender sender, Command command, String s, String[] args);
    List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args);
}
