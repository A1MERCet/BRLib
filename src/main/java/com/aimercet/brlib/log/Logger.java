package com.aimercet.brlib.log;

import com.aimercet.brlib.Options;
import com.aimercet.brlib.config.IYMLSerializable;
import com.aimercet.brlib.localization.Localization;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Logger implements IYMLSerializable
{
    public enum Type
    {
        PLAYER(Localization.register("log_type_player","Player")),
        SERVER(Localization.register("log_type_server","Server")),
        ;
        private String lang;
        Type(String lang) {this.lang = lang;}
    }
    
    public enum Level
    {
        INFO(Localization.register("log_level_info","Info")),
        WARN(Localization.register("log_level_warn","Warn")),
        ERROR(Localization.register("log_level_error","Error")),
        ;
        private String lang;
        Level(String lang) {this.lang = lang;}
    }
    
    public static class Log
    {
        public long date = System.currentTimeMillis();
        public Level level;
        public Type type;
        public String message;

        protected String cache;

        public Log() {}

        public void clearCache(){cache=null;}
        public String buildString()
        {
            if(cache==null)
            {
                StringBuilder builder = new StringBuilder();
                builder
                        .append("[")
                        .append(sdf.format(date))
                        .append(" ")
                        .append(Localization.get(level.lang)).append("]")
                        .append("[").append(Localization.get(type.lang)).append("]")
                        .append(message)
                ;

                cache = builder.toString();
            }
            return cache;
        }

        public Log setDate(long date) {this.date = date;return this;}
        public Log setLevel(Level level) {this.level = level;return this;}
        public Log setType(Type type) {this.type = type;return this;}
        public Log setMessage(String message) {this.message = message;return this;}
    }
    public static class LogPlayer extends Log
    {
        public String player;

        public LogPlayer setPlayer(String player) {this.player = player;return this;}

        @Override
        public String buildString()
        {
            if(cache==null)
            {
                StringBuilder builder = new StringBuilder();
                builder
                        .append("[")
                        .append(sdf.format(date))
                        .append(" ")
                        .append(Localization.get(level.lang)).append("]")
                        .append("[").append(Localization.get(type.lang)).append("]")
                        .append("[").append(Localization.get(player)).append("]")
                        .append(message)
                ;
                cache = builder.toString();
            }
            return cache;
        }
    }

    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static Logger logger;
    public static Logger Instance(){return logger;}

    private static List<Log> content = new ArrayList<Log>();

    public Logger()
    {
        logger = this;
    }

    public static void log(Log log)
    {
        content.add(log);

        String print = log.buildString();

        switch (log.level){
            case INFO:  Bukkit.getLogger().   info(print); break;
            case WARN:  Bukkit.getLogger().warning(print); break;
            case ERROR: Bukkit.getLogger(). severe(print); break;
        }
    }

    public static Log info(LogBuilder builder){return info(builder.toString());}
    public static Log info(String msg)
    {
        Log l = new Log()
                .setLevel(Level.INFO)
                .setMessage(msg)
                .setType(Type.SERVER);
        log(l);
        return l;
    }
    public static Log warn(LogBuilder builder){return warn(builder.toString());}
    public static Log warn(String msg)
    {
        Log l = new Log()
                .setLevel(Level.WARN)
                .setMessage(msg)
                .setType(Type.SERVER);
        log(l);
        return l;
    }
    public static Log error(LogBuilder builder){return warn(builder.toString());}
    public static Log error(String msg)
    {
        Log l = new Log()
                .setLevel(Level.ERROR)
                .setMessage(msg)
                .setType(Type.SERVER);
        log(l);
        return l;
    }
    public static Log infoPlayer(String player,LogBuilder builder){return infoPlayer(player,builder.toString());}
    public static Log infoPlayer(String player,String msg)
    {
        LogPlayer l = new LogPlayer();
        l       .setPlayer(player)
                .setLevel(Level.INFO)
                .setMessage(msg)
                .setType(Type.PLAYER);
        log(l);
        return l;
    }
    public static Log warnPlayer(String player,LogBuilder builder){return warnPlayer(player,builder.toString());}
    public static Log warnPlayer(String player,String msg)
    {
        LogPlayer l = new LogPlayer();
        l       .setPlayer(player)
                .setLevel(Level.WARN)
                .setMessage(msg)
                .setType(Type.PLAYER);
        log(l);
        return l;
    }
    public static Log errorPlayer(String player,LogBuilder builder){return errorPlayer(player,builder.toString());}
    public static Log errorPlayer(String player,String msg)
    {
        LogPlayer l = new LogPlayer();
        l       .setPlayer(player)
                .setLevel(Level.ERROR)
                .setMessage(msg)
                .setType(Type.PLAYER);
        log(l);
        return l;
    }

    @Override public String getFilePath() {return Options.Instance().configPath+"/log/"+new SimpleDateFormat("yyyy-MM-dd HH.mm.ss").format(System.currentTimeMillis())+".yml";}

    @Override
    public void save(File file, YamlConfiguration yml)
    {
        List<String> list = new ArrayList<>();
        for (Log log : content)
            list.add(log.buildString());
        yml.set("Date", sdf.format(System.currentTimeMillis()));
        yml.set("Count", list.size());
        yml.set("Logs", list);
        list.clear();
    }

    @Override
    public void load(File file, YamlConfiguration yml)
    {

    }
}
