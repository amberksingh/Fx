package com.example.Fx.programs;

import java.util.Arrays;

public class ArmstrongCheck {

    //Number	Calculation	Armstrong?
    //370	3³ + 7³ + 0³ = 27 + 343 + 0 = 370	✅ Yes
    //371	3³ + 7³ + 1³ = 27 + 343 + 1 = 371	✅ Yes
    //9474	9⁴ + 4⁴ + 7⁴ + 4⁴ = 9474	        ✅ Yes
    //123	1³ + 2³ + 3³ = 36 ≠ 123	            ❌ No

    static boolean isAnagram(int number) {

        //In short:
        //
        //Character.getNumericValue(65) → looks at the character 'A'.
        //
        //'A' corresponds to 10 in base-36 (0–9 + A–Z).
        //
        //Hence both lines print 10.
        //2️⃣ What getNumericValue() does
        //
        //It converts a Unicode character (or code point) into its numeric value if one exists.
        //
        //For example:
        //
        //Input	Meaning	            Output
        //'0'	digit zero	            0
        //'5'	digit five	            5
        //'A'	hexadecimal digit	    10
        //'B'	hexadecimal digit	    11
        //'a'	hexadecimal digit	    10
        //'Z'	base-36 value	        35
        //'@'	non-numeric	            -1
        //🧩 1️⃣ 65 — Unicode code point
        //


        //Every character in Java (and Unicode in general) has an integer code point.
        //
        //'A' → code point 65 (in ASCII and Unicode).
        //
        //(int) 'A' == 65
        //
        //
        //That’s the binary/encoding identity of 'A'.
        //It’s how 'A' is stored internally.
        //
        //🧠 2️⃣ 10 — Numeric meaning (logical interpretation)
        //
        //When you call:
        //
        //Character.getNumericValue('A');
        //
        //
        //you’re not asking for the Unicode code,
        //you’re asking:
        //
        //“If this character represents some kind of number (like a digit, letter digit, or Roman numeral),
        // what number does it stand for?”
        //
        //Java defines these numeric meanings for alphanumeric characters:
        //
        //Character	Numeric value	Explanation
        //'0'	            0	    digit zero
        //'9'	            9	    digit nine
        //'A' or 'a'	    10	    first letter used in hexadecimal / base-36
        //'B' or 'b'	    11	    second letter
        //…	…	…
        //'Z' or 'z'	    35	    last base-36 symbol
        //
        //So 'A' ⇒ 10
        //'B' ⇒ 11
        //… 'Z' ⇒ 35
        //
        //⚙️ 3️⃣ Summary
        //Concept	Source	Meaning
        //Unicode code point	(int) 'A' → 65	Internal binary identity
        //Numeric value	Character.getNumericValue('A') → 10	Logical numeric meaning (for base-36 / hex)
        //
        //✅ In short:
        //
        //65 → the encoding of 'A'
        //
        //10 → the numeric interpretation of 'A' (used in base-36 or hex systems)
        int numericValue65 = Character.getNumericValue(65);
        System.out.println("numericValue65 A = " + numericValue65);//10
        int numericValue97 = Character.getNumericValue(97);
        System.out.println("numericValue97 a = " + numericValue97);//10

        int A = Character.getNumericValue('A');
        System.out.println("A = " + A);//10
        int a = Character.getNumericValue('a');
        System.out.println("a = " + a);//10


        int one = Character.getNumericValue('1');
        System.out.println("one = " + one);//1

        //THIS IS THE TRICKY ONE! BELOW PART NOT ABOVE
        //
        //👉 Here, you are not passing '1' (the character),
        //you’re passing the integer value 1, which Java interprets as Unicode code point U+0001.
        //
        //U+0001 is a control character, not a printable digit.
        //
        //SO CHARACTER.GETNUMERICVALUE(1) TRIES TO FIND THE NUMERIC MEANING OF THAT CONTROL CHARACTER…
        //➡️ NONE EXISTS → RETURNS -1
        int oneNum = Character.getNumericValue(1);
        System.out.println("oneNum = " + oneNum);//-1

        //Perfect 👏 — you’re absolutely right to ask this —
        //because Character.getNumericValue() can actually return positive values, -1, or -2, and each has a specific meaning.
        //
        //Let’s break it down clearly 👇
        //
        //🧩 Method summary
        //public static int Character.getNumericValue(int codePoint)
        //
        //
        //➡️ Returns an int representing the numeric value of the given Unicode character, if it has one.
        //
        //✅ Return value meanings
        //Return	Meaning
        //0–9, 10–35, etc.	A valid numeric value (e.g., digits, letters like A=10, B=11, etc.)
        //-1	The character does not have any numeric value
        //-2	The character has a numeric value, but it’s not a simple integer (for example, fractions, superscripts,
        // special symbols in Unicode)**
        //🧠 1️⃣ When you get -1
        //
        //Returned when Java cannot interpret the character as a number.
        //
        //Example:
        //
        //System.out.println(Character.getNumericValue('@'));   // -1
        //System.out.println(Character.getNumericValue(' '));   // -1
        //System.out.println(Character.getNumericValue('#'));   // -1
        //System.out.println(Character.getNumericValue(1));     // -1  (control char)
        //
        //
        //✅ Meaning:
        //
        //“This character has no numeric meaning in Unicode.”
        //
        //🧠 2️⃣ When you get -2
        //
        //Returned for characters that represent numeric concepts but not exact integers —
        //for example, fractions, Roman numerals, or symbols that imply numbers but don’t map to a single integer.
        //
        //Examples:
        //
        //System.out.println(Character.getNumericValue('\u00BD')); // ½ (half) → -2
        //System.out.println(Character.getNumericValue('\u216C')); // Ⅼ (Roman numeral 50) → 50
        //System.out.println(Character.getNumericValue('\u2153')); // ⅓ (one third) → -2
        //
        //
        //✅ Meaning:
        //
        //“This character represents a numeric concept, but not a standard whole number.”
        //
        //🧾 3️⃣ Summary table
        //Character	Description	Unicode	Result	Meaning
        //'A'	Latin capital A	U+0041	10	valid number (A→10)
        //'1'	Digit one	U+0031	1	valid number
        //' '	Space	U+0020	-1	no numeric meaning
        //'@'	Symbol	U+0040	-1	no numeric meaning
        //'½'	Fraction one-half	U+00BD	-2	numeric, but not integer
        //'Ⅲ'	Roman numeral 3	U+2162	3	valid numeric meaning
        //
        //✅ In short:
        //
        //-1 → no numeric meaning
        //
        //-2 → has a numeric meaning, but not a single integer (fraction, etc.)
        //
        //0–35 (and beyond) → valid numeric or alphanumeric character value


        //153
        //1^3 + 5^3 + 3^3 = 153
        String s = String.valueOf(number);
        double sum = Arrays.stream(s.split(""))
                .map(Integer::parseInt)
                .mapToDouble(n -> Math.pow(n, s.length()))
                .sum();
        System.out.println("mapToDouble way sum = " + sum);
        return sum == number;
    }

    public static boolean isAnagramGetNumericValueWay(int number) {
        String s = String.valueOf(number);
        Double v = s.chars()
                .map(Character::getNumericValue)
                .mapToObj(x -> Math.pow(x, s.length()))
                .reduce((num1, num2) -> num1 + num2)
                .orElse(0d);
        System.out.println("isAnagramGetNumericValueWay = " + v);
        return v == number;
    }

    public static boolean isAnagramOldSkool(int number) {
        String s = String.valueOf(number);
        int temp = number;
        int sum = 0;
        while (number > 0) {
            int r = number % 10;
            sum += Math.pow(r, s.length());
            number=number/10;
        }
        System.out.println("isAnagramOldSkool sum = " + sum);
        return temp == sum;
    }

    public static void main(String[] args) {

        int num = 153;
        int num1 = 371;
        int num2 = 123;

        String result = isAnagram(num) ? "Armstrong number" : "NOT Armstrong";
        System.out.println("isAnagram = " + result);

        String result1 = isAnagramGetNumericValueWay(num) ? "Armstrong number" : "NOT Armstrong";
        System.out.println("isAnagramGetNumericValueWay = " + result1);

        String result2 = isAnagramOldSkool(num) ? "Armstrong number" : "NOT Armstrong";
        System.out.println("isAnagramOldSkool = " + result2);

    }
}
