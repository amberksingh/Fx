package com.example.Fx.programs;

import java.util.Stack;

public class ValidParentheses {

    static boolean isValid(String s) {

        char[] charArray = s.toCharArray();
        Stack<Character> stack = new Stack<>();

        for (char ch : charArray) {
            if (ch == '[' || ch == '{' || ch == '(') {
                stack.push(ch);
            } else {
                if (stack.isEmpty())
                    return false;
                char pop = stack.pop();
                if (ch == ']' && pop != '[')
                    return false;
                if (ch == '}' && pop != '{')
                    return false;
                if (ch == ')' && pop != '(')
                    return false;
            }
        }

        return stack.isEmpty();
    }

    public static void main(String[] args) {

        System.out.println("() : " + isValid("()"));       // true
        System.out.println("()[]{} : " + isValid("()[]{}"));   // true
        System.out.println("(] : " + isValid("(]"));       // false
        System.out.println("([)] : " + isValid("([)]"));     // false
        System.out.println("{[]} : " + isValid("{[]}"));     // true
        System.out.println("{[0]} : " + isValid("{[0]}"));     // false
        System.out.println("{[ : " + isValid("{["));     // false
        System.out.println(" : " + isValid(""));     // true
        System.out.println("[[[]]] : " + isValid("[[[]]]"));     // true
        System.out.println("[[[{}]]] : " + isValid("[[[{}]]]"));     // true
    }
}
