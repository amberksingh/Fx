package com.example.Fx.programs.streams;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StreamDemo2 {

    public static void main(String[] args) {
        
        //---takeWhile(Predicate) (Java 9+)
        //Takes elements until the predicate fails.
        //after 0, the remaining values are discarded at the very fist predicate cond check failure
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(6);
        list.add(3);
        list.add(0);//
        list.add(9);
        list.add(44);
        list.add(0);

        List<Integer> list1 = list.stream()
                .takeWhile(n -> n > 0)
                .toList();
        System.out.println("list1 = " + list1);//1,6,3
        
        //---dropWhile(Predicate) (Java 9+)
        //Skips elements while the predicate is true, then processes the rest.
        //when first 0 comes, it stops and takes rest of the elements as while drop stops after first fail
        List<Integer> list2 = list.stream()
                .dropWhile(n -> n != 0)
                .toList();
        System.out.println("list2 = " + list2);//0,9,44,0

        //reduce
        //find sum of all list elements
        Integer sum = list.stream()
                .reduce(Integer::sum)
                .orElse(0);
        System.out.println("sum = " + sum);

         /*======== NOTE =======
        sum(), max(), min() present in IntStream/LongStream/Double etc not in normal stream.
        .max(Comparator.naturalOrder()), .min(Comparator.naturalOrder()) expects a COMPARATOR
        for normal Stream<T>*/

        ToIntFunction<String> intFunction = (value) -> value.length();
        ToIntFunction<String> intFunction1 = String::length;
        ToIntFunction<Integer> intFunction2 = Integer::intValue;
        System.out.println(intFunction.applyAsInt("abcde"));//5
        System.out.println(intFunction1.applyAsInt("abcde"));//5

        int sum1 = list.stream()
                //.mapToInt(x -> x.intValue())
                //.mapToInt(Integer::intValue)
                .mapToInt(intFunction2)
                .sum();
        System.out.println("sum1 = " + sum1);

        //
        BinaryOperator<Integer> binaryOperator = (num1, num2) -> num1 + num2;
        BinaryOperator<Integer> binaryOperator1 = Integer::sum;
        Supplier<Integer> supplier = () -> 0;
        List<String> nums = List.of("1", "2", "3");
        Integer sum2 = nums.stream()
                .map(Integer::valueOf)
                //.reduce(binaryOperator)
                .reduce(binaryOperator1)
                .orElseGet(supplier);
        System.out.println("sum2 = " + sum2);//6

        //with identity
        Integer reduce = nums.stream()
                .map(Integer::parseInt)
                .reduce(0, Integer::sum);
        System.out.println("reduce with identity = " + reduce);

        int sum3 = nums.stream()
                .mapToInt(Integer::parseInt)
                .sum();
        System.out.println("sum3 = " + sum3);//6
        
        //max in mapToInt
        OptionalInt max1 = nums.stream()
                .mapToInt(Integer::parseInt)
                .max();
        System.out.println("max1.getAsInt() = " + max1.getAsInt());

        //---mapToDouble
        //max
        OptionalDouble max = nums.stream()
                .mapToInt(Integer::parseInt)
                .mapToDouble(x -> Double.valueOf(x))
                .max();
        double asDouble = max.getAsDouble();
        System.out.println("asDouble = " + asDouble);

        OptionalDouble max2 = nums.stream()
                .mapToInt(Integer::parseInt)
                .asDoubleStream()
                .max();
        if (max2.isPresent())
            System.out.println("max2 = " + max2.getAsDouble());

        int max3 = nums.stream()
                .mapToInt(Integer::parseInt)
                .mapToObj(Double::valueOf)
                .mapToInt(Double::intValue)
                .max()
                        .orElseGet(() -> 0);
        System.out.println("max3 = " + max3);

        nums.stream()
                .mapToDouble(Double::parseDouble)
                .max()
                .ifPresent(x -> System.out.println("double max using parseDouble: "+x));//DoubleConsumer


        Double dvalue = Double.valueOf(10);	//✅ Correct	Explicit conversion
        System.out.println("dvalue = " + dvalue);
        Double dvalue1 = 10.0;	//✅ Best	Most common
        System.out.println("dvalue1 = " + dvalue1);
        double v = Double.parseDouble("10");//✅ Correct	From String give double primitive
        System.out.println("v = " + v);

        System.out.println("value = " + dvalue);
        System.out.println("value.doubleValue() : "+dvalue.doubleValue());
        System.out.println("value.longValue() : "+dvalue.longValue());
        System.out.println("value.intValue() : "+dvalue.intValue());

        //---mapToLong
        //List<String> nums = List.of("1", "2", "3");
        ToLongFunction<Long> longToLongFunction = Long::longValue;
        ToLongFunction<String> stringToLongFunction = Long::parseLong;
        ToLongFunction<String> stringToLongFunction1 = Long::valueOf;
        Long longValueSum = nums.stream()
                .map(Long::valueOf)
                .reduce(0L, Long::sum);
        System.out.println("longValueSum = " + longValueSum);

        long sum4 = nums.stream()
                //.mapToLong(Long::parseLong)
                //.mapToLong(stringToLongFunction1)
                .mapToLong(stringToLongFunction)
                .sum();
        System.out.println("sum4 = " + sum4);

        //long max
        Long lMax = nums.stream()
                .map(Long::parseLong)
                //.max(Comparator.naturalOrder())
                //.max(Comparator.comparing(Function.identity()))
                //.max(Comparator.comparing(Long::longValue))
                //.max(Comparator.comparingLong(Long::longValue))
                .max(Comparator.comparing(Long::longValue, Comparator.naturalOrder()))
                .orElse(0L);
        System.out.println("lMax = " + lMax);//3

        //long min
        Optional<Long> min = nums.stream()
                .map(Long::valueOf)
                .min(Comparator.naturalOrder());
        if (min.isPresent())
            System.out.println("min = " + min.get());//1

        Long minLong = nums.stream()
                .map(Long::valueOf)
                .min(Comparator.reverseOrder())
                .orElse(0L);
        System.out.println("minLong = " + minLong);//3 gives acc to reverse comparator
        
        //average
        double asDouble1 = IntStream.of(1, 2, 3, 4, 5)
                .average()
                .getAsDouble();
        System.out.println("asDouble1 = " + asDouble1);//3.0

        OptionalDouble average = nums.stream()
                .map(Integer::parseInt)
                .mapToInt(Integer::intValue)
                .average();
        if (average.isPresent())
            System.out.println("average.getAsDouble() = " + average.getAsDouble());//2

        Double collect = nums.stream()
                .map(Long::parseLong)
                .collect(
                        Collectors.averagingLong(Long::longValue)
                );
        System.out.println("collect = " + collect);//2

        Double collect1 = nums.stream()
                .map(Double::parseDouble)
                .collect(
                        Collectors.averagingDouble(Double::doubleValue)
                );
        System.out.println("collect1 = " + collect1);

        //summaryStatistics
        IntSummaryStatistics intSummaryStatistics = nums.stream()
                .mapToInt(Integer::parseInt)
                .summaryStatistics();
        System.out.println("intSummaryStatistics.getAverage() = " + intSummaryStatistics.getAverage());
        System.out.println("intSummaryStatistics.getMax() = " + intSummaryStatistics.getMax());
        System.out.println("intSummaryStatistics.getMin() = " + intSummaryStatistics.getMin());
        System.out.println("intSummaryStatistics.getCount() = " + intSummaryStatistics.getCount());
        System.out.println("intSummaryStatistics.getSum() = " + intSummaryStatistics.getSum());

        LongSummaryStatistics longSummaryStatistics = nums.stream()
                .map(Long::parseLong)
                .collect(
                        Collectors.summarizingLong(x -> x)
                );
        System.out.println("longSummaryStatistics = " + longSummaryStatistics);

        //summingInt,long
        Integer summingInt = nums.stream()
                .map(Integer::parseInt)
                .collect(
                        Collectors.summingInt(Integer::intValue)
                );
        System.out.println("summingInt = " + summingInt);//6

        //BinaryOperator
        BinaryOperator<Integer> maxBinOperator = BinaryOperator.maxBy(Comparator.naturalOrder());
        BinaryOperator<Integer> minBinOperator = BinaryOperator.minBy(Comparator.naturalOrder());

        Optional<Integer> maxBin = nums.stream()
                .map(Integer::parseInt)
                .reduce(maxBinOperator);
        System.out.println("maxBin = " + maxBin);

        Optional<Integer> minBin = nums.stream()
                .map(Integer::parseInt)
                .reduce(minBinOperator);
        System.out.println("minBin = " + minBin);

        //this seems misleading. explain same for max also. so sorting is not done here, just in reverse order comparator, bigger is smaller ? explain same for max
        //
        //Excellent — this confusion is very common 👌
        //You’re thinking in terms of sorting. But min() and max() do NOT sort.
        //
        //Let’s clear this up properly.
        //
        //🚫 First: No Sorting Happens
        //
        //Neither of these:
        //
        //stream.min(...)
        //stream.max(...)
        //
        //
        //performs sorting.
        //
        //They just scan once through the stream and keep a running candidate.
        //
        //Time complexity = O(n)
        //Sorting would be O(n log n).
        //
        //🧠 How min() Actually Works
        //
        //Internally it behaves conceptually like this:
        //
        //T candidate = firstElement;
        //
        //for (T element : stream) {
        //    if (comparator.compare(element, candidate) < 0) {
        //        candidate = element;
        //    }
        //}
        //return candidate;
        //
        //
        //Key rule:
        //
        //If compare(a, b) < 0, then a is considered "smaller than" b.
        //
        //That’s it.
        //
        //🔎 What Does Comparator.reverseOrder() Do?
        //
        //Normal comparator (natural order):
        //
        //a.compareTo(b)
        //
        //
        //Reverse comparator:
        //
        //b.compareTo(a)
        //
        //
        //So yes — in reverse comparator:
        //
        //Bigger values are considered "smaller".
        //
        //That’s the entire trick.
        //
        //📌 Let’s Use Example
        //
        //Stream:
        //
        //5, 2, 9, 1
        //
        //✅ Case 1: min(naturalOrder())
        //
        //Natural comparison:
        //
        //1 < 2 < 5 < 9
        //
        //
        //Process:
        //
        //candidate = 5
        //
        //compare 2 vs 5 → 2 smaller → candidate = 2
        //
        //compare 9 vs 2 → 9 not smaller
        //
        //compare 1 vs 2 → 1 smaller → candidate = 1
        //
        //Result = 1
        //
        //✔ Smallest number
        //
        //✅ Case 2: max(naturalOrder())
        //
        //Logic:
        //
        //if (compare(element, candidate) > 0)
        //
        //
        //Process:
        //
        //candidate = 5
        //
        //compare 2 vs 5 → not bigger
        //
        //compare 9 vs 5 → bigger → candidate = 9
        //
        //compare 1 vs 9 → not bigger
        //
        //Result = 9
        //
        //✔ Largest number
        //
        //🔁 Case 3: min(reverseOrder())
        //
        //Now comparator is flipped:
        //
        //Reverse means:
        //
        //9 < 5 < 2 < 1   (according to comparator)
        //
        //
        //So comparator thinks 9 is smallest.
        //
        //Process:
        //
        //candidate = 5
        //
        //compare 2 vs 5 → comparator says 2 is NOT smaller
        //
        //compare 9 vs 5 → comparator says 9 IS smaller → candidate = 9
        //
        //compare 1 vs 9 → comparator says 1 is NOT smaller
        //
        //Result = 9
        //
        //✔ Largest number
        //
        //🔁 Case 4: max(reverseOrder())
        //
        //Now max checks:
        //
        //if (compare(element, candidate) > 0)
        //
        //
        //But since comparator is reversed:
        //
        //Comparator thinks:
        //
        //9 < 5 < 2 < 1
        //
        //
        //So "greater" according to reverse comparator means actually numerically smaller.
        //
        //Process:
        //
        //candidate = 5
        //
        //compare 2 vs 5 → reverse comparator says 2 > 5 → candidate = 2
        //
        //compare 9 vs 2 → reverse comparator says 9 NOT > 2
        //
        //compare 1 vs 2 → reverse comparator says 1 > 2 → candidate = 1
        //
        //Result = 1
        //
        //✔ Smallest number
        //
        //🎯 Final Truth Table
        //Code	Actual Result
        //min(naturalOrder())	Smallest
        //max(naturalOrder())	Largest
        //min(reverseOrder())	Largest
        //max(reverseOrder())	Smallest
        //🔥 Key Mental Model
        //
        //min() and max() do not care about numeric meaning.
        //They only care about what the comparator defines as "smaller" or "greater".
        //
        //So yes:
        //
        //In reverse comparator, bigger is considered smaller.
        //
        //Not because sorting happens —
        //but because comparison logic is flipped.


    }
}
