package com.aimercet.brlib.player;

import com.aimercet.brlib.Options;
import com.aimercet.brlib.config.IYMLSerializable;
import com.aimercet.brlib.localization.Localization;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class PlayerState implements IYMLSerializable
{
    public static String filePath()                 {return Options.Instance().configPath+"/player/";};
    public static String filePath(String player)    {return Options.Instance().configPath+"/player/"+player+"/";};

    public final String name;
    public final String uuid;
    public final PlayerModuleController moduleController;
    public Locale locale = Locale.getDefault();

    private boolean unloaded = false;

    public PlayerState(String uuid)
    {
        this.uuid = uuid;
        this.moduleController = new PlayerModuleController(this);

        String name = null;
        try {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null) { name = player.getName(); }
            else {
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(UUID.fromString(uuid));
                if  (offlinePlayer != null) { name = offlinePlayer.getName(); }
            }
        }catch (Exception e){
            Bukkit.getLogger().severe(String.format("[玩家状态] 获取玩家名失败 %s", uuid));
            e.printStackTrace();
        }
        this.name = name;
    }

    public void onPreInit()
    {
        this.moduleController.init();
    }
    public void onInit()
    {
    }

    public String getLang(String id)                    {return Localization.instance.getText(locale,id);}
    public String getLang(String id,String... replace)  {return Localization.instance.getText(locale,id,replace);}

    @Override public String getDefaultFilePath() {return filePath(name)+name+".yml";}

    @Override
    public void save(File file, YamlConfiguration yml)
    {
        yml.set("name",name);
        yml.set("locale",locale.toLanguageTag());

        moduleController.saveAll();
    }

    @Override
    public void load(File file, YamlConfiguration yml)
    {
        locale = Locale.forLanguageTag(yml.getString("locale","zh"));

        moduleController.loadAll();
    }

    public void onUnload()
    {
        moduleController.unRegisterAll();
        unloaded = true;
    }

    public String getName() {return name;}
    public boolean isUnloaded()     {return unloaded;}
    public Player getPlayer() {return Bukkit.getPlayerExact(name);}

    public PlayerModuleController getModuleController() {return moduleController;}
    public IPlayerModule getModule(String id)           {return moduleController.get(id);}
    public <T extends IPlayerModule> T getModule(Class<T> clz) {return moduleController.get(clz);}
    public List<IPlayerModule> getModules()             {return moduleController.getModules();}

}
