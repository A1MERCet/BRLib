package com.aimercet.brlib.player;

import com.aimercet.brlib.event.custom.player.EventPlayerLoaded;
import com.aimercet.brlib.event.custom.player.EventPlayerUnloaded;
import com.aimercet.brlib.log.Logger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class PlayerManager
{
    public static PlayerManager instance;
    public static PlayerState Get(Player player)    {return player == null ? null : instance.get(player.getUniqueId().toString());}
    public static PlayerState Get(String uuid)      {return instance.get(uuid);}

    private HashMap<String,PlayerState> players = new HashMap<>();

    public PlayerManager()
    {
        instance=this;
    }

    public PlayerState get(String name) {return players.get(name);}
    private PlayerState create(String uuid) {return new PlayerState(uuid);}

    public PlayerState load(String uuid,boolean register)
    {
        PlayerState ps = get(uuid);
        if(ps==null)
        {
            Logger.info(String.format("[玩家] 创建玩家状态 %s 注册: %s", uuid, register));
            ps = create(uuid);
            ps.onPreInit();
            ps.load();
            ps.onInit();
            players.put(uuid,ps);

            if(register) players.put(uuid,ps);
            EventPlayerLoaded evt = new EventPlayerLoaded(ps);
            Bukkit.getPluginManager().callEvent(evt);
        }
        return ps;
    }

    public PlayerState unload(String uuid){return unload(uuid,true);}
    public PlayerState unload(String uuid , boolean save)
    {
        Logger.info(String.format("[玩家] 卸载玩家状态 %s 保存: %s", uuid, save));
        PlayerState ps = get(uuid);
        if(ps==null) return null;
        if(save) ps.save();
        ps.onUnload();
        players.remove(uuid);
        EventPlayerUnloaded evt = new EventPlayerUnloaded(ps);
        Bukkit.getPluginManager().callEvent(evt);
        return ps;
    }

    public HashMap<String, PlayerState> getPlayers() {return players;}
}
