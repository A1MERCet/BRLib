package com.aimercet.brlib;

import com.aimercet.brlib.command.CMDLocalization;
import com.aimercet.brlib.command.CMDPlayer;
import com.aimercet.brlib.event.EventPlayer;
import com.aimercet.brlib.event.EventServer;
import com.aimercet.brlib.localization.Localization;
import com.aimercet.brlib.log.LogBuilder;
import com.aimercet.brlib.log.Logger;
import com.aimercet.brlib.player.ModuleBRLib;
import com.aimercet.brlib.player.PlayerManager;
import com.aimercet.brlib.player.PlayerModuleManager;
import com.aimercet.brlib.runnable.PluginEnableRunnable;
import com.aimercet.brlib.util.UtilString;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class BRLib extends JavaPlugin {
    private static BRLib instance;
    public static BRLib instance(){return instance;}

    public Options options;
    public Localization localization;
    private Logger logger;

    public CMDLocalization cmdLocalization;
    public CMDPlayer cmdPlayer;

    public PlayerManager playerManager;
    public PlayerModuleManager playerModuleManager;

    @Override
    public void onLoad()
    {
        super.onLoad();
        options = new Options();
        options.load();

        playerModuleManager = new PlayerModuleManager();

        playerManager = new PlayerManager();

        localization = new Localization();
        Localization.instance.load();

        logger = new Logger();
        logger.load();
    }

    @Override
    public void onEnable()
    {
        super.onEnable();
        instance = this;

        saveDefaultConfig();

        registerCMD();
        registerEvent();
        LogBuilder.Lang(Localization.serverEnable).info();

        playerModuleManager.init();
        PlayerModuleManager.instance.registerPreModule(ModuleBRLib.class);

        new PluginEnableRunnable().runTaskTimer(this,0L,1L);
    }

    @Override
    public void onDisable()
    {
        super.onDisable();
        try {
            for (Player player : Bukkit.getOnlinePlayers()) {
                PlayerManager.instance.unload(player.getName());
            }
        }catch (Exception e){e.printStackTrace();}

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

        cmdPlayer = new CMDPlayer();
        Bukkit.getPluginCommand(cmdPlayer.name).setExecutor(cmdPlayer);
    }
    private void registerEvent()
    {
        Bukkit.getPluginManager().registerEvents(new EventPlayer(), this);
        Bukkit.getPluginManager().registerEvents(new EventServer(), this);
    }
}
