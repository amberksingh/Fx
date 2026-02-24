package com.example.Fx.programs.regex;

import java.util.Arrays;

public class SmallwRegex {

    public static void main(String[] args) {

        //split("\\w+")
        //👉 removes all words
        //👉 returns only what lies between words
        //⚠️ Rule to remember forever
        //You split by	    You REMOVE	    You GET
        //  \\w+	         words	        symbols
        //  \\W+	         symbols	    words

        String str = "hello_world, java8.0 is fun!";
        System.out.println("original string : "+str);
        System.out.println("original string length : "+str.length());
        String[] invalidChars = str.split("\\w+");//[a-zA-Z0-9_] -> [, , , .,  ,  , !]
        //["",  ", ",  ".",  " ",  " ",  "!"]
        System.out.println("\\w+ -> "+ Arrays.toString(invalidChars));
        System.out.println("\\w+ -> "+invalidChars.length);


        //Index:
        //0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29
        //h e l l o _ w o r l d  ,     j  a  v  a  8  .  0     i  s     f  u  n  !

       //Iteration-wise split("\\w+") (beginner, exact)
        //
        //Regex: \\w+ matches word blocks ([A-Za-z0-9_])
        //
        //String: hello_world, java8.0 is fun!
        //
        //We track:
        //
        //lastEnd (end of previous match)
        //
        //each match (start, end)
        //
        //we add substring between lastEnd and match.start()
        //
        //Match 1: "hello_world"
        //
        //start=0, end=11
        //
        //add substring(0,0) → ""
        //
        //lastEnd=11
        //
        //Match 2: "java8"
        //
        //start=13, end=18
        //
        //add substring(11,13) → ", " (comma+space)
        //
        //lastEnd=18
        //
        //Match 3: "0"
        //
        //start=19, end=20
        //
        //add substring(18,19) → "."
        //
        //lastEnd=20
        //
        //Match 4: "is"
        //
        //start=21, end=23
        //
        //add substring(20,21) → " " (space)
        //
        //lastEnd=23
        //
        //Match 5: "fun"
        //
        //start=24, end=27
        //
        //add substring(23,24) → " " (space)
        //
        //lastEnd=27
        //
        //After all matches: add tail
        //
        //add substring(27,28) → "!"
        //
        //✅ Final array:
        //
        //["", ", ", ".", " ", " ", "!"]
        //
        //That’s the precise output for split("\\w+").
        //
        //If you print it:
        //
        //System.out.println(Arrays.toString(str.split("\\w+")));
        //
        //you should see:
        //
        //[, , .,  ,  , !]
    }
}
