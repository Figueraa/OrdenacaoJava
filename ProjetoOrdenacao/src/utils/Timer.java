package utils;

public class Timer {
    public static long measureExecutionTime(Runnable algorithm) {
        long start = System.nanoTime();
        algorithm.run();
        long end = System.nanoTime();
        return end - start;
    }
}
