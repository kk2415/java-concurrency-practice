package com.example.concurrency.join;

import java.util.stream.IntStream;

public class JoinExample {

    static long startTime = 0;

    public static void example() {
        Thread thread1 = new Thread(() -> {
            IntStream.rangeClosed(0, 1_000_000_000).forEach((i) -> {});
            System.out.println("thread1 종료");
        });

        Thread thread2 = new Thread(() -> {
            IntStream.rangeClosed(0, 1_000_000_000).forEach((i) -> {});
            System.out.println("thread2 종료");
        });

        thread1.start();
        thread2.start();
        startTime = System.currentTimeMillis();

        try {
            thread1.join(); //main쓰레드가 thrad1의 작업이 끝날 때까지 기다린다.
            thread2.join(); //main쓰레드가 thrad2의 작업이 끝날 때까지 기다린다.
        } catch (InterruptedException e) {}

        System.out.println("소요시간: " + (System.currentTimeMillis() - JoinExample.startTime));
        System.out.println("main 종료");
    }
}
