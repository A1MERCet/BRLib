package com.aimercet.brlib.player;

import com.aimercet.brlib.log.Logger;

import java.util.ArrayList;
import java.util.List;

public class PlayerModuleManager
{
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

    public PlayerModuleManager()
    {
        instance = this;
    }
}
