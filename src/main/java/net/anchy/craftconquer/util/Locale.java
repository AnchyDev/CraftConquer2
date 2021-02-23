package net.anchy.craftconquer.util;

import net.anchy.craftconquer.Main;
import org.bukkit.ChatColor;

import java.util.HashMap;

public class Locale
{
    private HashMap<String, String> localeMessages;

    public Locale()
    {
        localeMessages = new HashMap<>();
        localeMessages.put("ERR_NO_PERMISSION", "You do not have permission to run this command.");
        localeMessages.put("CHAT_PREFIX", "&f[&bCraft & Conquer&f]");
        localeMessages.put("LIST_PLAYERS", "Online Player(s): ");
        localeMessages.put("LIST_MODULES", "Module(s): ");
        localeMessages.put("LIST_COMMANDS", "Command(s): ");
    }

    public String getMessage(String key)
    {
        return ChatColor.translateAlternateColorCodes('&', localeMessages.get(key));
    }

    public static Locale getLocale()
    {
        return Main.getInst().getLocale();
    }
}
