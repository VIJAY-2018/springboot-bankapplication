package com.epsilon.training.project.banking;


import java.util.*;

public class Customer {
    private String name;
    private List<Account> accounts;
    private String address;
    private String pan;

    public Customer(String name, String address, String pan) {
        this.name = name;
        this.address = address;
        this.pan = pan;
        this.accounts = new ArrayList<Account>();
    }

    public String toString() {
        return this.name;
    }

    public void addAccount(Account acc) {
        this.accounts.add(acc);
    }

    public List<Account> getAccounts() {
        return this.accounts;
    }

}
