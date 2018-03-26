package com.anagram.finder;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentMap;

import com.anagram.loader.FileParser;

public class AnagramFinder {
  ConcurrentMap<String, ArrayList<String>> dictionaryMap;

  public AnagramFinder(ConcurrentMap<String, ArrayList<String>> dictionaryMap) {
    this.dictionaryMap = dictionaryMap;
  }

  public ArrayList<String> getAnagramIfPresent(String input) {
    String sortedKey = new FileParser().getSortedKey(input);
    if (dictionaryMap.containsKey(sortedKey)) {
      ArrayList<String> matchingList = dictionaryMap.get(sortedKey);
      if (matchingList.size() == 1)
        return null;
      else
        return matchingList;
    }
    return null;
  }
}
