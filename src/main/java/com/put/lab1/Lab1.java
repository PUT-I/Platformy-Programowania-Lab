package com.put.lab1;

import javax.xml.datatype.DatatypeConstants;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@SuppressWarnings({"MismatchedQueryAndUpdateOfCollection", "SameParameterValue", "MismatchedReadAndWriteOfArray", "ComparatorResultComparison"})
public class Lab1 {

    private static final long TIME_DIVIDER = 1000 * 1000;
    private static final int SIZE = 10000000;
    private static final String UNIT = " ms";
    private static final String MACBETH_PATH = "resources/macbeth.txt";


    private static int randomInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    private static String toMs(long nano) {
        return nano / TIME_DIVIDER + UNIT;
    }


    private static long createTable(int size) {
        long startTime = System.nanoTime();
        Integer[] table = new Integer[size];
        for (int i = 0; i < size; i++) {
            table[i] = randomInt(0, size);
        }
        return System.nanoTime() - startTime;
    }

    private static long createArrayList(int size) {
        long startTime = System.nanoTime();
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(randomInt(0, size));
        }
        return System.nanoTime() - startTime;
    }

    private static long createArrayListWithCapacity(int size) {
        long startTime = System.nanoTime();
        List<Integer> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(randomInt(0, size));
        }
        return System.nanoTime() - startTime;
    }

    private static long createLinkedList(int size) {
        long startTime = System.nanoTime();
        List<Integer> list = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            list.add(randomInt(0, size));
        }
        return System.nanoTime() - startTime;
    }

    private static void ex1() {
        final long tableTime = createTable(SIZE);
        final long arrayListTime = createArrayList(SIZE);
        final long arrayListCapacityTime = createArrayListWithCapacity(SIZE);
        final long linkedListTime = createLinkedList(SIZE);

        System.out.println("---------- Exercise 1 ----------");
        System.out.println("-------- Creation Times --------");
        System.out.print("                      table : ");
        System.out.println(toMs(tableTime));
        System.out.print("   array list (no capacity) : ");
        System.out.println(toMs(arrayListTime));
        System.out.print(" array list (with capacity) : ");
        System.out.println(toMs(arrayListCapacityTime));
        System.out.print("linked list (with capacity) : ");
        System.out.println(toMs(linkedListTime));
    }


    private static String scientificNotation(BigInteger i) {
        NumberFormat formatter = new DecimalFormat("0.######E0", DecimalFormatSymbols.getInstance(Locale.ROOT));
        return formatter.format(new BigDecimal(i));
    }

    private static long factorial(BigInteger number) {
        final long startTime = System.nanoTime();
        BigInteger result = BigInteger.ONE;
        for (BigInteger i = BigInteger.ZERO; i.compareTo(number) == DatatypeConstants.LESSER; i = i.add(BigInteger.ONE)) {
            result = result.multiply(i.add(BigInteger.ONE));
        }
        System.out.println(scientificNotation(result));
        return System.nanoTime() - startTime;
    }


    private static void ex2() {
        System.out.println("---------- Exercise 2 ----------");
        System.out.println("-------- Factorial Time --------");
        final long time = factorial(BigInteger.valueOf(10000));
        System.out.println(toMs(time));
    }


    private static void ex3() {
        System.out.println("---------- Exercise 3 ----------");
        try {
            Stream<String> stringStream = Files.lines(Paths.get(MACBETH_PATH))
                    .flatMap(line -> Stream.of(line.split("\\W+")))
                    .filter(word -> word.length() >= 3);
            System.out.println(stringStream.distinct().count());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void ex4() {
        System.out.println("---------- Exercise 4 ----------");
        try {
            List<String> wordList = Files.lines(Paths.get(MACBETH_PATH))
                    .flatMap(line -> Stream.of(line.toLowerCase()))
                    .flatMap(line -> Stream.of(line.split("\\W+")))
                    .filter(word -> word.length() >= 3)
                    .collect(toList());

            Map<String, Integer> wordMap = new HashMap<>();
            for (String word : wordList) {
                wordMap.put(word, wordMap.containsKey(word) ? wordMap.get(word) + 1 : 1);
            }

            SortedSet<Map.Entry<String, Integer>> sortedWords = new TreeSet<>(
                    (e1, e2) -> e2.getValue().compareTo(e1.getValue())
            );
            sortedWords.addAll(wordMap.entrySet());

            for (int i = 0; i < 10; i++) {
                System.out.println((i + 1) + ". " + sortedWords.toArray()[i]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Integer[] randomArray(int size) {
        Integer[] result = new Integer[size];
        for (int i = 0; i < size; i++) {
            result[i] = randomInt(0, size);
        }
        return result;
    }

    private static long insertionSort(Integer[] table) {
        final long startTime = System.nanoTime();

        Integer[] result = table;

        int i, j, element;
        for (i = 1; i < result.length; i++) {
            j = i;
            element = result[i];
            while ((j > 0) && (result[j - 1] > element)) {
                result[j] = result[j - 1];
                j--;
            }
            result[j] = element;
        }

        return System.nanoTime() - startTime;
    }

    public static Integer[] combineArrays(Integer[] a, Integer[] b) {
        int length = a.length + b.length;
        Integer[] result = new Integer[length];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }

    private static Integer[] merge(Integer[] table1, Integer[] table2) {
        Integer[] result = new Integer[table1.length + table2.length];

        int i = 0;
        int j = 0;
        int r = 0;

        while (i < table1.length && j < table2.length) {
            if (table1[i] <= table2[j]) {
                result[r] = table1[i];
                i++;
            } else {
                result[r] = table1[j];
                j++;
            }
            r++;
        }

        while (i < table1.length) {
            result[r] = table1[i];
            i++;
            r++;
        }

        while (j < table2.length) {
            result[r] = table2[j];
            j++;
            r++;
        }

        return result;
    }

    private static Integer[] mergeSort(Integer[] table) {
        if (table.length <= 1) {
            return table;
        } else {
            Integer[] result = new Integer[table.length];

            int centerIndex = (int) Math.ceil(table.length / 2);

            Integer[] leftSide = mergeSort(Arrays.copyOfRange(table, 0, centerIndex));
            Integer[] rightSide = mergeSort(Arrays.copyOfRange(table, centerIndex, table.length));

            result = merge(leftSide, rightSide);

            return result;
        }
    }

    private static long mergeSortMeasure(Integer[] table) {
        final long startTime = System.nanoTime();
        Integer[] result = mergeSort(table);
        return System.nanoTime() - startTime;
    }


    private static void ex5() {
        System.out.println("---------- Exercise 5 ----------");
        Integer[] table = randomArray(100000);
        // long insertionSortTime = insertionSort(table);
        long mergeSortTime = mergeSortMeasure(table);

        // System.out.println("Insertion sort time : " + toMs(insertionSortTime));
        System.out.println("    Merge sort time : " + toMs(mergeSortTime));
    }

    public static void runExercises() {
        ex1();
        System.out.println();
        ex2();
        System.out.println();
        ex3();
        System.out.println();
        ex4();
        System.out.println();
        ex5();
    }

    public static void main(String[] args) {
        // runExercises();
        ex5();
    }
}
