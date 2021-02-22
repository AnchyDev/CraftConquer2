package net.anchy.craftconquer;

import net.anchy.craftconquer.command.craftconquer.CommandCraftConquer;
import net.anchy.craftconquer.command.craftconquer.subcommand.SubCommandList;
import net.anchy.craftconquer.command.craftconquer.subcommand.SubCommandPing;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin
{
    @Override
    public void onEnable()
    {
        registerCommands();
    }

    private void registerCommands()
    {
        var commandCraftConquer = new CommandCraftConquer();
        commandCraftConquer.registerSubCommand(new SubCommandList());
        commandCraftConquer.registerSubCommand(new SubCommandPing());
        this.getCommand(commandCraftConquer.getCommand()).setExecutor(commandCraftConquer);
    }
}
