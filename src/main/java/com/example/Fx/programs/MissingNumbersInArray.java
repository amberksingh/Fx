package com.example.Fx.programs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MissingNumbersInArray {

    //Given an array nums of n integers where nums[i] is in the range [1, n],
    //return an array of all the integers in the range [1, n] that do not appear in
    //nums.
    public static void main(String[] args) {

        //Input: nums = [4, 3, 2, 7, 8, 2, 3, 1]
        //n = 8
        //Output: [5, 6]
        int[] nums = new int[]{4, 3, 2, 7, 8, 2, 3, 1};
        //int[] nums = new int[]{5, 3, 2, 7, 6, 2, 3, 1};//4,8

        System.out.println("Entered array : " + Arrays.toString(nums));

        //1st way
        for (int i = 0; i < nums.length; i++) {
            int index = Math.abs(nums[i]) - 1;
            nums[index] = -Math.abs(nums[index]);
        }

        //int[] missingNums = new int[10];
        List<Integer> missingNums = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > 0)
                missingNums.add(i + 1);
        }
        Integer[] array = missingNums.toArray(new Integer[0]);
        Integer[] array1 = missingNums.toArray(Integer[]::new);

        IntFunction<Integer[]> intFunction = (i) -> new Integer[i];
        //Integer[] array2 = missingNums.toArray(Integer[]::new);
        Integer[] array2 = missingNums.toArray(intFunction);

        System.out.println("missingNums = " + Arrays.toString(array1));

        //2nd way
        int[] nums2 = new int[]{5, 3, 2, 7, 6, 2, 3, 1};//4,8
        int[] nums3 = new int[]{4, 3, 2, 7, 8, 2, 3, 1};//5,6
        Set<Integer> collect = Arrays.stream(nums3)
                .boxed()
                .collect(Collectors.toSet());
        int[] array3 = IntStream.rangeClosed(1, nums.length)
                .filter(i -> !collect.contains(i))
                .toArray();
        System.out.println("Arrays.toString(array3) = " + Arrays.toString(array3));
    }
}
