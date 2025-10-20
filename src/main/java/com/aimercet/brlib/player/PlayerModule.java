package com.aimercet.brlib.player;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;


public abstract class PlayerModule implements IPlayerModule
{
    public final String id;
    public final PlayerState playerState;
    public final boolean persistence;

    protected PlayerModule(String id, PlayerState playerState, boolean persistence)
    {
        this.id = id;
        this.playerState = playerState;
        this.persistence = persistence;
    }

    @Override public String getID()                 {return id;}
    @Override public PlayerState getPlayerState()   {return playerState;}
    @Override public boolean isPersistence()        {return persistence;}

    @Override
    public void save(File file, YamlConfiguration yml) {

    }

    @Override
    public void load(File file, YamlConfiguration yml) {

    }

    @Override
    public void onRegister() {

    }

    @Override
    public void onUnRegister() {

    }
}
