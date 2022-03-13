package com.efimchick.ifmo.collections.countwords;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Words {

    private static final String WORD_DETERMINER = "[^\\p{L}]+";
    private static final String LINE_SEPARATOR = "\n";
    private static final int MINIMAL_WORD_LENGTH = 4;
    private static final int MINIMAL_WORD_FREQUENCY = 10;
    private final Map<String, Integer> map = new HashMap<>();

    public String countWords(Iterable<String> lines) {
        for (String s: lines) {
            String[] words = s.toLowerCase(Locale.ROOT).split(WORD_DETERMINER);
            for (String word: words) {
                fillMap(word);
            }
        }
        removeAllRareWords();
        return listToString(sortAll());

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
    public String listToString(Iterable<Map.Entry<String, Integer>> list) {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, Integer> entry: list) {
            builder.append(LINE_SEPARATOR).append(entry.getKey()).append(" - ").append(entry.getValue());
        }
        return builder.toString().replaceFirst(LINE_SEPARATOR,"");
    }



    private void removeAllRareWords() {
        Iterator<Integer> iterator = map.values().iterator();
        while (iterator.hasNext()) {
            Integer value = iterator.next();
            if (value < MINIMAL_WORD_FREQUENCY) {
                iterator.remove();
            }
        }
    }

    private List<Map.Entry<String, Integer>> sortAll() {
        List<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());
        list.sort(new MapComparator());
        return list;
    }

    /* Lambda/streams variant
    private String mapToString() {
       LinkedHashMap<String, Integer> reverseSortedMap = new LinkedHashMap<>();
        map.entrySet()
                .stream().sorted(Map.Entry.comparingByKey())
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> reverseSortedMap.put(x.getKey(), x.getValue()));
        return reverseSortedMap.entrySet().stream().filter(x -> x.getValue() >= MINIMAL_WORD_FREQUENCY)
                .map(key -> key.getKey().toLowerCase(Locale.ROOT) + " - " + key.getValue())
                .collect(Collectors.joining("\n"));
    } */

}
