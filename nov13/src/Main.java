import java.util.List;
import java.util.concurrent.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(1000); //create executor service to keep track of 1000 threads in a thread pool

        // Create an array to hold the Callable tasks           //each task is a thread
        Callable<Integer>[] tasks = new Callable[1000];         //bc callable return Integer

        for (int i = 0; i < 1000; i++)
        {
            tasks[i] = () -> {
                int c = 0;
                for (int j = 0; j < 1000000; j++) {
                    c++;
                }
                return c;           // return the count for this task
            };
        }

        long startTime = System.currentTimeMillis();
        List<Future<Integer>> futures = executor.invokeAll(List.of(tasks)); //tell executor to do tasks and get the result as a future (list gets made even when task is not complete and you don't have result)
        int count = 0;

        for (Future<Integer> future : futures)
            count += future.get();      //get waits until task is complete, then gets

        long endTime = System.currentTimeMillis();

        System.out.println("Total count: " + count);
        System.out.println("Time taken: " + (endTime - startTime) + " milliseconds");
    }
}