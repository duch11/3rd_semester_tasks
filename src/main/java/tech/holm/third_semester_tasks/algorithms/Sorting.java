package tech.holm.third_semester_tasks.algorithms;

import javafx.application.Application;
import javafx.stage.Stage;

import static javafx.application.Application.launch;

public class Sorting {

    public static void main(String[] args) {

        Sorting sort = new Sorting();
        sort.start();
    }

    public void start(){
        for (int i :
                bubble(new int[]{2,4,34,55,55,33,22,11,9,23})) {
            System.out.println(i);

        }

    }

    private int[] bubble(int[] arrayIncoming){
        int[] array = arrayIncoming;
        boolean sorted = false;
        while (!sorted){
            sorted = true;
            for(int i = 1; i < array.length; i++){
                if(array[i-1] > array[i]){
                    sorted = false;
                    int integer = array[i];
                    array[i] = array[i-1];
                    array[i-1] = integer;
                }
            }
        }
        return array;
    }


}
