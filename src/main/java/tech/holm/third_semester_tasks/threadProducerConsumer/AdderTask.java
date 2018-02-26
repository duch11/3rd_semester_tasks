package tech.holm.third_semester_tasks.threadProducerConsumer;

public class AdderTask implements Runnable {

    private Diamanter diamanter;

    public AdderTask(Diamanter diamanter) {
        this.diamanter = diamanter;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++){
            diamanter.addDiamond();
        }
    }
}
