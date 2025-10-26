package com.aimercet.brlib.runnable;

import com.aimercet.brlib.log.Logger;
import com.aimercet.brlib.player.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class PluginEnableRunnable extends BukkitRunnable
{
    @Override
    public void run()
    {
        boolean enabledAll = true;
        for (Plugin plugin : Bukkit.getPluginManager().getPlugins())
            if(!plugin.isEnabled()){
                enabledAll = false;
                break;
            }

        if(enabledAll) {
            try {
                if(!Bukkit.getOnlinePlayers().isEmpty()) Logger.info("重载在线玩家");
                for (Player player : Bukkit.getOnlinePlayers())
                    PlayerManager.instance.load(player.getName(),true);
            }catch (Exception e){e.printStackTrace();}
            this.cancel();
        }
    }
}
