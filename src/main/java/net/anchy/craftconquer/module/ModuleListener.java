package net.anchy.craftconquer.module;

import lombok.Getter;
import lombok.Setter;

public class ModuleListener implements IModule
{
    @Getter @Setter
    private boolean enabled;

    @Override
    public String getName()
    {
        return null;
    }
}
