package com.aimercet.brlib;

import com.aimercet.brlib.command.CMDLocalization;
import com.aimercet.brlib.event.EventPlayer;
import com.aimercet.brlib.localization.Localization;
import com.aimercet.brlib.log.LogBuilder;
import com.aimercet.brlib.log.Logger;
import com.aimercet.brlib.player.PlayerModuleManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class BRLib extends JavaPlugin {

    public Options options;
    public Localization localization;
    private Logger logger;

    public CMDLocalization cmdLocalization;

    public PlayerModuleManager playerModuleManager;

    @Override
    public void onEnable()
    {
        saveDefaultConfig();

        options = new Options();
        options.load();

        playerModuleManager = new PlayerModuleManager();

        localization = new Localization();
        Localization.instance.load();

        logger = new Logger();
        logger.load();

        registerCMD();
        registerEvent();
        LogBuilder.Lang(Localization.serverEnable).info();
    }

    @Override
    public void onDisable()
    {
        LogBuilder.Lang(Localization.serverDisable).info();
        saveConfig();
    }


    public void saveConfig()
    {
        logger.save();
        localization.generaRegistryFile();
    }

    private void registerCMD()
    {
        cmdLocalization = new CMDLocalization();
        Bukkit.getPluginCommand(cmdLocalization.name).setExecutor(cmdLocalization);
    }
    private void registerEvent()
    {
        Bukkit.getPluginManager().registerEvents(new EventPlayer(), this);
    }
}
