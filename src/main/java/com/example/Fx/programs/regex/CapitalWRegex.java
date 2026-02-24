package com.example.Fx.programs.regex;

import java.util.Arrays;

public class CapitalWRegex {

    public static void main(String[] args) {

        //\\W+ eats symbols & spaces
        //
        //Words are between those symbol blocks
        //
        //So words survive
        //
        //🔑 Lock-in rule (compare both)
        //Regex	Removes	Returns
        //\\w+	words	symbols
        //\\W+	symbols	words
        //
        //One-line mental model
        //split("\\W+") =
        //“cut wherever symbols appear and give me the words in between”

        String str = "hello_world, java8.0 is fun!";
        String[] validWords = str.split("\\W+");//[^a-zA-Z0-9_] -> [hello_world, java8, 0, is, fun]
        System.out.println("\\W+ -> "+ Arrays.toString(validWords));
        System.out.println("\\W+ -> "+validWords.length);

        //Index:
        //0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29
        //h e l l o _ w o r l d  ,     j  a  v  a  8  .  0     i  s     f  u  n  !

        //Now split("\W+") — iterative, match by match
        //
        //Regex: \\W+ matches blocks of non-word chars.
        //
        //We track lastEnd again.
        //
        //Match 1: ", " (comma + space)
        //
        //This is at indices 11..12.
        //
        //match.start() = 11
        //
        //match.end() = 13 (end is exclusive; it stops right before index 13)
        //
        //Add substring before match:
        //
        //substring(0, 11) → "hello_world"
        //
        //Add "hello_world"
        //
        //Update:
        //
        //lastEnd = 13
        //
        //Array so far:
        //
        //["hello_world"]

        //Index:
        //0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29
        //h e l l o _ w o r l d  ,     j  a  v  a  8  .  0     i  s     f  u  n  !
        //
        //Match 2: "." (dot)
        //
        //Dot at index 18 is a non-word char.
        //
        //match.start() = 18
        //
        //match.end() = 19
        //
        //Add substring between lastEnd and matchStart:
        //
        //substring(13, 18) → "java8"
        //
        //Add "java8"
        //
        //Update:
        //
        //lastEnd = 19
        //
        //Array:
        //
        //["hello_world", "java8"]
        //Match 3: " " (space)
        //
        //Space at index 20.
        //
        //match.start() = 20
        //
        //match.end() = 21
        //
        //Add substring:
        //
        //substring(19, 20) → "0"
        //
        //Add "0"
        //
        //Update:
        //
        //lastEnd = 21
        //
        //Array:
        //
        //["hello_world", "java8", "0"]
        //
        //Index:
        //0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29
        //h e l l o _ w o r l d  ,     j  a  v  a  8  .  0     i  s     f  u  n  !
        //
        //Match 4: " " (space)
        //
        //Space at index 23.
        //
        //match.start() = 23
        //
        //match.end() = 24
        //
        //Add substring:
        //
        //substring(21, 23) → "is"
        //
        //Add "is"
        //
        //Update:
        //
        //lastEnd = 24
        //
        //Array:
        //
        //["hello_world", "java8", "0", "is"]
        //Match 5: "!" (exclamation)
        //
        //! at index 27 is non-word.
        //
        //match.start() = 27
        //
        //match.end() = 28 (end of string)
        //
        //Add substring:
        //
        //substring(24, 27) → "fun"
        //
        //Add "fun"
        //
        //Update:
        //
        //lastEnd = 28
        //
        //Array:
        //
        //["hello_world", "java8", "0", "is", "fun"]
        //Tail after last match
        //
        //After loop: add substring(lastEnd, length):
        //
        //substring(28, 28) → "" (empty)
        //
        //Java removes trailing empty strings by default, so this last "" is dropped.
        //
        //✅ Final:
        //
        //[hello_world, java8, 0, is, fun]
        //length = 5

    }
}
