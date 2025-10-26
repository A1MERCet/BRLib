package com.aimercet.brlib;

import com.aimercet.brlib.command.CMDLocalization;
import com.aimercet.brlib.command.CMDPlayer;
import com.aimercet.brlib.event.EventPlayer;
import com.aimercet.brlib.event.EventServer;
import com.aimercet.brlib.localization.Localization;
import com.aimercet.brlib.log.LogBuilder;
import com.aimercet.brlib.log.Logger;
import com.aimercet.brlib.player.PlayerManager;
import com.aimercet.brlib.player.PlayerModuleManager;
import com.aimercet.brlib.runnable.PluginEnableRunnable;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class BRLib extends JavaPlugin {

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
        playerModuleManager = new PlayerModuleManager();
        options = new Options();
        options.load();

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
        saveDefaultConfig();

        registerCMD();
        registerEvent();
        LogBuilder.Lang(Localization.serverEnable).info();

        new PluginEnableRunnable().runTaskTimerAsynchronously(this,0L,1L);
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
