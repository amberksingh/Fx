package com.example.Fx.programs;

import java.util.Arrays;
import java.util.Set;
import java.util.function.IntFunction;
import java.util.stream.Collectors;

public class CommonElementsBetweenArrays {

    public static void main(String[] args) {

        int arr1[] = {1, 2, 3, 4, 5, 6, 6};
        int arr2[] = {1, 2, 9, 0, 0, 6};

        //1st way
        Set<Integer> collect1 = Arrays.stream(arr1)
                .boxed()
                .collect(Collectors.toSet());
        Set<Integer> collect2 = Arrays.stream(arr2)
                .boxed()
                .collect(Collectors.toSet());
        collect1.retainAll(collect2);
        System.out.println("common elements = " + collect1);

        //2nd way
        int[] array = Arrays.stream(arr2)
                .filter(collect1::contains)
                .toArray();
        System.out.println("common elements 2nd way = " + Arrays.toString(array));

        IntFunction<Integer[]> intFunction = (i) -> new Integer[i];
        IntFunction<Integer[]> intFunction1 = Integer[]::new;

        Integer[] array1 = Arrays.stream(arr2)
                .filter(collect1::contains)
                .boxed()
                .toArray(intFunction1);
        System.out.println("common elements 3rd way = " + Arrays.toString(array1));
    }
}
