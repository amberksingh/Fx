package com.example.Fx.programs;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PalindromeDemo {

    static boolean isPalindrome(String s) {
        //0 is the identity element, which acts like a default / neutral value for the operation.
        //
        //But “default” alone is not the full story — identity has strict rules.
        //
        //Your code
        //List<String> nums = List.of("1", "2", "3");
        //
        //Integer reduce = nums.stream()
        //        .map(Integer::valueOf)
        //        .reduce(0, (num1, num2) -> num1 + num2);
        //
        //What does 0 really mean here?
        //Identity = starting value + neutral element
        //
        //For addition:
        //
        //0 + x = x
        //
        //
        //So 0:
        //
        //Does not change the result
        //
        //Is safe to start with
        //
        //Is safe for parallel execution
        //
        //Step-by-step execution (sequential)
        //
        //Stream after map:
        //
        //[1, 2, 3]
        //
        //
        //Reduction:
        //
        //start = 0
        //0 + 1 = 1
        //1 + 2 = 3
        //3 + 3 = 6
        //
        //
        //Final result:
        //
        //6
        //
        //What if stream is empty?
        //List<String> nums = List.of();
        //
        //
        //Then:
        //
        //reduce = 0
        //
        //
        //✔ No exception
        //✔ Identity is returned
        //
        //Why identity is NOT just “any default”
        //
        //Identity must satisfy:
        //
        //identity ⊕ x = x
        //x ⊕ identity = x
        //
        //
        //For addition → 0
        //For multiplication → 1
        //For string concat → ""
        //
        //What if identity is WRONG?
        //.reduce(10, Integer::sum)
        //
        //
        //Execution:
        //
        //10 + 1 + 2 + 3 = 16
        //
        //
        //⚠️ Still works syntactically, but changes meaning
        //
        //Identity is CRITICAL for parallel streams
        //
        //In parallel:
        //
        //Each thread starts with identity
        //
        //Partial results are merged
        //
        //Wrong identity = wrong result
        //
        //Compare with reduce WITHOUT identity
        //Optional<Integer> sum =
        //    nums.stream()
        //        .map(Integer::valueOf)
        //        .reduce(Integer::sum);
        //
        //
        //No default value
        //
        //Returns Optional<Integer>
        //
        //Empty stream → Optional.empty()
        //
        //When to use which
        //Use case	reduce with identity	reduce without identity
        //Need guaranteed result	✅	❌
        //Empty stream allowed	✅	❌
        //Neutral operation	✅	❌
        //Want Optional	❌	✅
        //One-line interview answer
        //
        //The identity in reduce() is the neutral starting value that does not affect the result and is required for safe parallel reduction.
        //
        //Final confirmation
        //
        //“0 for identity means default val?”
        //
        //✅ Yes — but more precisely, it is the neutral element of the operation, not just any default.

        List<String> nums = List.of("1", "2", "3");
        Integer reduce = nums.stream()
                .map(Integer::valueOf)
                .reduce(0, (num1, num2) -> num1 + num2);
        System.out.println("reduce = " + reduce);

        //reduce
        String rev = Arrays.stream(s.split(""))
                .reduce((c1, c2) -> c2 + c1)
                .orElse(null);
        System.out.println("rev = " + rev);
        return rev.equals(s);
    }

    public static boolean isPalindromeMethod(String s) {
        String collect = Stream.of(s)
                .map(StringBuffer::new)
                .map(StringBuffer::reverse)
                .map(StringBuffer::toString)
                .collect(Collectors.joining());
        System.out.println("rev buffer way = " + collect);
        return s.equals(collect);
    }

    public static boolean numberWay(int number) {
        //5115
        int temp = number;
        int rev = 0;
        while (number > 0) {
            int r = number % 10;//5//1//1//5
            rev = (rev * 10) + r;//5//51//511//5110
            number /= 10;//511//51//5
        }
        System.out.println("rev = " + rev);
        return temp == rev;
    }

    public static void main(String[] args) {
        String str = "hannah";
        String str1 = "malayalam";
        String str2 = "abc";

        boolean palindrome = isPalindrome(str);
        String res = palindrome ? "palindrome" : "NOT palindrome";
        System.out.println(res);
        System.out.println("=============");

        boolean palindrome1 = isPalindromeMethod(str1);
        String res1 = palindrome1 ? "palindrome" : "NOT palindrome";
        System.out.println(res1);
        System.out.println("=============");

        int number = 4053;
        int number1 = 5115;
        int number2 = 4004;
        boolean palindrome2 = numberWay(number1);
        String res2 = palindrome2 ? "palindrome" : "NOT palindrome";
        System.out.println(res2);
    }
}
