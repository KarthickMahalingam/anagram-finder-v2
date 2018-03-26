package com.anagram.loader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Stream;

public class FileParser {
  public String getSortedKey(String lineFromFile) {
    char[] charArr = lineFromFile.toCharArray();
    Arrays.sort(charArr);
    return (new String(charArr));

  }

  public ConcurrentMap<String, ArrayList<String>> loadToMap(Path inputFileName) throws IOException {
    ConcurrentMap<String, ArrayList<String>> dictionaryMap = new ConcurrentHashMap<String, ArrayList<String>>();
    long startTime = System.currentTimeMillis();
    try (Stream<String> stream = Files.lines(inputFileName)) {
      stream.map(String::toLowerCase).parallel().forEach(line -> {
        String sortedKey = getSortedKey(line);
        addToMap(dictionaryMap, sortedKey, line);
      });
    }
    System.out.println("Dictionary loaded in " + (System.currentTimeMillis() - startTime) + " ms");
    return dictionaryMap;
  }

  private void addToMap(ConcurrentMap<String, ArrayList<String>> dictionaryMap, String key, String value) {
    if (dictionaryMap.containsKey(key)) {
      dictionaryMap.get(key).add(value);
    } else {
      ArrayList<String> arrList = new ArrayList<String>();
      arrList.add(value);
      dictionaryMap.put(key, arrList);
    }
  }

}
