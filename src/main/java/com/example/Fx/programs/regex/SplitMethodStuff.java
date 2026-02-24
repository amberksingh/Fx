package com.example.Fx.programs.regex;

import java.util.Arrays;

public class SplitMethodStuff {

    public static void main(String[] args) {

        String demo = "boo:and:foo";
        String[] demoSplit = demo.split(":");
        System.out.println("demoSplit arr : " + Arrays.toString(demoSplit));
        System.out.println("demoSplit arr.length on colon(:) basis -> " + demoSplit.length);

        //🔹 Input string
        //String demo = "boo:and:foo";
        //
        //
        //Index map (important):
        //
        //Index : Char
        //0  b
        //1  o
        //2  o
        //3  :
        //4  a
        //5  n
        //6  d
        //7  :
        //8  f
        //9  o
        //10 o
        //
        //1️⃣ demo.split(":")
        //String[] split = demo.split(":");
        //
        //Regex used
        //:
        //
        //
        //👉 matches exactly one colon
        //
        //String demo = "boo:and:foo";
        //Internal working (ITERATIVE, not one-time)
        //🔹 First : found at index 3
        //
        //match.start() = 3
        //
        //match.end() = 4
        //
        //Add substring before match:
        //
        //substring(0, 3) → "boo"
        //
        //
        //❌ : removed
        //✔ "boo" added
        //
        //Update:
        //
        //String demo = "boo:and:foo";
        //start = 4
        //
        //🔹 Second : found at index 7
        //
        //match.start() = 7
        //
        //match.end() = 8
        //
        //Add substring:
        //
        //substring(4, 7) → "and"
        //
        //
        //❌ : removed
        //✔ "and" added
        //
        //String demo = "boo:and:foo";
        //Update:
        //
        //start = 8
        //
        //🔹 No more matches → FINAL STEP
        //
        //Add tail:
        //
        //substring(8, 11) → "foo"
        //
        //✅ Result
        //["boo", "and", "foo"]

        System.out.println("=======================================");
        String demo1 = "boo:and:foo";
        String[] os = demo1.split("o");
        System.out.println("Arrays.toString(os) = " + Arrays.toString(os));
        System.out.println("Arrays.toString(os) length = " + os.length);
        //2️⃣ demo1.split("o")
        //match.start() = 1
        //
        //match.end() = 2
        //Add substring before match:
        //
        //substring(0, 1) → "b"
        //"b" added and "o" removed -> "b"

        //start at 2
        //found next match at 2
        //match end == 3
        //substring(2,2) -> ""
        //✔ empty string added -> ""
        //❌ o removed

        //next match at index 9
        //match end at 10
        //start at 3 i.e prev match end
        //subString(3, 9) -> :and:f
        //✔ added :and:f -> ":and:f"
        //❌ o removed



        //String demo1 = "boo:and:foo";

        //match at index 10
        //match end at 11
        //start at prev end i.e 10
        //subString(10,10) -> add ""
        //
        //
        //✔ empty string added
        //❌ o removed
        //Update:
        //
        //start = 11
        //
        //🔹 FINAL STEP (tail)
        //substring(11, 11) → ""
        //
        //
        //⚠️ Trailing empty strings are DROPPED by default
        //
        //✅ Final result
        //["b", "", ":and:f", ""] -> end space discarded by java

    }
}
