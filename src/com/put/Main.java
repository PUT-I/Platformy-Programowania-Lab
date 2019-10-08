package com.put;

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
import java.util.regex.Pattern;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Main {

    private static final long TIME_DIVIDER = 1000 * 1000;
    private static final int SIZE = 10000000;
    private static final String UNIT = " ms";

    private static int randomInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
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

    public static void ex1() {
        final long tableTime = createTable(SIZE);
        final long arrayListTime = createArrayList(SIZE);
        final long arrayListCapacityTime = createArrayListWithCapacity(SIZE);
        final long linkedListTime = createLinkedList(SIZE);

        System.out.println("---------- Exercise 1 ----------");
        System.out.println("-------- Creation Times --------");
        System.out.print("                      table : ");
        System.out.println(tableTime / TIME_DIVIDER + UNIT);
        System.out.print("   array list (no capacity) : ");
        System.out.println(arrayListTime / TIME_DIVIDER + UNIT);
        System.out.print(" array list (with capacity) : ");
        System.out.println(arrayListCapacityTime / TIME_DIVIDER + UNIT);
        System.out.print("linked list (with capacity) : ");
        System.out.println(linkedListTime / TIME_DIVIDER + UNIT);
    }

    private static String scientificNotation(BigInteger i) {
        NumberFormat formatter = new DecimalFormat("0.######E0", DecimalFormatSymbols.getInstance(Locale.ROOT));
        return formatter.format(new BigDecimal(i));
    }

    private static long factorial(BigInteger number) {
        final long startTime = System.nanoTime();
        BigInteger result = BigInteger.ONE;
        for (BigInteger i = BigInteger.ZERO; i.compareTo(number) == -1; i = i.add(BigInteger.ONE)) {
            result = result.multiply(i.add(BigInteger.ONE));
        }
        System.out.println(scientificNotation(result));
        return System.nanoTime() - startTime;
    }

    private static void ex2() {
        System.out.println("---------- Exercise 2 ----------");
        System.out.println("-------- Factorial Time --------");
        final long time = factorial(BigInteger.valueOf(10000));
        System.out.println(time / TIME_DIVIDER + UNIT);
    }

    private static void ex3() {
        System.out.println("---------- Exercise 3 ----------");
        try {
            Stream<String> stringStream = Files.lines(Paths.get("macbeth.txt"))
                    .flatMap(line -> Stream.of(line.split("\\W+")))
                    .filter(word -> word.length() > 3);
            System.out.println(stringStream.distinct().count());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ex1();
        System.out.println();
        ex2();
        System.out.println();
        ex3();
    }
}
