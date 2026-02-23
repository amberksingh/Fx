package com.example.Fx.programs;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

public class SecondHighestNumber {

    public static void main(String[] args) {

        List<Integer> list = List.of(100, 20, 100, 77, 10, 44, 5, 9, 98, 12, 29);

        //streams way
        Integer sec = list.stream()
                .distinct()
                //.sorted(Comparator.reverseOrder())
                .sorted((num1, num2) -> num2.compareTo(num1))
                .skip(1)
                .findFirst()
                .orElse(0);
        System.out.println("SecondHighestNumber = " + sec);

        //min way
        Optional<Integer> min = list.stream()
                .distinct()
                .sorted(Comparator.reverseOrder())
                .skip(1)
                //.mapToInt(Integer::intValue)
                .min(Comparator.reverseOrder());
        if (min.isPresent())
            System.out.println("secondHighest using min : " + min.get());

        //old skool way
        int highest = Integer.MIN_VALUE;
        int secondHighest = Integer.MIN_VALUE;
        for (int num : list) {
            if (num > highest) {
                secondHighest = highest;
                highest = num;
            } else if (num > secondHighest && num != highest) {//num!=highest shud be commented for getting 100
                secondHighest = num;
            }
        }
        System.out.println("secondHighest = " + secondHighest);

    }
}
