package com.aimercet.brlib;

import com.aimercet.brlib.command.CMDLocalization;
import com.aimercet.brlib.localization.Localization;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class BRLib extends JavaPlugin {

    public Options options = new Options();
    public Localization localization = new Localization();

    public CMDLocalization cmdLocalization = new CMDLocalization();

    @Override
    public void onEnable()
    {
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
    }
}
