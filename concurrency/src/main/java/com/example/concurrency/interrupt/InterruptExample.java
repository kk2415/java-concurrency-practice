package com.example.concurrency.interrupt;

public class InterruptExample {

    public static void correctExample() {
        CorrectCounter thread = new CorrectCounter();
        thread.start();

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {}

        thread.interrupt();
    }

    static class CorrectCounter extends Thread {
        @Override
        public void run() {
            int i = 10;

            while (i > 0 && !interrupted()) {
                System.out.println(i--);
                for (long x=0; x<2500000000L;x++);
            }
            System.out.println("카운트가 종료되었습니다.");
        }
    }


    /**
     * thread.interrupt()를 호출했지만 WrongCounter는 멈추지 않는다.
     * 그 이유는 Thread.sleep(1000)에서 InterruptedException이 발생했기 때문이다.
     * sleep()에 의해 쓰레드가 잠시 멈춰있을 때, interrupt()를 호출하면 InterruptedException이
     * 발생되고 쓰레드의 interrupted 상태는 false로 자동 초기화된다.
     * */
    public static void wrongExample() {
        WrongCounter thread = new WrongCounter();
        thread.start();

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {}

        thread.interrupt();
    }

    static class WrongCounter extends Thread {
        @Override
        public void run() {
            int i = 10;

            while (i > 0 && !interrupted()) {
                System.out.println(i--);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {}
            }
            System.out.println("카운트가 종료되었습니다.");
        }
    }
}
