package com.aimercet.brlib;

import com.aimercet.brlib.command.CMDLocalization;
import com.aimercet.brlib.event.EventPlayer;
import com.aimercet.brlib.localization.Localization;
import com.aimercet.brlib.player.PlayerModuleManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class BRLib extends JavaPlugin {

    public Options options = new Options();
    public Localization localization = new Localization();

    public CMDLocalization cmdLocalization = new CMDLocalization();

    public PlayerModuleManager playerModuleManager;

    @Override
    public void onEnable()
    {
        playerModuleManager = new PlayerModuleManager();
        Localization.instance.load();

        registerCMD();
        registerEvent();
    }

    @Override
    public void onDisable() {

    }

    private void registerCMD()
    {
        Bukkit.getPluginCommand(cmdLocalization.name).setExecutor(cmdLocalization);
    }
    private void registerEvent()
    {
        Bukkit.getPluginManager().registerEvents(new EventPlayer(), this);
    }
}
