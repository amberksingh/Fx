package com.example.Fx.programs.regex;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class WordFreqUsingRegex {

    public static void main(String[] args) {
        
        String str = "hello_world, java8.0 is fun!";
        System.out.println("original string : "+str);

        String[] words = str.split("\\W+");
        System.out.println("Arrays.toString(words) = " + Arrays.toString(words));

        String normalisedStr = str.replaceAll("[^a-zA-Z0-9]", " ");
        System.out.println("normalisedStr = " + normalisedStr);
        String[] normalisedWords = normalisedStr.split("\\s+");
        System.out.println("Arrays.toString(normalisedWords) = " + Arrays.toString(normalisedWords));

        Map<String, Long> collected = Arrays.stream(normalisedWords)
                .collect(
                        Collectors.groupingBy(
                                w -> w,
                                Collectors.counting()
                        )
                );
        System.out.println("words with frequencies : ");
        collected.entrySet()
                .stream()
                .forEach(entry -> System.out.println(entry.getKey()+" -> "+entry.getValue()));
    }
}
