package com.example.Fx.programs;

import java.util.HashSet;

public class LongestUniqueSubstring {

    static int subString(String s) {
        int left = 0, right = 0, startIndex = 0, maxLength = 0;
        HashSet<Character> hashSet = new HashSet<>();
        while (right < s.length()) {
            if (!hashSet.contains(s.charAt(right))) {
                hashSet.add(s.charAt(right));
                if (right - left + 1 > maxLength) {
                    maxLength = right - left + 1;
                    startIndex = left;
                }
                right++;
            } else {
                hashSet.remove(s.charAt(left));
                left++;
            }
        }
        System.out.println("LongestUniqueSubstring :" + s.substring(startIndex, startIndex + maxLength));
        return maxLength;
    }

    public static void main(String[] args) {

        String input = "abcabcbb";//3//abc
        String input1 = "zabczdfz";//6//abczdf
        String input2 = "abba";//2/ab
        String input3 = "aaaa";//1//a
        String input4 = "abca";//3//abc
        String input5 = "pwwkew";//3//wke

        int subStringLength = subString(input);
        System.out.println("subStringLength = " + subStringLength);
        System.out.println("====================");
        int subStringLength1 = subString(input1);
        System.out.println("subStringLength = " + subStringLength1);
        System.out.println("====================");
        int subStringLength2 = subString(input2);
        System.out.println("subStringLength = " + subStringLength2);
        System.out.println("====================");
        int subStringLength3 = subString(input3);
        System.out.println("subStringLength = " + subStringLength3);
        System.out.println("====================");
        int subStringLength4 = subString(input4);
        System.out.println("subStringLength = " + subStringLength4);
        System.out.println("====================");
        int subStringLength5 = subString(input5);
        System.out.println("subStringLength = " + subStringLength5);
    }
}
