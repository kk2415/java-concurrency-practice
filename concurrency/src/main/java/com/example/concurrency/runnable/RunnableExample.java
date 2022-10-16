package com.example.concurrency.runnable;

public class RunnableExample {

    /**
     * Runnable 인터페이스의 구현 클래스를 명시적으로 정의
     * */
    public static void createThead1() {
        RunnableTheadExample1 runnableThead = new RunnableTheadExample1();
        Thread thread1 = new Thread(runnableThead);
        thread1.start();
    }

    public static class RunnableTheadExample1 implements Runnable {
        @Override
        public void run() {
            System.out.println("쓰레드1 생성 완료");
        }
    }


    /**
     * Runnable 인터페이스의 구현 클래스를 익명 클래스로 구현
     * */
    public static void createThead2() {
        Runnable runnableThead = new Runnable() {
            @Override
            public void run() {
                System.out.println("쓰레드2 생성 완료");
            }
        };
        Thread thread2 = new Thread(runnableThead);
        thread2.start();
    }


    /**
     * Runnable 인터페이스의 구현 클래스를 람다로 구현
     * */
    public static void createThead3() {
//        Runnable runnableThead = () -> System.out.println("쓰레드3 생성 완료");
        Thread thread3 = new Thread(() -> System.out.println("쓰레드3 생성 완료"));
        thread3.start();
    }
}
