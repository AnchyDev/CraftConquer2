package net.anchy.craftconquer.config;

import lombok.Getter;
import lombok.Setter;

public class LocaleConfig
{
    @Getter @Setter
    private String locale;

    public LocaleConfig()
    {
        locale = "en";
    }
}
