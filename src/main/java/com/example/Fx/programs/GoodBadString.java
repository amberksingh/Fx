package com.example.Fx.programs;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GoodBadString {

    //Given a string s, return true if s is a "good" string, or false otherwise.
    //A string s is good if all characters that appear in s have the same number of
    //occurrences (i.e., the same frequency).

    public static void checkGoodBad(String s) {

        System.out.println("checkGoodBad : ");
        Set<Long> collect = Stream.of(s.split(""))
                .collect(
                        Collectors.groupingBy(
                                Function.identity(),
                                Collectors.counting()
                        )
                )
                .entrySet()
                .stream()
                .map(Map.Entry::getValue)
                .collect(Collectors.toSet());
        if (collect.size() == 1)
            System.out.println("Good String : " + s);
        else
            System.out.println("Bad String : " + s);

    }

    public static void usingCollectionFrequency(String s) {

        System.out.println("usingCollectionFrequency : ");
        List<String> list = Arrays.stream(s.split("")).toList();
        HashSet<Integer> collect = Arrays.stream(s.split(""))
                .map(c -> Collections.frequency(list, c))
                .collect(Collectors.toCollection(HashSet::new));
        if (collect.size() == 1)
            System.out.println("Good String : " + s);
        else
            System.out.println("Bad String : " + s);

    }

    public static void main(String[] args) {

        List<Object> objects = List.of("abc", "def");
        Object[] array = objects.toArray(new Object[0]);
        Object[] array1 = objects.toArray(new Object[objects.size()]);
        System.out.println("Arrays.toString(array) = " + Arrays.toString(array));
        System.out.println("Arrays.toString(array) = " + Arrays.toString(array1));

        String str = "hheelloo";//good
        String str1 = "hhelloo";//bad
        String str2 = "wooden";//bad
        String str3 = "night";//good string as 1 is freq of every char here also
        checkGoodBad(str);
        checkGoodBad(str1);
        checkGoodBad(str2);
        checkGoodBad(str3);

        usingCollectionFrequency(str);
        usingCollectionFrequency(str1);
        usingCollectionFrequency(str2);
        usingCollectionFrequency(str3);
    }
}
