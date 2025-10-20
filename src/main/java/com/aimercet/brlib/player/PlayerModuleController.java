package com.aimercet.brlib.player;

import com.aimercet.brlib.log.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlayerModuleController
{
    private final List<IPlayerModule> moudles = new ArrayList<>();
    private final HashMap<String, IPlayerModule> moudleMap = new HashMap<>();

    public final PlayerState playerState;
    public PlayerModuleController(PlayerState playerState)
    {
        this.playerState = playerState;
    }

    private boolean init = false;
    public PlayerModuleController init()
    {
        if(playerState == null){throw new NullPointerException("PlayerModuleController init failed - PlayerState is null");}
        if(init)return this;
        init=true;

        PlayerModuleManager.instance.getPreRegisterModules().forEach(e->{try {

            IPlayerModule module = e.getConstructor(PlayerState.class).newInstance(playerState);
            register(module);

        }catch (Exception ex){ex.printStackTrace();}});

        return this;
    }

    public PlayerModuleController loadAll()
    {
        moudles.forEach(e->{
            try {e.load();}catch (Exception ex){ex.printStackTrace();}
        });
        return this;
    }

    public PlayerModuleController saveAll()
    {
        moudles.forEach(e->{
            try {e.save();}catch (Exception ex){ex.printStackTrace();}
        });
        return this;
    }

    public List<IPlayerModule> getMoudles()                                     {return moudles;}
    public IPlayerModule get(String moudleName)                                 {return moudleMap.get(moudleName);}

    public PlayerModuleController register(IPlayerModule module){return register(module,true);}
    public PlayerModuleController register(IPlayerModule module, boolean load)
    {
        if(playerState.isUnloaded())                {Logger.warn("module register warn - PlayerState is unloaded");}
        if(module==null)                            { throw new NullPointerException("failed to register module - Module is null");}
        if(module.getPlayerState()==null)           { throw new NullPointerException("failed to register module["+module.getID()+"] - PlayerState is null");}
        if(moudleMap.containsKey(module.getID()))   {throw new IllegalArgumentException("module["+module.getID()+"] is already registered");}

        moudles.add(module);
        moudleMap.put(module.getID(),module);
        if(load) try {module.load();}catch (Exception e){e.printStackTrace();}
        try {module.onRegister();}catch (Exception e){e.printStackTrace();}
        return this;
    }

    public IPlayerModule unregister(IPlayerModule module){return unregister(module,true);}
    public IPlayerModule unregister(IPlayerModule module,boolean save)
    {
        if(playerState.isUnloaded()){Logger.warn("module unregister warn - PlayerState is unloaded");}
        if(moudleMap.get(module.getID())==null)return null;
        module.save();
        moudleMap.remove(module.getID());
        moudles.remove(module);
        if(save) try {module.save();}catch (Exception e){e.printStackTrace();}
        try {module.onUnRegister();}catch (Exception e){e.printStackTrace();}
        return module;
    }
    public void unRegisterAll(){unRegisterAll(true);}
    public void unRegisterAll(boolean save)
    {
        new ArrayList<>(moudles).forEach(e->{
            unregister(e,save);
        });
    }
}
