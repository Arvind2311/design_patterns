package behavioral.null_object;

import java.lang.reflect.Proxy;

public class NullObject {

    // Drawbacks:- Computationally intensive.
    @SuppressWarnings("unchecked")
    public static <T> T noOp(Class<T> itf){
        return (T) Proxy.newProxyInstance(
                itf.getClassLoader(),
                new Class<?>[] {itf},
                (((proxy, method, args) -> {
                    if(method.getReturnType().equals(Void.TYPE))
                        return null;
                    else
                        return method.getReturnType().getConstructor().newInstance();
                }))
        );
    }

    public static void main(String[] args) {
        ConsoleLog log = new ConsoleLog();
        BankAccount account = new BankAccount(log);
        account.deposit(100);
        // What if i don't want any logging to be done
        // BankAccount account = new BankAccount(null);
        // Now you can pass the value as new BankAccount(new NullLog());
        // This case it fails (no reference exception).

        Log log1 = noOp(Log.class);
        BankAccount account1 = new BankAccount(log1);
        account1.deposit(100);

    }
}

interface Log{
    void info(String msg);
    void warn(String msg);
}

class ConsoleLog implements Log{
    @Override
    public void info(String msg) {
        System.out.println(msg);
    }

    @Override
    public void warn(String msg) {
        System.out.println("WARNING: "+ msg);
    }
}

// Null object implementation
final class NullLog implements Log{
    @Override
    public void info(String msg) {

    }

    @Override
    public void warn(String msg) {

    }
}

class BankAccount{
    private Log log;
    private int balance;

    public BankAccount(Log log) {
        this.log = log;
    }

    public void deposit(int amount){
        balance+=amount;
        log.info("Deposited " + amount);
    }
}