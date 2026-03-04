package com.example.Fx.programs;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StringPrograms {

    public static void main(String[] args) {

        String charStringRepeating = "the quick brown fox jumps over the lazy dog";
        //Calculate the average length of each word in the string
        Double collect = Arrays.stream(charStringRepeating.split("\\W+"))
                .collect(
                        Collectors.averagingInt(String::length)
                );
        System.out.println("average word length = " + collect);

        //2nd way
        OptionalDouble average = Arrays.stream(charStringRepeating.split("\\W+"))
                .map(String::length)
                .mapToInt(Integer::intValue)
                .average();
        System.out.println("average = " + average);

        //3rd way
        OptionalDouble avg = Arrays.stream(charStringRepeating.split("\\W+"))
                .map(String::length)
                .collect(
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                l -> l.stream().mapToInt(Integer::intValue).average()
                        )
                );
        System.out.println("avg.getAsDouble() = " + avg.getAsDouble());


        String str = "hello world";
        //o/p:dlrow olleh
        //reverse full string
        Optional<String> reduce = Arrays.stream(str.split(""))
                .reduce((c1, c2) -> c2 + c1);
        if (reduce.isPresent())
            System.out.println("reverse str = " + reduce.get());

        //reverse full old skool way
        String reverse = "";
        for (int i = str.length() - 1; i >= 0; i--) {
            //reverse = reverse.concat(Character.toString(str.charAt(i)));
            reverse = reverse + str.charAt(i);
        }
        System.out.println("reverse = " + reverse);

        //chars way IntStream stuff
        String s = str.chars()
                //.mapToObj(c -> Character.toString(c))
                .mapToObj(Character::toString)
                .reduce((c1, c2) -> c2 + c1)
                .orElse(null);
        System.out.println("reverse IntStream way = " + s);

        List<Character> reduce2 = str.chars()
                .mapToObj(i -> (char) i)
                .toList();
        System.out.println("reduce2 = " + reduce2);

        str.chars()
                .map(s1 -> (char) s1)//still IntStream
                .forEach(c -> System.out.print(c+", "));//prints integers/unicode/ascii code still

        //Reverse the order of the words in the string
        //String str = "hello world";
        //op : world hello
        Optional<String> reduce1 = Arrays.stream(str.split("\\W+"))
                .reduce((w1, w2) -> w2 + " " + w1);
        if (reduce1.isPresent())
            System.out.println("\nreduce1 = " + reduce1.get());

        //2nd way
        String collect1 = Stream.of(str.split("\\W+"))
                .collect(
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                list -> {
                                    //list.sort(Comparator.reverseOrder());//alphabetic reverse/lexicographical way
                                    Collections.reverse(list);//position reverse
                                    return String.join(" ", list);
                                }
                        )
                );
        System.out.println("collect1 = " + collect1);


        //Reverse the characters of each word in a given string while keeping the order of words intact
        //op: 33olleh 77dl4row
        String str1 = "hello33 wor4ld77";
        String regex = "[^a-zA-Z0-9 ]";
        String normalizedString = str1.replaceAll(regex, " ");
        System.out.println("normalizedString = " + normalizedString);
        String[] split = normalizedString.split("\\s+");
        System.out.println("Arrays.toString(split) = " + Arrays.toString(split));

        String collect2 = Arrays.stream(split)
                .map(StringBuffer::new)
                .map(StringBuffer::reverse)
                .map(String::valueOf)
                .collect(Collectors.joining(" "));
        System.out.println("collect2 = " + collect2);

        //Replace all occurrences of a specific word with another word "Banana" -> "Apple"
        String replaceWord = "Banana is tasty, but some people prefer Banana pie.";
        String replace = replaceWord.replace("Banana", "Apple");
        System.out.println("replace = " + replace);
        String replaceAll = replaceWord.replaceAll("Banana", "Apple");
        System.out.println("replaceAll = " + replaceAll);

        String[] split1 = replaceWord.split("\\W+");
        System.out.println("Arrays.toString(split1) = " + Arrays.toString(split1));
        String[] split2 = replaceWord.split("\\s+");
        System.out.println("Arrays.toString(split2) = " + Arrays.toString(split2));
        String collect3 = Arrays.stream(replaceWord.split("\\s+"))//if we use \\W+ to split, we loose , and .
                .map(w -> {
                    if (w.equals("Banana"))
                        return "Apple";
                    return w;
                })
                .collect(Collectors.joining(" "));
        System.out.println("collect3 = " + collect3);

        //Capitalize the first character of each word in the string
        String collect4 = Arrays.stream(replaceWord.split("\\s+"))
                .map(w -> {
                    //return Character.toUpperCase(w.charAt(0)) + w.substring(1);
                    return Character.toString(Character.toUpperCase(w.charAt(0))).concat(w.substring(1));//beginIndex – the beginning index, inclusive.
                })
                .collect(Collectors.joining(" "));
        System.out.println("collect4 = " + collect4);

        //Remove all non-alphabetic characters from a string
        String alpha = "good1234 hgh_";
        //op : good1234hgh
        String collect5 = alpha.chars()
                .filter(c -> Character.isDigit(c) || Character.isAlphabetic(c))
                .mapToObj(Character::toString)
                .collect(Collectors.joining());
        System.out.println("collect5 = " + collect5);

        //Count the number of occurrences of a specific character (e.g., 'l')
        //count of l
        long l = Arrays.stream(str.split(""))
                .filter(c -> c.equalsIgnoreCase("l"))
                .count();
        System.out.println("count of l str.split(\"\") = " + l);

        long count = str.chars()
                .filter(c -> c == 'l')
                .count();
        System.out.println("count of l str.chars() = " + count);

        List<Character> list1 = str.chars()
                .mapToObj(n -> (char) n)
                .toList();
        int l1 = Collections.frequency(list1, 'l');
        System.out.println("count of l Collections.frequency = " + l1);

        //Filter out all the vowels from the string
        //String str = "hello world";
        //op : eo
        String vowels = "aeiou";
        String collect6 = Arrays.stream(str.split(""))
                .filter(vowels::contains)
                .distinct()
                .collect(Collectors.joining());
        System.out.println("collect6 = " + collect6);

        //Find the first non-repeating character in the string
        //1st way
        //op:q
        //String charStringRepeating = "the quick brown fox jumps over the lazy dog";
        List<String> list = Arrays.stream(charStringRepeating.split("")).toList();
        String s1 = Arrays.stream(charStringRepeating.split(""))
                .filter(c -> Collections.frequency(list, c) == 1)
                .findFirst()
                .orElseGet(() -> "chooran");
        System.out.println("s1 = " + s1);
        
        //2nd way
        String s2 = Arrays.stream(charStringRepeating.split(""))
                .collect(
                        Collectors.groupingBy(
                                Function.identity(),
                                LinkedHashMap::new,
                                Collectors.counting()
                        )
                )
                .entrySet()
                .stream()
                .filter(e -> e.getValue() == 1)
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);
        System.out.println("s2 = " + s2);
        
        //count the occurrences of each character and then sort these characters based on their counts
        // in ascending order

        //🔑 Rule of thumb
        //
        //Type arguments for constructors: new HashMap<String, Long>()
        //
        //Type arguments for static methods: ClassName.<String, Long>methodName()

        //String charStringRepeating = "the quick brown fox jumps over the lazy dog";
        //List<String> repeatingCharList = Stream.of(charStringRepeating.split("")).toList();


        Comparator<Map.Entry<String, Long>> comparator = Map.Entry.comparingByValue();
        Comparator<Map.Entry<String, Long>> comparator1 = Map.Entry.<String, Long>comparingByValue()
                .thenComparing(Map.Entry::getKey);
        Comparator<Map.Entry<String, Long>> comparator2 = Map.Entry.<String, Long>comparingByValue()
                .thenComparing(Map.Entry::getKey, Comparator.reverseOrder());

        Map<String, Long> collected = Arrays.stream(charStringRepeating.split(""))
                .collect(
                        Collectors.groupingBy(
                                Function.identity(),
                                //LinkedHashMap::new,
                                Collectors.counting()
                        )
                )
                .entrySet()
                .stream()
                .sorted(comparator)
                //.sorted(comparator1)
                //.sorted(comparator2)
                .collect(
                        Collectors.toMap(
                                Map.Entry::getKey,
                                Map.Entry::getValue,
                                (oldVal, newVal) -> oldVal,
                                LinkedHashMap::new
                        )
                );
        System.out.println("collected = " + collected);

        //Sort the words in the string alphabetically (ascending or descending)
        //String charStringRepeating = "the quick brown fox jumps over the lazy dog";
        List<String> list2 = Arrays.stream(charStringRepeating.split("\\W+"))
                //.sorted()
                //.sorted(Comparator.naturalOrder())
                //.sorted(Comparator.comparing(Function.identity()))
                //.sorted(Comparator.comparing(x -> x))
                //.sorted(Comparator.comparing(x -> x, Comparator.naturalOrder()))
                //.sorted((word1, word2) -> word1.compareTo(word2))
                //.sorted(String::compareTo)
                //.sorted(Comparator.reverseOrder())
                //.sorted(Comparator.comparing(Function.<String>identity(), Comparator.reverseOrder()))
                //.sorted(Comparator.comparing(Function.<String>identity()).reversed())
                //.sorted(Comparator.comparing(x -> x, Comparator.reverseOrder()))
                .sorted(Comparator.comparing((String x) -> x, Comparator.reverseOrder()))
                .toList();
        System.out.println("list2 = " + list2);

        //Find the longest and shortest word in the string
        //String charStringRepeating = "the quick brown fox jumps over the lazy dog";
        Comparator<String> stringComparator = Comparator.comparing(String::length);
        Comparator<String> stringComparator1 = Comparator.comparing(String::length, Comparator.reverseOrder());
        Comparator<String> stringComparator2 = Comparator.comparing(String::length, (w1, w2) -> w1 -w2);
        Comparator<String> stringComparator4 = Comparator.comparing(String::length, (w1, w2) -> w2 -w1);
        Comparator<String> stringComparator3 = Comparator.comparing(String::length, Integer::compareTo);

        List<String> list3 = Arrays.stream(charStringRepeating.split("\\W+"))
                //.sorted(stringComparator)
                //.sorted(stringComparator1)
                //.sorted(stringComparator2)
                .sorted(stringComparator3)
                .toList();
        System.out.println("list3 = " + list3);

        String shortest = Arrays.stream(charStringRepeating.split("\\W+"))
                //.sorted(stringComparator)
                .min(stringComparator)
                .orElse(null);
        System.out.println("shortest = " + shortest);

        String longest = Arrays.stream(charStringRepeating.split("\\W+"))
                //.sorted(stringComparator)
                .max(stringComparator)
                .orElse(null);
        System.out.println("longest = " + longest);

        String collect8 = Stream.of(charStringRepeating.split(" "))
                .sorted(stringComparator4)
                .collect(Collectors.joining("-", "@", "#"));
        System.out.println("desc order collect8 = " + collect8);

        String collect9 = Stream.of(charStringRepeating.split(" "))
                .sorted(stringComparator3)
                .collect(Collectors.joining("-", "@", "#"));
        System.out.println("asc order collect9 = " + collect9);

        //Join all the words in the string with a specific delimiter (e.g., "-")
        List<String> list6 = Arrays.stream(charStringRepeating.split("\\s"))
                .toList();
        String join = String.join("-", list6);
        System.out.println("join = " + join);

        //String charStringRepeating = "the quick brown fox jumps over the lazy dog";
        //Print only the even-indexed characters in uppercase
        System.out.println("Print only the even-indexed characters in uppercase 1st way : ");
        //Arrays.stream(charStringRepeating.split(""))
        List<String> list4 = IntStream.range(0, charStringRepeating.length())
                .filter(i -> i % 2 == 0)
                .map(j -> Character.toUpperCase(charStringRepeating.charAt(j)))
                .mapToObj(Character::toString)
                .toList();
        System.out.println("list4 = " + list4);

        //Check if a string is a palindrome
        String palindrome = "level";
        //1st way
        boolean equals = Arrays.stream(palindrome.split(""))
                .reduce((c1, c2) -> c2 + c1)
                .orElse(null)
                .equals(palindrome);
        if (equals)
            System.out.println("palindrome");
        else
            System.out.println("NOT palindrome");

        boolean b = IntStream.range(0, palindrome.length() / 2)
                .allMatch(c -> palindrome.charAt(c) == palindrome.charAt(palindrome.length() - 1 - c));
        if (b)
            System.out.println("palindrome");
        else
            System.out.println("NOT palindrome");

        //Find all the substrings of a specific length (e.g., all 3-letter substrings)
        int k = 3;//substring length
        String subString = "abcdef";//length = 6
        //op: list1 = [abc, bcd, cde, def]
        List<String> list5 = IntStream.rangeClosed(0, subString.length() - k)
                .mapToObj(i -> subString.substring(i, i + k))
                .toList();
        System.out.println("list5 = " + list5);

        //Map each character of "hello world" to its uppercase version
        //String str = "hello world";
        Map<String, String> collect7 = Arrays.stream(str.split(""))
                .filter(r -> !r.isBlank())
                .collect(
                        Collectors.toMap(
                                Function.identity(),
                                String::toUpperCase,
                                (oldVal, newVal) -> oldVal
                        )
                );
        System.out.println("collect7 = " + collect7);//this removes duplicates as its a Map
        
        //preserve duplicates
        List<Map.Entry<Character, Character>> list7 = str.chars()
                .mapToObj(c -> Map.entry((char) c, Character.toUpperCase((char) c)))
                .toList();
        System.out.println("list7 = " + list7);

        List<Map.Entry<String, String>> list8 = str.chars()
                .mapToObj(Character::toString)
                .filter(value -> !value.isBlank())//use isBlank for removing spaces and use isEmpty for blank zero length string
                .map(v -> Map.entry(v, v.toUpperCase()))
                .toList();
        list8.forEach(stringStringEntry ->
                System.out.println(stringStringEntry.getKey() + " -> "+stringStringEntry.getValue()));

        //DoubleStream asDoubleStream(); allowed in LongStream
        //DoubleStream asDoubleStream() and LongStream asLongStream(); allowed in IntStream
        //NOT POSSIBLE in DoubleStream
        String numString = "384";//3+8+4=15
        double sum = Arrays.stream(numString.split(""))
                .mapToInt(Integer::parseInt)
                .asDoubleStream()
                .sum();
        System.out.println("sum = " + sum);


    }
}
