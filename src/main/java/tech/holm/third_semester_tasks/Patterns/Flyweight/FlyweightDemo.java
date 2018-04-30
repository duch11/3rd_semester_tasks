package tech.holm.third_semester_tasks.Patterns.Flyweight;

public class FlyweightDemo {
    //Definer vores rows og cols
    public static final int ROWS = 6, COLS = 10;

    public static void main(String[] args) {

        //laver et factory til at lave gazillions med
        Factory theFactory = new Factory(ROWS);

        //matrix printing
        for (int i=0; i < ROWS; i++) {

            for (int j=0; j < COLS; j++){
                //getFlyweight(i) spørger efter en
                theFactory.getFlyweight(i).report(j);
            }

            System.out.println();

        }
    }
}

class Factory {
    private Gazillion[] gazillionPool;

    public Factory(int maxRows) {
        gazillionPool = new Gazillion[maxRows];
    }

    public Gazillion getFlyweight(int row) {
        if (gazillionPool[row] == null) {
            gazillionPool[row] = new Gazillion(row);
        }
        return gazillionPool[row];
    }
}

class Gazillion {
    private int row;

    public Gazillion(int row) {
        this.row = row;
        System.out.println("ctor: " + this.row);
    }

    void report(int col) {
        System.out.print(" " + row + col);
    }
}

class OldGazillion {
    private static int num = 0;
    private int row, col;

    public OldGazillion(int maxPerRow) {
        row = num / maxPerRow;
        col = num % maxPerRow;
        num++;
    }

    void report() {
        System.out.print(" " + row + col);
    }
}
