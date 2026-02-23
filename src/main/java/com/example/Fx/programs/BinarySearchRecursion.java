package com.example.Fx.programs;

public class BinarySearchRecursion {

    static int binarySearchRecursion(int[] arr, int target, int low, int high) {

        //int low =0, high = arr.length-1;
        //base case
        //int low = 0, high = arr.length - 1;
        if (low > high)
            return -1;

        int mid = low + (high - low) / 2;
        if (arr[mid] == target)
            return mid;
        else if (arr[mid] < target)
            return binarySearchRecursion(arr, target, mid + 1, high);
        else
            return binarySearchRecursion(arr, target, low, mid - 1);
    }

    public static void main(String[] args) {

        int[] arr = {2, 4, 6, 8, 10};
        int[] arr1 = {2, 4, 6, 8, 10, 18, 25, 30};
        int[] arr2 = {2, 4, 6, 8, 10, 28};
        //low 0, high 4
        int target = 8;
        int res = binarySearchRecursion(arr, target, 0, arr.length - 1);//3
        System.out.println("target position = " + res);

        target = 25;
        res = binarySearchRecursion(arr1, target, 0, arr1.length - 1);//6
        System.out.println("target position = " + res);

        target = 28;
        res = binarySearchRecursion(arr2, target, 0, arr2.length - 1);//5
        System.out.println("target position = " + res);
    }
}
