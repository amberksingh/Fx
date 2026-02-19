package com.example.Fx.programs;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InterviewFindUncommonElements {

    public static void main(String[] args) {

        String s1 = "i like to play badminton";
        String s2 = "i like to play cricket";
        //op:badminton, cricket
        //print unique words-not common words
        HashSet<String> collect1 = Stream.of(s1.split(" "))
                .collect(Collectors.toCollection(HashSet::new));
        HashSet<String> collect2 = Stream.of(s2.split(" "))
                .collect(Collectors.toCollection(HashSet::new));

        Set<String> common = new HashSet<>(collect1);
        common.retainAll(collect2);

        collect1.removeAll(common);
        collect2.removeAll(common);

        collect1.addAll(collect2);
        System.out.println("uncommon elements/unique words : "+collect1);


    }
}
