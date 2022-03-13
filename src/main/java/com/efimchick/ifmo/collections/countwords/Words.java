package com.efimchick.ifmo.collections.countwords;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

public class Words {
    private static final Pattern WORD_PATTERN = Pattern.compile("[^\\p{L}]+");
    private static final String LINE_SEPARATOR = "\n";
    private static final Pattern NEWLINE_PATTERN = Pattern.compile(LINE_SEPARATOR);
    private static final int MINIMAL_WORD_LENGTH = 4;
    private static final int MINIMAL_WORD_FREQUENCY = 10;
    private final Map<String, Integer> map = new HashMap<>();

    public String countWords(Iterable<String> lines) {
        for (String s: lines) {
            String[] words = WORD_PATTERN.split(s.toLowerCase(Locale.ROOT));
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
        return NEWLINE_PATTERN.matcher(builder.toString()).replaceFirst("");
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


}
