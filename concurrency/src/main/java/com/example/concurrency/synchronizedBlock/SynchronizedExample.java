package com.example.concurrency.synchronizedBlock;

public class SynchronizedExample {

    public static void example() {
        AccountThread accountThread = new AccountThread();
        Thread account1 = new Thread(accountThread);
        Thread account2 = new Thread(accountThread);

        account1.start();
        account2.start();
    }

    static class AccountThread implements Runnable {
        Account acc = new Account();

        @Override
        public void run() {
            while (acc.getBalance() > 0) {
                int money = (int) (Math.random() * 3 + 1) * 100;
                acc.withdraw(money);
                System.out.println("balance: " + acc.getBalance());
            }
        }
    }

    static class Account {

        private int balance = 1000;

        public int getBalance() {
            return this.balance;
        }

        public void withdraw(int money) {
            synchronized (this) {
                if (balance >= money) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {}
                    balance -= money;
                }
            }
        }
    }
}
