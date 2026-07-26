package com.aimercet.brlib.util;

import com.aimercet.brlib.log.Logger;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.UUID;

public class UtilString
{
    private static final String BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static HashSet<String> base62UUIDCache = new HashSet<>();

    public static boolean isEmpty(String v){return v== null || v.isEmpty();}

    public static String randomBase62UUID() {
        UUID uuid = UUID.randomUUID();
        byte[] bytes = new byte[16];
        long msb = uuid.getMostSignificantBits();
        long lsb = uuid.getLeastSignificantBits();
        for (int i = 7; i >= 0; i--) {
            bytes[i] = (byte) (msb & 0xFF);
            msb >>= 8;
            bytes[8 + i] = (byte) (lsb & 0xFF);
            lsb >>= 8;
        }
        bytes[0] &= 0x7F;
        BigInteger value = new BigInteger(1, bytes);

        StringBuilder sb = new StringBuilder();
        while (value.compareTo(BigInteger.ZERO) > 0) {
            BigInteger[] divRem = value.divideAndRemainder(BigInteger.valueOf(62));
            sb.append(BASE62.charAt(divRem[1].intValue()));
            value = divRem[0];
        }
        while (sb.length() < 22) {
            sb.append('0');
        }
        String r = sb.reverse().toString();
        if (base62UUIDCache.contains(r)) {
            String r2 = randomBase62UUID();
            Logger.warn(String.format("重复的Base62UUID生成: %s (小概率事件), 已重新生成: %s" , r, r2));
            return r2;
        }
        base62UUIDCache.add(r);
        return r;
    }

    public static void addBase62UUIDCache(String u) {base62UUIDCache.add(u);}
    public static void removeBase62UUIDCache(String u) {base62UUIDCache.remove(u);}
}
