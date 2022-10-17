package com.example.concurrency.wait_notify;

import java.util.ArrayList;

public class WaitAndNotifyExample {

    public static void example() throws InterruptedException {
        Table table = new Table();
        new Thread(new Cook(table), "COOK1").start();
        new Thread(new Customer(table, "donut"), "CUSTOMER1").start();
        new Thread(new Customer(table, "burger"), "CUSTOMER2").start();

        Thread.sleep(2000);
        System.exit(0);
    }

    static class Customer implements Runnable {

        private Table table;
        private String food;

        public Customer(Table table, String food) {
            this.table = table;
            this.food = food;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {}

                String name = Thread.currentThread().getName();
                table.remove(food);
                System.out.println(name + " ate a " + food);
            }
        }
    }

    static class Cook implements Runnable {
        private Table table;

        public Cook(Table table) {
            this.table = table;
        }

        @Override
        public void run() {
            while (true) {
                int idx = (int) (Math.random() * table.dishNum());
                table.add(table.dishNames[idx]);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {}
            }
        }
    }

    static class Table {
        String[] dishNames = {"donut", "donut", "burger"};
        final int MAX_FOOD = 6;
        private ArrayList<String> dishes = new ArrayList<>();

        public synchronized void add(String dish) {
            while (dishes.size() >= MAX_FOOD) {
                String name = Thread.currentThread().getName();
                System.out.println(name + " is waiting");

                try {
                    wait(); //손님 음식을 소비할 때 까지 요리사를 wait 하게함
                    Thread.sleep(500);
                } catch (InterruptedException e) {}
            }

            dishes.add(dish);
            notify(); // wait된 손님을 깨움
            System.out.println("Dishes: " + dishes.toString());
        }

        public void remove(String dishName) {
            synchronized (this) {
                String name = Thread.currentThread().getName();

                while (dishes.size() == 0) {
                    System.out.println(name + " is waiting");
                    try {
                        wait();
                        Thread.sleep(500);
                    } catch (InterruptedException e) {}
                }

                while (true) {
                    for (int i = 0; i < dishes.size(); i++) {
                        if (dishName.equals(dishes.get(i))) {
                            dishes.remove(i);
                            notify();
                            return ;
                        }
                    }

                    try {
                        System.out.println(name + " is waiting");
                        wait();
                        Thread.sleep(500);
                    } catch (InterruptedException e) {}
                }
            }
        }

        public int dishNum () {return dishNames.length;}
    }
}
