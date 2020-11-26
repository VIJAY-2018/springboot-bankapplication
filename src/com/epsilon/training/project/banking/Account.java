package com.epsilon.training.project.banking;
import java.time.*;
import java.util.*;

public abstract class Account {

    protected String accountId;
    protected double balance;
    protected LocalDate accountCreatedOn;
    protected List<Transaction> transactions;

    public double getBalance() {
        return this.balance;
    }

    protected class Transaction {


        public boolean isDeposit; //True if deposit, false if withdrawal
        public double initialAmount;
        public double transactionAmount;
        public double finalAmount;
        public LocalDateTime transactionTime;

        public Transaction(boolean isDeposit, double initialAmount, double transacAmount) {
            this.isDeposit = isDeposit;
            this.initialAmount = initialAmount;
            this.transactionAmount = transacAmount;
            this.transactionTime = LocalDateTime.now();
            if (isDeposit) {
                this.finalAmount = this.initialAmount + this.transactionAmount;
            } else {
                this.finalAmount = this.initialAmount - this.transactionAmount;
            }

        }
    }

    public Account(String accountId, double balance, LocalDate date) {
        this.accountId = accountId;
        this.balance = balance;
        this.accountCreatedOn = date;
        this.transactions = new ArrayList<Transaction>();


    }

    //Abstract method - Needs to be implemented by the child Account classes
    public abstract void withdrawAmount(double amount);

    public synchronized void depositAmount(double amount) {
        //No restrictions exist on making deposits for any kind of accounts, hence this is a default method in the abstract base class

        //Create a new transaction and update transactions list and the balance:
        this.updateTransactionsAndBalance(new Transaction(true, this.balance, amount));
    }

    public void generateStatement() {
        if (this.transactions.size() == 0) {
            System.out.println("No transactions have been made on this account. Current balance: " + this.balance);
        } else {
            System.out.println("TRANSACTION HISTORY: ");
            for (Transaction t: this.transactions) {
                System.out.println("***************");
                System.out.println((t.isDeposit)? "TRANSACTION : DEPOSIT": "TRANSACTION: WITHDRAWAL");
                System.out.println("Initial balance: " + t.initialAmount);
                System.out.println((t.isDeposit)? "Amount deposited: " + t.transactionAmount: "Amount withdrawn: " + t.transactionAmount);
                System.out.println("Transaction done on: " + t.transactionTime);
                System.out.println("New balance: " + t.finalAmount);
                System.out.println("***************");
            }
        }

    }


    //Default method: Behaviour shared by all Account types
    public void updateTransactionsAndBalance(Transaction t) {
        this.transactions.add(t);
        if (t.isDeposit) {
            this.balance += t.transactionAmount;
        } else {
            this.balance -= t.transactionAmount;
        }
    }


}

//Limit SavingsAccount to one withdrawal every month
//Calculate interest for the SavingsAccount

class SavingsAccount extends Account {

   // private static short interestRate = 2; //Update the balance with accrued interest every month, starting from the time of account creation
    private LocalDateTime lastWithdrawalDateTime; //Used to restrict number of withdrawals

    public SavingsAccount(String accountId, double balance) {
        super(accountId, balance, LocalDate.now());
        this.lastWithdrawalDateTime = LocalDateTime.now();
    }

    @Override
    public synchronized void withdrawAmount(double amount) {
        if (LocalDateTime.now().getMinute() - this.lastWithdrawalDateTime.getMinute() < 1) {
            System.out.println("You have very recently made a withdrawal or opened this account. You cannot withdraw again right now.");
        }
        else {
            if (amount > this.balance) {
                System.out.println("!! Insufficient balance !!");
            } else if (amount > (0.5 * balance)) {
                System.out.println("Savings account does not allow for a withdrawal of this amount. Please enter a lower amount");
            } else {
                //Create a new transaction and add to list of transactions
                this.updateTransactionsAndBalance(new Transaction(false, this.balance, amount));

            }
        }

    }

    @Override
    public String toString() {
        return "Savings Account with balance of: " + this.balance;
    }

}

class CheckingAccount extends Account {


    public CheckingAccount(String accountId, double balance) {
        super(accountId, balance, LocalDate.now());
    }

    @Override
    public synchronized void withdrawAmount(double amount) {
        if (amount > this.balance) {
            System.out.println("!! Insufficient balance !!");
        } else {
            //Create transaction, update transaction history and the balance
            this.updateTransactionsAndBalance(new Transaction(false, this.balance, amount));

        }
    }

    @Override
    public String toString() {
        return "Checking Account with balance of: " + this.balance;
    }

}

