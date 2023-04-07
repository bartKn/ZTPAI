package pl.bartkn.ztpai.util;

import java.util.*;

public class MapUtil {
    public static <K, V extends Comparable<? super  V>> Map<K, V> sortDescending(Map<K, V> map) {
        return sort(map, (e1, e2) -> e2.getValue().compareTo(e1.getValue()));
    }

    public static <K, V extends Comparable<? super  V>> Map<K, V> sortAscending(Map<K, V> map) {
        return sort(map, Map.Entry.comparingByValue());
    }

    private static <K, V extends Comparable<? super  V>> Map<K, V> sort(Map<K, V> map, Comparator<? super Map.Entry<K, V>> comparator) {
        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(comparator);
        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }

    public static <K, V extends Comparable<? super  V>> K getKeyOfTopValue(Map<K, V> map) {
        return Collections.max(map.entrySet(), Map.Entry.comparingByValue()).getKey();
    }
}
