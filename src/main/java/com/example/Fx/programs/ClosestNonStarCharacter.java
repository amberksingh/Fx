package com.example.Fx.programs;

import java.util.Stack;

public class ClosestNonStarCharacter {

    //        you are given a string s, which contains stars *.
    //        In one operation, you can:
    //· Choose a star in s.
    //· Remove the closest non-star character to its left, as well as remove the star itself.
    //        Return the string after all stars have been removed.
    //                Note:
    //· The input will be generated such that the operation is always possible.
    //· It can be shown that the resulting string will always be unique.
    //
    //        Input: s = "leet**cod*e"
    //
    //        Output: "lecoe"
    public static void main(String[] args) {

        String str = "leet**cod*e";

        //1st way
        Stack<Character> stack = new Stack<>();
        char[] charArray = str.toCharArray();
        for (char ch : charArray) {
            if (ch != '*') {
                stack.push(ch);
            } else {
                stack.pop();
            }
        }
        StringBuilder sb = new StringBuilder();
        for (char poppedElement : stack) {
            sb.append(poppedElement);
        }
        System.out.println("str after removing * stack way : "+sb);

        //2nd way
        StringBuilder stringBuilder = new StringBuilder();
        for (char c : charArray) {
            if (c == '*')
                stringBuilder.deleteCharAt(stringBuilder.length()-1);
            else
                stringBuilder.append(c);
        }
        System.out.println("str after removing * stringBuilder way : " + stringBuilder);
    }
}
