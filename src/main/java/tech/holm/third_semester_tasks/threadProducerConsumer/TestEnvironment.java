package tech.holm.third_semester_tasks.threadProducerConsumer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestEnvironment {

    static Diamanter diamanter = new Diamanter();

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        executorService.execute(new ProducerTask(diamanter));

        executorService.execute(new ConsumerTask(diamanter));
        /*executorService.execute(new AdderTask(diamanter));
        executorService.execute(new AdderTask(diamanter));
        executorService.execute(new AdderTask(diamanter));
        executorService.execute(new AdderTask(diamanter));
        executorService.execute(new AdderTask(diamanter));
        executorService.execute(new AdderTask(diamanter));
        executorService.execute(new AdderTask(diamanter));
        executorService.execute(new AdderTask(diamanter));
        */
        executorService.shutdown();
    }
}
