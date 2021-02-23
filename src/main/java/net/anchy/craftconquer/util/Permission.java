package net.anchy.craftconquer.util;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permissible;

public class Permission
{
    private Permission(){}

    public static boolean hasPermission(Permissible target, String perm, String subperm)
    {
        if(!target.hasPermission(perm + "." + subperm) && !target.hasPermission(perm + ".*"))
        {
            return false;
        }

        return true;
    }
}
