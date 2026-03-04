package com.example.Fx.programs;

public class ConversionsStuff {

    public static void main(String[] args) {

        String s = String.valueOf(65);//no unicode conversion here
        System.out.println("s = " + s);//65

        int primitive = Integer.parseInt("100");
        Integer wrapper1 = Integer.valueOf("100");
        Integer wrapper2 = Integer.valueOf(100);

        System.out.println(primitive);  // 100
        System.out.println(wrapper1);   // 100
        System.out.println(wrapper2);   // 100

        System.out.println(Character.toString(65));     // "A"
        System.out.println(Character.toString('A'));    // "A"

        System.out.println(Character.toUpperCase(97));  // 65 (code point for 'A')
        System.out.println(Character.toUpperCase('a')); // 'A'

        //1. Character.toString(int c)
        //•	Signature:
        //•	public static String toString(int codePoint)
        //•	Takes a Unicode code point (not the character itself, but its integer value).
        //•	Returns a String representing the character(s) for that code point.
        //•	If the code point is in the BMP (Basic Multilingual Plane), it’s a single char;
        // if it’s outside, it may be 2 chars (surrogate pair).
        //Example:
        //System.out.println(Character.toString(65));     // "A" (65 = 'A')
        //System.out.println(Character.toString(9731));   // "☃" (Unicode snowman U+2603)
        //
        //2. Character.toString(char c)
        //•	Signature:
        //•	public static String toString(char c)
        //•	Takes a char (16-bit primitive) and turns it into a String.
        //•	Equivalent to String.valueOf(c).
        //
        //
        //
        //Example:
        //char ch = 'A';
        //System.out.println(Character.toString(ch)); // "A"
        //So:
        //•	toString(int) interprets the int as a Unicode code point.
        //•	toString(char) just converts a char directly to a String.
        //
        //3. Character.toUpperCase(int c)
        //•	Signature:
        //•	public static int toUpperCase(int codePoint)
        //•	Accepts a Unicode code point (int).
        //•	Returns another code point (int) for the uppercase form.
        //•	Useful when dealing with supplementary Unicode characters (beyond char).
        //Example:
        //System.out.println((char) Character.toUpperCase(97)); // 'A' (97 = 'a')
        //System.out.println(Character.toUpperCase(0x10428));   // 0x10400 (Deseret alphabet)
        //
        //4. Character.toUpperCase(char c)
        //•	Signature:
        //•	public static char toUpperCase(char ch)
        //•	Accepts a single char.
        //•	Returns the uppercase form as another char.
        //•	Limited to characters that fit in 16 bits.
        //Example:
        //System.out.println(Character.toUpperCase('a')); // 'A'
        //
        //✅ Summary Table
        //Method	                    Input type	                Output	            Use case
        //Character.toString(int c)	    Unicode code point (int)	String	            Convert int → String (handles supplementary chars too)
        //Character.toString(char c)	Single char	                String	            Convert char → String
        //Character.toUpperCase(int c)	Unicode code point (int)	int (code point)	Uppercase transformation for Unicode code point
        //Character.toUpperCase(char c)	Single char	                char	            Uppercase transformation for single char


    }
}
