package net.anchy.craftconquer.command.craftconquer.subcommand;

import net.anchy.craftconquer.command.ISubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class SubCommandGive implements ISubCommand
{
    @Override
    public String getCommand()
    {
        return "give";
    }

    @Override
    public void onCommand(CommandSender sender, Command command, String alias, String[] args)
    {

    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args)
    {
        if(args.length == 2)
        {
            //Returning null by default returns the player list.
            return null;
        }

        if(args.length == 3)
        {
            var testItems = new ArrayList<String>();
            testItems.add("cc:item1");
            testItems.add("cc:item2");
            testItems.add("cc:item3");
            testItems.add("cc:item4");
            return testItems;
        }

        return new ArrayList<>();
    }
}
