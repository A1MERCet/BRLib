package com.aimercet.brlib.player;

import com.aimercet.brlib.log.Logger;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlayerModuleController
{

    public final PlayerState playerState;

    private final List<IPlayerModule> modules = new ArrayList<>();
    private final HashMap<String, IPlayerModule> moduleMap = new HashMap<>();


    public PlayerModuleController(PlayerState playerState)
    {
        this.playerState = playerState;
    }

    private boolean _init = false;
    public PlayerModuleController init()
    {
        if(_init)return this;
        _init =true;
        if(playerState == null){throw new NullPointerException("PlayerModuleController init failed - PlayerState is null");}

        PlayerModuleManager.instance.getPreRegisterModules().forEach(e->{try {

            Constructor<? extends IPlayerModule> constructor = e.getDeclaredConstructor(PlayerState.class);
            constructor.setAccessible(true);
            IPlayerModule module = constructor.newInstance(playerState);
            register(module);

        }catch (Exception ex){Logger.error("为玩家["+playerState.name+"]创建模块["+e.getSimpleName()+"]失败");ex.printStackTrace();}});

        return this;
    }

    public PlayerModuleController loadAll()
    {
        modules.forEach(e->{
            try {e.load();}catch (Exception ex){ex.printStackTrace();}
        });
        return this;
    }

    public PlayerModuleController saveAll()
    {
        modules.forEach(e->{
            try {e.save();}catch (Exception ex){ex.printStackTrace();}
        });
        return this;
    }

    public List<IPlayerModule> getModules()                                     {return modules;}
    public IPlayerModule get(String moduleName)                                 {return moduleMap.get(moduleName);}
    public <T extends IPlayerModule> T get(Class<T> clz) {
        for (IPlayerModule v : moduleMap.values())
            if (clz.isInstance(v))
                return clz.cast(v);
        return null;
    }

    public PlayerModuleController register(IPlayerModule module){return register(module,true);}
    public PlayerModuleController register(IPlayerModule module, boolean load)
    {
        if(playerState.isUnloaded())                {Logger.warn("module register warn - PlayerState is unloaded");}
        if(module==null)                            { throw new NullPointerException("failed to register module - Module is null");}
        if(module.getPlayerState()==null)           { throw new NullPointerException("failed to register module["+module.getID()+"] - PlayerState is null");}
        if(moduleMap.containsKey(module.getID()))   {throw new IllegalArgumentException("module["+module.getID()+"] is already registered");}

        modules.add(module);
        moduleMap.put(module.getID(),module);
        if(load) try {module.load();}catch (Exception e){e.printStackTrace();}
        try {module.onRegister();}catch (Exception e){e.printStackTrace();}
        return this;
    }

    public IPlayerModule unregister(IPlayerModule module){return unregister(module,true);}
    public IPlayerModule unregister(IPlayerModule module,boolean save)
    {
        if(playerState.isUnloaded()){Logger.warn("module unregister warn - PlayerState is unloaded");}
        if(moduleMap.get(module.getID())==null)return null;
        module.save();
        moduleMap.remove(module.getID());
        modules.remove(module);
        if(save) try {module.save();}catch (Exception e){e.printStackTrace();}
        try {module.onUnRegister();}catch (Exception e){e.printStackTrace();}
        return module;
    }
    public void unRegisterAll(){unRegisterAll(true);}
    public void unRegisterAll(boolean save)
    {
        new ArrayList<>(modules).forEach(e->{
            unregister(e,save);
        });
    }
}
