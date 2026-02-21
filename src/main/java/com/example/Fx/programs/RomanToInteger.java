package com.example.Fx.programs;

import java.util.HashMap;

public class RomanToInteger {
    //Roman numerals are represented by seven different symbols: I, V, X, L, C, D and M.
    //
    //Symbol       Value
    //I             1
    //V             5
    //X             10
    //L             50
    //C             100
    //D             500
    //M             1000
    //For example, 2 is written as II in Roman numeral, just two ones added together.
    // 12 is written as XII, which is simply X + II. The number 27 is written as XXVII, which is XX + V + II.
    //
    //Roman numerals are usually written largest to smallest from left to right.
    // However, the numeral for four is not IIII. Instead, the number four is written as IV.
    // Because the one is before the five we subtract it making four. The same principle applies to the number nine,
    // which is written as IX. There are six instances where subtraction is used:
    //
    //I can be placed before V (5) and X (10) to make 4 and 9.
    //X can be placed before L (50) and C (100) to make 40 and 90.
    //C can be placed before D (500) and M (1000) to make 400 and 900.
    //Given a roman numeral, convert it to an integer.
    //
    //
    //
    //Example 1:
    //
    //Input: s = "III"
    //Output: 3
    //Explanation: III = 3.
    //Example 2:
    //
    //Input: s = "LVIII"
    //Output: 58
    //Explanation: L = 50, V= 5, III = 3.
    //Example 3:
    //
    //Input: s = "MCMXCIV"
    //Output: 1994
    //Explanation: M = 1000, CM = 900, XC = 90 and IV = 4.
    //
    //
    //Constraints:
    //
    //1 <= s.length <= 15
    //s contains only the characters ('I', 'V', 'X', 'L', 'C', 'D', 'M').
    //It is guaranteed that s is a valid roman numeral in the range [1, 3999].
    static void convertToInteger(String number) {

        HashMap<Character, Integer> map = new HashMap<>();
        map.put('I', 1);
        map.put('V', 5);
        map.put('X', 10);
        map.put('L', 50);
        map.put('C', 100);
        map.put('D', 500);
        map.put('M', 1000);

        char[] charArray = number.toCharArray();
        int sum = 0;
        for (int i = 0; i < charArray.length; i++) {
            if (i < charArray.length - 1 && map.get(charArray[i]) < map.get(charArray[i + 1]))
                sum -= map.get(charArray[i]);
            else
                sum += map.get(charArray[i]);
        }
        System.out.println("Roman " + number + " = " + sum);
    }

    public static void main(String[] args) {
        String input = "III";//3
        String input1 = "LVIII";//58
        String input2 = "MCMXCIV";//1994
        String input3 = "XIV";//14
        String input4 = "CVI";//106
        String input5 = "XLIII";//43
        String input6 = "XLVII";//47

        convertToInteger(input);
        convertToInteger(input1);
        convertToInteger(input2);
        convertToInteger(input3);
        convertToInteger(input4);
        convertToInteger(input5);
        convertToInteger(input6);
    }
}
