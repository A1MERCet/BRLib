package com.aimercet.brlib.event;

import com.aimercet.brlib.event.custom.player.EventPlayerLoaded;
import com.aimercet.brlib.log.Logger;
import com.aimercet.brlib.player.PlayerManager;
import com.aimercet.brlib.player.PlayerState;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class EventPlayer implements Listener
{
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent evt)
    {
        Player player = evt.getPlayer();
        PlayerState ps = PlayerManager.instance.load(player.getUniqueId().toString(), true);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent evt)
    {
        Player player = evt.getPlayer();
        PlayerState ps = PlayerManager.instance.unload(player.getUniqueId().toString());
    }
}
