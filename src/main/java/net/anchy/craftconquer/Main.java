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
        loadConfig();
        registerCommands();
    }

    private void loadConfig()
    {
        var pathCraftConquer = Path.of(Paths.craftConquer);

        if(Files.notExists(pathCraftConquer))
        {
            try
            {
                Files.createDirectory(pathCraftConquer);
            } catch (IOException e)
            {
                this.getLogger().log(Level.SEVERE, "Failed to create directory '"+pathCraftConquer.toString()+"'.");
                e.printStackTrace();

                this.getPluginLoader().disablePlugin(this);
            }
        }

        var pathLocaleConfig = Path.of(Paths.localeConfig);

        if(Files.notExists(pathLocaleConfig))
        {
            var serializer = new Gson();
            localeConfig = new LocaleConfig();
            var data = serializer.toJson(localeConfig);

            try
            {
                Files.createFile(pathLocaleConfig);
            } catch (IOException e)
            {
                this.getLogger().log(Level.SEVERE, "Failed to create file '"+pathLocaleConfig.toString()+"'.");
                e.printStackTrace();

                this.getPluginLoader().disablePlugin(this);
            }

            try
            {
                Files.writeString(pathLocaleConfig, data);
            } catch (IOException e)
            {
                this.getLogger().log(Level.SEVERE, "Failed to write to file '"+pathLocaleConfig.toString()+"'.");
                e.printStackTrace();

                this.getPluginLoader().disablePlugin(this);
            }
        }
        else
        {
            var serializer = new Gson();
            String json = null;
            try
            {
                json = Files.readString(pathLocaleConfig);
            } catch (IOException e)
            {
                this.getLogger().log(Level.SEVERE, "Failed to read from file '"+pathLocaleConfig.toString()+"'.");
                e.printStackTrace();

                this.getPluginLoader().disablePlugin(this);
            }
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
