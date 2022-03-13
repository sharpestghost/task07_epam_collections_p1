package com.efimchick.ifmo.collections.countwords;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Map;

public class MapComparator implements Comparator<Map.Entry<String, Integer>>, Serializable {

    private static final long serialVersionUID = 1;

    public int compare(Map.Entry<String, Integer> that, Map.Entry<String, Integer> other) {
        int difference = other.getValue().compareTo(that.getValue());
        if (difference == 0) {
            difference = that.getKey().compareTo(other.getKey());
        }
        return difference;
    }
}
