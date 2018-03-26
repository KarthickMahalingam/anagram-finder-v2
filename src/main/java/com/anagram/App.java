package com.anagram;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ConcurrentMap;

import com.anagram.finder.AnagramFinder;
import com.anagram.loader.FileParser;

public class App {
  private static Scanner sc;

  public static void main(String[] args) throws IOException {
    if (args[0] == null) {
      throw new IllegalArgumentException("No filename specified");
    }
    File file = new File(args[0]);
    if (!file.exists())
      throw new IllegalArgumentException("No such file exists");
    if (file.length() == 0)
      throw new IllegalArgumentException("Empty file specified");
    System.out.println("Welcome to the Anagram Finder");
      FileParser fileParser = new FileParser();
      ConcurrentMap<String, ArrayList<String>> dictionaryMap = fileParser.loadToMap(Paths.get(args[0]));
      readInput(dictionaryMap);
  }

  private static void readInput(ConcurrentMap<String, ArrayList<String>> dictionaryMap) {
    String input;
    sc = new Scanner(System.in);
    AnagramFinder anagram = new AnagramFinder(dictionaryMap);
    while (true) {
      System.out.print("Anagram Finder>");
      input = sc.nextLine();
      if (input.equals("exit"))
        System.exit(1);
      searchAnagram(input, anagram);
    }
  }

  private static void searchAnagram(String input, AnagramFinder anagram) {
    ArrayList<String> result = anagram.getAnagramIfPresent(input.toLowerCase());
    printIfAnagramPresent(result, input, System.currentTimeMillis());
  }

  private static void printIfAnagramPresent(ArrayList<String> result, String word, long startTime) {
    if (result == null)
      System.out.println("No Anagrams found for " + word + " in " + (System.currentTimeMillis() - startTime) + "ms");
    else {
      System.out.println(
          result.size() + " Anagrams found for " + word + " in " + (System.currentTimeMillis() - startTime) + "ms");
      System.out.println(String.join(", ", result));
    }

  }
}
