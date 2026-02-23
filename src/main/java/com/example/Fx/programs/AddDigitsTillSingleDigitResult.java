package com.example.Fx.programs;

import java.util.stream.Stream;

public class AddDigitsTillSingleDigitResult {

    //Given a non-negative integer num, repeatedly add all its digits until the
    //result has only one digit.

    public static void addDigitOldSkool(int num) {

        while (num > 9) {
            int sum = 0;
            while (num > 0) {
                int r = num % 10;
                sum += r;
                num /= 10;
            }
            num = sum;
        }
        System.out.println("Sum till one digit result addDigitOldSkool : " + num);
    }

    public static void addDigits(int num) {
        //int sum = 0;
        while (num > 9) {
            String s = String.valueOf(num);
            num = Stream.of(s.split(""))
                    .mapToInt(Integer::parseInt)
                    .sum();
        }
        System.out.println("Sum till one digit result streams : " + num);
    }

    public static void main(String[] args) {

        int num = 9876;//3
        addDigits(num);
        num = 1231;//7
        addDigits(num);
        num = 1843;//16//7
        addDigits(num);
        num = 84179;//29//11//2
        addDigits(num);

        //
        num = 9876;//3
        addDigitOldSkool(num);
        num = 1231;//7
        addDigitOldSkool(num);
        num = 1843;//16//7
        addDigitOldSkool(num);
        num = 84179;//29//11//2
        addDigitOldSkool(num);
    }
}
