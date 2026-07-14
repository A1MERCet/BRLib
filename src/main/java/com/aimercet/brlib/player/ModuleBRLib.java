package com.aimercet.brlib.player;

public class ModuleBRLib extends PlayerModule
{
    public static String MODULE_ID = "brplayer";

    protected ModuleBRLib(PlayerState playerState) {
        super(MODULE_ID, playerState, true);
    }
}
