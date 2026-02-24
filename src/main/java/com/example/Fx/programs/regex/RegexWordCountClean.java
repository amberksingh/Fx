package com.example.Fx.programs.regex;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class RegexWordCountClean {

    public static void main(String[] args) {

        //String str = "hello ,world . java space branch hello nick feature branch edit world";
        String str1 = " hello ,world . java space branch hello nick feature branch edit world ";
        System.out.println("original str and length =" + str1+"->"+str1.length());

        String regex = "[^a-zA-Z ]";//“Match any character that is NOT a letter or space”
        String normalizedString = str1.replaceAll(regex, " ");
        System.out.println("normalizedString and length =" + normalizedString+"->"+normalizedString.length());

        String[] split = normalizedString.split("\\s+");//“split wherever there is whitespace”
        System.out.println("Arrays.toString(split) = " + Arrays.toString(split));
        System.out.println("split array length = " + split.length);//12
        //hello__world___java
        //     ^^     ^^^
        //     cut    cut

        //["", "hello", "world", "java", "space",
        // "branch", "hello", "nick", "feature",
        // "branch", "edit", "world", ""] -> last trailing spaces are discarded by java be default
        //So, above gives one leading space length is 12 -> not 13 not 11.
        long wordCount = Arrays.stream(split)
                .filter(s -> !s.isBlank())
                .count();
        System.out.println("wordCount = " + wordCount);//11//after removing leading space using isBlank()

        Map<String, Long> collect = Arrays.stream(split)
                .filter(s -> !s.isBlank())
                .collect(
                        Collectors.groupingBy(
                                Function.identity(),
                                Collectors.counting()
                        )
                );
        System.out.println("words with frequencies = " + collect);

        System.out.println("=======================================");
        String alphaNumStr = " hello77 ,world . ja55va space 80branch hello77 nick feature 80branch edit4 world ";
        String alphaNumRegex = "[^a-zA-Z0-9 ]";
        String normalizedAlphaNumStr = alphaNumStr.replaceAll(alphaNumRegex, " ");
        System.out.println("normalizedAlphaNumStr and length =" + normalizedAlphaNumStr+"->"+normalizedAlphaNumStr.length());

        String[] normalizedAlphaNumStrArr = normalizedAlphaNumStr.split("\\s+");
        System.out.println("Arrays.toString(normalizedAlphaNumStrArr) = " + Arrays.toString(normalizedAlphaNumStrArr));
        System.out.println("normalizedAlphaNumStrArr array length = " + split.length);//12
        Map<String, Long> collected = Arrays.stream(normalizedAlphaNumStrArr)
                .filter(s -> !s.isBlank())
                .collect(
                        Collectors.groupingBy(
                                Function.identity(),
                                Collectors.counting()
                        )
                );
        System.out.println("normalizedAlphaNumStrArr with frequencies = " + collected);

    }
}
