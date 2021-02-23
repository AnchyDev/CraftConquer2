package net.anchy.craftconquer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import net.anchy.craftconquer.command.craftconquer.CommandCraftConquer;
import net.anchy.craftconquer.command.craftconquer.subcommand.SubCommandList;
import net.anchy.craftconquer.command.craftconquer.subcommand.SubCommandPing;
import net.anchy.craftconquer.config.LocaleConfig;
import net.anchy.craftconquer.util.Locale;
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

    @Getter
    private Locale locale;

    @Getter
    private static Main inst;

    private boolean firstRun = false;

    @Override
    public void onEnable()
    {
        if(inst == null)
        {
            inst = this;
        }

        loadConfig();
        loadLocale();
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

                firstRun = true;
            } catch (IOException e)
            {
                this.getLogger().log(Level.SEVERE, "Failed to create directory '"+pathCraftConquer.toString()+"'.");
                e.printStackTrace();

                this.getPluginLoader().disablePlugin(this);
                return;
            }
        }

        var pathLocales = Path.of(Paths.locales);

        if(Files.notExists(pathLocales))
        {
            try
            {
                Files.createDirectory(pathLocales);
            } catch (IOException e)
            {
                this.getLogger().log(Level.SEVERE, "Failed to create directory '"+pathLocales.toString()+"'.");
                e.printStackTrace();

                this.getPluginLoader().disablePlugin(this);
                return;
            }
        }

        var pathLocaleConfig = Path.of(Paths.localeConfig);

        if(Files.notExists(pathLocaleConfig))
        {
            var serializer = new GsonBuilder().setPrettyPrinting().create();
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
                return;
            }

            try
            {
                Files.writeString(pathLocaleConfig, data);
            } catch (IOException e)
            {
                this.getLogger().log(Level.SEVERE, "Failed to write to file '"+pathLocaleConfig.toString()+"'.");
                e.printStackTrace();

                this.getPluginLoader().disablePlugin(this);
                return;
            }
        }
        else
        {

            try
            {
                var serializer = new Gson();
                var json = Files.readString(pathLocaleConfig);
                localeConfig = serializer.fromJson(json, LocaleConfig.class);
            } catch (IOException e)
            {
                this.getLogger().log(Level.SEVERE, "Failed to read from file '"+pathLocaleConfig.toString()+"'.");
                e.printStackTrace();

                this.getPluginLoader().disablePlugin(this);
                return;
            }
        }
    }

    private void loadLocale()
    {
        var localeId = localeConfig.getLocale();
        var localePath = Path.of(Paths.locales.concat("/" + localeId + ".json"));

        if(firstRun)
        {
            var serializer = new GsonBuilder().setPrettyPrinting().create();
            locale = new Locale();
            var data = serializer.toJson(locale);

            try
            {
                Files.createFile(localePath);
            } catch (IOException e)
            {
                this.getLogger().log(Level.SEVERE, "Failed to create locale '"+localePath.toString()+"'.");
                e.printStackTrace();

                this.getPluginLoader().disablePlugin(this);
                return;
            }

            try
            {
                Files.writeString(localePath, data);
            } catch (IOException e)
            {
                this.getLogger().log(Level.SEVERE, "Failed to write to locale '"+localePath.toString()+"'.");
                e.printStackTrace();

                this.getPluginLoader().disablePlugin(this);
                return;
            }
        }
        else
        {
            if(Files.notExists(localePath))
            {
                this.getLogger().log(Level.SEVERE, "Locale '"+localePath.toString()+"' does not exist..");

                this.getPluginLoader().disablePlugin(this);
                return;
            }
            else
            {
                try
                {
                    var serializer = new Gson();
                    var json = Files.readString(localePath);
                    locale = serializer.fromJson(json, Locale.class);
                } catch (IOException e)
                {
                    this.getLogger().log(Level.SEVERE, "Failed to read from locale '"+localePath.toString()+"'.");
                    e.printStackTrace();

                    this.getPluginLoader().disablePlugin(this);
                    return;
                }
            }
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
