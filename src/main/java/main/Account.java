package main;

public class Account {
    private String accountId;
    private Customer customer;
    private float balance;
    private boolean isFixedDeposit;

    public Account(String accountId, Customer customer, float balance) {
        this.accountId = accountId;
        this.customer = customer;
        this.balance = balance;
        this.isFixedDeposit = false;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public boolean isFixedDeposit() {
        return isFixedDeposit;
    }

    public void setFixedDeposit(boolean fixedDeposit) {
        isFixedDeposit = fixedDeposit;

    }

    @Override
    public String toString() {
        return "Account with Account ID " + accountId + " has a balance of " + balance;
    }
}
