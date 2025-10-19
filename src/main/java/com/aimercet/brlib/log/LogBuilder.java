package com.aimercet.brlib.log;

import com.aimercet.brlib.localization.Localization;

import java.util.Locale;

public class LogBuilder
{
    public static LogBuilder Text(String text)
    {
        return new LogBuilder().text(text);
    }
    public static LogBuilder Lang(String text)
    {
        return new LogBuilder().lang(text);
    }
    public static LogBuilder Lang(String text,String... replace)
    {
        return new LogBuilder().lang(text,replace);
    }

    protected String text = "";

    public LogBuilder text(String text)
    {
        this.text+=text;
        return this;
    }

    public LogBuilder lang(String id)
    {
        this.text+= Localization.get(id);
        return this;
    }

    public LogBuilder lang(Locale l ,String id)
    {
        this.text+=Localization.get(l,id);
        return this;
    }

    public LogBuilder lang(String id,String... replace)
    {
        this.text+=Localization.get(id,replace);
        return this;
    }

    public LogBuilder lang(Locale l ,String id,String... replace)
    {
        this.text+=Localization.get(l,id,replace);
        return this;
    }

    public LogBuilder info()
    {
        Logger.info(toString());
        return this;
    }

    public LogBuilder warn()
    {
        Logger.warn(toString());
        return this;
    }

    public LogBuilder error()
    {
        Logger.error(toString());
        return this;
    }

    public LogBuilder infoPlayer(String player)
    {
        Logger.infoPlayer(player,toString());
        return this;
    }

    public LogBuilder warnPlayer(String player)
    {
        Logger.warnPlayer(player,toString());
        return this;
    }

    public LogBuilder errorPlayer(String player)
    {
        Logger.errorPlayer(player,toString());
        return this;
    }

    @Override public String toString() {return text;}
}
