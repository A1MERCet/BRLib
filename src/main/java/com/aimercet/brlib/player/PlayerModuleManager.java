package com.aimercet.brlib.player;

import com.aimercet.brlib.BRLib;
import com.aimercet.brlib.log.Logger;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

public class PlayerModuleManager
{
    public class ModuleRunnable {
        private BukkitTask taskSync;
        private BukkitTask taskAsync;
        private long perTick = 1;

        public void runModuleRunnable() {
            taskSync = Bukkit.getScheduler().runTaskTimer(BRLib.instance(), this::runSync,0, perTick);
            taskAsync = Bukkit.getScheduler().runTaskTimerAsynchronously(BRLib.instance(), this::runAsync,0, perTick);
        }

        private void runSync() {
            for (PlayerState ps : PlayerManager.instance.getPlayers().values())
                try {
                    if (ps == null) {Logger.info("[模块管理器] 玩家不存在");continue;}
                    for (IPlayerModule module : ps.getModuleController().getModules())
                        try {module.onTick();}catch (Exception e){e.printStackTrace();}
                }catch (Exception e){e.printStackTrace();}
        }

        private void runAsync() {
            for (PlayerState ps : PlayerManager.instance.getPlayers().values())
                try {
                    if (ps == null) {Logger.info("[模块管理器-异步] 玩家不存在");continue;}
                    for (IPlayerModule module : ps.getModuleController().getModules())
                        try {module.onTickAsync();}catch (Exception e){e.printStackTrace();}
                }catch (Exception e){e.printStackTrace();}
        }

        public long getPerTick() {return perTick;}
        public ModuleRunnable setPerTick(long perTick) {this.perTick = perTick; return this;}
    }

    public static PlayerModuleManager instance;

    /**
     * 预注册模块<br/>
     * 在创建玩家PlayerState的时候会自动创建对应玩家的模块<br/>
     */
    private final List<Class<? extends IPlayerModule>> preRegisterModules = new ArrayList<>();
    public List<Class<? extends IPlayerModule>> getPreRegisterModules()         {return preRegisterModules;}
    public void registerPreModule(Class<? extends IPlayerModule> module)        {preRegisterModules.add(module);Logger.info("玩家预注册模块 > "+module.getSimpleName());}
    public void unRegisterPreModule(Class<? extends IPlayerModule> module)      {preRegisterModules.remove(module);}
    public boolean hasRegisterPreModule(Class<? extends IPlayerModule> module)  {return preRegisterModules.contains(module);}

    public ModuleRunnable runnable;

    public PlayerModuleManager()
    {
        instance = this;
        runnable = new ModuleRunnable();
    }
    private boolean _init = false;
    public void init()
    {
        if(_init)return;
        _init =true;

        runnable.runModuleRunnable();
    }
}
