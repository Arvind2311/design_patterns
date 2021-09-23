package low_level_design.atm;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class ATM {
    int atmId;

    Address location;

    CashDispenser cashDispenser;
    Keypad keypad;
    CashDeposit cashDeposit;
    ChequeDeposit chequeDeposit;
    Screen screen;
    CardReader cardReader;

    BankService bankService;
}

class Address{
    String country;
    String state;
    String city;
    String pincode;//ZipCode
    String street;
}

abstract class CashDispenser{
    Map<CashType, List<Cash>> cashAvailable;

    public abstract void dispenseCash(int amount);
}

enum CashType{
    HUNDRED, FIVE_HUNDRED
}

class Cash{
    CashType cashType;
    String serialNo;
}

abstract class Screen{
    public abstract void display(String message);
}

abstract class CardReader{
    public abstract CardInfo fetchCardDetails();
}

class CardInfo{
    CardType cardType;
    String cardNumber;
    Bank bank;
    Date expiryDate;
    int cvv;
    float withdrawalLimit;
}

enum CardType{
    DEBIT, CREDIT
}

abstract class Keypad{
    public abstract String getInput();
}

class Bank{
    String name;
    Address location;

    List<ATM> atmList;
}

interface BankService{
    public boolean isValidUser(String pin, CardInfo cardInfo);
    public Customer getCustomerDetails(CardInfo cardInfo);
    public TransactionDetail executeTransaction(Transaction transaction);
}

class BankA implements BankService{
    @Override
    public boolean isValidUser(String pin, CardInfo cardInfo) {
        return false;
    }

    @Override
    public Customer getCustomerDetails(CardInfo cardInfo) {
        return null;
    }

    @Override
    public TransactionDetail executeTransaction(Transaction transaction) {
        return null;
    }
}

class BankB implements BankService{
    @Override
    public boolean isValidUser(String pin, CardInfo cardInfo) {
        return false;
    }

    @Override
    public Customer getCustomerDetails(CardInfo cardInfo) {
        return null;
    }

    @Override
    public TransactionDetail executeTransaction(Transaction transaction) {
        return null;
    }
}

abstract class BankServiceFactory{
    public abstract BankService getBankServiceObject(CardInfo cardInfo);
}

class Customer{
    String name;
    CardInfo cardInfo;
    Account account;
    BankService bankService;
    CustomerStatus customerStatus;
}

class Account{
    String accountNumber;
    float avaialableBalance;
}

enum CustomerStatus{
    ACTIVE, INACTIVE, BLOCKED
}

class Transaction{
    Integer transactionId;
    String sourceAccount;
    Date transactionDate;
}

class Deposit extends Transaction{
    float amount;
}

abstract class CashDeposit extends Deposit{
    public abstract Cheque getCheque();
}

abstract class ChequeDeposit extends Deposit{
    public abstract List<Cash> getCash();
}

class Withdraw extends Transaction{
    float amount;
}

class Transfer extends Transaction{
    float amount;
    String destnAccount;
}

class TransactionDetail{
    TransactionStatus transactionStatus;
    String sourceAccountNumber;
    Date transactionDate;
    TransactionType transactionType;
    int transactionId;
}

enum TransactionStatus{
    PENDING, SUCCESSFUL, ERROR, FAILED
}

enum TransactionType{
    DEPOSIT, CHEQUE, TRANSFER
}