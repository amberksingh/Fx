package com.example.Fx.programs;

public class BinarySearchNormal {

    public static int binarySearch(int[] arr, int target) {
        int low = 0;
        int high = arr.length - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (arr[mid] == target) {
                return mid;
            } else if (arr[mid] < target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }

    public static void main(String[] args) {

        int[] arr = {2, 4, 6, 8, 10};
        int[] arr1 = {2, 4, 6, 8, 10, 18, 25, 30};
        int[] arr2 = {2, 4, 6, 8, 10, 28};
        //low 0, high 4
        int target = 8;
        int res = binarySearch(arr, target);//3
        System.out.println("target position = " + res);

        target = 25;
        res = binarySearch(arr1, target);//6
        System.out.println("target position = " + res);

        target = 28;
        res = binarySearch(arr2, target);//5
        System.out.println("target position = " + res);
    }
    //We add low again because:
    //
    //We are calculating the middle relative to the current search range, not from index 0.
    //
    //And this formula also prevents integer overflow.
    //
    //🔎 Step-by-Step Understanding
    //
    //Binary search doesn’t always search the full array.
    //
    //After some iterations:
    //
    //low = 40
    //high = 80
    //
    //Now we want the middle between 40 and 80.
    //
    //What does this part mean?
    //(high - low) / 2
    //
    //This calculates:
    //
    //Half of the current range size.
    //
    //Example:
    //
    //high = 80
    //low = 40
    //
    //high - low = 40
    //40 / 2 = 20
    //
    //So the midpoint is 20 steps ahead of low.
    //
    //Now we add low:
    //
    //mid = low + 20
    //mid = 40 + 20 = 60
    //
    //Correct middle = 60.
    //
    //🧠 Why Not Just (low + high) / 2 ?
    //
    //Mathematically it's the same:
    //
    //(40 + 80) / 2 = 120 / 2 = 60
    //
    //But here’s the problem 👇
    //
    //⚠️ Integer Overflow Problem
    //
    //Suppose:
    //
    //low = 2,000,000,000
    //high = 2,100,000,000
    //
    //Then:
    //
    //low + high
    //
    //becomes:
    //
    //4,100,000,000
    //
    //But int max value is:
    //
    //2,147,483,647
    //
    //Boom 💥 overflow → negative number.
    //
    //Binary search breaks.
    //
    //✅ Why low + (high - low)/2 Is Safe
    //
    //Because:
    //
    //high - low
    //
    //is always within array size.
    //
    //Then we add that smaller number to low.
    //
    //No overflow.
    //
    //🔥 Visual Intuition
    //
    //Think of it like this:
    //
    //Instead of:
    //
    //"Go to the middle of 0 and high"
    //
    //We say:
    //
    //"Start at low, then move half the remaining distance"
    //
    //That’s why we add low again.
    //
    //🎯 Clean Mental Model
    //mid = low + half_of_remaining_distance
    //
    //where:
    //
    //half_of_remaining_distance = (high - low) / 2
    //🚀 Summary
    //
    //We use:
    //
    //low + (high - low) / 2
    //
    //because:
    //
    //It calculates midpoint relative to current range.
    //
    //It avoids integer overflow.
    //
    //It is mathematically equivalent to (low + high)/2.
    //
    //It is the safe industry standard.
    //
    //If you'd like, I can also show how binary search fails with overflow using a real example.
    //
    //give example of low add to show correct mid window size
    //
    //Perfect 👍 let’s walk through a real example with numbers so you clearly see why we add low.
    //
    //🔢 Suppose We Have This Array (indexes shown)
    //Index:  0  1  2  3  4  5  6  7  8  9
    //Value:  2  5  8 12 16 23 38 56 72 91
    //
    //Now suppose after some iterations:
    //
    //low = 4;
    //high = 9;
    //
    //So we are only searching in this window:
    //
    //Index:  4  5  6  7  8  9
    //Value: 16 23 38 56 72 91
    //
    //This is our current search window.
    //
    //❌ If You Forget to Add low
    //
    //If you do:
    //
    //int mid = (high - low) / 2;
    //
    //Compute it:
    //
    //high - low = 9 - 4 = 5
    //5 / 2 = 2
    //
    //So:
    //
    //mid = 2
    //
    //But index 2 is:
    //
    //Value = 8
    //
    //🚨 That is OUTSIDE the current search window!
    //
    //We wanted something between index 4 and 9.
    //
    //✅ Correct Way
    //
    //Now use:
    //
    //int mid = low + (high - low) / 2;
    //
    //Compute:
    //
    //high - low = 5
    //5 / 2 = 2
    //mid = 4 + 2 = 6
    //
    //Now:
    //
    //Index = 6
    //Value = 38
    //
    //✔ That is correctly inside our window.
    //
    //🧠 Why This Works
    //
    //Think of it like this:
    //
    //(high - low) / 2  → half the window size
    //
    //In this case:
    //
    //Window size = 6 elements (indexes 4 to 9)
    //Half ≈ 2
    //
    //So we move 2 steps from low.
    //
    //low (4) → +2 → 6
    //🎯 Visual Representation
    //
    //Current window:
    //
    //[4, 5, 6, 7, 8, 9]
    // L        M       H
    //
    //We are saying:
    //
    //Start at L, move halfway toward H.
    //
    //That’s exactly:
    //
    //low + (high - low)/2
    //💡 Simple Mental Formula
    //mid = starting_point + half_of_window
    //
    //If you don’t add low,
    //you’re calculating mid relative to index 0 — which is wrong once the window shifts.
    //
    //🚀 Final Takeaway
    //
    //We add low because:
    //
    //(high - low)/2 only gives distance
    //
    //We must add it to low to get the correct index
    //
    //Otherwise, mid will fall outside the current search range
}
