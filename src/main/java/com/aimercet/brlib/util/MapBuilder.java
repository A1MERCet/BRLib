package com.aimercet.brlib.util;

import java.util.HashMap;

public class MapBuilder<K,V>
{
    public final HashMap<K,V> map = new HashMap<>();
    public MapBuilder<K,V> put(K k, V v) {map.put(k, v);return this;}
}
