package com.aimercet.brlib.command;

import com.aimercet.brlib.localization.Localization;
import org.bukkit.command.CommandSender;

import java.util.Locale;

public class CMDLocalization extends CMDBasic
{
    public CMDLocalization()
    {
        super("loc", CMDLocalization.class);
    }

    @CommandArgs(
            describe = "重载本地化文件",
            args = {"lang","reload"},
            types = {ArgType.DEPEND,ArgType.DEPEND}
    )
    public void reloadLang(CommandSender sender)
    {
        Localization.instance.reload();
    }

    @CommandArgs(
            describe = "打印本地化条目",
            args = {"print"},
            types = {ArgType.DEPEND}
    )
    public void printLang(CommandSender sender)
    {
        if(Localization.getLang().isEmpty()){sender.sendMessage("空");return;}
        Localization.getLang().forEach((local,lang)->{
            sender.sendMessage(String.format("区域[%s-%s]",local.getDisplayLanguage(),local.getCountry()));
            if(lang == null){
                sender.sendMessage(String.format("  空"));
            }else{
                StringBuilder builder = new StringBuilder().append("\n");
                lang.texts.forEach((k,v)->{
                    builder.append(String.format("  %s: %s\n",k,v));
                });
                sender.sendMessage(builder.toString());
            }
        });
    }

    @CommandArgs(
            describe = "打印本地化条目",
            args = {"print","区域(zh、en等)"},
            types = {ArgType.DEPEND,ArgType.STRING}
    )
    public void printLang(CommandSender sender,String localStr)
    {
        Locale local = Locale.forLanguageTag(localStr);
        if(local== null){sender.sendMessage("区域 "+localStr+" 不存在");return;}

        sender.sendMessage(String.format("区域[%s-%s]",local.getDisplayLanguage(),local.getCountry()));
        Localization.Language lang = Localization.getLang(local);
        if(lang == null){
            sender.sendMessage(String.format("  空"));
        }else{
            StringBuilder builder = new StringBuilder().append("\n");
            lang.texts.forEach((k,v)->{
                builder.append(String.format("  %s: %s\n",k,v));
            });
            sender.sendMessage(builder.toString());
        }
    }
}
