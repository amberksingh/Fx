package com.example.Fx.programs;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class InterviewReplaceAlternateChars {

    public static void main(String[] args) {

        String s = "bangladesh";
        //o/p : bynylydysy
        //replace alternate with y
        String collect = IntStream.range(0, s.length())
                .mapToObj(i -> {
                    if (i % 2 != 0)
                        return "y";
                    return Character.toString(s.charAt(i));
                })
                .collect(Collectors.joining());
        System.out.println("collect = " + collect);

        int code = 97;//'a'
        int upperCase = Character.toUpperCase(code);//65 i.e 'A'
        System.out.println("upperCase = " + upperCase);//65
        String capA = Character.toString(upperCase);
        System.out.println("capA = " + capA);//"A"
        
        char charSmallA = 'a';
        char charUpperCaseA = Character.toUpperCase(charSmallA);//'A'
        System.out.println("upperCaseA = " + charUpperCaseA);//'A'
        String stringCapA = Character.toString(charUpperCaseA);//"A"
        System.out.println("stringCapA = " + stringCapA);
    }
}
