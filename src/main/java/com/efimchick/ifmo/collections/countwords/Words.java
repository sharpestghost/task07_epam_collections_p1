package com.efimchick.ifmo.collections.countwords;


import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class Words {

    private static final String WORD_DETERMINER = "[^\\p{L}]+";
    private final int MINIMAL_WORD_LENGTH = 4;
    private final int MINIMAL_WORD_FREQUENCY = 10;
    private final Map<String, Integer> map = new HashMap<>();

    public String countWords(List<String> lines) {
        for (String s: lines) {
            String[] words = s.toLowerCase(Locale.ROOT).split(WORD_DETERMINER);
            for (String word: words) {
                fillMap(word);
            }
        }
        return mapToString();

    }

    private void fillMap(String word) {
        if (word.length() >= MINIMAL_WORD_LENGTH) {
            if (!map.containsKey(word)) {
                map.put(word, 1);
            } else {
                int count = map.get(word);
                map.put(word, count + 1);
            }
        }
    }

    private String mapToString() {
        LinkedHashMap<String, Integer> reverseSortedMap = new LinkedHashMap<>();
        map.entrySet()
                .stream().sorted(Map.Entry.comparingByKey())
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> reverseSortedMap.put(x.getKey(), x.getValue()));
        return reverseSortedMap.entrySet().stream().filter(x -> x.getValue() >= MINIMAL_WORD_FREQUENCY)
                .map(key -> key.getKey().toLowerCase(Locale.ROOT) + " - " + key.getValue())
                .collect(Collectors.joining("\n"));

    }
}
