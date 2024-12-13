import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class  FactorialCalculator{
    public static void main(String[] args) {
        List<Integer> numbers = List.of(5, 10, 15, 20, 25);
        ExecutorService executor = Executors.newFixedThreadPool(3);
        List<Future<Long>> futures = new ArrayList<>();
        for (int number : numbers) {
            Callable<Long> task = () -> {
                System.out.println(Thread.currentThread().getName() + " обчислює факторіал числа " + number);
                return calculateFactorial(number);
            };
            Future<Long> future = executor.submit(task);
            futures.add(future);
        }
        for (int i = 0; i < numbers.size(); i++) {
            try {
                long result = futures.get(i).get();
                System.out.println("Факторіал числа " + numbers.get(i) + " дорівнює " + result);
            } catch (InterruptedException | ExecutionException e) {
                System.err.println("Помилка при обчисленні факторіалу числа " + numbers.get(i));
            }
        }
        executor.shutdown();
    }
    private static long calculateFactorial(int number) {
        long result = 1;
        for (int i = 2; i <= number; i++) {
            result *= i;
        }
        return result;
    }
}