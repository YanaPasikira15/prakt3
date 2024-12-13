import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PrimeNumberChecker {
    public static void main(String[] args) {
        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            numbers.add(i);
        }
        int numberOfThreads = 5;
        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);
        ConcurrentHashMap<Integer, Boolean> results = new ConcurrentHashMap<>();
        for (int number : numbers) {
            executor.submit(() -> {
                boolean isPrime = isPrime(number);
                results.put(number, isPrime);
                System.out.println(Thread.currentThread().getName() + " перевірив число " + number + ": " + (isPrime ? "просте" : "не просте"));
            });
        }
        executor.shutdown();
        while (!executor.isTerminated()) {
        }
        System.out.println("\nРезультати обробки:");
        for (Map.Entry<Integer, Boolean> entry : results.entrySet()) {
            System.out.println("Число " + entry.getKey() + ": " + (entry.getValue() ? "просте" : "не просте"));
        }
    }
    private static boolean isPrime(int number) {
        if (number < 2) return false;
        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) return false;
        }
        return true;
    }
}