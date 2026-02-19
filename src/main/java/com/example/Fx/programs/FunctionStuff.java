package com.example.Fx.programs;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FunctionStuff {

    public static void main(String[] args) {

        List<String> list = List.of("varun", "arun", "varun", "sharma", "dhoni", "dhoni", "hero", "arun", "vibhore");
        
        //find frequency of every word
        Map<String, Long> collect = list.stream()
                .collect(
                        Collectors.groupingBy(
                                Function.identity(),
                                Collectors.counting()
                        )
                );
        System.out.println("collect = " + collect);

        //find frequency of every word using toMap
        Map<String, Integer> toMapFreq = list.stream()
                .collect(
                        Collectors.toMap(
                                word -> word,
                                freq -> 1,
                                //Integer::sum
                                (oldVal, newVal) -> oldVal + newVal
                        )
                );
        System.out.println("toMapFreq = " + toMapFreq);

        //find duplicates in above map
        System.out.println("duplicates using filter on entrySet : ");
        toMapFreq.entrySet()
                .stream()
                .filter(entry -> entry.getValue() > 1)
                .map(Map.Entry::getKey)
                .forEach(key -> System.out.print(key+", "));

        //find duplicates using Collections.frequency
        List<String> listFreq = list.stream().toList();
        List<String> list1 = list.stream()
                .filter(c -> Collections.frequency(listFreq, c) > 1)
                .distinct()
                .toList();
        System.out.println("\nduplicates in list using Collections.frequency = " + list1);

    }
}
