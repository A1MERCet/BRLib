package com.aimercet.brlib.event.custom.player;

import com.aimercet.brlib.player.PlayerState;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class EventPlayerBase extends Event implements Cancellable
{
    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled = false;


    public final PlayerState ps;

    public EventPlayerBase(PlayerState ps) {
        this.ps = ps;
    }

    @Override public boolean isCancelled() {return cancelled;}
    @Override public void setCancelled(boolean cancel) {this.cancelled = cancel;}
    @Override public HandlerList getHandlers() {return handlers;}
    public static HandlerList getHandlerList() {return handlers;}
}
