package com.example.Fx.programs;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Anagram {

    public static void isAnagramStreams(String s1, String s2) {

        System.out.println("entered strings : "+s1+", "+s2);
        String collect1 = Stream.of(s1.toLowerCase().split(""))
                .sorted()
                .collect(Collectors.joining());
        String collect2 = Stream.of(s2.toLowerCase().split(""))
                .sorted()
                .collect(Collectors.joining());
        System.out.println("collect1 = " + collect1);
        System.out.println("collect2 = " + collect2);
        if (collect1.equals(collect2))
            System.out.println("Anagram");
        else
            System.out.println("NOT Anagram");

    }

    public static void charArrayWay(int num1, int num2) {

        System.out.println("entered numbers : "+num1+", "+num2);
        char[] charArray1 = String.valueOf(num1)
                .toCharArray();
        char[] charArray2 = String.valueOf(num2)
                .toCharArray();
        Arrays.sort(charArray1);
        Arrays.sort(charArray2);

        System.out.println("charArray1 = " + Arrays.toString(charArray1));
        System.out.println("charArray2 = " + Arrays.toString(charArray2));
        if (Arrays.equals(charArray1, charArray2))
            System.out.println("Anagram");
        else
            System.out.println("NOT Anagram");
        System.out.println("====================");
    }

    public static void main(String[] args) {

        int num1 = 12345;
        int num2 = 54321;
        String str1 = "Cheater";
        String str2 = "Teacher";
        charArrayWay(num1, num2);
        isAnagramStreams(str1, str2);

    }
}
