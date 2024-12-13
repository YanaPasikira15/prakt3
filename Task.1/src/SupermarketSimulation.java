
class CheckoutCounter implements Runnable {
    private final int counterId;
    private final String customer;
    public CheckoutCounter(int counterId, String customer) {
        this.counterId = counterId;
        this.customer = customer;
    }
    @Override
    public void run() {
        System.out.println("Каса " + counterId + " почала обслуговувати клієнта " + customer);
        try {
            Thread.sleep((long) (Math.random() * 5000 + 1000));
        } catch (InterruptedException e) {
            System.out.println("Каса " + counterId + " була перервана.");
        }
        System.out.println("Каса " + counterId + " завершила обслуговування клієнта " + customer);
    }
}
public class SupermarketSimulation {
    public static void main(String[] args) {
        // Список клієнтів
        String[] customers = {"Андрій", "Олена", "Іван", "Марія", "Софія"};
        for (int i = 0; i < customers.length; i++) {
            Thread checkoutThread = new Thread(new CheckoutCounter(i + 1, customers[i]));
            checkoutThread.start(); // Запуск потоку
        }
    }
}