package com.aimercet.brlib;

import com.aimercet.brlib.config.IYMLSerializable;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.Locale;

public class Options implements IYMLSerializable
{
    public Locale local = Locale.getDefault();
    public String configPath = "C:/workspace/ProjectBR/Server/config/";

    private static Options instance;
    public static Options Instance(){return instance;};

    public Options()
    {
        instance = this;
    }

    @Override public String getFilePath() {return configPath+"/options.yml";}

    @Override
    public void save(File f, YamlConfiguration y)
    {
        y.set("local",local.getLanguage());
        y.set("configPath",configPath);
    }

    @Override
    public void load(File f, YamlConfiguration y)
    {
        local = Locale.forLanguageTag(y.getString("local"));
        configPath = y.getString("configPath");
    }
}
