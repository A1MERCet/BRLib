package com.aimercet.brlib.command;

import com.aimercet.brlib.player.IPlayerModule;
import com.aimercet.brlib.player.PlayerManager;
import com.aimercet.brlib.player.PlayerState;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMDPlayer extends CMDBasic
{
    public CMDPlayer()
    {
        super("brp", CMDPlayer.class);
    }

    @CommandArgs(
            describe = "打印所有玩家名(BRLib本插件的玩家状态信息)",
            args = {"list"},
            types = {ArgType.DEPEND}
    )
    public void list(CommandSender sender)
    {
        StringBuilder b = new StringBuilder();;
        b.append("所有玩家["+PlayerManager.instance.getPlayers().size()+"]\n");
        for (PlayerState value : PlayerManager.instance.getPlayers().values()) {
            Player player = value.getPlayer();
            b.append(" - ").append(value.name).append("[").append(player==null?"null":(player.isOnline()?"Online":"Offline")).append("]");
        }
        sender.sendMessage(b.toString());
    }

    @CommandArgs(
            describe = "打印所有玩家信息",
            args = {"print","玩家名"},
            types = {ArgType.DEPEND,ArgType.STRING}
    )
    public void print(CommandSender sender,String player)
    {
        PlayerState ps = PlayerManager.Get(player);
        if(ps==null){sender.sendMessage("玩家["+player+"]不存在");return;}

        StringBuilder b = new StringBuilder().append(player).append(": ");
        b.append("已注册的模块["+ps.getModuleController().getMoudles().size()+"]\n");
        for (IPlayerModule module : ps.getModuleController().getMoudles())
        {
            b.append("  - ").append(module.getID()).append("\n");
        }
        sender.sendMessage(b.toString());
    }
}
