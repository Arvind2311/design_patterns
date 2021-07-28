package behavioral.memento;

public class Memento {
    public static void main(String[] args) {
        BankAccount ba = new BankAccount(100);
        BankAccountToken bat1 = ba.deposit(50); //150
        BankAccountToken bat2 = ba.deposit(25); //175
        System.out.println(ba);
        // restore to m1
        ba.restore(bat1);
        System.out.println(ba);

        // restore to m2
        ba.restore(bat2);
        System.out.println(ba);
    }
}

class BankAccountToken{
    public int balance;

    public BankAccountToken(int balance) {
        this.balance = balance;
    }
}

class BankAccount{
    private int balance;

    public BankAccount(int balance) {
        this.balance = balance;
    }

    public BankAccountToken deposit(int amount){
        balance+=amount;
        return new BankAccountToken(balance);
    }

    public void restore(BankAccountToken bat){
        balance = bat.balance;
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "balance=" + balance +
                '}';
    }
}