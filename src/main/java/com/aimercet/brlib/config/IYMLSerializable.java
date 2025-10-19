package com.aimercet.brlib.config;

import com.aimercet.brlib.log.Logger;
import com.aimercet.brlib.util.UtilString;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public interface IYMLSerializable
{
    String getFilePath();

    default void save(){save(getFilePath());}
    default void save(String path){
        if(UtilString.isEmpty(path)){Logger.error("An error occurred while saving yml. The path is empty");return;}
        File file = new File(path);
        YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);
        save(file,yml);
        try {yml.save(file);}catch (IOException e){e.printStackTrace();}
    }

    void save(File file, YamlConfiguration yml);

    default void load() {load(getFilePath());}
    default void load(String path)
    {
        if(!fileExists(path))return;
        File file = new File(path);
        YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);
        load(file,yml);
    }

    void load(File file, YamlConfiguration yml);

    default boolean fileExists()
    {
        return new File(getFilePath()).exists();
    }
    default boolean fileExists(String path)
    {
        return new File(path).exists();
    }
}
