package com.example.concurrency.sleep;

import java.util.stream.IntStream;

public class SleepExample {

    /**
     * Thread.sleep(1000)을 통해 1초 동안 쓰레드를 멈춘다.
     * */
    public static void correctExample() {
        Thread thread = new Thread(() -> {
            System.out.println("example1 started");

            for (int i = 0; i < 10; i++) {
                System.out.println(i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {}
            }

            System.out.println("example1 ended");
        });
        thread.start();
    }


    /**
     * thread1을 sleep() 했기 때문에 무조건 thread2가 먼저 끝나야되지만
     * 실제 결과는 때에 따라 다르다.
     *
     * 그 이유는 sleep()이 항상 현재 실행 중인 쓰레드에 대해 작동하기 때문에
     * thread1.sleep()과 같이 호출하여도 실제로 영향을 받는 건 main 쓰레드이다.
     *
     * 그래서 sleep()은 static으로 선언되어 있으며 참조변수를 이용해서 호출하기 보다는
     * 'Thread.sleep(1000)'과 같이 해야 한다.
     * */
    public static void wrongExample() {
        Thread thread1 = new Thread(() -> {
            IntStream.rangeClosed(0, 1_000_000).forEach((i) -> {});
            System.out.println("thread1 종료");
        });

        Thread thread2 = new Thread(() -> {
            IntStream.rangeClosed(0, 1_000_000_000).forEach((i) -> {});
            System.out.println("thread2 종료");
        });

        thread1.start();
        thread2.start();

        try {
            thread1.sleep(2000);
        } catch (InterruptedException e) {}
    }
}
