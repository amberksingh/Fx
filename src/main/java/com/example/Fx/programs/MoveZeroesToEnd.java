package com.example.Fx.programs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MoveZeroesToEnd {

    //Given an array nums, write a function to move all 0's to the end of it
    //while maintaining the relative order of the non-zero elements.
    //Input:  nums = [0, 1, 0, 3, 12]
    //Output: [1, 3, 12, 0, 0]

    public static void main(String[] args) {

        int[] nums = {0, 1, 0, 3, 12};
        System.out.println("nums original array = " + Arrays.toString(nums));

        //1st way old skool
        int pos = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                nums[pos] = nums[i];
                pos++;
            }
        }

        while (pos < nums.length) {
            nums[pos] = 0;
            pos++;
        }
        System.out.println("Arrays.toString(nums) = " + Arrays.toString(nums));

        int[] nums1 = {0, 1, 0, 3, 12};
        //2nd way IntStream
        int[] array = IntStream.concat(
                        IntStream.of(nums1).filter(num -> num != 0),
                        IntStream.of(nums1).filter(num -> num == 0)
                )
                .toArray();
        System.out.println("IntStream way : " + Arrays.toString(array));

        //3rd way
        ArrayList<Integer> list = new ArrayList<>(Arrays.stream(nums1).filter(n -> n!=0).boxed().toList());
        list.addAll(Arrays.stream(nums1).filter(n -> n==0).boxed().toList());
        Integer[] array1 = list.toArray(Integer[]::new);
        System.out.println("List addAll() way : " + Arrays.toString(array1));

        //4th way Collections.nCopies way
        List<Integer> collect = Arrays.stream(nums1)
                .filter(n -> n != 0)
                .boxed()
                .collect(Collectors.toList());
        int zeroFreq = nums1.length - collect.size();
        List<Integer> zeroList = Collections.nCopies(zeroFreq, 0);
        collect.addAll(zeroList);
        System.out.println("Collections.nCopies = " + collect);

    }
}
