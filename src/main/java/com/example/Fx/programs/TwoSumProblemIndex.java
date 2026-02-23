package com.example.Fx.programs;

import java.util.HashMap;
import java.util.Map;

public class TwoSumProblemIndex {

    //Given an array of integers nums and an integer target, return indices
    //of the two numbers such that they add up to target.
    public static void main(String[] args) {

        int[] nums = {3, 7, 15, 11};
        //int target = 26;//2,3
        int target = 18;//1,3 and 0,2
        //int target = 10;//0,1

        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int diff = target - nums[i];
            if (map.containsKey(diff)) {
                System.out.println(map.get(diff) + ", " + i);
            } else {
                map.put(nums[i], i);
            }
        }
    }
}
