package main;

import java.util.List;

public class Bank {
    private List<Customer> customers;
    private List<Account> accounts;

    public Bank(List<Customer> customers, List<Account> accounts) {
        this.customers = customers;
        this.accounts = accounts;
    }
    public float checkBalance(String accountId) {
        Account account = accounts.stream().filter(account1 -> account1.getAccountId().equals(accountId)).findFirst().orElse(null);
        if (account == null) {
            System.out.println("Invalid AccountID");
            return -1.0f;

        }
        else return account.getBalance();

    }

    public void depositMoney(String accountId, float amount) {
        Account account = accounts.stream().filter(account1 -> account1.getAccountId().equals(accountId)).findFirst().orElse(null);
        if (account == null) {
            System.out.println("Invalid AccountID");
        }
        else if (amount > 100000F){
            System.out.println("Amount is above maximum limit. Please contact bank manager");
        }
        else {
            account.setBalance(account.getBalance() + amount);
            System.out.println("Transaction successful!");
        }

    }

    public void withdrawMoney (String accountId, float amount) {
        Account account = accounts.stream().filter(account1 -> account1.getAccountId().equals(accountId)).findFirst().orElse(null);
        if (account == null) {
            System.out.println("Invalid AccountID");
        } else if (account.getBalance() < amount) {
            System.out.println("Insufficient Funds");

        } else if (account.isFixedDeposit()) {
            System.out.println("Cannot withdraw money from a Fixed Deposit Account");

        } else {
            account.setBalance(account.getBalance() - amount);
            System.out.println("Transaction successful!");
        }


    }








    public static void main(String[] args) {
        System.out.println("Welcome to the Bank!");

    }
}