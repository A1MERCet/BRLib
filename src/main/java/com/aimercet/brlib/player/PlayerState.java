package com.aimercet.brlib.player;

import com.aimercet.brlib.Options;
import com.aimercet.brlib.config.IYMLSerializable;
import com.aimercet.brlib.localization.Localization;
import com.aimercet.brlib.log.Logger;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.List;
import java.util.Locale;

public class PlayerState implements IYMLSerializable
{
    public static String filePath()                 {return Options.Instance().configPath+"/player/";};
    public static String filePath(String player)    {return Options.Instance().configPath+"/player/"+player+"/";};

    public final String name;
    public final PlayerModuleController moduleController;
    public Locale locale = Locale.getDefault();

    private boolean unloaded = false;

    public PlayerState(String name)
    {
        this.name = name;
        this.moduleController = new PlayerModuleController(this).init();
    }

    public void onPreInit()
    {
    }
    public void onInit()
    {
    }

    public String getLang(String id)                    {return Localization.instance.getText(locale,id);}
    public String getLang(String id,String... replace)  {return Localization.instance.getText(locale,id,replace);}

    @Override public String getFilePath() {return filePath(name)+name+".yml";}

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
    public List<IPlayerModule> getModules()             {return moduleController.getMoudles();}

}
