package net.anchy.craftconquer.module;

import net.anchy.craftconquer.Main;

import java.util.ArrayList;
import java.util.List;

public class ModuleRegistry
{
    private List<ModuleListener> modules;

    public ModuleRegistry()
    {
        modules = new ArrayList<>();
    }

    public void register(ModuleListener module)
    {
        module.setEnabled(true);
        modules.add(module);
        Main.getInst().getServer().getPluginManager().registerEvents(module, Main.getInst());
    }
}
