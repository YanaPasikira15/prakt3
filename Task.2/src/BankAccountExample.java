import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


class BankAccount {
    private int balance;
    private final Lock lock = new ReentrantLock();
    public BankAccount(int initialBalance) {
        this.balance = initialBalance;
    }
    public synchronized void deposit(int amount) {
        balance += amount;
        System.out.println(Thread.currentThread().getName() + " поповнив рахунок на " + amount + ". Баланс: " + balance);
    }
    public void withdraw(int amount) {
        lock.lock();
        try {
            if (balance >= amount) {
                balance -= amount;
                System.out.println(Thread.currentThread().getName() + " зняв " + amount + ". Баланс: " + balance);
            } else {
                System.out.println(Thread.currentThread().getName() + " намагався зняти " + amount + ", але недостатньо коштів. Баланс: " + balance);
            }
        } finally {
            lock.unlock();
        }
    }
    public synchronized int getBalance() {
        return balance;
    }
}
public class BankAccountExample {
    public static void main(String[] args) {
        BankAccount account = new BankAccount(1000);
        Thread depositor = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                account.deposit(200);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }, "Depositor");
        Thread withdrawer = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                account.withdraw(300);
                try {
                    Thread.sleep(150);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }, "Withdrawer");
        depositor.start();
        withdrawer.start();
        try {
            depositor.join();
            withdrawer.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Фінальний баланс: " + account.getBalance());
    }
}