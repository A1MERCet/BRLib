package com.aimercet.brlib.util;

public class UtilColor
{
    public static final String GWHITE   = "#FFFFFFFF";
    public static final String GGRAY    = "#FF888888";
    public static final String GBLACK   = "#FF000000";
    public static final String GYELLOW  = "#FFFFFF00";
    public static final String GBROWN   = "#FF91890A";
    public static final String GRED     = "#FF9B0000";
    public static final String GPINK    = "#FFFF4444";
    public static final String GPURPLE  = "#FF92167F";
    public static final String GBLUE    = "#FF3387f0";
    public static final String GGREEN   = "#FF00FF00";
    public static final String GORANGE  = "#FFFFAA00";

    public static final long WHITE_ALP = 0x33FFFFFF;
    public static final long WHITE = 0xFFFFFFFF;
    public static final String SWHITE = "#FFFFFFFF";

    public static final long GRAY_ALP = 0x33888888;
    public static final long GRAY = 0xFF888888;
    public static final String SGRAY = "#FF888888";

    public static final long BLACK_ALP = 0x33000000;
    public static final long BLACK = 0xFF000000;
    public static final String SBLACK = "#FF000000";

    public static final long YELLOW_ALP = 0x33FFFF00;
    public static final long YELLOW = 0xFFFFFF00;
    public static final String SYELLOW = "#FFFFFF00";

    public static final long BROWN_ALP = 0x3391890A;
    public static final long BROWN = 0xFF91890A;
    public static final String SBROWN = "#FF91890A";

    public static final long RED_ALP = 0x339B0000;
    public static final long RED = 0xFF9B0000;
    public static final String SRED = "#FF9B0000";
    public static final long PINK_ALP = 0x33FF4444;
    public static final long PINK = 0xFFFF4444;
    public static final String SPINK = "#FFFF4444";

    public static final long PURPLE_ALP = 0x2292167F;
    public static final long PURPLE = 0xFF92167F;
    public static final String SPURPLE = "#FF92167F";

    public static final long BLUE_ALP = 0x333387f0;
    public static final long BLUE = 0xFF3387f0;
    public static final String SBLUE = "#FF3387f0";

    public static final long GREEN_ALP = 0x3333BB33;
    public static final long GREEN = 0xFF00FF00;
    public static final String SGREEN = "#FF00FF00";

    public static final long ORANGE_ALP = 0x33FFAA00;
    public static final long ORANGE = 0xFFFFAA00;
    public static final String SORANGE = "#FFFFAA00";



//    public static final long WHITE_ALP = 0x11FFFFFF;
//    public static final long WHITE = 0xFFFFFFFF;
//
//    public static final long GRAY_ALP = 0x66888888;
//    public static final long GRAY = 0xFF888888;
//
//    public static final long BLACK_ALP = 0x66000000;
//    public static final long BLACK = 0xFF000000;
//
//    public static final long YELLOW_ALP = 0x33FFFF00;
//    public static final long YELLOW = 0xFFFFFF00;
//
//    public static final long BROWN_ALP = 0x5591890A;
//    public static final long BROWN = 0xFF91890A;
//
//    public static final long RED_ALP = 0x559B0000;
//    public static final long RED = 0xFF9B0000;
//    public static final long PINK_ALP = 0x66FF4444;
//    public static final long PINK = 0xFFFF4444;
//
//    public static final long PURPLE_ALP = 0x2292167F;
//    public static final long PURPLE = 0xFF92167F;
//
//    public static final long BLUE_ALP = 0x553387f0;
//    public static final long BLUE = 0xFF3387f0;
//
//    public static final long GREEN_ALP = 0x5500FF00;
//    public static final long GREEN = 0xFF00FF00;
//
//    public static final long ORANGE_ALP = 0x55FFAA00;
//    public static final long ORANGE = 0xFFFFAA00;

    public static String getColorReserve(float cur , float max)
    {
        float v = cur/max;

        if(v<=0.0001F)         return "§4";
        else if(v<=0.2F)    return "§6";
        else if(v<=0.4F)    return "§e";
        else return "§f";
    }
    public static String getColor(float cur , float max)
    {
        float v = cur/max;

        if(v<=0.0001F)      return "§f";
        else if(v<=0.4F)    return "§e";
        else if(v<=0.8F)    return "§6";
        else return "§4";
    }
}
