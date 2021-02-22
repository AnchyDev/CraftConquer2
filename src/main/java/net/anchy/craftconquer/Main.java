package net.anchy.craftconquer;

import com.google.gson.Gson;
import lombok.Getter;
import net.anchy.craftconquer.command.craftconquer.CommandCraftConquer;
import net.anchy.craftconquer.command.craftconquer.subcommand.SubCommandList;
import net.anchy.craftconquer.command.craftconquer.subcommand.SubCommandPing;
import net.anchy.craftconquer.config.LocaleConfig;
import net.anchy.craftconquer.util.Paths;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;

public class Main extends JavaPlugin
{
    @Getter
    private LocaleConfig localeConfig;

    @Override
    public void onEnable()
    {
        try
        {
            loadConfig();
        } catch (IOException e)
        {
            this.getLogger().log(Level.SEVERE, "Failed to load configs.");
            e.printStackTrace();

            this.getPluginLoader().disablePlugin(this);
            return;
        }
        registerCommands();
    }

    private void loadConfig() throws IOException
    {
        var pathCraftConquer = Path.of(Paths.craftConquer);

        if(Files.notExists(pathCraftConquer))
        {
            Files.createDirectory(pathCraftConquer);
        }

        var pathLocaleConfig = Path.of(Paths.localeConfig);

        if(Files.notExists(pathLocaleConfig))
        {
            var serializer = new Gson();
            localeConfig = new LocaleConfig();
            var data = serializer.toJson(localeConfig);
            Files.createFile(pathLocaleConfig);
            Files.writeString(pathLocaleConfig, data);
        }
        else
        {
            var serializer = new Gson();
            var json = Files.readString(pathLocaleConfig);
            localeConfig = serializer.fromJson(json, LocaleConfig.class);
        }
    }

    private void registerCommands()
    {
        var commandCraftConquer = new CommandCraftConquer();
        commandCraftConquer.registerSubCommand(new SubCommandList());
        commandCraftConquer.registerSubCommand(new SubCommandPing());
        this.getCommand(commandCraftConquer.getCommand()).setExecutor(commandCraftConquer);
    }
}
