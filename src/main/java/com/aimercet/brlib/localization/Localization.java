package com.aimercet.brlib.localization;

import com.aimercet.brlib.Options;
import com.aimercet.brlib.config.IYMLSerializable;
import com.aimercet.brlib.log.LogBuilder;
import com.aimercet.brlib.log.Logger;
import com.aimercet.brlib.util.MapBuilder;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Localization implements IYMLSerializable
{
    private static HashMap<String,String> preRegistery = new HashMap<>();

    public static String register(String id){register(id,id);return id;}
    public static String register(String id,String text)
    {
        if(Localization.instance==null) preRegistery.put(id,text);
        else                            Localization.instance.registry.put(id,text);
        return id;
    }
    public HashMap<String,String> registry = new MapBuilder<String,String>()
            //#########################################################################
            //##################################Sever##################################
            //#########################################################################
            .put(serverEnable,"ProjectBR已启用")
            .put(serverDisable,"ProjectBR已停用")
            .put(serverLangMissing,"Lang($l) missing text")
            .map;

    public static String serverEnable = "server_enable";
    public static String serverDisable = "server_disable";
    public static String serverLangMissing = "server_lang_missing";



    public class Language implements IYMLSerializable
    {
        public Locale parent;
        public Locale locale;
        public HashMap<String, String> texts = new HashMap<>();

        public Language() {}

        public Language(Locale locale,Locale parent)
        {
            this.parent = parent;
            this.locale = locale;
        }

        public String getText(String id)
        {
            if(texts.containsKey(id))return texts.get(id);
            if(parent!=null)
            {
                Language l = Localization.getLang(parent);
                return l.texts.containsKey(id)?l.getText(id):registry.get(id);
            }
            return registry.get(id);
        }

        @Override public String getFilePath() {return "";}

        @Override
        public void save(File file, YamlConfiguration yml) {
            yml.set("parent",parent==null?null:parent.getLanguage());
            yml.set("locale",locale==null?null:locale.getLanguage());
            yml.set("Table", texts);
        }

        @Override
        public void load(File file, YamlConfiguration yml) {
            texts.clear();
            if(yml.getString("parent")!=null)parent = Locale.forLanguageTag(yml.getString("parent"));
            if(yml.getString("locale")!=null)locale = Locale.forLanguageTag(yml.getString("locale"));

            if(locale==null) Bukkit.getLogger().severe("名为 "+Locale.forLanguageTag(yml.getString("local")+"的地区不存在"));

            if(yml.getConfigurationSection("table")==null){
                if(logWarn)Bukkit.getLogger().warning("名为 "+Locale.forLanguageTag(yml.getString("local")+"的语言文件没有有效文本"));
                return;
            }
            Set<String> keys = yml.getConfigurationSection("table").getKeys(false);

            for (String k : keys) {
                String v = yml.getString("table." + k);
                if(v==null){continue;}
                texts.put(k, v);
            }
        }

        public void reload(){load();}

        public List<String> getMissing()
        {
            List<String> list = new ArrayList<String>();
            for(String s : registry.keySet())
                if(!texts.containsKey(s))
                    list.add(s);
            return list;
        }
    }


    public final HashMap<Locale, Language> langs = new HashMap<>();
    public static Language getLang(Locale l){return instance.langs.get(l);}
    public static HashMap<Locale, Language> getLang(){return instance.langs;}
    public static String get(Locale l,String id){return instance.getText(l,id);}
    public static String get(String id){return instance.getText(id);}
    public static String get(Locale l,String id,String... replace){return instance.getText(l,id,replace);}
    public static String get(String id,String... replace){return instance.getText(id,replace);}
    public static boolean has(String id){return instance.hasText(id);}

    public boolean logWarn = true;

    public static Localization instance;

    public Localization()
    {
        instance = this;
        registry.putAll(preRegistery);
    }

    public boolean hasText(String id)
    {
        return registry.containsKey(id);
    }

    public String getText(String id){return getText(Options.Instance().local, id);}
    public String getText(Locale l, String id)
    {
        return langs.containsKey(l)?langs.get(l).getText(id):registry.get(id);
    }
    public String getText(String id , HashMap<String,String> replace){return getText(Options.Instance().local, id,replace);}
    public String getText(Locale l , String id , HashMap<String,String> replace)
    {
        String v = getText(l,id);
        for(String k : replace.keySet())
            v = v.replace(k,replace.get(k));
        return v;
    }

    public String getText(String id , String... replace){return getText(Options.Instance().local, id,replace);}
    public String getText(Locale l , String id , String... replace)
    {
        String v = getText(l,id);
        for(int i = 0;i < replace.length-1;i++)
            v = v.replace(replace[i],replace[i+1]);
        return v;
    }

    public void generaRegistryFile()
    {
        File file = new File(Options.Instance().configPath+"/lang/registry.yml");
        YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);

        ArrayList<String> keys = new ArrayList<>(registry.keySet());
        keys.sort(String.CASE_INSENSITIVE_ORDER);
        keys.forEach(k->{
            yml.set("Registry."+k,registry.get(k));
            Logger.info(k+": "+registry.get(k));
        });
        try {yml.save(file);}catch (IOException e){e.printStackTrace();}
    }

    @Override public String getFilePath() {return Options.Instance().configPath+"/lang/lang.yml";}

    @Override
    public void save(File file, YamlConfiguration yml)
    {
        yml.set("logWarn",logWarn);

        langs.values().forEach(e->{
            if(e.locale==null){Bukkit.getLogger().severe("保存语言文件失败 - 地区不存在");return;}
            e.save(Options.Instance().configPath+"/lang/lang/"+e.locale.getLanguage()+".yml");
        });
    }

    @Override
    public void load(File file, YamlConfiguration yml)
    {
        logWarn = yml.getBoolean("logWarn");

        File d = new File(Options.Instance().configPath+"/lang/lang/");
        Logger.warn(d.getAbsolutePath());
        if(!d.exists() || !d.isDirectory())return;

        for(File f : Objects.requireNonNull(d.listFiles()))
        {
            Language l = new Language();
            l.load(f.getPath());

            if(l.locale==null){
                Bukkit.getLogger().severe("加载语言文件失败 - 地区不存在["+f.getAbsolutePath()+"]");
                continue;
            }

            langs.put(l.locale,l);
            List<String> missing = l.getMissing();
            if(!missing.isEmpty() && logWarn)
                LogBuilder.Lang(serverLangMissing,"$l",l.locale.getLanguage()).text("\n missing").info();

            if(langs.containsKey(l.locale)){
                langs.get(l.locale).texts.putAll(l.texts);
            }else {
                langs.put(l.locale,l);
            }
        }
    }

    public void reload(){load();}
}
