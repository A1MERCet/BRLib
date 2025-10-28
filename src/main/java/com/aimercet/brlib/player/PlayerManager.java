package com.aimercet.brlib.player;

import com.aimercet.brlib.log.Logger;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class PlayerManager
{
    public static PlayerManager instance;
    public static PlayerState Get(Player player)    {return instance.get(player.getName());}
    public static PlayerState Get(String name)      {return instance.get(name);}

    private HashMap<String,PlayerState> players = new HashMap<>();

    public PlayerManager()
    {
        instance=this;
    }

    public PlayerState get(String name) {return players.get(name);}
    private PlayerState create(String name) {return new PlayerState(name);}

    public PlayerState load(String name,boolean register)
    {
        PlayerState ps = get(name);
        if(ps==null)
        {
            ps = create(name);
            ps.onPreInit();
            ps.load();
            ps.onInit();
            players.put(name,ps);

            if(register) players.put(name,ps);
            Logger.info("Player "+name+" loaded register="+register);
        }
        return ps;
    }


    public PlayerState unload(String name ){return unload(name,true);}
    public PlayerState unload(String name , boolean save)
    {
        PlayerState ps = get(name);
        if(ps==null)return null;
        if(save) ps.save();
        ps.onUnload();
        players.remove(name);
        Logger.info("Player "+name+" unloaded");
        return ps;
    }

    public HashMap<String, PlayerState> getPlayers() {return players;}
}
