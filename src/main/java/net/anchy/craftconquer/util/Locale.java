package net.anchy.craftconquer.util;

import net.anchy.craftconquer.Main;

import java.util.HashMap;

public class Locale
{
    private HashMap<String, String> localeMessages;

    public Locale()
    {
        localeMessages = new HashMap<>();
        localeMessages.put("ERR_NO_PERMISSION", "You do not have permission to run this command.");
        localeMessages.put("LIST_PLAYERS", "Online Player(s): ");
    }

    public String getMessage(String key)
    {
        return localeMessages.get(key);
    }

    public static Locale getLocale()
    {
        return Main.getInst().getLocale();
    }
}
