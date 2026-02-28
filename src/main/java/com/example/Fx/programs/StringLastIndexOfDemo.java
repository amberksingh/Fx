package com.example.Fx.programs;

public class StringLastIndexOfDemo {

    public static void main(String[] args) {

        //indexOf(char, fromIndex) → searches forward starting at fromIndex or 0 if no fromIndex.
        //searches forward and returns first occurrence
        //if no fromIndex, then start from beginning i.e 0
        //start from fromIndex and ignore previous occurrences if found before fromIndex
        //overall index value will still be counted from 0 only
        //returns -1 if not found

        String str = "hello world order";
        System.out.println("indexOf : ");
        System.out.println(str.indexOf('o'));//4
        System.out.println(str.indexOf('o', 5));//7
        System.out.println(str.indexOf('l', 5));//9
        System.out.println(str.indexOf('r'));//8
        System.out.println(str.indexOf('r', 10));//13
        System.out.println(str.indexOf('l', 1));//2
        System.out.println(str.indexOf("l"));//2
        System.out.println(str.indexOf("l", 4));//9
        System.out.println(str.indexOf("h"));//0
        System.out.println(str.indexOf("h", 5));//-1
        System.out.println(str.indexOf("e", 5));//15
        System.out.println(str.indexOf("z", 5));//-1

        //searches backward and returns last occurrence
        //returns -1 if not found
        //
        //lastIndexOf(char, fromIndex) → searches backward starting at fromIndex.
        //The String is searched backwards starting at the last character.
        //if no fromIndex is specified, search will continue till last index
        //backwards starting at the last character if no fromIndex
        //overall index value will still be counted from 0 only
        String str1 = "order hello world";
        System.out.println("lastIndexOf : ");
        System.out.println(str1.lastIndexOf('o'));//13
        System.out.println(str1.lastIndexOf('o', 5));//0
        System.out.println(str1.lastIndexOf('l', 5));//-1
        System.out.println(str1.lastIndexOf('l'));//15
        System.out.println(str1.lastIndexOf("w"));//12
        System.out.println(str1.lastIndexOf("w", 14));//12
        System.out.println(str1.lastIndexOf("l", 2));//-1
        System.out.println(str1.lastIndexOf("e", 6));//3
        System.out.println(str1.lastIndexOf("g"));//-1
        System.out.println(str1.lastIndexOf('r'));//14
        System.out.println(str1.lastIndexOf('r', 10));//4
        System.out.println(str1.lastIndexOf('o', 12));//10
        System.out.println(str1.lastIndexOf('r', 4));//4

    }
}
