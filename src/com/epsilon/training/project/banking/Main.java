package com.epsilon.training.project.banking;

import java.util.*;

//import jdk.javadoc.internal.doclets.formats.html.SourceToHTMLConverter;

public class Main {
    private static List<Customer> clients = new ArrayList<Customer>();
    private static Customer currentCustomer;
    
    public static void displayCurrentCustomerAccounts() {
        if (currentCustomer == null) {
            return;
        }
        else {
            List<Account> accounts = currentCustomer.getAccounts();
            if (accounts.size() == 0) {
                return;
            }
            else {
                System.out.println("All accounts associated with customer " + currentCustomer + " are: ");
                for (int i = 1; i <= accounts.size(); i++) {
                    System.out.println(i + ". " + accounts.get(i - 1));
                }
            }
        }
    }

    public static void main(String[] args) {
        int flag = 1;double bal = 0;
        Scanner scan = new Scanner(System.in);
        while (flag == 1) {
            System.out.println("------------------------------------------------------------------------------------");
            System.out.println("Welcome to the Automated Bank!. Please enter one of the following choices to proceed: ");
            System.out.println("1. Create new customer, 2. Choose customer, 3. Add new account 4. Make transaction 5. Statement generation,  6. exit");
            int input = scan.nextInt();
            scan.nextLine();
            switch(input) {
                case 1: {
                    System.out.println("Enter new customer name: ");
                    String name = scan.nextLine();
                    System.out.println("Enter customer address: ");
                    String address = scan.nextLine();
                    System.out.println("Enter customer PAN: ");
                    String pan = scan.nextLine();
                    Customer cust = new Customer(name, address, pan);
                    clients.add(cust);
                    System.out.println("New customer added to system! ");
                    break;
                }

                case 2: {
                    if (currentCustomer == null) {
                        System.out.println("No customer currently chosen.");
                    }
                    System.out.println("List of customers: ");
                    for (int i = 1; i <= clients.size(); i++) {
                        System.out.println(i + ". " + clients.get(i - 1));
                    }
                    System.out.println("Enter choice by customer number: ");
                    int ind = scan.nextInt();
                    scan.nextLine();
                    if (ind >= 1 && ind <= clients.size()) {
                        currentCustomer = clients.get(ind - 1);
                        System.out.println("Customer chosen as: " + clients.get(ind - 1));
                    } else {
                        System.out.println("Invalid choice");
                    }
                    break;
                }

                case 3: {
                    if (currentCustomer == null) {
                        System.out.println("Please enter choice 2 in main menu and choose a customer");
                    } else {
                        String accId;
                        System.out.println("Enter an accound ID:");
                        accId = scan.nextLine();
                        System.out.println("Enter initial balance for this account:");
                        
                        while(bal <= 0)
                        {
                            System.out.println("Please enter bal greater than 0\n");
                            bal = scan.nextDouble();
                        } 
                        scan.nextLine();
                        System.out.println("Enter type of account you want: 1. for Savings account and 2 for Checking Account");
                        int accType = scan.nextInt();
                        scan.nextLine();
                        Account newAccount;
                        if (accType == 1) {
                            newAccount = new SavingsAccount(accId, bal);
                        } else {
                            newAccount = new CheckingAccount(accId, bal);
                        }
                        //Adding this account to list of current customer's accounts:
                        currentCustomer.addAccount(newAccount);
                    }
                    break;
                }

                case 4: {
                    Account currentAccount;
                    if (currentCustomer == null) {
                        System.out.println("Go to main menu and choose 2. for choosing an existing customer");
                    } else {
                        if (currentCustomer.getAccounts().size() == 0) {
                            System.out.println("This customer has no accounts. Either choose a different customer or make an account in main menu");
                        } else {
                            //Show all existing accounts and choose one
                            displayCurrentCustomerAccounts();
                            List<Account> accounts = currentCustomer.getAccounts();
                            System.out.println("Choose an account: ");
                            int accChoice = scan.nextInt();
                            scan.nextLine();
                            if (accChoice >= 1 && accChoice <= accounts.size()) {
                                //Transaction options
                                currentAccount = accounts.get(accChoice - 1);
                                System.out.println("Current balance is: " + currentAccount.getBalance());
                                System.out.println("Choose 1. for deposit and 2. for withdrawal");
                                int transactChoice = scan.nextInt();
                                scan.nextLine();
                                System.out.println("Enter amount to deposit/withdraw");
                                Double amt = Double.parseDouble(scan.nextLine());
                                if (transactChoice == 1) {
                                    currentAccount.depositAmount(amt);
                                } else {
                                    if (amt > bal) {
                                        System.out.println("You have insufficient balance\n");
                                    }
                                    else
                                        currentAccount.withdrawAmount(amt);
                                }

                            } else {
                                System.out.println("Invalid choice");
                            }

                        }
                    }
                    break;
                }

                case 5: {
                    //Statement generation
                    if (currentCustomer == null) {
                        System.out.println("Go to main menu and choose 2. for choosing an existing customer");
                    } else {
                        if (currentCustomer.getAccounts().size() == 0) {
                            System.out.println("This customer has no accounts");
                        } else {
                            //Display all accounts for given customer and get statement for each account:
                            displayCurrentCustomerAccounts();
                            System.out.println("Generating report for each account:");
                            for (Account acc : currentCustomer.getAccounts()) {
                                acc.generateStatement();
                            }
                        }

                    }
                    break;
                }

                case 6: {
                    //Exit

                    flag = 0;
                    System.out.println("Exiting application. Goodbye");
                    break;
                }
                default: {
                    System.out.println("Invalid option chosen. Try again");
                    break;
                }

            }
        }
        scan.close();
    }
}
