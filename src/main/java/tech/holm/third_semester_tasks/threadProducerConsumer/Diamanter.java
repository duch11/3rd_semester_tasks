package tech.holm.third_semester_tasks.threadProducerConsumer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Diamanter {

    private static Lock lock = new ReentrantLock();
    private static Condition diamanterAdded = lock.newCondition();
    private static int antal = 0;

    public synchronized void makeDiamond(Object o ,int nytAntal){

        lock.lock();
        try{
            antal = antal + nytAntal;
            System.out.println("tilføjer " + nytAntal + " diamanter så der er: " + antal + " Diamanter");
            diamanterAdded.signalAll();
        } catch (Exception e){

        } finally {
            lock.unlock();
        }


    }

    public void consumeDiamond(int antalToConsume){
        lock.lock();
        try {

            while (antal < antalToConsume){
                System.out.println("waiting to consume " + antalToConsume);
                diamanterAdded.await();
            }

            System.out.println("Consumed: " + antalToConsume);
            antal = antal - antalToConsume;

        } catch (Exception e){

        } finally {
            lock.unlock();
        }
    }

    public synchronized int getAntal() {
        return antal;
    }

    public void addDiamond(){

        int nytAntal = antal;
        try {
            Thread.sleep((long)Math.abs(Math.random()));
        } catch (InterruptedException e) {

        }
        antal = nytAntal + 1;
        System.out.println(antal);
    }
}
