package com.aimercet.brlib.event;

import com.aimercet.brlib.player.PlayerManager;
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
        PlayerManager.instance.load(player.getName(),true);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent evt)
    {
        Player player = evt.getPlayer();
        PlayerManager.instance.unload(player.getName());
    }
}
