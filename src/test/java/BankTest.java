import main.Account;
import main.Bank;
import main.Customer;
import main.CustomerNotFoundException;
import org.easymock.Mock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;


import static org.easymock.EasyMock.*;
import static org.junit.jupiter.api.Assertions.*;

public class BankTest {
    private final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    private final PrintStream printStream = System.out;



        Customer john = new Customer("100", "John", "Doe", "Johndoe@yahoo.com");
        Customer jane = new Customer("200", "Jane", "Doe", "janedoe@gmail.com");
        Account john1 = new Account("1001", john, 20000f); //20k
        Account john2 = new Account("1002", john, 30000f); //30k
        Account jane1 = new Account("2001", jane, 40000f); //40k
        Bank bank = new Bank(List.of(john, jane), List.of(john1, john2, jane1));


    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(byteArrayOutputStream));
    }

    @Test
    void depositMoneySuccessTest() {
        bank.depositMoney("1001", 1000f);
        assertEquals(21000f, john1.getBalance());
        assertEquals("Transaction successful!\n", byteArrayOutputStream.toString());
        System.setOut(printStream);

    }
    @Test
    void depositMoneyInvalidAccountId() {
        bank.depositMoney("3001", 1000f);
        assertEquals("Invalid AccountID\n", byteArrayOutputStream.toString());
        System.setOut(printStream);

    }
    @Test
    void depositMoneyOverLimitTest() {
        bank.depositMoney("1002", 200000f);
        assertEquals(30000f, john2.getBalance());
        assertEquals("Amount is above maximum limit. Please contact bank manager\n", byteArrayOutputStream.toString());
        System.setOut(printStream);
    }

    @Test
    void withdrawMoneySuccessTest() {
        bank.withdrawMoney("2001", 10000f);
        assertEquals(30000f, jane1.getBalance());
        assertEquals("Transaction successful!\n", byteArrayOutputStream.toString());
        System.setOut(printStream);


    }
    @Test
    void withdrawMoneyInsufficientFunds() {
        bank.withdrawMoney("2001", 1000000f);
        assertEquals(40000f, jane1.getBalance());
        assertEquals("Insufficient Funds\n", byteArrayOutputStream.toString());
        System.setOut(printStream);

    }
    @Test
    void withdrawMoneyFixedDeposit() {
        john2.setFixedDeposit(true);
        bank.withdrawMoney("1002", 2000f);
        assertEquals(30000f, john2.getBalance());
        assertEquals("Cannot withdraw money from a Fixed Deposit Account\n", byteArrayOutputStream.toString());
        System.setOut(printStream);
    }

    @Test
    void withdrawMoneyInvalidAccountID() {
        bank.withdrawMoney("3003", 200f);
        assertEquals("Invalid AccountID\n", byteArrayOutputStream.toString());
        System.setOut(printStream);
    }
    @Test
    void checkBalanceSuccessTest() {
        bank.checkBalance(john1.getAccountId());
        assertEquals(20000f, john1.getBalance());

    }
    @Test
    void checkBalanceFail() {
        bank.checkBalance("20000");
        assertEquals("Invalid AccountID\n", byteArrayOutputStream.toString());
        System.setOut(printStream);
        assertEquals(-1.0f, bank.checkBalance("20000"));

    }
    @Test
    void setFixedDepositSuccess() {
        assertTrue(bank.createFixedDeposit("1001"));
    }

    @Test
    void setFixedDepositFail() {
        assertFalse(bank.createFixedDeposit("20000"));
        assertEquals("Invalid AccountID\n", byteArrayOutputStream.toString());
        System.setOut(printStream);

    }
    @Test
    void getOwnerOfAccountSuccess() {
        assertEquals(john, bank.getOwnerOfAccount("1001"));

    }
    @Test
    void getOwnerOfAccountFail() {
        assertEquals(null, bank.getOwnerOfAccount("200000"));
        assertEquals("Invalid AccountID\n", byteArrayOutputStream.toString());
        System.setOut(printStream);
    }

    @Test
    void getAccountsOfCustomerSuccess() throws CustomerNotFoundException {
        john.setAccounts(new ArrayList<>(List.of(john1, john2)));
        assertEquals(new ArrayList<>(List.of("1001", "1002")), bank.getAccountIDsOfCustomer("100"));
        assertDoesNotThrow(() -> bank.getAccountIDsOfCustomer("200"));
    }
    @Test
    void getAccountsOfCustomerFail() throws CustomerNotFoundException {
        CustomerNotFoundException thrown =
                assertThrows(CustomerNotFoundException.class, ()->bank.getAccountIDsOfCustomer("20000"));
    }









}
