package com.aimercet.brlib.player;

import com.aimercet.brlib.config.IYMLSerializable;

/**
 * 需要创建指定形参的构造函数用于玩家预注册模块<br/>
 * PlayerModule(PlayerState playerState)<br/>
 * <br/>
 * 或直接继承自PlayerModule<br/>
 *   protected Example(PlayerState playerState)<br/>
 *   {<br/>
 *         super("module_id",playerState,true);<br/>
 *   }<br/>
 */
public interface IPlayerModule extends IYMLSerializable
{
    String getID();
    PlayerState getPlayerState();


    void onTick();
    void onTickAsync();
    void onRegister();
    void onUnRegister();


    /**
     * 决定是否保存到本地文件
     */
    boolean isPersistence();

    default String getDefaultFilePath()
    {
        if(getPlayerState()==null)
            throw new NullPointerException("save module ["+getID()+"] failed - PlayerState is null");
        else if(getPlayerState().name == null || getPlayerState().name.isEmpty())
            throw new NullPointerException("save module ["+getID()+"] failed - PlayerState name is "+(getPlayerState().name==null?null:"empty"));

        return PlayerState.filePath(getPlayerState().name)+getID()+".yml";
    }

    default void save() {if(isPersistence())IYMLSerializable.super.save();}
    default void load() {if(isPersistence())IYMLSerializable.super.load();}
    default void save(String path) {if(isPersistence())IYMLSerializable.super.save(path);}
    default void load(String path) {if(isPersistence())IYMLSerializable.super.load(path);}
}
