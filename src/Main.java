import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {

    private double a = -1, b = 0;
    private int n = 1000;
    private double h = (b - a) / n;


    public static void main(String[] args) {
        Main main = new Main();
        for (int i = 0; i < 1000; i++) {
            main.run();
        }
    }

    private void run() {
        System.out.println("just loop: " + timeLoop());
        System.out.println("with one thread: " + timeSingleThread());
        System.out.println("with pool of thread: " + timePoolThread());
    }

    private long timeLoop() {
        long start = System.nanoTime();
        test();
        long finish = System.nanoTime();
        return finish - start;
    }

    private long timeSingleThread() {
        long start = System.nanoTime();
        ExecutorService service = Executors.newSingleThreadExecutor();
        Future<Double> future = service.submit(new Calculate(a, b, h));

        while (future.isDone()) {
            System.out.println("wait");
        }

        long finish = System.nanoTime();
        return finish - start;
    }

    private long timePoolThread() {
        long start = System.nanoTime();
        ExecutorService service = Executors.newFixedThreadPool(n);

        Future<Double> future = service.submit(new Calculate(a, b, h));

        while (future.isDone()) {
            System.out.println("wait");
        }
        service.shutdown();

        long finish = System.nanoTime();
        return finish - start;
    }

    private double test() {
        double sum = 0;
        for (double i = a; i < b; i += h) {
            sum += MyFunction.f(i);
        }
        return sum;
    }
}
