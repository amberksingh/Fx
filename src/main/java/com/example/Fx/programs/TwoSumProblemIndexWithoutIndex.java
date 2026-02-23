package com.example.Fx.programs;

import java.util.HashSet;
import java.util.Set;

public class TwoSumProblemIndexWithoutIndex {

    public static void main(String[] args) {

        //Given an array of integers 3, 5, 7, 8, 9, 24 and a target sum of 15,
        // find the two numbers from the array whose sum is equal to the given target.

        Integer[] arr = new Integer[]{3, 5, 7, 8, 9, 24};
        int target = 15;//op:8,7
        //int target = 17;//8,9
        Set<Integer> set = new HashSet<>();
        for (int num : arr) {
            int diff = target - num;
            if (set.contains(diff)) {
                System.out.println(diff+", "+num);
            } else {
                set.add(num);
            }
        }
    }
}
