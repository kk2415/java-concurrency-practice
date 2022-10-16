package com.example.concurrency.yield;

public class YieldExample {

    public static void example() {
        NamePrinter thread1 = new NamePrinter("thread1");
        NamePrinter thread2 = new NamePrinter("thread2");
        NamePrinter thread3 = new NamePrinter("thread3");

        thread1.start();
        thread2.start();
        thread3.start();

        try {
            Thread.sleep(2000);
            thread1.suspend();
            Thread.sleep(2000);
            thread2.suspend();
            Thread.sleep(2000);
            thread1.resume();
            Thread.sleep(2000);
            thread1.stop();
            thread2.stop();
            Thread.sleep(2000);
            thread3.stop();
        } catch (InterruptedException e) {}
    }

    static class NamePrinter implements Runnable {
        boolean suspended = false;
        boolean stopped = false;

        Thread th;

        public NamePrinter(String name) {
            th = new Thread(this, name);
        }

        public void run() {
            String name = th.getName();
            int timeWatch = 10;

            while (!stopped && timeWatch > 0) {
                if (!suspended) {
                    System.out.println(name);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        System.out.println(name + " - interrupted");
                    }
                } else {
                    Thread.yield();
                }
                timeWatch--;
            }
            System.out.println(name + " - stopped");
        }

        public void suspend() {
            suspended = true;
            th.interrupt();
            System.out.println(th.getName() + " - interrupt() by suspend()");
        }

        public void stop() {
            stopped = true;
            th.interrupt();
            System.out.println(th.getName() + " - interrupt() by stop()");
        }

        public void resume() {
            suspended = false;
        }

        public void start() {
            th.start();
        }
    }
}
