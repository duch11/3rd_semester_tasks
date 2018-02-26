package tech.holm.third_semester_tasks.threadProducerConsumer;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class ConsumerTask implements Runnable {

    private Diamanter diamanter;

    public ConsumerTask(Diamanter diamanter) {
        this.diamanter = diamanter;
    }

    @Override
    public void run() {
        while (true){
            ThreadLocalRandom random =  ThreadLocalRandom.current();
            diamanter.consumeDiamond(Math.abs(random.nextInt()));
            try {
                Thread.sleep((long) Math.abs(Math.random()));
            } catch (InterruptedException e) {

            }
        }
    }
}
