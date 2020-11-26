package com.epsilon.training.project.banking;

public class MTMain {
    public static void main(String[] args) throws InterruptedException {
        String[] transactions ={"Deposit 200", "Withdraw 50", "Deposit 150", "Deposit 100", "Withdraw 50"};
        //Creating two different Accounts:
        //Account account1 = new SavingsAccount("01", 1000);
        Account account2 = new CheckingAccount("02", 1000);
        for (String op: transactions) {
            String[] data = op.split(" ");
            double amt = Double.parseDouble(data[1]);
            if (data[0].equals("Deposit")) {
                Thread t = new Thread(new MyRunnable(account2, true, amt));
                t.start();
                t.join();
            } else {
                Thread t = new Thread(new MyRunnable(account2, false, amt));
                t.start();
                t.join();
            }
        }
        System.out.println("Final balance in the account is: " + account2.balance);

    }
}

class MyRunnable implements Runnable {

    private Account acc;
    private boolean toDeposit;
    private double amt;

    public MyRunnable(Account acc, boolean toDeposit, double amt) {
        this.acc = acc;
        this.toDeposit = toDeposit;
        this.amt = amt;
    }

    public void run() {
        System.out.println(Thread.currentThread().getName() + " Current balance: " + this.acc.getBalance());
        if (toDeposit) {
            this.acc.depositAmount(this.amt);
        } else {
            this.acc.withdrawAmount(this.amt);
        }
        System.out.println("New balance is: " + this.acc.getBalance());
    }

}