import main.Account;
import main.Bank;
import main.Customer;
import org.easymock.Mock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;


import static org.easymock.EasyMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BankTest {
    private final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    private final PrintStream printStream = System.out;



        Customer john = new Customer("100", "John", "Doe", "Johndoe@yahoo.com");
        Customer jane = new Customer("200", "Jane", "Doe", "janedoe@gmail.com");
        Account john1 = new Account("1001", john, 20000f);
        Account john2 = new Account("1002", john, 30000f);
        Account jane1 = new Account("2001", jane, 40000f);
        Bank bank = new Bank(List.of(john, jane), List.of(john1, john2, jane1));


    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(byteArrayOutputStream));
    }

    @Test
    void depositMoneySuccessTest() {
        bank.depositMoney("1001", 1000f);
        assertEquals(21000f, john1.getBalance());
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
    }

    @Test
    void withdrawMoneySuccessTest() {
        bank.withdrawMoney("2001", 10000f);
        assertEquals(30000f, jane1.getBalance());

    }





}
